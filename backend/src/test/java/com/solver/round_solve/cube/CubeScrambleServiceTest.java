package com.solver.round_solve.cube;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CubeScrambleServiceTest {

    private final CubeScrambleService scrambleService =
            new CubeScrambleService();

    @Test
    void shouldCreateAValidTwentyMoveScramble() {
        ScrambleResult result = scrambleService.createScramble(20);

        assertEquals(20, result.moves().size());
        assertTrue(result.cube().hasCorrectColorCounts());
        assertTrue(result.cube().hasUniqueCenterColors());

        List<Move> moves = result.moves();

        for (int index = 1; index < moves.size(); index++) {
            char previousFace = moves.get(index - 1)
                    .getNotation()
                    .charAt(0);

            char currentFace = moves.get(index)
                    .getNotation()
                    .charAt(0);

            assertNotEquals(previousFace, currentFace);
        }
    }
}