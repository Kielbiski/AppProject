package quest;

public class Test extends AdventureCard
{
    private Test(String name, String imageFilename, int pBids)
    {
        super(name, imageFilename);
        bids = pBids;
    }

    public Test(String name, String imageFilename)
    {
        this(name, imageFilename, 3);
    }
}
