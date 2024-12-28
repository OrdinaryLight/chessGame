package GameplayLogic;

import java.util.ArrayList;

import Constants.BoardConstants;
import GameplayLogic.Pieces.Bishop;
import GameplayLogic.Pieces.King;
import GameplayLogic.Pieces.Knight;
import GameplayLogic.Pieces.Pawn;
import GameplayLogic.Pieces.Piece;
import GameplayLogic.Pieces.Queen;
import GameplayLogic.Pieces.Rook;

public class Board {
    private Piece[][] board;

    public Board() {
        this.board = new Piece[BoardConstants.SIZE][BoardConstants.SIZE];
        initializeBoard();
    }

    // SIZE 8
    private void initializeBoard() {
        // Setup white pieces
        board[0][0] = new Rook(0, 0, true);
        board[0][1] = new Knight(0, 1, true);
        board[0][2] = new Bishop(0, 2, true);
        board[0][3] = new Queen(0, 3, true);
        board[0][4] = new King(0, 4, true);
        board[0][5] = new Bishop(0, 5, true);
        board[0][6] = new Knight(0, 6, true);
        board[0][7] = new Rook(0, 7, true);
        // for (int i = 0; i < BoardConstants.SIZE; i++) {
        // board[i][1] = new Pawn(1, i, true);
        // }

        // Setup black pieces
        board[7][0] = new Rook(7, 0, false);
        board[7][1] = new Knight(7, 1, false);
        board[7][2] = new Bishop(7, 2, false);
        board[7][3] = new Queen(7, 3, false);
        board[7][4] = new King(7, 4, false);
        board[7][5] = new Bishop(7, 5, false);
        board[7][6] = new Knight(7, 6, false);
        board[7][7] = new Rook(7, 7, false);
        for (int i = 0; i < BoardConstants.SIZE; i++) {
            board[6][i] = new Pawn(6, i, false);
        }
    }

    // Move a piece from one position to another
    public boolean movePiece(int startX, int startY, int endX, int endY) {
        if (!isWithinBoard(startX, startY) || !isWithinBoard(endX, endY)) {
            return false;
        }

        Piece piece = board[startX][startY];
        Piece targetPiece = board[endX][endY];

        if (piece == null || !piece.isValidMove(endX, endY)) {
            return false;
        }

        // Check if the target position is occupied by a piece of the same color
        if (piece != null && targetPiece != null && piece.isWhite() == targetPiece.isWhite()) {
            return false;
        }

        // Execute the move
        board[endX][endY] = piece;
        board[startX][startY] = null;
        piece.setX(endX);
        piece.setY(endY);

        return true;
    }

    // Sets a piece to a position and changes the pieces position
    public Piece setPiece(int x, int y, Piece piece) {
        Piece oldPiece = board[x][y];
        board[x][y] = piece;

        if (piece != null) {
            piece.setX(x);
            piece.setY(y);
        }

        return oldPiece;
    }

    public Piece getPiece(int x, int y) {
        return board[x][y];
    }

    // checks if a position is in bounds
    private boolean isWithinBoard(int x, int y) {
        return (x >= 0 && x < BoardConstants.SIZE) && (y >= 0 && y < BoardConstants.SIZE);
    }

    // gets all kings of a certain colour
    private ArrayList<Piece> getKings(boolean whiteKings) {
        ArrayList<Piece> kings = new ArrayList<Piece>();

        for (int i = 0; i < BoardConstants.SIZE; i++) {
            for (Piece p : board[i]) {
                if (p instanceof King && p.isWhite() == whiteKings) {
                    kings.add(p);
                }
            }
        }

        return kings;
    }

