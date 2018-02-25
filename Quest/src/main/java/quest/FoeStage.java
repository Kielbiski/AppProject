package quest;

import java.util.ArrayList;

public class FoeStage extends QuestStage {
    private int totalBattlePoints = 0;
    FoeStage(ArrayList<AdventureCard> sponsorCards, ArrayList<Player> participatingPlayers){
        super(participatingPlayers);
        for(AdventureCard adventureCard : sponsorCards){
            totalBattlePoints += adventureCard.getBattlePoints();
        }
    }

    ArrayList<Player> getWinningPlayers(){
        ArrayList<Player> winningPlayers = new ArrayList<>();
        for(Player player : this.getParticipatingPlayers()){
            int playerBattlePoints = 0;
            playerBattlePoints += (player.calculateBattlePoints() + player.calculateCardsBattlePoints(player.getCardsOnTable()));
            if(playerBattlePoints > totalBattlePoints) {
                winningPlayers.add(player);
            }
        }
        return winningPlayers;
    }
}
