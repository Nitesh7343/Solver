package com.solver.round_solve.cube;

public enum Move {
    UP("U"),
    UP_PRIME("U'"),
    UP2("U2"),

    RIGHT("R"),
    RIGHT_PRIME("R'"),
    RIGHT2("R2"),

    FRONT("F"),
    FRONT_PRIME("F'"),
    FRONT2("F2"),

    DOWN("D"),
    DOWN_PRIME("D'"),
    DOWN2("D2"),

    LEFT("L"),
    LEFT_PRIME("L'"),
    LEFT2("L2"),

    BACK("B"),
    BACK_PRIME("B'"),
    BACK2("B2");

    private final String notation;

    Move(String notation) {
        this.notation = notation;
    }

    public String getNotation() {
        return notation;
    }

    public static Move fromNotation(String notation) {
        return switch (notation) {
            case "U" -> UP;
            case "U'" -> UP_PRIME;
            case "U2" -> UP2;

            case "R" -> RIGHT;
            case "R'" -> RIGHT_PRIME;
            case "R2" -> RIGHT2;

            case "F" -> FRONT;
            case "F'" -> FRONT_PRIME;
            case "F2" -> FRONT2;

            case "D" -> DOWN;
            case "D'" -> DOWN_PRIME;
            case "D2" -> DOWN2;

            case "L" -> LEFT;
            case "L'" -> LEFT_PRIME;
            case "L2" -> LEFT2;

            case "B" -> BACK;
            case "B'" -> BACK_PRIME;
            case "B2" -> BACK2;

            default -> throw new IllegalArgumentException("Unknown move: " + notation);
        };
    }
}
