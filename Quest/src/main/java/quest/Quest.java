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
    }

<<<<<<< HEAD




}
=======
    void goToNextStage()
    {

    }
}
>>>>>>> 3ecf9c610e9f55977e8e8cb351bc7b56c47af7f0
