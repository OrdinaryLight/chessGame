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

//////////
/// Class is used to preform tasks on and be a chessBoard
/// *Used as such
/// ***Seperating some logic out may be helpful.
/// 
public class Board {
    private Piece[][] board; // the board
    private Piece whitePassant; // white pawn that can be enpassanted or null
    private Piece blackPassant; // black pawn that can be enpassanted or null

    public Board() {
        this.board = new Piece[BoardConstants.SIZE][BoardConstants.SIZE];
        initializeBoard();
        this.whitePassant = null;
        this.blackPassant = null;
    }

    /**
     * initializes a board of size 8
     * 
     */
    private void initializeBoard() {
        // Setup white pieces
        board[0][0] = new Rook(0, 0, true);
        board[0][1] = new Knight(1, 0, true);
        board[0][2] = new Bishop(2, 0, true);
        board[0][3] = new Queen(3, 0, true);
        board[0][4] = new King(4, 0, true);
        board[0][5] = new Bishop(5, 0, true);
        board[0][6] = new Knight(6, 0, true);
        board[0][7] = new Rook(7, 0, true);
        for (int i = 0; i < BoardConstants.SIZE; i++) {
            board[1][i] = new Pawn(i, 1, true);
        }

        // Setup black pieces
        board[7][0] = new Rook(0, 7, false);
        board[7][1] = new Knight(1, 7, false);
        board[7][2] = new Bishop(2, 7, false);
        board[7][3] = new Queen(3, 7, false);
        board[7][4] = new King(4, 7, false);
        board[7][5] = new Bishop(5, 7, false);
        board[7][6] = new Knight(6, 7, false);
        board[7][7] = new Rook(7, 7, false);
        for (int i = 0; i < BoardConstants.SIZE; i++) {
            board[6][i] = new Pawn(i, 6, false);
        }
    }

    // Move a piece from one position to another, but check if the move creates a
    // check
    public boolean doPlayerMove(int startX, int startY, int endX, int endY) {
        if (!isWithinBoard(startX, startY) || !isWithinBoard(endX, endY)) {
            return false;
        }

        Piece piece = getPiece(startX, startY);
        Piece targetPiece = getPiece(endX, endY);
        Move potentialMove = new Move(startX, startY, endX, endY, piece, targetPiece);

        // Check if the move could never be done
        if (piece == null || !piece.isValidMove(endX, endY) || !isPathClear(piece, endX, endY)) {
            return false;
        }

        // Check if the target position is occupied by a piece of the same color
        if (piece != null && targetPiece != null && piece.isWhite() == targetPiece.isWhite()) {
            return false;
        }

        // Check if the move creates a check for your king
        if (moveCreatesIllegalCheck(potentialMove)) {
            return false;
        }

        // this happens if the piece is moving via en passant (I should change this)
        if (piece instanceof Pawn && getPassant(piece.isWhite()) != null
                && getPassant(piece.isWhite()) == getPiece(endX, endY + (piece.isWhite() ? -1 : 1))) {
            getPiece(endX, endY + (piece.isWhite() ? -1 : 1)).setVisible(false);
            getPiece(endX, endY + (piece.isWhite() ? -1 : 1)).relocate(0, 0);
            getPiece(endX, endY + (piece.isWhite() ? -1 : 1)).resize(0, 0);
            getPiece(endX, endY + (piece.isWhite() ? -1 : 1)).disableProperty();
            setPiece(endX, endY + (piece.isWhite() ? -1 : 1), null);
            System.out.println("Passant detected"); // For Testing
        }

        // resets whitePassant after black moved
        if (piece.isWhite() && whitePassant != null) {
            whitePassant = null;
        }

        // resets BlackPassant after white moved
        if (!piece.isWhite() && blackPassant != null) {
            blackPassant = null;
        }

        // if a Pawn is double moving it can be passanted
        if (piece instanceof Pawn && ((startY == 1 && endY == 3)
                || (startY == BoardConstants.SIZE - 2 && endY == BoardConstants.SIZE - 4))) {
            if (piece.isWhite()) {
                whitePassant = piece;
            } else {
                blackPassant = piece;
            }
        }

        // Execute the move
        board[endY][endX] = piece;
        board[startY][startX] = null;
        piece.setX(endX);
        piece.setY(endY);

        return true;
    }

