import java.util.Random;
import java.util.Scanner;

public class Game{
    private GameItem[][] board = new GameItem[4][4];
    private int playerRow,playerCol;

    public Game(){
        setBoard();
    }
    // sets up the board with wumpus, pits, and gold in random loactions
    private void setBoard()
    {
        Random rand= new Random();
        // place wumpus
        placeItem(new Wumpus());
        // place pits(exactly 3)
        for (int i=0;i<3;i++){
            placeItem(new Pit());
        }
        //place gold(1 to 3 pieces)
        int goldCount=rand.nextInt(3)+1;
        for (int i=0;i<goldCount;i++){
            placeItem(new Gold());
        }
        // fill remaining cells with clearGround
        for (int i=0;i<4;i++)
        {
            for (int j=0;j<4;j++)
            {
                if (board[i][j]==null)
                {
                    board[i][j]=new ClearGround();
                }
            }
        }
        // place the player at a random clear ground loaction
        do { 
           playerRow = rand.nextInt(4);
           playerCol =rand.nextInt(4);
        }
        while (!(board[playerRow][playerCol] instanceof ClearGround));
    }
    // place a specific GameItem at a random position on the board
    private void placeItem(GameItem item)
    {
        Random rand=new Random();
        int row,col;

        do{
            row = rand.nextInt(4);
            col = rand.nextInt(4);
        }
        while (board[row][col] != null);
        board[row][col] = item;
    }
    //display the current state of the board 
    private void display()
    {
        for (int i=0 ; i<4;i++){
            for (int j=0;j<4;j++){
                if (i == playerRow && j == playerCol){
                    System.out.print('*');
                }
                else{
                    board[i][j].display();
                }
            }
            System.out.println();
        }
    }
     // describe what the player senses in adjacent cells
    private void senseNearby()
    {
        System.out.println("Sensing around...");
        for (int i=-1;i<=1;i++)
        {
            for (int j=-1;j<=1;j++){
                int newRow = (playerRow + i + 4)%4;
                int newCol = (playerCol + j + 4)%4;
                if (board[newRow][newCol] instanceof Pit){
                        System.out.println("You feel a breeze.");
                }
                else if (board[newRow][newCol] instanceof Gold){
                    System.out.println("You see a faint glitter.");
                }
                else if (board[newRow][newCol] instanceof Wumpus){
                    System.out.println("You smell something vile.");
                }
            }
        }
    }
    // Main menu for user input and game actions
    private void main()
    {
        System.out.println("=====Wumpus====");
        System.out.println("1. Move player left");
        System.out.println("2. Move player right");
        System.out.println("3. Move player up");
        System.out.println("4. Move player down");
        System.out.println("5. Quit");
    }
    // handles the player's movement and checks for game events
    private void movePlayer(int dRow, int dCol)
    {
        playerRow = (playerRow + dRow + 4) % 4;
        playerCol = (playerCol + dCol + 4) % 4;
        // Check if player hits any items 
        if (board[playerRow][playerCol] instanceof Pit){
            System.out.println("You fell into a pit. Game over!");
            System.exit(0);// End the game
        }
        else if (board[playerRow][playerCol] instanceof Wumpus){
            System.out.println("The Wumpus ate you. Game over!");
            System.exit(0); // End the game
        }
        else if (board[playerRow][playerCol] instanceof Gold){
            System.out.println("You found gold! Your score increases.");
            board[playerRow][playerCol] = new ClearGround();
        }
    }
    // Main game loop where the board is displayed, user input is taken, and actions processed
    public void rumGame()
    {
        Scanner sc = new Scanner(System.in);
        while(true)
        {
            display();
            senseNearby();
            menu();

            System.out.print("Your choice: ");
            int choice = sc.nextInt();

            switch(choice){
                case 1: // move left
                movePlayer(0,-1); 
                break;
                case 2:
                movePlayer(0,1);
                break;
                case 3:
                movePlayer(-1,0);
                break;
                case 4:
                movePlayer(1,0);
                break;
                case 5:
                System.out.println("You have quit the game.");
                System.exit(0);
                default:
                System.out.println("Invalid choice! Try again.");
            }
        }
    }

    public GameItem[][] getBoard() {
        return board;
    }
}