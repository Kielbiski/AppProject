package quest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;

//Quests

class BoarHunt extends Quest {
    private static final Logger logger = LogManager.getLogger(App.class);
    BoarHunt()
    {
        super("Boar Hunt", "Q_Boar_Hunt.jpg", 2, new Boar());
        logger.info("Successfully called : Boar Hunt constructor.");
    }
}

class DefendTheQueensHonor extends Quest {

    private static final Logger logger = LogManager.getLogger(App.class);

    DefendTheQueensHonor()
    {
        super("Defend The Queen's Honor", "Q_Defend_The_Queens_Honor.jpg", 4, new ArrayList<>());
        this.questFoes.add(new BlackKnight());
        this.questFoes.add(new Boar());
        this.questFoes.add(new Dragon());
        this.questFoes.add(new EvilKnight());
        this.questFoes.add(new Giant());
        this.questFoes.add(new GreenKnight());
        this.questFoes.add(new Mordred());
        this.questFoes.add(new RobberKnight());
        this.questFoes.add(new SaxonKnight());
        this.questFoes.add(new Saxons());
        this.questFoes.add(new Thieves());
        logger.info("Successfully called : Defend The Queen's Honor constructor.");
    }
}

class JourneyThroughTheEnchantedForest extends Quest {

    private static final Logger logger = LogManager.getLogger(App.class);

    JourneyThroughTheEnchantedForest()
    {
        super("Journey Through The Enchanted Forest", "Q_Journey_Through_The_Enchanted_Forest.jpg", 3, new EvilKnight());
        logger.info("Successfully called : Journey Through The Enchanted Forest constructor.");
    }
}

class RepelTheSaxonRaiders extends Quest {

    private static final Logger logger = LogManager.getLogger(App.class);

    RepelTheSaxonRaiders()
    {
        super("Repel The Saxon Raiders", "Q_Repel_The_Saxon_Raiders.jpg", 2, new ArrayList<>());
        this.questFoes.add(new SaxonKnight());
        this.questFoes.add(new Saxons());
        logger.info("Successfully called : Repel The Saxon Raiders constructor.");
    }
}

class RescueTheFairMaiden extends Quest {

    private static final Logger logger = LogManager.getLogger(App.class);

    RescueTheFairMaiden()
    {
        super("Rescue The Fair Maiden", "Q_Rescue_The_Fair_Maiden.jpg", 3, new BlackKnight());
        logger.info("Successfully called : Rescue The Fair Maiden constructor.");
    }
}

class SearchForTheHolyGrail extends Quest {

    private static final Logger logger = LogManager.getLogger(App.class);

    SearchForTheHolyGrail()
    {
        super("Search For The Holy Grail", "Q_Search_For_The_Holy_Grail.jpg", 5, new ArrayList<>());
        this.questFoes.add(new BlackKnight());
        this.questFoes.add(new Boar());
        this.questFoes.add(new Dragon());
        this.questFoes.add(new EvilKnight());
        this.questFoes.add(new Giant());
        this.questFoes.add(new GreenKnight());
        this.questFoes.add(new Mordred());
        this.questFoes.add(new RobberKnight());
        this.questFoes.add(new SaxonKnight());
        this.questFoes.add(new Saxons());
        this.questFoes.add(new Thieves());
        logger.info("Successfully called : Search For The Holy Grail constructor.");
    }
}

class SearchForTheQuestingBeast extends Quest {

    private static final Logger logger = LogManager.getLogger(App.class);

    SearchForTheQuestingBeast()
    {
        super("Search For The Questing Beast", "Q_Search_For_The_Questing_Beast.jpg", 4);
        logger.info("Successfully called : Search For The Questing Beast constructor.");
    }
}

class SlayTheDragon extends Quest {

    private static final Logger logger = LogManager.getLogger(App.class);

    SlayTheDragon()
    {
        super("Slay The Dragon", "Q_Slay_The_Dragon.jpg", 3, new Dragon());
        logger.info("Successfully called : Slay The Dragon constructor.");
    }
}

class TestOfTheGreenKnight extends Quest {

    private static final Logger logger = LogManager.getLogger(App.class);

    TestOfTheGreenKnight()
    {
        super("Test Of The Green Knight", "Q_Test_Of_The_Green_Knight.jpg", 4, new GreenKnight());
        logger.info("Successfully called : Test Of The Green Knight constructor.");
    }
}

class VanquishKingArthursEnemies extends Quest {

    private static final Logger logger = LogManager.getLogger(App.class);

    VanquishKingArthursEnemies()
    {
        super("Vanquish King Arthur's Enemies", "Q_Vanquish_King_Arthurs_Enemies.jpg", 3);
        logger.info("Successfully called : Vanquish King Arthur's Enemies constructor.");
    }
}

//Events

class ChivalrousDeed extends Event {

    private static final Logger logger = LogManager.getLogger(App.class);

    ChivalrousDeed()
    {
        super("Chivalrous Deed", "E_Chivalrous_Deed.jpg");
        logger.info("Successfully called : Chivalrous Deed constructor.");
    }

    @Override
    public void applyEvent(ArrayList<Player> playersToAffect, Player activePlayer, Stack<AdventureCard> deckOfAdventureCards) {
        playersToAffect.sort(Comparator.comparing(Player::getShields));
        Player lowestShields = playersToAffect.get(0);
        lowestShields.setShields(playersToAffect.get(0).getShields() + 3);
        playersToAffect.sort(Comparator.comparing(Player::getPlayerRank));
        Player lowestRank = playersToAffect.get(0);
        if (lowestRank != lowestShields) {
            lowestRank.setShields(playersToAffect.get(0).getShields() + 3);
        }
    }
}

class CourtCalledToCamelot extends Event {

