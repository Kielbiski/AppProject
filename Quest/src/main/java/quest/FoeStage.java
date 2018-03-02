package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class FoeStage extends QuestStage {

    private static final Logger logger = LogManager.getLogger(App.class);

    private int totalBattlePoints = 0;

    FoeStage(ArrayList<AdventureCard> sponsorCards, ArrayList<Player> participatingPlayers){
        super(participatingPlayers);
        logger.info("FoeStage: Calculating sponsor battle points");
        for(AdventureCard adventureCard : sponsorCards){
            totalBattlePoints += adventureCard.getBattlePoints();
        }
        logger.info("FoeStage: Sponsor has " + totalBattlePoints + " battle points");
        logger.info("Successfully called : FoeStage constructor");

    }

    ArrayList<Player> getWinningPlayers(){
        ArrayList<Player> winningPlayers = new ArrayList<>();
        logger.info("FoeStage: Going through participant battle points");
        for(Player player : this.getParticipatingPlayers()){
            int playerBattlePoints = 0;
            playerBattlePoints += (player.calculateBattlePoints() + player.calculateCardsBattlePoints(player.getCardsOnTable()));
            logger.info("FoeStage: "+ this.getParticipatingPlayers() +" has "+ playerBattlePoints + " battle points");
            if(playerBattlePoints > totalBattlePoints) {
                winningPlayers.add(player);
            }
        }
        logger.info("FoeStage: Returning list of winning player");
        return winningPlayers;
    }
}
