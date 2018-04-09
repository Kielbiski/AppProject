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
    DataOutputStream dos;
    DataInputStream dis;
    DataOutputStream pdos;
    DataInputStream pdis;

    private int numberOfPlayers;
    private Model game;

    private String scenario;

    Server(int numberOfPlayers, String scenario, int portNumber) {
        this.numberOfPlayers = numberOfPlayers;
        this.scenario = scenario;
        Socket player;
        Socket playerBackgroundWorker;
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
            ServerSocket backgroundWorkerServerSocket = new ServerSocket(portNumber+1);

//            this is where players connect, we will have to make sure this properly sets their name and type(i.e human or a.i.)
//            the server will also need a gui to initiate the game with parameters (i.e which version of the game and number of players)

            while (true) {
                if(players.size() < numberOfPlayers) {
                    player = serverSocket.accept();
                    dis = new DataInputStream(player.getInputStream());
                    dos = new DataOutputStream(player.getOutputStream());
                    playerBackgroundWorker = backgroundWorkerServerSocket.accept();
                    pdis = new DataInputStream(playerBackgroundWorker.getInputStream());
                    pdos = new DataOutputStream(playerBackgroundWorker.getOutputStream());
                    String name = dis.readUTF();
                    PlayerConnection user = new PlayerConnection(dos, dis, pdos, pdis, name, game);
                    System.out.println("Connected : " + user.getName());
                    players.add(user);
                }
                else {
                    initialize();
                    break;
                }
            }
        } catch (IOException E) {
            E.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
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
        game.setCurrentPlayer(game.getPlayers().get(0));
        JSONObject json = new JSONObject();
        json.put("behaviour", "DEFAULT");
        players.get(0).writeToDataOutputStream(json.toJSONString());
    }

}