    private static final Logger logger = LogManager.getLogger(App.class);

    CourtCalledToCamelot()
    {
        super("Court Called To Camelot", "E_Court_Called_To_Camelot.jpg");
        logger.info("Successfully called : Court Called To Camelot constructor.");
    }

    @Override
    public void applyEvent(ArrayList<Player> playersToAffect, Player activePlayer, Stack<AdventureCard> deckOfAdventureCards) {
        for (Player player : playersToAffect) {
            for (AdventureCard adventureCard : player.getCardsOnTable()) {
                if (adventureCard instanceof Ally) {
                    player.removeCardFromTable(adventureCard);
                }
            }
        }
    }
}

class KingsCallToArms extends Event {

    private static final Logger logger = LogManager.getLogger(App.class);

    KingsCallToArms(){
        super("King's Call To Arms", "E_Kings_Call_To_Arms.jpg");
        logger.info("Successfully called : King's Call To Arms constructor.");

    }

    @Override
    public void applyEvent(ArrayList<Player> playersToAffect, Player activePlayer, Stack<AdventureCard> deckOfAdventureCards) {
        System.out.println("////////////////////////////////////////////////////////////////////////////////");
        ///////////////////////////////////////////////////////////////////////////////////////////////////////

    }
}

class KingsRecognition extends Event {

    private static final Logger logger = LogManager.getLogger(App.class);

    KingsRecognition(){
        super("King's Recognition", "E_Kings_Recognition.jpg");
        logger.info("Successfully called : King's Recognition constructor.");

    }

    @Override
    public void applyEvent(ArrayList<Player> playersToAffect, Player activePlayer, Stack<AdventureCard> deckOfAdventureCards) {
        System.out.println("Boolean kingsRecognition set in Model.");
    }
}

class Plague extends Event {

    private static final Logger logger = LogManager.getLogger(App.class);

    Plague(){
        super("Plague", "E_Plague.jpg");
        logger.info("Successfully called : Plague constructor.");

    }

    @Override
    public void applyEvent(ArrayList<Player> playersToAffect, Player activePlayer, Stack<AdventureCard> deckOfAdventureCards) {
        if (activePlayer.getShields() < 2) {
            activePlayer.setShields(0);
        } else {
            activePlayer.setShields(playersToAffect.get(0).getShields() - 2);
        }
    }
}

class Pox extends Event {

    private static final Logger logger = LogManager.getLogger(App.class);

    Pox(){
        super("Pox", "E_Pox.jpg");
        logger.info("Successfully called : Pox constructor.");

    }

    @Override
    public void applyEvent(ArrayList<Player> playersToAffect, Player activePlayer, Stack<AdventureCard> deckOfAdventureCards) {
        for (Player player : playersToAffect) {
            if (player != activePlayer) {
                if (player.getShields() < 1) {
                    player.setShields(0);
                } else {
                    player.setShields(activePlayer.getShields() - 1);
                }
            }
        }
    }
}

class ProsperityThroughoutTheRealm extends Event {

    private static final Logger logger = LogManager.getLogger(App.class);

    ProsperityThroughoutTheRealm(){
        super("Prosperity Throughout The Realm", "E_Prosperity_Throughout_The_Realm.jpg");
        logger.info("Successfully called : Prosperity Throughout The Realm constructor.");

    }
    ///////////////////////////////////////////////
    ///////////////////////////////////////////////

    @Override
    public void applyEvent(ArrayList<Player> playersToAffect, Player activePlayer, Stack<AdventureCard> deckOfAdventureCards) {
        for (Player player : playersToAffect) {
            player.addCardToHand(deckOfAdventureCards.pop());
        }
    }

    ///////////////////////////////////////////////
    ///////////////////////////////////////////////
}

class QueensFavor extends Event {

    private static final Logger logger = LogManager.getLogger(App.class);

    QueensFavor(){
        super("Queen's Favor", "E_Queens_Favor.jpg");
        logger.info("Successfully called : Queen's Favor constructor.");

    }
    ///////////////////////////////////////////////
    ///////////////////////////////////////////////

    @Override
    public void applyEvent(ArrayList<Player> playersToAffect, Player activePlayer, Stack<AdventureCard> deckOfAdventureCards) {
        playersToAffect.sort(Comparator.comparing(Player::getShields));
        playersToAffect.get(0).addCardToHand(deckOfAdventureCards.pop());
        playersToAffect.get(0).addCardToHand(deckOfAdventureCards.pop());
    }

    ///////////////////////////////////////////////
    ///////////////////////////////////////////////

}

//Tournaments

class TournamentAtCamelot extends Tournament {

    private static final Logger logger = LogManager.getLogger(App.class);

    TournamentAtCamelot(){
        super("Tournament at Camelot", "TM_Camelot.jpg", new ArrayList<>());
        logger.info("Successfully called : Tournament at Camelot constructor.");
    }
}

class TournamentAtOrkney extends Tournament {

    private static final Logger logger = LogManager.getLogger(App.class);

    TournamentAtOrkney(){
        super("Tournament at Orkney", "TM_Orkney.jpg", new ArrayList<>());
        logger.info("Successfully called : Tournament at Orkney constructor.");
    }
}

class TournamentAtTintagel extends Tournament {

    private static final Logger logger = LogManager.getLogger(App.class);

    TournamentAtTintagel(){
        super("Tournament at Tintagel", "TM_Tintagel.jpg", new ArrayList<>());
        logger.info("Successfully called : Tournament at Tintagel constructor.");
    }
}

class TournamentAtYork extends Tournament {

    private static final Logger logger = LogManager.getLogger(App.class);

    TournamentAtYork(){
        super("Tournament at York", "TM_York.jpg", new ArrayList<>());
        logger.info("Successfully called : Tournament at York constructor.");
    }
}
