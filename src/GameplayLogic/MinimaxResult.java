package GameplayLogic;

public class MinimaxResult {
    private final int evaluation;
    private final Move move;

    public MinimaxResult(int evaluation, Move move) {
        this.evaluation = evaluation;
        this.move = move;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public Move getMove() {
        return move;
    }
}