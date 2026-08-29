package com.solver.round_solve.cube;

public enum Face {
    UP('U'),
    RIGHT('R'),
    FRONT('F'),
    DOWN('D'),
    LEFT('L'),
    BACK('B');

    private final char notation;

    Face(char notation) {
        this.notation = notation;
    }

    public char getNotation() {
        return notation;
    }
}