    /**
     * sets current position to null and moves the piece to another location
     * 
     * @param startX starting X of the movingPiece
     * @param startY starting Y of the movingPiece
     * @param endX   ending X of the movingPiece
     * @param endY   ending Y of the movingPiece
     * @return whether or not the move was successful
     */
    public boolean movePiece(int startX, int startY, int endX, int endY) {
        if (!isWithinBoard(startX, startY) || !isWithinBoard(endX, endY)) {
            return false;
        }

        Piece piece = getPiece(startX, startY);

        // Execute the move
        board[endY][endX] = piece;
        board[startY][startX] = null;

        piece.setX(endX);
        piece.setY(endY);

        return true;
    }

    /**
     * sets a Piece to a position
     * 
     * @param x     X the Piece will be in
     * @param y     X the Piece will be in
     * @param piece the Piece to set
     * @return whatever was in the position of the piece that was overwritten
     */
    public Piece setPiece(int x, int y, Piece piece) {
        Piece oldPiece = getPiece(x, y);
        board[y][x] = piece;

        if (piece != null) {
            piece.setX(x);
            piece.setY(y);
        }

        return oldPiece;
    }

    /**
     * obvious
     * 
     * @param x X to get
     * @param y Y to get
     * @return the Piece in the spot
     */
    public Piece getPiece(int x, int y) {
        return board[y][x];

    }

    /**
     * gets the Piece that can be enpassanted by the other color
     * 
     * @param isWhite whether or not the attacking Piece is white
     * @return the Piece that can be enpassanted by the other color or null if
     *         nothing
     */
    public Piece getPassant(boolean isWhite) {
        return isWhite ? blackPassant : whitePassant;
    }

    /**
     * checks if a position is in bounds
     * 
     * @param x the X to check
     * @param y the Y to check
     * @return whether or not the Piece is in bounds
     */
    public boolean isWithinBoard(int x, int y) {
        return (x >= 0 && x < BoardConstants.SIZE) && (y >= 0 && y < BoardConstants.SIZE);
    }

    /**
     * gets every king on the board that is a certain color
     * 
     * @param whiteKings whether or not the kings to get are white
     * @return an ArrayList of kings
     */
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

    /**
     * Checks if a path from a piece to a place is unobstructed
     * 
     * @param piece the movingPiece
     * @param newX  the X the Piece will move to
     * @param newY  the Y the Piece will move to
     * @return whether or not the path is clear
     */
    private boolean isPathClear(Piece piece, int newX, int newY) {
        int x = piece.getX();
        int y = piece.getY();
        int dx = Math.abs(newX - x);
        int dy = Math.abs(newY - y);
        int dirX = newX > x ? 1 : -1;
        int dirY = newY > y ? 1 : -1;

        if (piece instanceof Pawn && dy != 0 && dx == 0) {
            if (getPiece(x, y + dirY) != null) {
                return false;
            }

            if (dy == 2 && getPiece(x, y + 2 * dirY) != null) {
                return false;
            }

        }

        if ((piece instanceof Bishop || piece instanceof Queen) && (dx == dy)) {
            for (int i = 1; i < Math.abs(dx); i++) {
                if (getPiece(x + i * dirX, y + i * dirY) != null) {
                    return false;
                }
            }

        }

        if ((piece instanceof Rook || piece instanceof Queen) && (dx == 0 || dy == 0)) {
            for (int i = 1; i < dy; i++) {
                if (getPiece(x, y + dirY * i) != null) {
                    return false;
                }
            }
            for (int i = 1; i < dx; i++) {
                if (getPiece(x + dirX * i, y) != null) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if a king of a certain color is in check
     * 
     * @param isWhiteTurn whether or not the king were checking for is white
     * @return whether or not a king is in check
     */
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
                    Piece piece = getPiece(x, y);
                    if (piece != null && piece.isWhite() != isWhiteTurn && piece.isValidMove(kingX, kingY)
                            && isPathClear(piece, kingX, kingY)) {
                        return true;
                    }
                }
            }
        }

