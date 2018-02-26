package quest;
import java.util.ArrayList;

//Quests

class BoarHunt extends Quest {
    BoarHunt() {
        super("Boar Hunt", "Q_Boar_Hunt.jpg", 2, new Boar());
    }
}

class DefendTheQueensHonor extends Quest {
    DefendTheQueensHonor() {
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
    }
}

class JourneyThroughTheEnchantedForest extends Quest {
    JourneyThroughTheEnchantedForest() {
        super("Journey Through The Enchanted Forest", "Q_Journey_Through_The_Enchanted_Forest.jpg", 3, new EvilKnight());
    }
}

class RepelTheSaxonRaiders extends Quest {
    RepelTheSaxonRaiders() {
        super("Repel The Saxon Raiders", "Q_Repel_The_Saxon_Raiders.jpg", 2, new ArrayList<>());
        this.questFoes.add(new SaxonKnight());
        this.questFoes.add(new Saxons());
    }
}

class RescueTheFairMaiden extends Quest {
    RescueTheFairMaiden() {
        super("Rescue The Fair Maiden", "Q_Rescue_The_Fair_Maiden.jpg", 3, new BlackKnight());
    }
}

class SearchForTheHolyGrail extends Quest {
    SearchForTheHolyGrail() {
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
    }
}

class SearchForTheQuestingBeast extends Quest {
    SearchForTheQuestingBeast() {
        super("Search For The Questing Beast", "Q_Search_For_The_Questing_Beast.jpg", 4);
    }
}

class SlayTheDragon extends Quest {
    SlayTheDragon() {
        super("Slay The Dragon", "Q_Slay_The_Dragon.jpg", 3, new Dragon());
    }
}

class TestOfTheGreenKnight extends Quest {
    TestOfTheGreenKnight() {
        super("Test Of The Green Knight", "Q_Test_Of_The_Green_Knight.jpg", 4, new GreenKnight());
    }
}

class VanquishKingArthursEnemies extends Quest {
    VanquishKingArthursEnemies() {
        super("Vanquish King Arthur's Enemies", "Q_Vanquish_King_Arthurs_Enemies.jpg", 3);
    }
}

//Events

class ChivalrousDeed extends Event {
    ChivalrousDeed(){
        super("Chivalrous Deed", "E_Chivalrous_Deed.jpg");
    }
}

class CourtCalledToCamelot extends Event {
    CourtCalledToCamelot(){
        super("Court Called To Camelot", "E_Court_Called_To_Camelot.jpg");
    }
}

class KingsCallToArms extends Event {
    KingsCallToArms(){
        super("King's Call To Arms", "E_Kings_Call_To_Arms.jpg");
    }
}

class KingsRecognition extends Event {
    KingsRecognition(){
        super("King's Recognition", "E_Kings_Recognition.jpg");
    }
}

class Plague extends Event {
    Plague(){
        super("Plague", "E_Plague.jpg");
    }
}

class Pox extends Event {
    Pox(){
        super("Pox", "E_Pox.jpg");
    }
}

class ProsperityThroughoutTheRealm extends Event {
    ProsperityThroughoutTheRealm(){
        super("Prosperity Throughout The Realm", "E_Prosperity_Throughout_The_Realm.jpg");
    }
}

class QueensFavor extends Event {
    QueensFavor(){
        super("Queen's Favor", "E_Queens_Favor.jpg");
    }
}

//Tournaments

class TournamentAtCamelot extends Tournament {
    TournamentAtCamelot(){
        super("Tournament at Camelot", "TM_Camelot.jpg", new ArrayList<>());
    }
}

class TournamentAtOrkney extends Tournament {
    TournamentAtOrkney(){
        super("Tournament at Orkney", "TM_Orkney.jpg", new ArrayList<>());
    }
}

class TournamentAtTintagel extends Tournament {
    TournamentAtTintagel(){
        super("Tournament at Tintagel", "TM_Tintagel.jpg", new ArrayList<>());
    }
}

class TournamentAtYork extends Tournament {
    TournamentAtYork(){
        super("Tournament at York", "TM_York.jpg", new ArrayList<>());
    }
}