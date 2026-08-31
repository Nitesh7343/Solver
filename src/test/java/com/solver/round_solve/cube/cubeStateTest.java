package com.solver.round_solve.cube;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class cubeStateTest {

    @Test
    void cubeShouldHaveCorrectCenterColors() {
        CubeState cube = new CubeState();

        assertEquals(Color.WHITE,cube.getSticker(Face.UP,4));
        assertEquals(Color.RED,cube.getSticker(Face.RIGHT,4));
        assertEquals(Color.GREEN,cube.getSticker(Face.FRONT,4));
        assertEquals(Color.YELLOW,cube.getSticker(Face.DOWN,4));
        assertEquals(Color.ORANGE,cube.getSticker(Face.LEFT,4));
        assertEquals(Color.BLUE,cube.getSticker(Face.BACK,4));

        assertEquals(true, cube.isSolved());

    }

    @Test
    void cubeShouldNotBeSolvedAfterChangingASticker() {
        CubeState cube = new CubeState();

        cube.setSticker(Face.UP, 0, Color.RED);

        assertEquals(false, cube.isSolved());
    }

    @Test
    void rightClockwiseMoveShouldCycleAdjacentColumnsCorrectly() {
        CubeState cube = new CubeState();

        cube.rotateRightClockwise();

        // FRONT → UP
        assertEquals(Color.GREEN, cube.getSticker(Face.UP, 2));
        assertEquals(Color.GREEN, cube.getSticker(Face.UP, 5));
        assertEquals(Color.GREEN, cube.getSticker(Face.UP, 8));

        // UP → BACK
        assertEquals(Color.WHITE, cube.getSticker(Face.BACK, 6));
        assertEquals(Color.WHITE, cube.getSticker(Face.BACK, 3));
        assertEquals(Color.WHITE, cube.getSticker(Face.BACK, 0));

        // BACK → DOWN
        assertEquals(Color.BLUE, cube.getSticker(Face.DOWN, 2));
        assertEquals(Color.BLUE, cube.getSticker(Face.DOWN, 5));
        assertEquals(Color.BLUE, cube.getSticker(Face.DOWN, 8));

        // DOWN → FRONT
        assertEquals(Color.YELLOW, cube.getSticker(Face.FRONT, 2));
        assertEquals(Color.YELLOW, cube.getSticker(Face.FRONT, 5));
        assertEquals(Color.YELLOW, cube.getSticker(Face.FRONT, 8));
    }

    @Test
    void rightFaceShouldRotateClockwise() {
        CubeState cube = new CubeState();

        cube.setSticker(Face.RIGHT, 0, Color.WHITE);
        cube.setSticker(Face.RIGHT, 1, Color.YELLOW);
        cube.setSticker(Face.RIGHT, 2, Color.GREEN);
        cube.setSticker(Face.RIGHT, 3, Color.BLUE);
        cube.setSticker(Face.RIGHT, 5, Color.ORANGE);
        cube.setSticker(Face.RIGHT, 6, Color.ORANGE);
        cube.setSticker(Face.RIGHT, 7, Color.BLUE);
        cube.setSticker(Face.RIGHT, 8, Color.YELLOW);

        cube.rotateRightClockwise();

        assertEquals(Color.ORANGE, cube.getSticker(Face.RIGHT, 0));
        assertEquals(Color.ORANGE, cube.getSticker(Face.RIGHT, 7));
        assertEquals(Color.BLUE, cube.getSticker(Face.RIGHT, 1));
        assertEquals(Color.BLUE, cube.getSticker(Face.RIGHT, 3));
        assertEquals(Color.WHITE, cube.getSticker(Face.RIGHT, 2));
        assertEquals(Color.YELLOW, cube.getSticker(Face.RIGHT, 5));
        assertEquals(Color.YELLOW, cube.getSticker(Face.RIGHT, 6));
        assertEquals(Color.RED, cube.getSticker(Face.RIGHT, 4));
        assertEquals(Color.GREEN, cube.getSticker(Face.RIGHT, 8));
    }

    @Test
    void rightClockwiseThenCounterClockwiseShouldRestoreCube() {
        CubeState cube = new CubeState();

        cube.rotateRightClockwise();
        cube.rotateRightCounterClockwise();

        assertEquals(true, cube.isSolved());
    }

    @Test
    void fourRightClockwiseMovesShouldRestoreCube() {
        CubeState cube = new CubeState();

        for(int i = 0; i < 4;i++) {
            cube.rotateRightClockwise();
        }

        assertEquals(true, cube.isSolved());
    }

    @Test
    void upClockwiseMoveShouldCycleTopRowsCorrectly() {
        CubeState cube = new CubeState();

        cube.rotateUpClockwise();

        // RIGHT → FRONT
        assertEquals(Color.RED, cube.getSticker(Face.FRONT, 0));
        assertEquals(Color.RED, cube.getSticker(Face.FRONT, 1));
        assertEquals(Color.RED, cube.getSticker(Face.FRONT, 2));

        // BACK → RIGHT
        assertEquals(Color.BLUE, cube.getSticker(Face.RIGHT, 0));
        assertEquals(Color.BLUE, cube.getSticker(Face.RIGHT, 1));
        assertEquals(Color.BLUE, cube.getSticker(Face.RIGHT, 2));

        // LEFT → BACK
        assertEquals(Color.ORANGE, cube.getSticker(Face.BACK, 0));
        assertEquals(Color.ORANGE, cube.getSticker(Face.BACK, 1));
        assertEquals(Color.ORANGE, cube.getSticker(Face.BACK, 2));

        // FRONT → LEFT
        assertEquals(Color.GREEN, cube.getSticker(Face.LEFT, 0));
        assertEquals(Color.GREEN, cube.getSticker(Face.LEFT, 1));
        assertEquals(Color.GREEN, cube.getSticker(Face.LEFT, 2));
    }

    @Test
    void upClockwiseThenCounterClockwiseShouldRestoreCube() {
        CubeState cube = new CubeState();

        cube.rotateUpClockwise();
        cube.rotateUpCounterClockwise();

        assertEquals(true, cube.isSolved());
    }

    @Test
    void frontClockwiseMoveShouldCycleAdjacentStickersCorrectly() {
        CubeState cube = new CubeState();

        cube.rotateFrontClockwise();

        // UP bottom row → RIGHT left column
        assertEquals(Color.WHITE, cube.getSticker(Face.RIGHT, 0));
        assertEquals(Color.WHITE, cube.getSticker(Face.RIGHT, 3));
        assertEquals(Color.WHITE, cube.getSticker(Face.RIGHT, 6));

        // RIGHT left column → DOWN top row
        assertEquals(Color.RED, cube.getSticker(Face.DOWN, 0));
        assertEquals(Color.RED, cube.getSticker(Face.DOWN, 1));
        assertEquals(Color.RED, cube.getSticker(Face.DOWN, 2));

        // DOWN top row → LEFT right column
        assertEquals(Color.YELLOW, cube.getSticker(Face.LEFT, 2));
        assertEquals(Color.YELLOW, cube.getSticker(Face.LEFT, 5));
        assertEquals(Color.YELLOW, cube.getSticker(Face.LEFT, 8));

        // LEFT right column → UP bottom row
        assertEquals(Color.ORANGE, cube.getSticker(Face.UP, 6));
        assertEquals(Color.ORANGE, cube.getSticker(Face.UP, 7));
        assertEquals(Color.ORANGE, cube.getSticker(Face.UP, 8));
    }

    @Test
    void frontClockwiseThenCounterClockwiseShouldRestoreCube() {
        CubeState cube = new CubeState();

        cube.rotateFrontClockwise();
        cube.rotateFrontCounterClockwise();

        assertEquals(true, cube.isSolved());
    }

    @Test
    void leftClockwiseMoveShouldCycleAdjacentColumnsCorrectly() {
        CubeState cube = new CubeState();

        cube.rotateLeftClockwise();

        // UP left column → FRONT left column
        assertEquals(Color.WHITE, cube.getSticker(Face.FRONT, 0));
        assertEquals(Color.WHITE, cube.getSticker(Face.FRONT, 3));
        assertEquals(Color.WHITE, cube.getSticker(Face.FRONT, 6));

        // FRONT left column → DOWN left column
        assertEquals(Color.GREEN, cube.getSticker(Face.DOWN, 0));
        assertEquals(Color.GREEN, cube.getSticker(Face.DOWN, 3));
        assertEquals(Color.GREEN, cube.getSticker(Face.DOWN, 6));

        // DOWN left column → BACK right column
        assertEquals(Color.YELLOW, cube.getSticker(Face.BACK, 2));
        assertEquals(Color.YELLOW, cube.getSticker(Face.BACK, 5));
        assertEquals(Color.YELLOW, cube.getSticker(Face.BACK, 8));

        // BACK right column → UP left column
        assertEquals(Color.BLUE, cube.getSticker(Face.UP, 0));
        assertEquals(Color.BLUE, cube.getSticker(Face.UP, 3));
        assertEquals(Color.BLUE, cube.getSticker(Face.UP, 6));
    }

    @Test
    void leftClockwiseThenCounterClockwiseShouldRestoreCube() {
        CubeState cube = new CubeState();

        cube.rotateLeftClockwise();
        cube.rotateLeftCounterClockwise();

        assertEquals(true, cube.isSolved());
    }

    @Test
    void downClockwiseMoveShouldCycleBottomRowsCorrectly() {
        CubeState cube = new CubeState();

        cube.rotateDownClockwise();

        // FRONT → RIGHT
        assertEquals(Color.GREEN, cube.getSticker(Face.RIGHT, 6));
        assertEquals(Color.GREEN, cube.getSticker(Face.RIGHT, 7));
        assertEquals(Color.GREEN, cube.getSticker(Face.RIGHT, 8));

        // RIGHT → BACK
        assertEquals(Color.RED, cube.getSticker(Face.BACK, 6));
        assertEquals(Color.RED, cube.getSticker(Face.BACK, 7));
        assertEquals(Color.RED, cube.getSticker(Face.BACK, 8));

        // BACK → LEFT
        assertEquals(Color.BLUE, cube.getSticker(Face.LEFT, 6));
        assertEquals(Color.BLUE, cube.getSticker(Face.LEFT, 7));
        assertEquals(Color.BLUE, cube.getSticker(Face.LEFT, 8));

        // LEFT → FRONT
        assertEquals(Color.ORANGE, cube.getSticker(Face.FRONT, 6));
        assertEquals(Color.ORANGE, cube.getSticker(Face.FRONT, 7));
        assertEquals(Color.ORANGE, cube.getSticker(Face.FRONT, 8));
    }

    @Test
    void downClockwiseThenCounterClockwiseShouldRestoreCube() {
        CubeState cube = new CubeState();

        cube.rotateDownClockwise();
        cube.rotateDownCounterClockwise();

        assertEquals(true, cube.isSolved());
    }

    @Test
    void backClockwiseThenCounterClockwiseShouldRestoreCube() {
        CubeState cube = new CubeState();

        cube.rotateBackClockwise();
        cube.rotateBackCounterClockwise();

        assertEquals(true, cube.isSolved());
    }

    @Test
    void twoBackTwiceMovesShouldRestoreCube() {
        CubeState cube = new CubeState();

        cube.rotateBackTwice();
        cube.rotateBackTwice();

        assertEquals(true, cube.isSolved());
    }

    @Test
    void backClockwiseMoveShouldCycleAdjacentStickersCorrectly() {
        CubeState cube = new CubeState();

        cube.rotateBackClockwise();

        // RIGHT right column → UP top row
        assertEquals(Color.RED, cube.getSticker(Face.UP, 0));
        assertEquals(Color.RED, cube.getSticker(Face.UP, 1));
        assertEquals(Color.RED, cube.getSticker(Face.UP, 2));

        // UP top row → LEFT left column
        assertEquals(Color.WHITE, cube.getSticker(Face.LEFT, 0));
        assertEquals(Color.WHITE, cube.getSticker(Face.LEFT, 3));
        assertEquals(Color.WHITE, cube.getSticker(Face.LEFT, 6));

        // LEFT left column → DOWN bottom row
        assertEquals(Color.ORANGE, cube.getSticker(Face.DOWN, 6));
        assertEquals(Color.ORANGE, cube.getSticker(Face.DOWN, 7));
        assertEquals(Color.ORANGE, cube.getSticker(Face.DOWN, 8));

        // DOWN bottom row → RIGHT right column
        assertEquals(Color.YELLOW, cube.getSticker(Face.RIGHT, 2));
        assertEquals(Color.YELLOW, cube.getSticker(Face.RIGHT, 5));
        assertEquals(Color.YELLOW, cube.getSticker(Face.RIGHT, 8));
    }

    @Test
    void applyingMoveSequenceThenInverseShouldRestoreCube() {
        CubeState cube = new CubeState();

        cube.applyMove(Move.RIGHT);
        cube.applyMove(Move.UP);
        cube.applyMove(Move.FRONT);

        cube.applyMove(Move.FRONT_PRIME);
        cube.applyMove(Move.UP_PRIME);
        cube.applyMove(Move.RIGHT_PRIME);

        assertEquals(true, cube.isSolved());
    }

    @Test
    void solvedCubeShouldConvertToKociembaString() {
        CubeState cube = new CubeState();

        String expected =
                "UUUUUUUUU"
                        + "RRRRRRRRR"
                        + "FFFFFFFFF"
                        + "DDDDDDDDD"
                        + "LLLLLLLLL"
                        + "BBBBBBBBB";

        assertEquals(expected, cube.toKociembaString());
    }

    @Test
    void solvedCubeShouldHaveCorrectColorCounts() {
        CubeState cube = new CubeState();

        assertEquals(true, cube.hasCorrectColorCounts());
    }

    @Test
    void cubeShouldRejectIncorrectColorCounts() {
        CubeState cube = new CubeState();

        cube.setSticker(Face.UP, 0, Color.RED);

        assertEquals(false, cube.hasCorrectColorCounts());
    }

    @Test
    void solvedCubeShouldHaveUniqueCenterColors() {
        CubeState cube = new CubeState();

        assertEquals(true, cube.hasUniqueCenterColors());
    }

    @Test
    void cubeShouldRejectDuplicateCenterColors() {
        CubeState cube = new CubeState();

        cube.setSticker(Face.UP, 4, Color.RED);

        assertEquals(false, cube.hasUniqueCenterColors());
    }

    @Test
    void shouldCreateCubeFromKociembaString() {
        CubeState originalCube = new CubeState();

        originalCube.applyMove(Move.RIGHT);
        originalCube.applyMove(Move.UP);
        originalCube.applyMove(Move.FRONT_PRIME);

        String cubeString = originalCube.toKociembaString();

        CubeState recreatedCube = CubeState.fromKociembaString(cubeString);

        assertEquals(cubeString, recreatedCube.toKociembaString());
    }
}
