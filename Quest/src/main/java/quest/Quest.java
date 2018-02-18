package quest;

import java.util.ArrayList;

public class Quest extends Card {
    private ArrayList<Player> players;

    public Quest(String paramName, String paramImageFilename)
    {
        super(paramName, paramImageFilename);
        players = new ArrayList<>();
    }

    void goToNextStage()
    {

    }
}