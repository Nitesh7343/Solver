package com.solver.round_solve.solver;

import com.solver.round_solve.cube.CubeState;
import com.solver.round_solve.cube.Move;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KociembaSolverServiceTest {

    @Test
    void solverCubeShouldNeedNoMoves() {
        KociembaSolverService solverService = new KociembaSolverService();
        CubeState cube = new CubeState();

        String solution = solverService.solve(cube);
//        System.out.println(cube.toKociembaString());
//        System.out.println(solution);
        assertEquals("", solution);
    }

    @Test
    void solverSolutionShouldSolveAScrambledCube() {
        KociembaSolverService solverService = new KociembaSolverService();
        CubeState cube = new CubeState();

        cube.applyMove(Move.RIGHT);
        cube.applyMove(Move.UP);
        cube.applyMove(Move.FRONT);
        //System.out.println(cube.toKociembaString());
        assertEquals(false, cube.isSolved());

        String solution = solverService.solve(cube);
        for (String notation : solution.split("\\s+")) {
            cube.applyMove(Move.fromNotation(notation));
        }
        //System.out.println(cube.toKociembaString());
        assertEquals(true, cube.isSolved());
    }

    @Test
    void everyPairOfClockwiseMovesShouldProduceASolverValidCube() {
        KociembaSolverService solverService = new KociembaSolverService();

        Move[] moves = {
                Move.RIGHT,
                Move.UP,
                Move.FRONT,
                Move.DOWN,
                Move.LEFT,
                Move.BACK
        };

        for (Move firstMove : moves) {
            for (Move secondMove : moves) {
                CubeState cube = new CubeState();

                cube.applyMove(firstMove);
                cube.applyMove(secondMove);

                try {
                    solverService.solve(cube);
                } catch (IllegalArgumentException exception) {
                    throw new AssertionError(
                            firstMove + " then " + secondMove
                                    + " created an invalid cube: "
                                    + exception.getMessage()
                    );
                }
            }
        }
    }

    @Test
    void solverShouldSolveSeveralLongerScrambles() {
        assertSolverSolves(
                Move.RIGHT,
                Move.UP,
                Move.RIGHT_PRIME,
                Move.UP_PRIME,
                Move.FRONT2,
                Move.DOWN,
                Move.LEFT2,
                Move.BACK_PRIME,
                Move.UP2
        );

        assertSolverSolves(
                Move.FRONT,
                Move.RIGHT,
                Move.UP,
                Move.RIGHT_PRIME,
                Move.UP_PRIME,
                Move.FRONT_PRIME,
                Move.LEFT2,
                Move.DOWN,
                Move.BACK2,
                Move.RIGHT2
        );

        assertSolverSolves(
                Move.BACK,
                Move.LEFT,
                Move.DOWN2,
                Move.FRONT_PRIME,
                Move.RIGHT2,
                Move.UP,
                Move.LEFT_PRIME,
                Move.BACK2,
                Move.DOWN_PRIME,
                Move.FRONT2
        );
    }

    private void assertSolverSolves(Move... scramble) {
        CubeState cube = new CubeState();
        KociembaSolverService solverService = new KociembaSolverService();

        for (Move move : scramble) {
            cube.applyMove(move);
        }

        String solution = solverService.solve(cube);

        for (String moveNotation : solution.split("\\s+")) {
            if (!moveNotation.isBlank()) {
                cube.applyMove(Move.fromNotation(moveNotation));
            }
        }

        assertTrue(cube.isSolved(),
                () -> "Cube should be solved after solution: " + solution);
    }
}
