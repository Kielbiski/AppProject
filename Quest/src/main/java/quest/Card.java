package quest;

public class Card {
    protected String name;
    protected String imageFilename;

    Card(String paramName, String paramImageFilename){
        name = paramName;
        imageFilename = paramImageFilename;
    }
    public String getName(){
        return name;
    }
    public String getImageFilename(){
        return imageFilename;
    }
}
