package quest;

public class Weapon extends Card {

    private int damage = 0;

    public Weapon(int paramDamage){
        damage = paramDamage;
    }

    public int getDamage(){
        return damage;
    }

}
