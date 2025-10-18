import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class tictactoe {

    private static final int BOARD_SIZE = 5;
    private static final int WIN_LENGTH = 3;
    private static final char EMPTY_CELL = ' ';

    private char[][] board;
    private char currentPlayer;
    private boolean gameOver;

    public tictactoe() {
        this.board = new char[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
        this.currentPlayer = 'X';
        this.gameOver = false;
    }

    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            Arrays.fill(board[i], EMPTY_CELL);
        }
    }

    private void displayBoard() {
        final String HORIZONTAL_LINE = "───";
        final String CROSS = "┼";
        final String VERTICAL_LINE = "│";
        final String T_UP = "┬";
        final String T_DOWN = "┴";
        final String TOP_LEFT = "┌";
        final String TOP_RIGHT = "┐";
        final String MIDDLE_LEFT = "├";
        final String MIDDLE_RIGHT = "┤";
        final String BOTTOM_LEFT = "└";
        final String BOTTOM_RIGHT = "┘";

        StringBuilder header = new StringBuilder(" ");
        StringBuilder topBorder = new StringBuilder(TOP_LEFT);
        StringBuilder middleSeparator = new StringBuilder(MIDDLE_LEFT);
        StringBuilder bottomBorder = new StringBuilder(BOTTOM_LEFT);

        for (int k = 0; k < BOARD_SIZE; k++) {
            header.append(" " + k + " ");
            if (k < BOARD_SIZE - 1) {
                header.append(" ");
            }

            topBorder.append(HORIZONTAL_LINE);
            middleSeparator.append(HORIZONTAL_LINE);
            bottomBorder.append(HORIZONTAL_LINE);

            if (k < BOARD_SIZE - 1) {
                topBorder.append(T_UP);
                middleSeparator.append(CROSS);
                bottomBorder.append(T_DOWN);
            }
        }
        topBorder.append(TOP_RIGHT);
        middleSeparator.append(MIDDLE_RIGHT);
        bottomBorder.append(BOTTOM_RIGHT);


        System.out.println(header.toString());

        System.out.println(topBorder.toString());

        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + VERTICAL_LINE);
            for (int j = 0; j < BOARD_SIZE; j++) {
                String cellContent = " " + board[i][j] + " "; 
                System.out.print(cellContent + VERTICAL_LINE);
            }
            System.out.println();

            if (i < BOARD_SIZE - 1) {
                System.out.println(middleSeparator.toString());
            }
        }

        System.out.println(bottomBorder.toString());
    }

    private void makeMove(Scanner scanner) {
        System.out.println("\nPlayer " + currentPlayer + "'s turn. Enter your move (row column, e.g., 0 4):");

        int row = -1, col = -1;
        boolean validInput = false;

        while (!validInput) {
            try {
                String line = scanner.nextLine().trim();
                String[] parts = line.split("\\s+");

                if (parts.length != 2) {
                    System.out.println("Invalid format. Please enter two numbers (row column), e.g., 0 4.");
                    continue;
                }

                row = Integer.parseInt(parts[0]);
                col = Integer.parseInt(parts[1]);

                if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
                    System.out.println("Input out of bounds. Row and column must be between 0 and " + (BOARD_SIZE - 1) + ".");
                }
                else if (board[row][col] != EMPTY_CELL) {
                    System.out.println("Cell is already occupied. Choose an empty cell.");
                }
                else {
                    validInput = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numbers only (row column), e.g., 0 4.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred. Please try again.");
            }
        }

        board[row][col] = currentPlayer;

        if (checkWin()) {
            displayBoard();
            System.out.println("\n🎉 Player " + currentPlayer + " wins! Congratulations! (3-in-a-row)");
            gameOver = true;
        } else if (checkDraw()) {
            displayBoard();
            System.out.println("\n🤝 It's a draw! Better luck next time.");
            gameOver = true;
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }

    private boolean checkLine(int startRow, int startCol, int deltaRow, int deltaCol) {
        for (int k = 0; k < WIN_LENGTH; k++) {
            int r = startRow + k * deltaRow;
            int c = startCol + k * deltaCol;
            
            if (board[r][c] != currentPlayer) {
                return false;
            }
        }
        return true;
    }

    private boolean checkWin() {
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c <= BOARD_SIZE - WIN_LENGTH; c++) {
                if (checkLine(r, c, 0, 1)) return true;
            }
        }

        for (int r = 0; r <= BOARD_SIZE - WIN_LENGTH; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if (checkLine(r, c, 1, 0)) return true;
            }
        }

        for (int r = 0; r <= BOARD_SIZE - WIN_LENGTH; r++) {
            for (int c = 0; c <= BOARD_SIZE - WIN_LENGTH; c++) {
                if (checkLine(r, c, 1, 1)) return true;
            }
        }

        for (int r = 0; r <= BOARD_SIZE - WIN_LENGTH; r++) {
            for (int c = WIN_LENGTH - 1; c < BOARD_SIZE; c++) { 
                if (checkLine(r, c, 1, -1)) return true;
            }
        }
        
        return false;
    }

    private boolean checkDraw() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    return false;
                }
            }
        }
        return true;
    }

    private void printInstructions() {
        System.out.println("===============================");
        System.out.println("      Welcome to 5x5 Tic-Tac-Toe!");
        System.out.println("===============================");
        System.out.println("\nInstructions:");
        System.out.println("The board is now 5x5. The win condition is still **3 in a row**.");
        System.out.println("Two players will take turns, one is 'X' and the other is 'O'.");
        System.out.println("To make a move, enter two numbers: the **row** then the **column**.");
        System.out.println("Rows and columns are numbered from **0 to 4**.");
        System.out.println("Example: Entering '4 2' places your symbol in the bottom-middle cell.");
        System.out.println("\nPlayer 'X' goes first. Let the game begin!\n");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        while (playAgain) {
            tictactoe game = new tictactoe();
            game.printInstructions();

            while (!game.gameOver) {
                game.displayBoard();
                game.makeMove(scanner);
            }

            System.out.print("\nDo you want to play again? (yes/no): ");
            
            if (scanner.hasNextLine()) {
                String response = scanner.nextLine().trim().toLowerCase();
                playAgain = response.startsWith("y");
            } else {
                playAgain = false;
            }
        }

        System.out.println("Thanks for playing! Goodbye. 👋");
        scanner.close();
    }
}
