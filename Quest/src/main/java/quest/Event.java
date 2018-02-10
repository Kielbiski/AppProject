package quest;

public class Event extends Card
{
    public Event(String name, String imageFilename, CardEffect effect)
    {
        super(name, imageFilename);
        onCardPlayed = effect;
    }
}
