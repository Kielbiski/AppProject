package quest;

import java.util.ArrayList;

public class TestStage extends QuestStage {
    private AdventureCard sponsorTestCard;
    TestStage(AdventureCard sponsorTestCard, ArrayList<Player> participatingPlayers){
        super(participatingPlayers);
        this.sponsorTestCard = sponsorTestCard;
    }
    Player getTestWinner() {
        Player winningPlayer = null;
        int currentHighestBid = 0;
        for(Player player : this.getParticipatingPlayers()){
            if (player.getCurrentBid() > currentHighestBid) {
                currentHighestBid = player.getCurrentBid();
                winningPlayer = player;
            }
        }
        return winningPlayer;
    }
}
