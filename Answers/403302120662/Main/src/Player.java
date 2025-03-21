import java.util.Scanner;

public class Player {
    private final String name;
    public Board board;
    public Board trackingGrid;

    public Player() {
        System.out.print("Enter the name of the player: ");
        Scanner scan = new Scanner(System.in);
        this.name = scan.nextLine() ;
        this.board = new Board();
        this.trackingGrid = new Board();
    }


    public String getName() {
        return name;
    }


}
