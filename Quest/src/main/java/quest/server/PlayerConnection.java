package quest.server;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.RecursiveToStringStyle;

import static org.apache.logging.log4j.util.LoaderUtil.loadClass;


//TO DO HERE: associate a player with this list
public class PlayerConnection {
    private String name;
    private String playerDataRequest;
    Player player;
    private DataInputStream dis;
    private DataOutputStream dos;
    private DataInputStream pdis;
    private DataOutputStream pdos;

    private DataOutputStream getDos() {
        return dos;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public String getName(){
        return this.name;
    }

    public void writeToDataOutputStream(String json){
        try {
            pdos.writeUTF(json);
            pdos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings({"InfiniteLoopStatement", "unchecked"})
    PlayerConnection(DataOutputStream dos, DataInputStream dis, DataOutputStream pdos, DataInputStream pdis, String name, Model game) {
        player = new Player(name);
        for(Player p : game.getPlayers()){
            if(player.getPlayerName().equals(p.getPlayerName())){
                player.setPlayerName(name + "-");
            }
        }
        game.addPlayerToGame(player);
        this.name = name;
        this.dos = dos;
        this.dis = dis;
        this.pdos = pdos;
        this.pdis = pdis;

        ObjectMapper mapper = new ObjectMapper();
        try {
            org.json.simple.JSONObject json = new org.json.simple.JSONObject();
            json.put("player", mapper.writeValueAsString(player));
            this.pdos.writeUTF(json.toJSONString());
            this.pdos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            try {
               while(true) {
                    //this is where the player input will need to be parsed
                    try {
                        JSONObject clientRequest = new JSONObject(dis.readUTF());
                        System.out.println("\n\n-----------------------\n\n");
                        if (clientRequest.getString("type").equals("set")) {
                            System.out.println("Method Name: "+ clientRequest.getString("methodName"));
                            if (clientRequest.has("arguments")){
                                if(clientRequest.getString("methodName").equals("syncPlayer")) {
                                    System.out.println("Syncing player " + name + (new ReflectionToStringBuilder(player, new RecursiveToStringStyle()).toString()));
                                    player.syncPlayer(getObjectWithKnownType(clientRequest.getJSONArray("arguments").getJSONObject(0), "0", new TypeReference<Player>(){}));
                                    System.out.println("After player "+  name+ (new ReflectionToStringBuilder(player, new RecursiveToStringStyle()).toString()));
                                    game.changed();
                                } else {
                                    //fix
                                    System.out.println("MASSSIVE FUCk");
                                    String[] argumentStrings = convertJSONToObjectList(clientRequest.getJSONArray("arguments"));
                                    System.out.println(name + " requested: " + clientRequest);
                                    Class<?>[] argumentTypes = convertJSONToClassList(clientRequest.getJSONArray("argumentTypes"));
                                    Object[] arguments = convertObjectList(argumentStrings, argumentTypes);
                                    System.out.println(Arrays.toString(arguments));
                                    applyClientAction(game, clientRequest.getString("methodName"), argumentTypes, arguments);
                                }
                            } else {
                                System.out.println(name + " requested: " + clientRequest);
                                applyClientAction(game, clientRequest.getString("methodName"));
                            }
                        }
                        else if (clientRequest.getString("type").equals("get")){
                            if(clientRequest.getString("methodName").equals("getSelf")){
                                Player self = game.getSpecificPlayer(player);
                                System.out.println("Self -> " +name + self);
                                System.out.println(name + " requested: " + clientRequest);
                                playerDataRequest = mapper.writeValueAsString(self);
                                System.out.println("RETURNED-> " +name+ playerDataRequest);
                            } else {
                                playerDataRequest = mapper.writeValueAsString(getObjectForClient(game, clientRequest.getString("methodName")));
                            }
                            if(playerDataRequest != null){
                                System.out.println(name + " requested: " + clientRequest);
                                System.out.println("Server responded with: " + playerDataRequest + System.getProperty("line.separator"));
                                dos.writeUTF(playerDataRequest);
                                dos.flush();
                            }
                        }
                        else if (clientRequest.getString("type").equals("getWithParams")){
                            System.out.println(clientRequest);
                            Class<?>[] argumentTypes = convertJSONToClassList(clientRequest.getJSONArray("argumentTypes"));
                            Object[] arguments = convertJSONToObjectList(clientRequest.getJSONArray("arguments"));
                            playerDataRequest = mapper.writeValueAsString(getObjectWithParamsForClient(game, clientRequest.getString("methodName"), argumentTypes, arguments));
                            if(playerDataRequest != null){
                                System.out.println(name + " requested: " + clientRequest);
                                System.out.println("Server responded with: " + playerDataRequest);
                                dos.writeUTF(playerDataRequest);
                                dos.flush();
                            }
                        }
                    } catch(Exception E){
                        E.printStackTrace();
                    }
                }
            } catch (Exception E) { //IOException
                E.printStackTrace();
                try {
                    this.dis.close();
                    this.dos.close();
                    Server.players = Server.players.stream()
                            .filter(e -> {
                                if(!(e == this)) {
                                    String exit_message = "{ \"name\" : \"" + "[ SERVER NOTICE ]" + "\", \"message\" : \"" + name + " Disconnected" + "\"}";
                                    System.out.println(exit_message);
                                    try {
                                        e.getDos().writeUTF(exit_message + System.getProperty("line.separator"));
                                        e.getDos().flush();
                                    } catch (IOException err) {
                                        err.printStackTrace();
                                    }
                                }
                                return !(e == this);
                            })
                            .collect(Collectors.toList());

                    System.out.println("[Current User : " + Server.players.size() + "]");

                } catch(IOException E2) {
                    E2.printStackTrace();
                }
            }
        }).start();
    }
    private String[] convertJSONToObjectList(JSONArray jsonArray){
        String[] objectList = new String[jsonArray.length()];
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        for(int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println("jsonObject" + jsonObject);
            objectList[i] = jsonObject.getJSONObject(String.valueOf(i)).toString();
//            System.out.println("jsonString" + jsonString);
//                try {
//                    objectList[i] = objectMapper.readValue(jsonString,Object.class).toString();
//                } catch (IOException e) {
//                    e.printStackTrace();
//            }
        }
        return objectList;
    }

    private Class<?>[] convertJSONToClassList(JSONArray jsonArray){
        Class<?>[] classList = new Class<?>[jsonArray.length()];
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        for(int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println("\n\n");
            System.out.println("jsonObject" + jsonObject);
            String jsonString = jsonObject.getString(String.valueOf(i));
            System.out.println("jsonString" + jsonString);
            try {
//                objectMapper.readValue(
//                classList[i] = Player.class;
                classList[i] = Class.forName(jsonString.substring(1,jsonString.length()-1));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        System.out.println("classlist: " + Arrays.toString(classList));
        return classList;
    }
    private static <T> T getObjectWithKnownType(final JSONObject jsonFromServer, final String index, TypeReference<T> objectClass){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            String test = jsonFromServer.getJSONObject(index).toString();
            return objectMapper.readValue(test, objectClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Object[] convertObjectList(String[] list, Class<?>[] classList){
        Object[] objectList = new Object[list.length];
        for(int i = 0; i < list.length; i++){
            objectList[i] = getObject(list[i], classList[i]);
        }
        System.out.println(Arrays.toString(objectList));
        return objectList;
    }

    private static Object getObject(final String jsonFromServer, Class<?> objectClass){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            return objectMapper.readValue(jsonFromServer, objectClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void applyClientAction(Model game, String methodName){
        try {
            Method method = game.getClass().getDeclaredMethod(methodName);
            method.invoke(game);
            game.changed();
//            System.out.println("Changed at: " + System.currentTimeMillis());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException E) {
            E.printStackTrace();
        }
    }
    private void applyClientAction(Model game, String methodName, Class<?>[] paramTypes, Object[] params){
        try {
            System.out.println("paramTypes: "+Arrays.toString(paramTypes));
            System.out.println("params: " +Arrays.toString(params));
            Method method = Model.class.getDeclaredMethod(methodName, paramTypes);
            method.invoke(game, params);
            game.changed();
            System.out.println("invoked: "+methodName);
//            System.out.println("Changed at: " + System.currentTimeMillis());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException E) {
            E.printStackTrace();
        }
    }

    private Object getObjectForClient(Model game, String methodName){
        try {
            Method method = game.getClass().getDeclaredMethod(methodName);
            return method.invoke(game);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException E) {
            E.printStackTrace();
            return null;
        }
    }

    private Object getObjectWithParamsForClient(Model game, String methodName, Class<?>[] paramTypes, Object[] params){
        try {
            Method method = game.getClass().getDeclaredMethod(methodName, paramTypes);
            return method.invoke(game, params);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException E) {
            E.printStackTrace();
            return null;
        }
    }

}
