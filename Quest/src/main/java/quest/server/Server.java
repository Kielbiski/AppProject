package quest.server;
import org.json.simple.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class Server {
    public static List<PlayerConnection> players;
    //public static List<Socket> playerBackgroundWorkers;
    private DataOutputStream dos;
    private DataInputStream dis;
    private DataOutputStream pdos;
    private DataInputStream pdis;
    private Socket player;
    private Socket playerBackgroundWorker;

    private int numberOfPlayers;
    private Model game;

    private String scenario;
    @SuppressWarnings("InfiniteLoopStatement")
    Server(int numberOfPlayers, String scenario, int portNumber) {
        this.numberOfPlayers = numberOfPlayers;
        this.scenario = scenario;
        game = new Model();
        System.out.println("________________________________________\n");
        System.out.println("Server running.");
        System.out.println("\tNumber of players: " + this.numberOfPlayers);
        System.out.println("\tScenario: " + this.scenario);
        System.out.println("\tPort number: " + portNumber);
        System.out.println("________________________________________");
        players = new ArrayList<>();

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            ServerSocket backgroundWorkerServerSocket = new ServerSocket(portNumber + 1);

//            this is where players connect, we will have to make sure this properly sets their name and type(i.e human or a.i.)
//            the server will also need a gui to initiate the game with parameters (i.e which version of the game and number of players)
            boolean flag = true;
            while (true) {
                while (flag) {
                    System.out.println("Waiting for all players to join.");
                    if (players.size() < numberOfPlayers) {
                        this.player = serverSocket.accept();
                        this.dis = new DataInputStream(player.getInputStream());
                        this.dos = new DataOutputStream(player.getOutputStream());
                        this.playerBackgroundWorker = backgroundWorkerServerSocket.accept();
                        this.pdis = new DataInputStream(playerBackgroundWorker.getInputStream());
                        this.pdos = new DataOutputStream(playerBackgroundWorker.getOutputStream());
                        String name = dis.readUTF();
                        PlayerConnection user = new PlayerConnection(dos, dis, pdos, pdis, name, game);
                        System.out.println("Connected : " + user.getName());
                        players.add(user);
                    } else {
                        initialize();
                        System.out.println("Initializing game.");
                        flag = false;
                    }
                }
            }
        } catch (IOException E) {
            E.printStackTrace();
        }
    }
    private void initialize() {
        Stack<AdventureCard> deckOfAdventureCards = game.getDeckOfAdventureCards();
        Collections.shuffle(deckOfAdventureCards);
        switch (this.scenario){
            case "Regular":
                break;
            case "Boar Hunt":
                for(StoryCard storyCard : game.getDeckOfStoryCards()){
                    if(storyCard instanceof BoarHunt){ //to preserve deck card ratios
                        game.removeFromStoryDeck(storyCard);
                        break;
                    }
                }
                game.addToStoryDeck(new BoarHunt());
                break;
            case "Test AI No Quest":
                for(StoryCard storyCard : game.getDeckOfStoryCards()){
                    if(storyCard instanceof TournamentAtOrkney){ //to preserve deck card ratios
                        game.removeFromStoryDeck(storyCard);
                        break;
                    }
                }
                game.addToStoryDeck(new ProsperityThroughoutTheRealm());
                game.addToStoryDeck(new TournamentAtOrkney());
                game.addToStoryDeck(new Pox());
                break;
            case "Strategy 1":
                for(StoryCard storyCard : game.getDeckOfStoryCards()){
                    if(storyCard instanceof TournamentAtOrkney){ //to preserve deck card ratios
                        game.removeFromStoryDeck(storyCard);
                        break;
                    }
                }
                game.addToStoryDeck(new TournamentAtOrkney());
                break;
            case "Strategy 2":
                for(StoryCard storyCard : game.getDeckOfStoryCards()){
                    if(storyCard instanceof SlayTheDragon){ //to preserve deck card ratios
                        game.removeFromStoryDeck(storyCard);
                        break;
                    }
                }
                game.addToStoryDeck(new SlayTheDragon());
                break;
        }
        game.dealCards(deckOfAdventureCards);
        game.setActivePlayer(game.getPlayers().get(0));
        sendBehaviour(players.get(0), "DEFAULT");
    }
    @SuppressWarnings("unchecked")
    private void sendBehaviour(PlayerConnection player, String behaviour){
        JSONObject json = new JSONObject();
        json.put("behaviour", behaviour);
        player.writeToDataOutputStream(json.toJSONString());
    }
}
