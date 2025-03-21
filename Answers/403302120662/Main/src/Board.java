import java.util.Random;
import java.util.Scanner;

public class Board {
    private static final int size = Game.boardSize;
    static char[][] grid = new char[size][size];

    public Board() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col] = '~';
            }
        }
    }

    public void initializeBoard(int playerNumber, Scanner scan) {
        System.out.print("Player " + playerNumber + ": Do you want automatic ship placement? (Y/N) ");
        boolean autoPlacement = scan.nextLine().trim().equalsIgnoreCase("y");

        Game.separator();
        Game.helper();

        if (autoPlacement) {
            initializeByBot(scan);
        } else {
            initializeByUser(scan);
        }
    }


    public void initializeByUser(Scanner scan) {
        String[] shipNames = {null, "Aircraft Carrier", "Battleship", "Submarine", "Patrol Boat"};
        int[] shipSize = {0, 2, 3, 4, 5};
        Game.separator();
        for (int i = 0; i < 4; i++) {
            System.out.print(
                    """
                            1 . 🚢 Aircraft Carrier(siz = 5)
                            2 . 🛳 Battleship(siz = 4)
                            3 . 🚤 Submarine(siz = 3)
                            4 . ⛴ Patrol Boat(siz = 2)
                            whats you want ? (Enter number) :\s""");
            int shipsNum = scan.nextInt();
            Game.separator();
            printBoard();
            System.out.print("Enter row and col for example A5 or a5 but 5A is wrong .  ");
            String input = scan.nextLine().trim().toUpperCase();
            while (input.length() > 2){
                System.out.println("Invalid row or col. Try again . ");
                input = scan.nextLine().trim().toUpperCase();
            }

            int[] rowAndCol = Game.converterAddress(input);

            assert rowAndCol != null;
            int row = rowAndCol[0];
            int col = rowAndCol[1];
            System.out.println("Ship direction h) = horizontal, v) = vertical:");
            boolean isHorizontal = scan.next().equalsIgnoreCase("h");
                if (!(addressIsCorrect(row, col, shipSize[shipsNum], isHorizontal))) {
                    i--;
                    System.out.println("Invalid row or col. Try again . ");
                    Game.separator();
                    continue;
                }
                placeShip(row, col, shipSize[shipsNum], isHorizontal);
                System.out.println(shipNames[shipsNum] + "placed successfully .");
        }

    }

    public void placeShip(int row, int col, int shipSize, boolean horizontal) {
        if (horizontal) {
            for (int i = 0; i < shipSize; i++) {
                initializeShipName(i, 0, row, col, shipSize);
            }
        } else {
            for (int j = 0; j < shipSize; j++) {
                initializeShipName(0, j, row, col, shipSize);
            }
        }
    }


    private boolean addressIsCorrect(int row, int col, int shipSize, boolean horizontal) {
        if (horizontal) {
            if (row + shipSize >= size || col > size || row < 0 || col < 0)
                return false;
            if (checkPlaceIsAvailable(row, col, shipSize, true))
                return true;
        }

        if (row >= size || col + shipSize >= size)
            return false;
        return checkPlaceIsAvailable(row, col, shipSize, false);
    }

    private static boolean checkPlaceIsAvailable(int row, int col, int shipSize, boolean horizontal) {
        if (horizontal) {
            for (int i = 0; i < shipSize; i++) {
                if (grid[row + i][col] != '~')
                    return false;
            }
        } else {
            for (int i = 0; i < shipSize; i++) {
                if (grid[row][col + i] != '~')
                    return false;
            }
        }
        return true;
    }

    public void printBoard() {
        for (int row = 0; row < (Game.boardSize) + 1; row++) {
            for (int col = 0; col < (Game.boardSize) + 1; col++) {
                if (row == col && row == 0)
                    System.out.print("  ");
                else if (row == 0)
                    System.out.print(col + " ");
                else if (col == 0)
                    System.out.print((char) ('A' + row - 1) + " ");
                else if (col > 9) {
                    System.out.print(grid[row - 1][col - 1]);
                    System.out.print(grid[row - 1][col - 1] + " ");
                } else
                    System.out.print((grid[row - 1][col - 1]) + " ");
            }
            System.out.println();
        }

    }

    public void setCell(int row, int col, char cell) {
        grid[row][col] = cell;
    }

    public char getCell(int row, int col) {
        return grid[row][col];
    }


    public void initializeByBot(Scanner scan) {
        for (int i = 0; i < 4; i++) {
            boolean isPlaces = false;
            while (!isPlaces) {
                Random rand = new Random();
                int shipSize = i + 2;
                boolean horizontal = rand.nextBoolean();
                int row = rand.nextInt(Game.boardSize);
                int col = rand.nextInt(Game.boardSize);
                if (addressIsCorrect(row, col, shipSize, horizontal)) {
                    placeShip(row, col, shipSize, horizontal);
                    isPlaces = true;
                }

            }
        }
        Game.separator();
        printBoard();
        System.out.println("you like this initialized by the bot ?(Y/N)");
        if (scan.next().equalsIgnoreCase("n")) {
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    grid[row][col] = '~';
                }
            }
            Game.separator();
            initializeByBot(scan);
        }
    }

    private void initializeShipName(int i, int j, int row, int col, int shipSize) {
        switch (shipSize) {
            case 2:
                grid[row + i][col + j] = 'P';
                break;
            case 3:
                grid[row + i][col + j] = 'S';
                break;
            case 4:
                grid[row + i][col + j] = 'B';
                break;
            case 5:
                grid[row + i][col + j] = 'A';
                break;
        }
    }
}

