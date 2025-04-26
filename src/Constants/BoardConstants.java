package Constants;

public class BoardConstants {

    // piece values
    public static final int SIZE = 8;
    public static final int KING_VALUE = 10000;
    public static final int QUEEN_VALUE = 9;
    public static final int ROOK_VALUE = 5;
    public static final int BISHOP_VALUE = 3;
    public static final int KNIGHT_VALUE = 3;
    public static final int PAWN_VALUE = 1;

    // other
    public static final int CAPTURE_MULTIPLIER = 100;
    public static final int EXISTANCE_MULTIPLIER = 100;
    public static final int MAX_DEPTH = 3;

    // ai rules

    public static final boolean DO_CENTER_PAWNS = true; // 0
    public static final int DO_CENTER_PAWNS_IDX = 0;
    public static final boolean DO_ENPASSANTS = true; // 1
    public static final int DO_ENPASSANTS_IDX = 1;
    public static final boolean DO_CENTER_KNIGHTS = true; // 2
    public static final int DO_CENTER_KNIGHTS_IDX = 2;
    public static final boolean DO_PER_BISHOP_MOVE = true; // 3
    public static final int DO_PER_BISHOP_MOVE_IDX = 3;
    public static final boolean DO_FORWARD_PAWNS = true; // 4
    public static final int DO_FORWARD_PAWNS_IDX = 4;
    public static final boolean DO_CHECK_MULTIPLIER = true; // 5
    public static final int DO_CHECK_MULTIPLIER_IDX = 5;
    public static final boolean DO_PROMOTION_MULTIPLIER = true; // 6
    public static final int DO_PROMOTION_MULTIPLIER_IDX = 6;
    public static final boolean DO_MATERIAL_DIFFERENCE = true; // 7
    public static final int DO_MATERIAL_DIFFERENCE_IDX = 7;
    public static final boolean DO_WHITE_AI = true;
    public static final int DO_WHITE_AI_IDX = 8;
    public static final boolean DO_BLACK_AI = true;
    public static final int DO_BLACK_AI_IDX = 9;

    public static boolean[] aiRules = { DO_CENTER_PAWNS, DO_ENPASSANTS, DO_CENTER_KNIGHTS, DO_PER_BISHOP_MOVE,
            DO_FORWARD_PAWNS,
            DO_CHECK_MULTIPLIER, DO_PROMOTION_MULTIPLIER, DO_MATERIAL_DIFFERENCE, DO_WHITE_AI, DO_BLACK_AI };

    // ai rules values
    public static final int CENTER_PAWN_VALUE = 2;
    public static final int ENPASSANT_POINTS = 10000000;
    public static final int CENTER_KNIGHT_VALUE = 4;
    public static final int PER_BISHOP_MOVE_POINT = 4;
    public static final int FORWARD_PAWN_MULTIPLIER = 1;
    public static final int CHECK_MULTIPLIER = 2;

}
