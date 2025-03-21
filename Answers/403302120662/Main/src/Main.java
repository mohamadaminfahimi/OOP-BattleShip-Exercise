public static void main(String[] args) {
    var scan = new Scanner(System.in);
    do {
        Game game = new Game();
        game.startGame();

        Player player1 = new Player();
        Player player2 = new Player();

        initialize(player1, player2 , scan);
        clearScreen();

        boolean isNumber1Turn = game.randomTurn();
        while (!isGameOver(player1, player2)) {
            playerAttack(player1, player2, isNumber1Turn , scan);
            isNumber1Turn = !isNumber1Turn;
        }
        whoIsWon(player1, player2);
    } while (new Game().isReplay(scan));
}


private static void whoIsWon(Player player1 , Player player2) {
    if (PlayerIsWin(player2)) {
        System.out.println("🎉 Player 1 wins! 🎉\n" +
        "💫 Congratulations, " + player1.getName() + " ! You are the champion of the Battleship game! 🚢🔥 💎\n" +
                "🏆 All enemy ships have been sunk. Well played! 🏅👏");
    }
    else
        System.out.println("🎉 Player 2 wins! 🎉\n" +
                "💫 Congratulations, " + player2.getName() + " ! You are the champion of the Battleship game! 🚢🔥 💎\n" +
                "🏆 All enemy ships have been sunk. Well played! 🏅👏");
}


private static boolean isGameOver(Player player1, Player player2) {
    return PlayerIsWin(player1) || PlayerIsWin(player2);
}

private static boolean isAllShipsDestroyed(Player player) {
    for (int row = 0; row < Game.boardSize; row++) {
        for (int col = 0; col < Game.boardSize; col++) {
            if (player.board.getCell(row, col) == 'A' ||
                    player.board.getCell(row, col) == 'B' ||
                    player.board.getCell(row, col) == 'S' ||
                    player.board.getCell(row, col) == 'P') {
                return false;
            }
        }
    }
    return true;
}


private static boolean PlayerIsWin(Player player) {
    return isAllShipsDestroyed(player);
}

private static void playerAttack(Player player1, Player player2, boolean isNumber1Turn, Scanner scanner) {
    Game.helper();
    Player attacker = isNumber1Turn ? player1 : player2;
    Player defender = isNumber1Turn ? player2 : player1;

    Game.separator();
    System.out.println(attacker.getName() + " It's your turn to attack... ⚔️");
    attacker.trackingGrid.printBoard();

    int[] rowAndCol = getValidAddressForAtack(scanner , defender);
    int row = rowAndCol[0];
    int col = rowAndCol[1];

    if (defender.board.getCell(row, col) != '~') {
        System.out.println("🔥 Hit! You got it!");
        attacker.trackingGrid.setCell(row, col, '*');
        defender.board.setCell(row, col, '*');
    } else {
        System.out.println("❌ Miss! The target is lost!");
        attacker.trackingGrid.setCell(row, col, 'O');
    }
}

private static int[] getValidAddressForAtack(Scanner scan , Player defender) {
    boolean correct = false;
    int row = 0;
    int col = 0;
    while (!correct) {
        System.out.print("Enter position to attack (e.g., A5): ");
        String input = scan.nextLine().trim().toUpperCase();
        int[] rowAndCol = Game.converterAddress(input);
        assert rowAndCol != null;
        if (!(addressIsCorrect(rowAndCol[0], rowAndCol[1] , defender))) {
            System.out.println("❌ Invalid input! ");
            System.out.println("🔄 Please enter again.");
        } else {
            row = rowAndCol[0];
            col = rowAndCol[1];
            correct = true;
        }
    }
    return new int[]{row , col};
}

public static boolean addressIsCorrect(int row, int col , Player defender){
    if (row < 0 || row >= Game.boardSize || col < 0 || col >= Game.boardSize) {
        return false;
    }
    return defender.board.getCell(row , col) == '~';
}

private static void initialize( Player player1, Player player2 , Scanner scan) {
    player1.board.initializeBoard(1 , scan);
    player2.board.initializeBoard(2 , scan);
}

private static void clearScreen() {
    for (int i = 0; i < 50; i++) {
        System.out.println("🌟");
    }
}