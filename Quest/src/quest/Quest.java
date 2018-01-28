package quest;

import java.util.ArrayList;

public class Quest extends Card {
    ArrayList<QuestStage> stages;

    public Quest(String paramName, String paramImageFilename)
    {
        super(paramName, paramImageFilename);
        stages = new ArrayList<>();
    }
}