package VisualLogic;

import Constants.VisualConstants;
import GameplayLogic.Pieces.Bishop;
import GameplayLogic.Pieces.King;
import GameplayLogic.Pieces.Knight;
import GameplayLogic.Pieces.Pawn;
import GameplayLogic.Pieces.Piece;
import GameplayLogic.Pieces.Queen;
import GameplayLogic.Pieces.Rook;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class viPiece extends StackPane {
    private Piece piece;
    private int x;
    private int y;

    public viPiece(Piece piece, int x, int y) {
        this.piece = piece;
        this.x = x;
        this.y = y;

        ImageView image = new ImageView();

        image.setImage(getImage(piece));
        image.setFitWidth(VisualConstants.TILE_SIZE);
        image.setFitHeight(VisualConstants.TILE_SIZE);
        getChildren().add(image);
        relocatePiece(x, y);

    }

    public void relocatePiece(int x, int y) {
        relocate(VisualConstants.X_OFFSET + x * VisualConstants.TILE_SIZE,
                VisualConstants.Y_OFFSET + y * VisualConstants.TILE_SIZE);
    }

    private Image getImage(Piece piece) {
        if (piece == null) {
            return new Image("\\PieceImages\\Blank.png");

        }

        if (piece instanceof Pawn) {
            if (piece.isWhite()) {
                return new Image("\\PieceImages\\White_Pawn.png");
            } else {
                return new Image("\\PieceImages\\Black_Pawn.png");
            }
        } else if (piece instanceof Knight) {
            if (piece.isWhite()) {
                return new Image("\\PieceImages\\White_Knight.png");
            } else {
                return new Image("\\PieceImages\\Black_Knight.png");
            }
        } else if (piece instanceof Bishop) {
            if (piece.isWhite()) {
                return new Image("\\PieceImages\\White_Bishop.png");
            } else {
                return new Image("\\PieceImages\\Black_Bishop.png");
            }
        } else if (piece instanceof Rook) {
            if (piece.isWhite()) {
                return new Image("\\PieceImages\\White_Rook.png");
            } else {
                return new Image("\\PieceImages\\Black_Rook.png");
            }
        } else if (piece instanceof Queen) {
            if (piece.isWhite()) {
                return new Image("\\PieceImages\\White_Queen.png");
            } else {
                return new Image("\\PieceImages\\Black_Queen.png");
            }

        } else if (piece instanceof King) {
            if (piece.isWhite()) {
                return new Image("\\PieceImages\\White_King.png");
            } else {
                return new Image("\\PieceImages\\Black_King.png");
            }
        }

        return new Image("\\PieceImages\\White_King.png");
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

}
