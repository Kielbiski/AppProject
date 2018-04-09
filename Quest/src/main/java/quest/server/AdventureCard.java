package quest.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quest.client.App;

public abstract class AdventureCard extends Card
{

    private static final Logger logger = LogManager.getLogger(App.class);

    int battlePoints;
    int bids;
    int bonusBattlePoints;
    int bonusBids;
    AdventureCard(){}
    AdventureCard(String name, String imageFilename){

        super(name, imageFilename);
        battlePoints = 0;
        bids = 0;
        bonusBattlePoints = 0; 
        bonusBids = 0;
        logger.info("Successfully called : AdventureCard constructor");
    }

    public int getBattlePoints(){
        logger.info("Returning AdventureCard battle points");
        return battlePoints;
    }
    
    public int getBids(){
        logger.info("Returning AdventureCard bids");
        return bids;
    }
    
    public int getBonusBattlePoints() {
        logger.info("Returning AdventureCard bonus battle points");
        return bonusBattlePoints;
    }
    
    public int getBonusBids(){
        logger.info("Returning AdventureCard bonus bids");
        return bonusBids;
    }

    public void setBattlePoints(int paramBattlePoints) {
        logger.info("Setting AdventureCard battle points");
        battlePoints = paramBattlePoints;
    }
    public void setBonusBattlePoints(int bonusBattlePoints) {
        this.bonusBattlePoints = bonusBattlePoints;
    }

    public void setBonusBids(int bonusBids) {
        this.bonusBids = bonusBids;
    }

    public void setBids(int bids) {

        this.bids = bids;
    }
}

