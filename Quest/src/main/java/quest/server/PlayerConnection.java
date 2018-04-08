package quest.server;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Collectors;


//TO DO HERE: associate a player with this list
public class PlayerConnection {
    private String name;
    private String playerDataRequest;
    Player player;
    private DataInputStream dis;
    private DataOutputStream dos;

    public DataOutputStream getDos() {
        return dos;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public String getName(){
        return this.name;
    }

    PlayerConnection(DataOutputStream dos, DataInputStream dis, String name, Model game) {
        player = new Player(name);
        game.addPlayerToGame(player);
        this.name = name;
        this.dos = dos;
        this.dis = dis;
        ObjectMapper mapper = new ObjectMapper();
        new Thread(() -> {
            try {
               while(true) {
                    //this is where the player input will need to be parsed
                    try {
                        JSONObject clientRequest = new JSONObject(dis.readUTF());
                        if (clientRequest.getString("type").equals("set")) {
                            if (clientRequest.has("arguments")){
                                JSONArray arguments = new JSONArray(clientRequest.getJSONArray("arguments"));
                            } else {
                                String req = clientRequest.getString("methodName");
                                applyClientAction(game, req);
                            }
                        }
                        if (clientRequest.getString("type").equals("get")){
                            System.out.println(clientRequest);
                            playerDataRequest = mapper.writeValueAsString(getObjectForClient(game, clientRequest.getString("methodName")));
                            if(playerDataRequest != null){
                                System.out.println("Player request: " + playerDataRequest);
                                dos.writeUTF(playerDataRequest);
                                dos.flush();
                            }
                        }
                    } catch(Exception ignored){
                    }
        //                    List<PlayerConnection> entry = Server.players;
        ////                    this is where the players have their views updated
        //                    for (PlayerConnection cli : entry) {
        //                        DataOutputStream edos = cli.getDos();
        //                        edos.writeUTF(playerDataRequest + System.getProperty("line.separator"));
        //                    }
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
    private void applyClientAction(Model game, String methodName){
        try {
            Method method = game.getClass().getDeclaredMethod(methodName);
            method.invoke(game);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException E) {
            E.printStackTrace();
        }
    }

    public void applyClientAction(Model game, String methodName, Class<?>[] paramTypes, Object[] params){
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