        return false; // King is not in check
    }

    /**
     * gets all legal moves for all Pieces in a certain color
     * 
     * @param isWhiteTurn whether or not the color to get the moves for is white
     * @return an ArrayList of all the legal moves
     */
    public ArrayList<Move> generateAllLegalMoves(boolean isWhiteTurn) {
        ArrayList<Move> legalMoves = new ArrayList<>();
        for (int x = 0; x < BoardConstants.SIZE; x++) {
            for (int y = 0; y < BoardConstants.SIZE; y++) {
                Piece piece = getPiece(x, y);
                if (piece != null && piece.isWhite() == isWhiteTurn) {
                    legalMoves = getMoves(piece, legalMoves);

                }
            }
        }
        return legalMoves;
    }

    /**
     * gets all moves of a certain Piece
     * 
     * @param piece the Piece to get the moves from
     * @param moves the ArrayList to add to
     * @return the initial ArrayList given (i've truly been coding in c too much and
     *         I won't change it)
     */
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

    /**
     * checks if the game is over (checkMate, staleMate, insufficient material)
     * 
     * @param isWhiteTurn who's turn it is
     * @return whether or not the game is over
     */
    public boolean isGameOver(boolean isWhiteTurn) {
        boolean isCheckmate = false;
        boolean isStalemate = false;
        boolean insufficientMaterial = false; // Check if the current player's king is in check
        ArrayList<Move> legalMoves = generateAllLegalMoves(isWhiteTurn); // If there are no legal moves, determine if
                                                                         // it's checkmate or stalemate

        if (legalMoves.isEmpty()) {
            if (isKingInCheck(isWhiteTurn)) {
                isCheckmate = true;
            } else {
                isStalemate = true;
            }
        } else if (!isSufficientMaterial()) {
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

    /**
     * checks if a move cause your king to be in check
     * 
     * @param potentialMove the move to check (uses movingPiece to get who's turn it
     *                      is)
     * @return whether or not a move cause a king to be in check
     */
    private boolean moveCreatesIllegalCheck(Move potentialMove) {
        int x = potentialMove.getStartX();
        int y = potentialMove.getStartY();
        int newX = potentialMove.getEndX();
        int newY = potentialMove.getEndY();
        Piece targetPiece = potentialMove.getCapturedPiece();
        Piece enPassantPiece = getPiece(newX, newY);
        boolean illegalCheck = true;

        if (targetPiece != null && targetPiece.getY() != newY && movePiece(x, y, newX, newY)) {
            setPiece(newX, targetPiece.getY(), null);

            if (!isKingInCheck(potentialMove.getMovingPiece().isWhite())) {
                illegalCheck = false;
            }

            setPiece(newX, targetPiece.getY(), targetPiece);
            setPiece(x, y, getPiece(newX, newY));
            setPiece(newX, newY, enPassantPiece);
        } else if (movePiece(x, y, newX, newY)) {
            if (!isKingInCheck(potentialMove.getMovingPiece().isWhite())) {
                illegalCheck = false;
            }

            setPiece(x, y, getPiece(newX, newY));
            setPiece(newX, newY, targetPiece);
        }

        return illegalCheck;
    }

    /**
     * adds all legal moves found from a ArrayList of potential moves to another
     * ArrayList
     * 
     * @param potentialMoves List of potential legal moves
     * @param moves          List of current legal moves
     * @return moves
     */
    private ArrayList<Move> addLegalMoves(ArrayList<Move> potentialMoves, ArrayList<Move> moves) {
        for (Move move : potentialMoves)
            if (!moveCreatesIllegalCheck(move))
                moves.add(move);

        return moves;
    }

    /**
     * checks if enpassant is valid here
     * 
     * @param piece     starting Piece
     * @param targetX   X of pawn
     * @param targetY   Y of pawn
     * @param direction direction your moving
     * @return whether or not enpassant is possible
     */
    private boolean isEnPassantPossible(Piece piece, int targetX, int targetY, int direction) {
        Piece adjacentPiece = getPiece(targetX, targetY);

        if (piece.isWhite() && blackPassant == adjacentPiece && blackPassant != null) {
            return isCapturable(piece, targetX, targetY + direction);
        } else if (!piece.isWhite() && whitePassant == adjacentPiece && whitePassant != null) {
            return isCapturable(piece, targetX, targetY + direction);
        }

        return false;
    }

    /**
     * checks if a spot can be captured
     * 
     * @param initialPiece Piece (only used for its color)
     * @param targetX      X to move to
     * @param targetY      Y to move to
     * @return whether or not the spot can be captured
     */
    private boolean isCapturable(Piece initialPiece, int targetX, int targetY) {
        return isWithinBoard(targetX, targetY) && (getPiece(targetX, targetY) == null
                || getPiece(targetX, targetY).isWhite() != initialPiece.isWhite());
    }

    /**
     * checks if there is sufficient material for a side to checkmate
     * 
     * @return whether or not a checkmate is possible in relation to material
     */
    public boolean isSufficientMaterial() {
        ArrayList<Piece> whitePieces = new ArrayList<>();
        ArrayList<Piece> blackPieces = new ArrayList<>();

        for (int x = 0; x < BoardConstants.SIZE; x++) {
            for (int y = 0; y < BoardConstants.SIZE; y++) {
                Piece piece = getPiece(x, y);
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

    // it would make the code more readable if I moved all methods below to the Move
    // class prolly

    /**
     * adds to given list of moves all legal moves of given Piece
     * 
     * @param rook  the Piece
     * @param moves ArrayList to add to
     * @return moves
     */
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
                if (getPiece(x + i, y) != null) {
                    rightBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x + i, y, rook, getPiece(x + i, y)));
            } else {
                rightBlocked = true;
            }

            if (isCapturable(rook, x - i, y) && !leftBlocked) {
                if (getPiece(x - i, y) != null) {
                    leftBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x - i, y, rook, getPiece(x - i, y)));
            } else {
                leftBlocked = true;
            }

            if (isCapturable(rook, x, y + i) && !upBlocked) {
                if (getPiece(x, y + i) != null) {
                    upBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x, y + i, rook, getPiece(x, y + i)));
            } else {
                upBlocked = true;
            }

            if (isCapturable(rook, x, y - i) && !downBlocked) {
                if (getPiece(x, y - i) != null) {
                    downBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x, y - i, rook, getPiece(x, y - i)));
            } else {
                downBlocked = true;
            }

        }

        return addLegalMoves(potentialMoves, moves);
    }

    /**
     * adds to given list of moves all legal moves of given Piece
     * 
     * @param bishop the Piece
     * @param moves  ArrayList to add to
     * @return moves
     */
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
                if (getPiece(x + i, y + i) != null) {
                    rightBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x + i, y + i, bishop, getPiece(x + i, y + i)));
            } else {
                rightBlocked = true;
            }

            if (isCapturable(bishop, x - i, y - i) && !leftBlocked) {
                if (getPiece(x - i, y - i) != null) {
                    leftBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x - i, y - i, bishop, getPiece(x - i, y - i)));
            } else {
                leftBlocked = true;
            }

            if (isCapturable(bishop, x - i, y + i) && !upBlocked) {
                if (getPiece(x - i, y + i) != null) {
                    upBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x - i, y + i, bishop, getPiece(x - i, y + i)));
            } else {
                upBlocked = true;
            }

            if (isCapturable(bishop, x + i, y - i) && !downBlocked) {
                if (getPiece(x + i, y - i) != null) {
                    downBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x + i, y - i, bishop, getPiece(x + i, y - i)));
            } else {
                downBlocked = true;
            }

        }
        return addLegalMoves(potentialMoves, moves);
    }

    /**
     * adds to given list of moves all legal moves of given Piece
     * 
     * @param queen the Piece
     * @param moves ArrayList to add to
     * @return moves
     */
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
                if (getPiece(x + i, y) != null) {
                    rightBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x + i, y, queen, getPiece(x + i, y)));
            } else {
                rightBlocked = true;
            }

            if (isCapturable(queen, x - i, y) && !leftBlocked) {
                if (getPiece(x - i, y) != null) {
                    leftBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x - i, y, queen, getPiece(x - i, y)));
            } else {
                leftBlocked = true;
            }

            if (isCapturable(queen, x, y + i) && !upBlocked) {
                if (getPiece(x, y + i) != null) {
                    upBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x, y + i, queen, getPiece(x, y + i)));
            } else {
                upBlocked = true;
            }

            if (isCapturable(queen, x, y - i) && !downBlocked) {
                if (getPiece(x, y - i) != null) {
                    downBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x, y - i, queen, getPiece(x, y - i)));
            } else {
                downBlocked = true;
            }

            if (isCapturable(queen, x + i, y + i) && !upRightBlocked) {
                if (getPiece(x + i, y + i) != null) {
                    upRightBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x + i, y + i, queen, getPiece(x + i, y + i)));
            } else {
                upRightBlocked = true;
            }

            if (isCapturable(queen, x - i, y - i) && !downLeftBlocked) {
                if (getPiece(x - i, y - i) != null) {
                    downLeftBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x - i, y - i, queen, getPiece(x - i, y - i)));
            } else {
                downLeftBlocked = true;
            }

            if (isCapturable(queen, x - i, y + i) && !upLeftBlocked) {
                if (getPiece(x - i, y + i) != null) {
                    upLeftBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x - i, y + i, queen, getPiece(x - i, y + i)));
            } else {
                upLeftBlocked = true;
            }

            if (isCapturable(queen, x + i, y - i) && !downRightBlocked) {
                if (getPiece(x + i, y - i) != null) {
                    downRightBlocked = true;
                }
                potentialMoves.add(new Move(x, y, x + i, y - i, queen, getPiece(x + i, y - i)));
            } else {
                downRightBlocked = true;
            }

        }
        return addLegalMoves(potentialMoves, moves);
    }

    /**
     * adds to given list of moves all legal moves of given Piece
     * 
     * @param knight the Piece
     * @param moves  ArrayList to add to
     * @return moves
     */
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
                potentialMoves.add(new Move(x, y, newX, newY, knight, getPiece(newX, newY)));
            }
        }
        return addLegalMoves(potentialMoves, moves);
    }

    /**
     * adds to given list of moves all legal moves of given Piece
     * 
     * @param king  the Piece
     * @param moves ArrayList to add to
     * @return moves
     */
    private ArrayList<Move> getKingMoves(King king, ArrayList<Move> moves) {
        ArrayList<Move> potentialMoves = new ArrayList<Move>();
        int x = king.getX();
        int y = king.getY();
        int[][] kingMoves = { { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, 1 }, { 0, -1 }, { -1, 1 }, { -1, 0 }, { -1, -1 } };
        for (int[] move : kingMoves) {
            int newX = x + move[0];
            int newY = y + move[1];
            if (isCapturable(king, newX, newY)) {
                potentialMoves.add(new Move(x, y, newX, newY, king, getPiece(newX, newY)));
            }
        }
        return addLegalMoves(potentialMoves, moves);
    }

    /**
     * adds to given list of moves all legal moves of given Piece
     * 
     * @param pawn  the Piece
     * @param moves ArrayList to add to
     * @return moves
     */
    private ArrayList<Move> getPawnMoves(Pawn pawn, ArrayList<Move> moves) {
        ArrayList<Move> potentialMoves = new ArrayList<Move>();
        int x = pawn.getX();
        int y = pawn.getY();
        int direction = pawn.isWhite() ? 1 : -1;

        // Forward move
        if (isWithinBoard(x, y + direction) && getPiece(x, y + direction) == null) {
            potentialMoves.add(new Move(x, y, x, y + direction, pawn, null));
            // Double move from starting position
            if ((!pawn.isWhite() && y == BoardConstants.SIZE - 2) || (pawn.isWhite() && y == 1)) {
                if (isWithinBoard(x, y + 2 * direction) && getPiece(x, y + 2 * direction) == null) {
                    potentialMoves.add(new Move(x, y, x, y + 2 * direction, pawn, null));
                }
            }
        }

        if (isWithinBoard(x + 1, y + direction) && getPiece(x + 1, y + direction) != null
                && getPiece(x + 1, y + direction).isWhite() != pawn.isWhite()) {
            potentialMoves.add(new Move(x, y, x + 1, y + direction, pawn, getPiece(x + 1, y + direction)));
        }
        if (isWithinBoard(x - 1, y + direction) && getPiece(x - 1, y + direction) != null
                && getPiece(x - 1, y + direction).isWhite() != pawn.isWhite()) {
            potentialMoves.add(new Move(x, y, x - 1, y + direction, pawn, getPiece(x - 1, y + direction)));
        }

        // En passant
        if (isWithinBoard(x + 1, y + direction) && isEnPassantPossible(pawn, x + 1,
                y, direction)) {
            potentialMoves.add(new Move(x, y, x + 1, y + direction, pawn, getPiece(x + 1,
                    y)));
        }
        if (isWithinBoard(x - 1, y + direction) && isEnPassantPossible(pawn, x - 1,
                y, direction)) {
            potentialMoves.add(new Move(x, y, x - 1, y + direction, pawn, getPiece(x - 1,
                    y)));
        }

        return addLegalMoves(potentialMoves, moves);
    }

}