    // checks if a path from a piece to a place is unobstructed
    private boolean isPathClear(Piece piece, int newX, int newY) {
        int x = piece.getX();
        int y = piece.getY();
        int dx = Math.abs(newX - x);
        int dy = Math.abs(newY - y);
        int dirX = newX > x ? 1 : -1;
        int dirY = newX > y ? 1 : -1;

        if ((piece instanceof Bishop || piece instanceof Queen) && (dx == dy)) {
            for (int i = 1; i < Math.abs(dx); i++) {
                if (board[x + i * dirX][y + i * dirY] != null) {
                    return false; // Obstructed
                }
            }
        }

        if ((piece instanceof Rook || piece instanceof Queen) && (dx == 0 || dy == 0)) {
            if (dx == 0) {
                for (int i = Math.min(y, newY) + 1; i < Math.max(y, newY); i++) {
                    if (board[x][i] != null) {
                        return false; // Obstructed
                    }
                }
            } else {
                for (int i = Math.min(x, newX) + 1; i < Math.max(x, newX); i++) {
                    if (board[i][y] != null) {
                        return false; // Obstructed
                    }
                }
            }
        }

        return true;
    }

    // Print the board for testing
    public void printBoard() {
        for (int i = 0; i < BoardConstants.SIZE; i++) {
            for (int j = 0; j < BoardConstants.SIZE; j++) {
                Piece piece = board[i][j];
                if (piece == null) {
                    System.out.print(".");
                } else {
                    // prints the First Letter of the piece
                    System.out.print(piece.getClass().getSimpleName().charAt(0));
                }
            }
            System.out.println();
        }
    }

    public boolean isKingInCheck(boolean isWhiteTurn) {
        ArrayList<Piece> kings = getKings(isWhiteTurn);

        if (kings.isEmpty()) {
            return false; // No king found (should not happen)
        }

        for (int i = 0; i < kings.size(); i++) {
            King king = (King) kings.get(i);
            int kingX = king.getX();
            int kingY = king.getY();
            for (int x = 0; x < BoardConstants.SIZE; x++) {
                for (int y = 0; y < BoardConstants.SIZE; y++) {
                    Piece piece = board[x][y];
                    if (piece != null && piece.isWhite() != isWhiteTurn && piece.isValidMove(kingX, kingY)
                            && isPathClear(piece, kingX, kingY)) { // TODO they got vision through walls (check if
                                                                   // bishop, rook, queen)
                        return true;
                    }
                }
            }
        }

        return false; // King is not in check
    }

    public ArrayList<Move> generateAllLegalMoves(boolean isWhiteTurn) {
        ArrayList<Move> legalMoves = new ArrayList<>();
        for (int x = 0; x < BoardConstants.SIZE; x++) {
            for (int y = 0; y < BoardConstants.SIZE; y++) {
                Piece piece = board[x][y];
                if (piece != null && piece.isWhite() == isWhiteTurn) {
                    legalMoves = getMoves(piece, legalMoves);

                }
            }
        }
        return legalMoves;
    }

    // Method to get all possible moves for a piece
    public ArrayList<Move> getMoves(Piece piece, ArrayList<Move> moves) {
        if (piece instanceof Pawn) {
            return getPawnMoves((Pawn) piece, moves);
        } else if (piece instanceof Rook) {
            return getRookMoves((Rook) piece, moves);
        } else if (piece instanceof Bishop) {
            return getBishopMoves((Bishop) piece, moves);
        } else if (piece instanceof Queen) {
            return getQueenMoves((Queen) piece, moves);
        } else if (piece instanceof Knight) {
            return getKnightMoves((Knight) piece, moves);
        } else if (piece instanceof King) {
            return getKingMoves((King) piece, moves);
        }
        return moves;
    }

