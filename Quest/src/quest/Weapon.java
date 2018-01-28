package quest;

public class Weapon extends Card {

    private int damage = 0;

    public Weapon(String paramName, String imageFilename, int paramDamage){
        damage = paramDamage;
        super(paramName, paramImageFilename);
    }

    public int getDamage(){
        return damage;
    }

}
