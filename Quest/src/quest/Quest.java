package quest;

import java.util.ArrayList;

public class Quest extends Card {
    ArrayList<QuestStage> stages;

    public Quest()
    {
        stages = new ArrayList<>();
    }
}
