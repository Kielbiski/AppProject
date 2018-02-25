package quest;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuestStage {
    private ArrayList<AdventureCard> sponsorCards;
    private ArrayList<Player> participatingPlayers;
    private int totalBattlePoints = 0;
    QuestStage(ArrayList<AdventureCard> sponsorCards, ArrayList<Player> participatingPlayers){
        this.sponsorCards = sponsorCards;
        this.participatingPlayers = participatingPlayers;
        for(AdventureCard adventureCard : sponsorCards){
            totalBattlePoints += adventureCard.getBattlePoints();
        }
    }

    private ArrayList<Player> getWinningPlayers(){
        ArrayList<Player> winningPlayers = new ArrayList<>();
        for(Player player : participatingPlayers){
            int playerBattlePoints = 0;
        }
    return null;
    }
}
