package quest.server;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogPane;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import static java.lang.System.exit;

public class Server {
    public static List<PlayerConnection> players;
    public static DataOutputStream dos;
    DataInputStream dis;
    private int numberOfPlayers;
    public Model game;

    private String scenario;

    Server(int numberOfPlayers, String scenario, int portNumber) {
        String name;
        Socket player;
        game = new Model();
        System.out.println("________________________________________\n");
        System.out.println("Server running.");
        System.out.println("\tNumber of players: " + numberOfPlayers);
        System.out.println("\tScenario: " + scenario);
        System.out.println("\tPort number: " + portNumber);
        System.out.println("________________________________________");
        players = new ArrayList<PlayerConnection>();

        try {
            ServerSocket servSock = new ServerSocket(portNumber);

//            this is where players connect, we will have to make sure this properly sets their name and type(i.e human or a.i.)
//            the server will also need a gui to initiate the game with parameters (i.e which version of the game and number of players)

            while (true) {
                if(players.size() < numberOfPlayers) {
                    player = servSock.accept();
                    dis = new DataInputStream(player.getInputStream());
                    dos = new DataOutputStream(player.getOutputStream());

                    name = dis.readUTF() ;
                    PlayerConnection user = new PlayerConnection(dos, dis, name, this.game);
                    System.out.println("Connected : " + user.getName());
                    System.out.println(game.getCurrentPlayer().getPlayerName());
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

    private void initialize() {
        game = new Model();
        Stack<AdventureCard> deckOfAdventureCards = game.getDeckOfAdventureCards();
        Collections.shuffle(deckOfAdventureCards);
        switch (scenario){
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
//        setPlayerNames(numberOfPlayersResult);
//        game.addChangeListener(this);
//        updateClients();
    }

}
