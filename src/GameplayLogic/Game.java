package GameplayLogic;

import java.util.ArrayList;

public class Game {
    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    private boolean isWhiteTurn;

    public Game() {
        this.board = new Board();
        this.whitePlayer = new Player(true, false); // Assuming white is human
        this.blackPlayer = new Player(false, true); // Assuming black is AI
        this.isWhiteTurn = true; // White starts the game
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.playGame();
    }

    // Start the game loop
    public void playGame() {
        while (!isGameOver()) {
            if (isWhiteTurn) {
                handlePlayerMove(whitePlayer);
            } else {
                handlePlayerMove(blackPlayer);
            }
            isWhiteTurn = !isWhiteTurn;
        }
        System.out.println("Game Over!");
    }

    private void handlePlayerMove(Player player) {
        if (player.isAI()) {
            makeAIMove(player);
        } else {
            makeHumanMove(player);
        }
    }

    // placeholder
    private void makeAIMove(Player ai) {

    }

    // placeholder
    private void makeHumanMove(Player player) {
        // Placeholder for human move input
        // This is where you can add code to get input from the user (e.g., through a
        // GUI or console)
        // For now, we'll just print a message
    }

    private boolean isGameOver() {
        boolean isCheckmate = false;
        boolean isStalemate = false;
        boolean insufficientMaterial = false; // Check if the current player's king is in check
        ArrayList<Move> legalMoves = board.generateAllLegalMoves(isWhiteTurn); // If there are no legal moves, determine
                                                                               // if it's
        // checkmate or stalemate

        if (legalMoves.isEmpty()) {
            if (board.isKingInCheck(isWhiteTurn)) {
                isCheckmate = true;
            } else {
                isStalemate = true;
            }
        } else if (!board.isSufficientMaterial()) {
            insufficientMaterial = true;
        }

        if (isCheckmate) {
            System.out.println((isWhiteTurn ? "White" : "Black") + " is in checkmate. Game over.");
            return true;
        }

        if (isStalemate) {
            System.out.println("Stalemate. Game over.");
            return true;
        }

        if (insufficientMaterial) {
            System.out.println("Insufficient material. Game over.");
            return true;
        }

        return false;
    }

}
