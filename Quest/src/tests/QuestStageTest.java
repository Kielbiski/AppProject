package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class QuestStageTest {

    private static final Logger logger = LogManager.getLogger(App.class);

    private Player jay = new Player("Jay");
    private Player tom = new Player("Tom");
    private Player Jeremy = new Player("Jeremy");
    private Player Robert = new Player("Robert");

    private ArrayList<Player> participatingPlayers = new ArrayList<>();

//    @Test
//    public void getParticipatingPlayers()
//    {
//
//        participatingPlayers.add(jay);
//        participatingPlayers.add(tom);
//        participatingPlayers.add(Jeremy);
//        participatingPlayers.add(Robert);
//
//        QuestStage questStage = new QuestStage(participatingPlayers);
//        assertEquals(participatingPlayers, questStage.getParticipatingPlayers());
//
//        participatingPlayers.remove(Robert);
//
//        QuestStage questStage2 = new QuestStage(participatingPlayers);
//        assertEquals(participatingPlayers, questStage2.getParticipatingPlayers());
//
//
//    }
}