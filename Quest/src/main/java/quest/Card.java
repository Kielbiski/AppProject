package quest;

public class Card {
    private String name;
    private String imageFilename;

    boolean faceDown;

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
    public boolean isFaceDown() {return faceDown;}

    public void setFaceDown(boolean pFaceDown) {faceDown = pFaceDown;}
}
