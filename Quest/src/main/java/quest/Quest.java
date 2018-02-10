package quest;

import java.util.ArrayList;

public class Quest extends Card {
    ArrayList<QuestStage> stages;
    ArrayList<Player> players;
    QuestStage currentStage;

    public Quest(String paramName, String paramImageFilename)
    {
        super(paramName, paramImageFilename);
        stages = new ArrayList<>();
        players = new ArrayList<>();
        onCardPlayed = state -> currentStage = null;
    }

    void goToNextStage()
    {

    }
}