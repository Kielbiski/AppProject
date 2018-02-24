package quest;
import java.util.ArrayList;

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