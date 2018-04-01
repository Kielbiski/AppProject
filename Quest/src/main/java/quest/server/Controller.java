package quest.server;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogPane;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import static java.lang.System.exit;

public class Controller {

    private int numberOfPlayers;

    private String scenario;
    public Controller() {

        System.out.println("Server");
        serverSettingsSplash();
        new Server(getNumberOfPlayers(), getScenario());
        exit(0);
}


    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    private void serverSettingsSplash(){
        List<String> choices = new ArrayList<>();
        choices.add("Regular");
        choices.add("Boar Hunt");
        choices.add("Test AI No Quest");
        choices.add("Strategy 1");
        choices.add("Strategy 2");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Regular", choices);
        dialog.setTitle("Scenario selection.");
        dialog.setHeaderText("Select Scenario");
        dialog.setContentText("Please select the scenario you would like:");
        Optional<String> result = dialog.showAndWait();
        // The Java 8 way to get the response value (with lambda expression).
        String scenario = "Regular";
        result.ifPresent(this::setScenario);
        int numberOfPlayersResult = askNumberOfPlayers();
        if(numberOfPlayersResult == 0){
            okAlert("Error starting game, not enough players!", "Error.");
            exit(0);
        }
        setNumberOfPlayers(numberOfPlayersResult);
    }
    private void okAlert(String contentText, String headerText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contentText, ButtonType.OK);
        DialogPane dialog = alert.getDialogPane();
        alert.setHeaderText(headerText);
        dialog.getStylesheets().add(getClass().getResource("/CSS/Alerts.css").toExternalForm());
        dialog.getStyleClass().add("alertDialogs");
        alert.showAndWait();
    }

    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    private int askNumberOfPlayers(){
        List<String> choices = new ArrayList<>();
        choices.add("2 Players");
        choices.add("3 Players");
        choices.add("4 Players");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("4 Players", choices);
        dialog.setTitle("Number of Players?");
        dialog.setHeaderText("How many players would you like?");
        dialog.setContentText("Please select number of players:");
        Optional<String> result = dialog.showAndWait();
        // The Java 8 way to get the response value (with lambda expression).
        if (result.isPresent()) {
            String number = result.get();
            switch (number) {
                case "2 Players":
                    return 2;
                case "3 Players":
                    return 3;
                case "4 Players":
                    return 4;
                default:
                    return 0;
            }
        }
        return 0;
    }

}



