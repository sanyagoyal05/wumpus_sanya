public abstract class GameItem{
    private char displayChar;
    public GameItem(char displayChar){
        this.displayChar=displayChar;
    }
    public void display(){
        System.out.print(displayChar);
    }
}