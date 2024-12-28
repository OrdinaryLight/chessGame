package GameplayLogic;

public class Player {
    private boolean isWhite;
    private boolean isAI;

    public Player(boolean isWhite, boolean isAI) {
        this.isWhite = isWhite;
        this.isAI = isAI;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public boolean isAI() {
        return isAI;
    }

    public String toString() {
        return (isWhite ? "White" : "Black ") + (isAI ? " AI" : " Human");
    }
}
