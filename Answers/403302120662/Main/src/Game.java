import java.util.Random;
import java.util.Scanner;
public class Game {
    public static final int boardSize = setSizeOfBoard(new Scanner(System.in));


    private static int setSizeOfBoard(Scanner scan) {
        System.out.print("Enter the size of the board who you like: ");
        return scan.nextInt();
    }


    public boolean isReplay(Scanner scanner) {
        System.out.println("Would you like to play again? (Y/N)");
        return scanner.next().equalsIgnoreCase("y");
    }


    public void startGame() {
        System.out.println("Welcome to the Battle Ship Game!");
    }

    public boolean randomTurn(){
        Random rand = new Random();
        return rand.nextBoolean();
    }

    public static void separator() {
        System.out.println();
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 50; i++) {
                System.out.print("=");
            }
            System.out.println();
        }
    }

    public static int [] converterAddress( String address) {
        if (address.length() < 2) {
            System.out.println("Invalid input!");
            return null;
        }
        int row = address.charAt(0) - 'A';
        int col = Integer.parseInt(address.substring(1)) - 1;
        return new int[]{row,col};
    }

    public static void helper() {
        System.out.print(
                """
                        🚢 Ships:
                            •  🚢 Aircraft Carrier (size = 5) -> A
                            •  🛳 Battleship (size = 4) -> B
                            •  🚤 Submarine (size = 3) -> S
                            • ⛴ Patrol Boat (size =2) -> P
                        🌊 Water -> ~
                        🎯 Successful hit -> *
                        💔 Miss -> O \n""");
    }
}
    
