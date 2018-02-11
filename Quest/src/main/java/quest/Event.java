package quest;

public class Event extends StoryCard
{
    CardEffect effect;
    public Event(String name, String imageFilename, CardEffect pEffect)
    {
        super(name, imageFilename);
        effect = pEffect;
    }

    @Override
    public void startStory(GameState state)
    {
        effect.execute(state);
    }
}
