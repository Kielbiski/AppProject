package quest;

import java.util.ArrayList;

public abstract class doIParticipateInQuest {

    int lastStagePoints;

    public abstract boolean doIParticipateInQuest(ArrayList<AdventureCard> paramCard, int paramNumStage);

    public abstract ArrayList<AdventureCard> playStage (ArrayList<AdventureCard> paramCard);

    public abstract ArrayList<AdventureCard> lastStage (ArrayList<AdventureCard> paramCard);

    public ArrayList<AdventureCard>  foeList (ArrayList<AdventureCard> paramCard, int paramPoints)
    {

        ArrayList<AdventureCard> foeList= new ArrayList<>();
        for(AdventureCard card : paramCard)
        {
            if ((card instanceof Foe) && (card.getBattlePoints() < paramPoints))
            {
                foeList.add(card);
            }
        }

        return  foeList;
    }

}
