package quest.server;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import org.json.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


//TO DO HERE: associate a player with this list
public class PlayerConnection {
    private String name;
    private String playerAction;
    Player player;

    private DataOutputStream dos;

    public DataOutputStream getDos() {
        return dos;
    }

    public String getName(){
        return this.name;
    }

    PlayerConnection(DataOutputStream dos, DataInputStream dis, String name, Model game) {
        LocalDateTime now = LocalDateTime.now();
        this.name = name;
        this.dos = dos;
        game.setCurrentPlayer(new Player(this.name));
        ObjectMapper mapper = new ObjectMapper();
       new Thread(() -> {
            try {
               while(true) {
                    //this is where the player input will need to be parsed
                    try {
                        JSONObject clientRequest = new JSONObject(dis.readUTF());
                        if (clientRequest.getString("type").equals("set")) {
                            JSONArray arguments = new JSONArray(clientRequest.getJSONArray("arguments"));
                            playerAction = "i ain't got no type";
                        }
                        if (clientRequest.getString("type").equals("get")){
                            playerAction = mapper.writeValueAsString(getObjectForClient(game, clientRequest.getString("methodName")));
                        }
                    } catch(Exception E){
                            E.printStackTrace();
                        }
                    List<PlayerConnection> entry = Server.players;
                    //this is where the players have their views updated
                    for (PlayerConnection cli : entry) {
                        DataOutputStream edos = cli.getDos();
                        edos.writeUTF(playerAction);
                    }
                }
            } catch (IOException E) {
                try {
                    dis.close();
                    dos.close();
                    Server.players = Server.players.stream()
                            .filter(e -> {
                                if(!(e == this)) {
                                    String exit_message = "{ \"name\" : \"" + "[ SERVER NOTICE ]" + "\", \"message\" : \"" + name + " Disconnected" + "\"}";
                                    System.out.println(exit_message);
                                    try {
                                        e.getDos().writeUTF(exit_message);
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
    public static void applyClientAction(Model game, String className, String methodName, Object invokeObject, Class[] paramTypes, Object[] params){
        try {
            Method method = game.getClass().getDeclaredMethod(methodName, paramTypes);
            method.invoke(invokeObject, params);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException E) {
            E.printStackTrace();
        }
    }

    public void applyClientAction(Model game, String methodName, Class[] paramTypes, Object[] params){
        try {
            Method method = game.getClass().getDeclaredMethod(methodName, paramTypes);
            method.invoke(game, params);
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
}