    private ArrayList<Move> getPawnMoves(Pawn pawn, ArrayList<Move> moves) {
        ArrayList<Move> potentialMoves = new ArrayList<Move>();
        int x = pawn.getX();
        int y = pawn.getY();
        int direction = pawn.isWhite() ? 1 : -1;

        // Forward move
        if (isWithinBoard(x, y + direction) && board[x][y + direction] == null) {
            potentialMoves.add(new Move(x, y, x, y + direction, pawn, null));
            // Double move from starting position
            if ((!pawn.isWhite() && y == BoardConstants.SIZE - 2) || (pawn.isWhite() && y == 1)) {
                if (isWithinBoard(x, y + 2 * direction) && board[x][y + 2 * direction] == null) {
                    potentialMoves.add(new Move(x, y, x, y + 2 * direction, pawn, null));
                }
            }
        }
        // Captures
        if (isWithinBoard(x + 1, y + direction) && board[x + 1][y + direction] != null
                && board[x + 1][y + direction].isWhite() != pawn.isWhite()) {
            potentialMoves.add(new Move(x, y, x + 1, y + direction, pawn, board[x + 1][y + direction]));
        }
        if (isWithinBoard(x - 1, y + direction) && board[x - 1][y + direction] != null
                && board[x - 1][y + direction].isWhite() != pawn.isWhite()) {
            potentialMoves.add(new Move(x, y, x - 1, y + direction, pawn, board[x - 1][y + direction]));
        }
        // En passant
        if (isEnPassantPossible(pawn, x + 1, y, direction)) {
            potentialMoves.add(new Move(x, y, x + 1, y + direction, pawn, board[x + 1][y]));
        }
        if (isEnPassantPossible(pawn, x - 1, y, direction)) {
            potentialMoves.add(new Move(x, y, x - 1, y + direction, pawn, board[x - 1][y]));
        }

        return addLegalMoves(potentialMoves, moves);
    }

    private boolean moveCreatesIllegalCheck(Move potentialMove) {
        int x = potentialMove.getStartX();
        int y = potentialMove.getStartY();
        int newX = potentialMove.getEndX();
        int newY = potentialMove.getEndY();
        Piece targetPiece = potentialMove.getCapturedPiece();
        boolean illegalCheck = true;

        if (movePiece(x, y, newX, newY)) {
            if (!isKingInCheck(potentialMove.getMovingPiece().isWhite())) {
                illegalCheck = false;
            }
            movePiece(newX, newY, x, y);
            setPiece(newX, newY, targetPiece);
        }

        return illegalCheck;
    }

    private ArrayList<Move> addLegalMoves(ArrayList<Move> potentialMoves, ArrayList<Move> moves) {
        for (Move move : potentialMoves)
            if (!moveCreatesIllegalCheck(move))
                moves.add(move);

        return moves;
    }

    private boolean isEnPassantPossible(Piece piece, int targetX, int targetY, int direction) {
        Piece adjacentPiece = board[targetX][targetY];
        return adjacentPiece instanceof Pawn && ((Pawn) adjacentPiece).isPassentable(piece)
                && isCapturable(piece, targetX, targetY + direction);
    }

    private boolean isCapturable(Piece initialPiece, int targetX, int targetY) {
        return isWithinBoard(targetX, targetY)
                && (board[targetX][targetY] == null || board[targetX][targetY].isWhite() != initialPiece.isWhite());
    }

