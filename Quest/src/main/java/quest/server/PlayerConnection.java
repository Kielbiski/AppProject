package quest.server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


//TO DO HERE: associate a player with this list
public class PlayerConnection {
    String name;
    String message;
    Player player;

    private DataOutputStream dos;

    public DataOutputStream getDos() {
        return dos;
    }

    PlayerConnection(String name, DataOutputStream dos, DataInputStream dis) {
        this.name = name;
        this.dos = dos;

        new Thread(() -> {
            try {
                while(true) {
                    //this is where the player input will need to be parsed
                    message = dis.readUTF();
                    System.out.println(message);
                    List<PlayerConnection> entry = Server.players;
                    //this is where the players have their views updated
                    for (PlayerConnection cli : entry) {
                        DataOutputStream edos = cli.getDos();
                        edos.writeUTF(message);
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




}
