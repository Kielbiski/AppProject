package quest;

public class Weapon extends AdventureCard
{
    Weapon(String paramName, String paramImageFilename, int paramBattlePoints)
    {
        super(paramName, paramImageFilename);
        battlePoints = paramBattlePoints;
    }
}