    public boolean isSufficientMaterial() {
        ArrayList<Piece> whitePieces = new ArrayList<>();
        ArrayList<Piece> blackPieces = new ArrayList<>();

        for (int x = 0; x < BoardConstants.SIZE; x++) {
            for (int y = 0; y < BoardConstants.SIZE; y++) {
                Piece piece = board[x][y];
                if (piece != null) {
                    if (piece instanceof Pawn || piece instanceof Queen || piece instanceof Rook) {
                        return true;
                    }

                    if (!(piece instanceof King)) {
                        if (piece.isWhite()) {
                            whitePieces.add(piece);
                        } else {
                            blackPieces.add(piece);
                        }
                    }
                }
            }
        }

        // kings only
        if (whitePieces.size() == 0 && blackPieces.size() == 0) {
            return false;
        }

        // bishop/knight v king
        if ((whitePieces.size() == 1 && blackPieces.size() == 0)
                || (whitePieces.size() == 0 && blackPieces.size() == 1)) {
            return false;
        }

        // bishop v bishop (same colour)
        if ((whitePieces.size() == 1 && whitePieces.get(0) instanceof Bishop)
                && (blackPieces.size() == 1 && blackPieces.get(0) instanceof Bishop)
                && blackPieces.get(0).isWhite() == whitePieces.get(0).isWhite()) {
            return false;
        }

        // 2 white knights v king
        if (whitePieces.size() == 2 && blackPieces.size() == 0 && whitePieces.get(0) instanceof Knight
                && whitePieces.get(1) instanceof Knight) {
            return false;
        }

        // 2 blakc knights v king
        if (blackPieces.size() == 2 && whitePieces.size() == 0 && blackPieces.get(0) instanceof Knight
                && blackPieces.get(1) instanceof Knight) {
            return false;
        }

        return true;

    }

    private ArrayList<Move> getRookMoves(Rook rook, ArrayList<Move> moves) {
        ArrayList<Move> potentialMoves = new ArrayList<Move>();
        int x = rook.getX();
        int y = rook.getY();
        boolean upBlocked = false;
        boolean downBlocked = false;
        boolean leftBlocked = false;
        boolean rightBlocked = false;

        for (int i = 1; i < BoardConstants.SIZE; i++) {
            if (isCapturable(rook, x + i, y) && !rightBlocked) {
                if (board[x + i][y] != null) {
                    rightBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x + i, y, rook, board[x + i][y]));
            } else {
                rightBlocked = true;
            }

            if (isCapturable(rook, x - i, y) && !leftBlocked) {
                if (board[x - i][y] != null) {
                    leftBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x - i, y, rook, board[x - i][y]));
            } else {
                leftBlocked = true;
            }

