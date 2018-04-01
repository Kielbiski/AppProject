package quest.server;

//Weapons

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quest.client.App;

class BattleAx extends Weapon {

    private static final Logger logger = LogManager.getLogger(App.class);

    BattleAx(){

        super("Battle-ax", "W_Battle-ax.jpg", 15);
        logger.info("Successfully called : BattleAx constructor");

    }
}

class Dagger extends Weapon {

    private static final Logger logger = LogManager.getLogger(App.class);

    Dagger() {

        super("Dagger", "W_Dagger.jpg", 5);
        logger.info("Successfully called : Dagger constructor");

    }
}

class Excalibur extends Weapon {

    private static final Logger logger = LogManager.getLogger(App.class);

    Excalibur() {

        super("Excalibur", "W_Excalibur.jpg", 30);
        logger.info("Successfully called : Excalibur constructor");

    }
}

class Horse extends Weapon {

    private static final Logger logger = LogManager.getLogger(App.class);

    Horse() {

        super("Horse", "W_Horse.jpg", 10);
        logger.info("Successfully called : Horse constructor");

    }
}
class Lance extends Weapon {

    private static final Logger logger = LogManager.getLogger(App.class);

    Lance() {

        super("Lance", "W_Lance.jpg", 20);
        logger.info("Successfully called : Lance constructor");

    }
}

class Sword extends Weapon {

    private static final Logger logger = LogManager.getLogger(App.class);

    Sword() {

        super("Sword", "W_Sword.jpg", 10);
        logger.info("Successfully called : Sword constructor");

    }
}

//Foes

class BlackKnight extends Foe {

    private static final Logger logger = LogManager.getLogger(App.class);

    BlackKnight(){

        super("Black Knight", "F_Black_Knight.jpg", 25, 10);
        logger.info("Successfully called : BlackKnight constructor");

    }
}

class Boar extends Foe {

    private static final Logger logger = LogManager.getLogger(App.class);

    Boar(){

        super("Boar", "F_Boar.jpg", 5, 10);
        logger.info("Successfully called : Boar constructor");

    }
}

class Dragon extends Foe {

    private static final Logger logger = LogManager.getLogger(App.class);

    Dragon(){

        super("Dragon", "F_Dragon.jpg", 50, 20);
        logger.info("Successfully called : Dragon constructor");

    }
}

class EvilKnight extends Foe {

    private static final Logger logger = LogManager.getLogger(App.class);

    EvilKnight(){

        super("Evil Knight", "F_Evil_Knight.jpg", 20, 10);
        logger.info("Successfully called : EvilKnight constructor");

    }
}

class Giant extends Foe {

    private static final Logger logger = LogManager.getLogger(App.class);

    Giant(){

        super("Giant", "F_Giant.jpg", 40, 0);
        logger.info("Successfully called : Giant constructor");

    }
}

class GreenKnight extends Foe {

    private static final Logger logger = LogManager.getLogger(App.class);

    GreenKnight(){

        super("Green Knight", "F_Green_Knight.jpg", 25, 15);
        logger.info("Successfully called : GreenKnight constructor");

    }
}

class Mordred extends Foe {

    private static final Logger logger = LogManager.getLogger(App.class);

    Mordred(){

        super("Mordred", "F_Mordred.jpg", 30, 0);
        logger.info("Successfully called : Mordred constructor");

    }
}

class RobberKnight extends Foe {

    private static final Logger logger = LogManager.getLogger(App.class);

    RobberKnight(){

        super("Robber Knight", "F_Robber_Knight.jpg", 15, 0);
        logger.info("Successfully called : RobberKnight constructor");

    }
}

class SaxonKnight extends Foe {

    private static final Logger logger = LogManager.getLogger(App.class);

    SaxonKnight(){

        super("Saxon Knight", "F_Saxon_Knight.jpg", 15, 10);
        logger.info("Successfully called : SaxonKnight constructor");

    }
}

class Saxons extends Foe {

    private static final Logger logger = LogManager.getLogger(App.class);

    Saxons(){

        super("Saxons", "F_Saxons.jpg", 10, 10);
        logger.info("Successfully called : Saxons constructor");

    }
}

class Thieves extends Foe {

    private static final Logger logger = LogManager.getLogger(App.class);

    Thieves(){

        super("Thieves", "F_Thieves.jpg", 5, 0);
        logger.info("Successfully called : Thieves constructor");

    }
}

//Allies
class KingArthur extends Ally {
    KingArthur(){
        super("King Arthur", "A_King_Arthur.jpg", 10, 2, 0, 0, "");
    }
}

class KingPellinore extends Ally {
    KingPellinore(){
        super("King Pellinore", "A_King_Pellinore.jpg", 10, 0, 0, 4, "Search For The Questing Beast");
    }
}

class Merlin extends Ally {
    boolean wasUsed = false;

    public boolean isWasUsed() {
        return wasUsed;
    }

    public void setWasUsed(boolean wasUsed) {
        this.wasUsed = wasUsed;
    }

    Merlin(){
        super("Merlin", "A_Merlin.jpg", 0, 0, 0, 0, "");
    }

}

class QueenGuinevere extends Ally {
    QueenGuinevere(){
        super("Queen Guinevere", "A_Queen_Quinevere.jpg", 0, 3, 0, 0, "");
    }
}

class QueenIseult extends Ally {
    QueenIseult(){
        super("Queen Iseult", "A_Queen_Iseult.jpg", 0, 2, 0, 2, "");
    }
}

class SirGalahad extends Ally {
    SirGalahad(){
        super("Sir Galahad", "A_Sir_Galahad.jpg", 15, 0, 0, 0, "");
    }
}

class SirGawain extends Ally {
    SirGawain(){
        super("Sir Gawain", "A_Sir_Gawain.jpg", 10, 0, 10, 0, "Test Of The Green Knight");
    }
}

class SirLancelot extends Ally {
    SirLancelot(){
        super("Sir Lancelot", "A_Sir_Lancelot.jpg", 15, 0, 10, 0, "Defend The Queen's Honor");
    }
}

class SirPercival extends Ally {
    SirPercival(){
        super("Sir Percival", "A_Sir_Percival.jpg", 5, 0, 15, 0, "Search For The Holy Grail");
    }
}

class SirTristan extends Ally {
    SirTristan(){
        super("Sir Tristan", "A_Sir_Tristan.jpg", 10, 0, 10, 0, "Queen Iseult");
    }
}

//Tests

class TestOfMorganLeFey extends Test {
    TestOfMorganLeFey(){
        super("Test Of Morgan Le Fey", "T_Test_Of_Morgan_Le_Fey.jpg");
    }
}

class TestOfTemptation extends Test {
    TestOfTemptation(){
        super("Test Of Temptation", "T_Test_Of_Temptation.jpg");
    }
}

class TestOfTheQuestingBeast extends Test {
    TestOfTheQuestingBeast(){
        super("Test Of The Questing Beast", "T_Test_Of_The_Questing_Beast.jpg");
    }
}

class TestOfValor extends Test {
    TestOfValor(){
        super("Test Of Valor", "T_Test_Of_Valor.jpg");
    }
}