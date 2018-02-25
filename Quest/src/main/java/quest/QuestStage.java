package quest;

import java.util.ArrayList;

public class QuestStage {
    private ArrayList<Player> participatingPlayers;
    QuestStage(ArrayList<Player> participatingPlayers){
        this.participatingPlayers = participatingPlayers;
    }

    public ArrayList<Player> getParticipatingPlayers() {
        return participatingPlayers;
    }
}