            if (isCapturable(rook, x, y + i) && !upBlocked) {
                if (board[x][y + i] != null) {
                    upBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x, y + i, rook, board[x][y + i]));
            } else {
                upBlocked = true;
            }

            if (isCapturable(rook, x, y - i) && !downBlocked) {
                if (board[x][y - i] != null) {
                    downBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x, y - i, rook, board[x][y - i]));
            } else {
                downBlocked = true;
            }

        }

        return addLegalMoves(potentialMoves, moves);
    }

    private ArrayList<Move> getBishopMoves(Bishop bishop, ArrayList<Move> moves) {
        ArrayList<Move> potentialMoves = new ArrayList<Move>();
        int x = bishop.getX();
        int y = bishop.getY();
        boolean upBlocked = false;
        boolean downBlocked = false;
        boolean leftBlocked = false;
        boolean rightBlocked = false;

        for (int i = 1; i < BoardConstants.SIZE; i++) {
            if (isCapturable(bishop, x + i, y + i) && !rightBlocked) {
                if (board[x + i][y + i] != null) {
                    rightBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x + i, y + i, bishop, board[x + i][y + i]));
            } else {
                rightBlocked = true;
            }

            if (isCapturable(bishop, x - i, y - i) && !leftBlocked) {
                if (board[x - i][y - i] != null) {
                    leftBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x - i, y - i, bishop, board[x - i][y - i]));
            } else {
                leftBlocked = true;
            }

            if (isCapturable(bishop, x - i, y + i) && !upBlocked) {
                if (board[x - i][y + i] != null) {
                    upBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x - i, y + i, bishop, board[x - i][y + i]));
            } else {
                upBlocked = true;
            }

            if (isCapturable(bishop, x + i, y - i) && !downBlocked) {
                if (board[x + i][y - i] != null) {
                    downBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x + i, y - i, bishop, board[x + i][y - i]));
            } else {
                downBlocked = true;
            }

        }
        return addLegalMoves(potentialMoves, moves);
    }

    private ArrayList<Move> getQueenMoves(Queen queen, ArrayList<Move> moves) {
        ArrayList<Move> potentialMoves = new ArrayList<Move>();
        int x = queen.getX();
        int y = queen.getY();
        boolean upBlocked = false;
        boolean upRightBlocked = false;
        boolean downBlocked = false;
        boolean downRightBlocked = false;
        boolean leftBlocked = false;
        boolean upLeftBlocked = false;
        boolean rightBlocked = false;
        boolean downLeftBlocked = false;

        for (int i = 1; i < BoardConstants.SIZE; i++) {
            if (isCapturable(queen, x + i, y) && !rightBlocked) {
                if (board[x + i][y] != null) {
                    rightBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x + i, y, queen, board[x + i][y]));
            } else {
                rightBlocked = true;
            }

            if (isCapturable(queen, x - i, y) && !leftBlocked) {
                if (board[x - i][y] != null) {
                    leftBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x - i, y, queen, board[x - i][y]));
            } else {
                leftBlocked = true;
            }

            if (isCapturable(queen, x, y + i) && !upBlocked) {
                if (board[x][y + i] != null) {
                    upBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x, y + i, queen, board[x][y + i]));
            } else {
                upBlocked = true;
            }

            if (isCapturable(queen, x, y - i) && !downBlocked) {
                if (board[x][y - i] != null) {
                    downBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x, y - i, queen, board[x][y - i]));
            } else {
                downBlocked = true;
            }

            if (isCapturable(queen, x + i, y + i) && !upRightBlocked) {
                if (board[x + i][y + i] != null) {
                    upRightBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x + i, y + i, queen, board[x + i][y + i]));
            } else {
                upRightBlocked = true;
            }

            if (isCapturable(queen, x - i, y - i) && !downLeftBlocked) {
                if (board[x - i][y - i] != null) {
                    downLeftBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x - i, y - i, queen, board[x - i][y - i]));
            } else {
                downLeftBlocked = true;
            }

            if (isCapturable(queen, x - i, y + i) && !upLeftBlocked) {
                if (board[x - i][y + i] != null) {
                    upLeftBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x - i, y + i, queen, board[x - i][y + i]));
            } else {
                upLeftBlocked = true;
            }

            if (isCapturable(queen, x + i, y - i) && !downRightBlocked) {
                if (board[x + i][y - i] != null) {
                    downRightBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x + i, y - i, queen, board[x + i][y - i]));
            } else {
                downRightBlocked = true;
            }

        }
        return addLegalMoves(potentialMoves, moves);
    }

    private ArrayList<Move> getKnightMoves(Knight knight, ArrayList<Move> moves) {
        ArrayList<Move> potentialMoves = new ArrayList<Move>();
        int x = knight.getX();
        int y = knight.getY();
        int[][] knightMoves = { { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 }, { 1, 2 }, { 1, -2 }, { -1, 2 },
                { -1, -2 } };
        for (int[] move : knightMoves) {
            int newX = x + move[0];
            int newY = y + move[1];
            if (isCapturable(knight, newX, newY)) {
                potentialMoves.add(new Move(x, y, newX, newY, knight, board[newX][newY]));
            }
        }
        return addLegalMoves(potentialMoves, moves);
    }

    private ArrayList<Move> getKingMoves(King king, ArrayList<Move> moves) {
        ArrayList<Move> potentialMoves = new ArrayList<Move>();
        int x = king.getX();
        int y = king.getY();
        int[][] kingMoves = { { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, 1 }, { 0, -1 }, { -1, 1 }, { -1, 0 }, { -1, -1 } };
        for (int[] move : kingMoves) {
            int newX = x + move[0];
            int newY = y + move[1];
            if (isCapturable(king, newX, newY)) {
                potentialMoves.add(new Move(x, y, newX, newY, king, board[newX][newY]));
            }
        }
        return addLegalMoves(potentialMoves, moves);
    }

}
