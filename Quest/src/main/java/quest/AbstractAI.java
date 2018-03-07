package quest;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public abstract class AbstractAI extends Player{

    private static final Logger logger = LogManager.getLogger(App.class);

    protected String strategy;
    protected doIParticipateInTournamentAI TournamentAnswer ;
    protected nextBid nextBid ;
    protected doIParticipateInQuest quest;
    protected DoISponsorAQuest sponsorQuest;


    public void setDoIParticipateInTournamentAI(doIParticipateInTournamentAI paramTournamentAnswer)
    {
        logger.info(this.getPlayerName() + " is using " +this.strategy+" to determine if he wants to participate.");
        this.TournamentAnswer = paramTournamentAnswer;

    }


    public void setNextBid(nextBid paramNextBid)
    {
        logger.info(this.getPlayerName() + " is using " +this.strategy+" for his bids.");
        this.nextBid = paramNextBid;

    }

    public String getNextBid(nextBid paramNextBid)
    {
        logger.info(this.getPlayerName() + " is using " +this.strategy+" .");
        return this.strategy;

    }

    public boolean doIParticipateInTournament(ArrayList<Player> paramPlayerList, int paramShields)
    {

        logger.info(this.getPlayerName() + " is deciding on participating in the tournament, using strategy: "+this.strategy+" .");
        return TournamentAnswer.doIParticipateInTournament( paramPlayerList,  paramShields);

    }


    public boolean doISponsor(ArrayList<Player> paramPlayerList, ArrayList<AdventureCard> paramCard, int paramNumstage, int paramShields)
    {

        logger.info(this.getPlayerName() + " is deciding on participating in the sponsoring a quest, using strategy: "+this.strategy+" .");
        return sponsorQuest.doISponsor( paramPlayerList, paramCard,paramNumstage,  paramShields);

    }


    public ArrayList<AdventureCard> questFirstStage(ArrayList<AdventureCard> paramCard)
    {

        logger.info(this.getPlayerName() + " is returning the first stage setup cards, using strategy: "+this.strategy+" .");
        return sponsorQuest.firstStage(  paramCard);

    }


    public AdventureCard questMidStage(ArrayList<AdventureCard> paramCard)
    {

        logger.info(this.getPlayerName() + " is returning the mid stage setup cards, using strategy: "+this.strategy+" .");
        return sponsorQuest.midStage(  paramCard);

    }

    public ArrayList<AdventureCard>  questLastStage(ArrayList<AdventureCard> paramCard)
    {

        logger.info(this.getPlayerName() + " is returning the last stage setup cards, using strategy: "+this.strategy+" .");
        return sponsorQuest.lastStage(  paramCard);

    }

    public ArrayList<AdventureCard> playStage (ArrayList<AdventureCard> paraCard)
    {

        logger.info(this.getPlayerName() + " is playing a stage using strategy: "+this.strategy+" .");
        return this.quest.playStage(paraCard);

    }

    public ArrayList<AdventureCard> lastStage (ArrayList<AdventureCard> paraCard)
    {

        logger.info(this.getPlayerName() + " is playing a the last stage using strategy: "+this.strategy+" .");
        return this.quest.lastStage(paraCard);

    }

    public ArrayList<AdventureCard>  whatIPlay (ArrayList<AdventureCard> paramCard)
    {

        logger.info(this.getPlayerName() + " is return the following card using: "+this.strategy+" to play  .");
        return TournamentAnswer.whatIPlay( paramCard);

    }

    public abstract boolean DoISponsorAQuest();

    public  boolean doIParticipateInQuest(ArrayList<AdventureCard> paramCard, int numStage)
    {
        logger.info(this.getPlayerName() + " is determining if he wants to join a quest using: "+this.strategy+" to play");
        return this.quest.doIParticipateInQuest(paramCard, numStage);

    }

    public  ArrayList<AdventureCard> discardAfterWinningTest(ArrayList<AdventureCard>  paramCardList)
    {
        logger.info("The cards that "+this.getPlayerName()+" wants to bids." );
        return this.nextBid.discardAfterWinningTest(paramCardList) ;

    }

    public  int  nextBid(ArrayList<AdventureCard>  paramCardList)
    {
        logger.info(this.getPlayerName()+" is bidding :" );
        return this.nextBid.nextBid(paramCardList).size() ;

    }



}
