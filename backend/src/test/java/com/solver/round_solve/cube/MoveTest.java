package com.solver.round_solve.cube;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveTest {

    @Test
    void moveShouldReturnItsStandardNotation() {
        assertEquals("R", Move.RIGHT.getNotation());
        assertEquals("U'", Move.UP_PRIME.getNotation());
        assertEquals("F2", Move.FRONT2.getNotation());
    }

}
