package quest;

//Weapons

class BattleAx extends Weapon {
    BattleAx(){
        super("Battle-ax", "W_Battle-ax.jpg", 15);
    }
}

class Dagger extends Weapon {
    Dagger() {
        super("Dagger", "W_Dagger.jpg", 5);
    }
}

class Excalibur extends Weapon {
    Excalibur() {
        super("Excalibur", "W_Excalibur.jpg", 30);
    }
}

class Horse extends Weapon {
    Horse() {
        super("Horse", "W_Horse.jpg", 10);
    }
}
class Lance extends Weapon {
    Lance() {
        super("Lance", "W_Lance.jpg", 20);
    }
}

class Sword extends Weapon {
    Sword() {
        super("Sword", "W_Sword.jpg", 10);
    }
}

//Foes

class BlackKnight extends Foe {
    BlackKnight(){
        super("Black Knight", "F_Black_Knight.jpg", 25, 10);
    }
}

class Boar extends Foe {
    Boar(){
        super("Boar", "F_Boar.jpg", 5, 15);
    }
}

class Dragon extends Foe {
    Dragon(){
        super("Dragon", "F_Dragon.jpg", 50, 20);
    }
}

class EvilKnight extends Foe {
    EvilKnight(){
        super("Evil Knight", "F_Evil_Knight.jpg", 20, 10);
    }
}

class Giant extends Foe {
    Giant(){
        super("Giant", "F_Giant.jpg", 40, 0);
    }
}

class GreenKnight extends Foe {
    GreenKnight(){
        super("Green Knight", "F_Green_Knight.jpg", 25, 15);
    }
}

class Mordred extends Foe {
    Mordred(){
        super("Mordred", "F_Mordred.jpg", 30, 0);
    }
}

class RobberKnight extends Foe {
    RobberKnight(){
        super("Robber Knight", "F_Robber_Knight.jpg", 15, 0);
    }
}

class SaxonKnight extends Foe {
    SaxonKnight(){
        super("Saxon Knight", "F_Saxon_Knight.jpg", 5, 10);
    }
}

class Saxons extends Foe {
    Saxons(){
        super("Saxons", "F_Saxons.jpg", 10, 10);
    }
}

class Thieves extends Foe {
    Thieves(){
        super("Thieves", "F_Thieves.jpg", 5, 0);
    }
}
