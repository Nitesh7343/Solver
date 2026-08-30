package com.solver.round_solve.cube;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public class CubeState {
    private final Color[] up = new Color[9];
    private final Color[] right = new Color[9];
    private final Color[] front = new Color[9];
    private final Color[] down = new Color[9];
    private final Color[] left = new Color[9];
    private final Color[] back = new Color[9];

    public CubeState() {
        Arrays.fill(up,Color.WHITE);
        Arrays.fill(down,Color.YELLOW);
        Arrays.fill(right,Color.RED);
        Arrays.fill(front,Color.GREEN);
        Arrays.fill(left,Color.ORANGE);
        Arrays.fill(back,Color.BLUE);
    }

    

    @Override
    public String toString() {
        return "CubeState [up=" + Arrays.toString(up) + ", right=" + Arrays.toString(right) + ", front="
                + Arrays.toString(front) + ", down=" + Arrays.toString(down) + ", left=" + Arrays.toString(left)
                + ", back=" + Arrays.toString(back) + "]";
    }



    private Color[] getFaceStickers(Face face) {
        return switch(face) {
            case UP -> up;
            case RIGHT -> right;
            case FRONT -> front;
            case DOWN -> down;
            case LEFT -> left;
            case BACK -> back;
        };
    }

    public Color getSticker(Face face, int idx) {
        return getFaceStickers(face)[idx];
    }

    public void setSticker(Face face , int idx, Color color){
        getFaceStickers(face)[idx] = color;
    }

    public boolean hasCorrectColorCounts() {
        for (Color expectedColor : Color.values()) {
            int count = 0;

            for (Face face : Face.values()) {
                for (Color sticker : getFaceStickers(face)) {
                    if (sticker == expectedColor) {
                        count++;
                    }
                }
            }

            if (count != 9) {
                return false;
            }
        }

        return true;
    }

    public boolean hasUniqueCenterColors() {
        Set<Color> centerColors = EnumSet.noneOf(Color.class);

        for (Face face : Face.values()) {
            Color centerColor = getFaceStickers(face)[4];

            if (!centerColors.add(centerColor)) {
                return false;
            }
        }

        return true;
    }

    public boolean isSolved() {
        for(Face face : Face.values())
        {
            Color[] stickers = getFaceStickers(face);
            Color centerColor = stickers[4];

            for(Color color : stickers) {
                if(color != centerColor) return false;
            }
        }

        return true;
    }

    public void rotateRightClockwise() {
        rotateFaceClockwise(right);

        Color frontTop = front[2];
        Color frontMid = front[5];
        Color frontBottom = front[8];

        front[2] = down[2];
        front[5] = down[5];
        front[8] = down[8];

        down[2] = back[6];
        down[5] = back[3];
        down[8] = back[0];

        back[0] = up[8];
        back[3] = up[5];
        back[6] = up[2];

        up[2] = frontTop;
        up[5] = frontMid;
        up[8] = frontBottom;
    }

    public void rotateRightCounterClockwise() {
        rotateRightClockwise();
        rotateRightClockwise();
        rotateRightClockwise();
    }

    public void rotateRightTwice() {
        rotateRightClockwise();
        rotateRightClockwise();
    }

    public void rotateUpClockwise() {
        rotateFaceClockwise(up);

        Color frontLeft = front[0];
        Color frontMid = front[1];
        Color frontRight = front[2];

        front[0] = right[0];
        front[1] = right[1];
        front[2] = right[2];

        right[0] = back[0];
        right[1] = back[1];
        right[2] = back[2];

        back[0] = left[0];
        back[1] = left[1];
        back[2] = left[2];

        left[0] = frontLeft;
        left[1] = frontMid;
        left[2] = frontRight;
    }

    public void rotateUpCounterClockwise() {
        rotateUpClockwise();
        rotateUpClockwise();
        rotateUpClockwise();
    }

    public void rotateUpTwice() {
        rotateUpClockwise();
        rotateUpClockwise();
    }

    public void rotateFrontClockwise() {
        rotateFaceClockwise(front);

        Color upLeft = up[6];
        Color upMiddle = up[7];
        Color upRight = up[8];

        up[6] = left[8];
        up[7] = left[5];
        up[8] = left[2];

        left[2] = down[2];
        left[5] = down[1];
        left[8] = down[0];

        down[0] = right[0];
        down[1] = right[3];
        down[2] = right[6];

        right[0] = upLeft;
        right[3] = upMiddle;
        right[6] = upRight;
    }

    public void rotateFrontCounterClockwise() {
        rotateFrontClockwise();
        rotateFrontClockwise();
        rotateFrontClockwise();
    }

    public void rotateFrontTwice() {
        rotateFrontClockwise();
        rotateFrontClockwise();
    }

    public void rotateLeftClockwise() {
        rotateFaceClockwise(left);

        Color upTop = up[0];
        Color upMiddle = up[3];
        Color upBottom = up[6];

        up[0] = back[8];
        up[3] = back[5];
        up[6] = back[2];

        back[2] = down[6];
        back[5] = down[3];
        back[8] = down[0];

        down[0] = front[0];
        down[3] = front[3];
        down[6] = front[6];

        front[0] = upTop;
        front[3] = upMiddle;
        front[6] = upBottom;
    }

    public void rotateLeftCounterClockwise() {
        rotateLeftClockwise();
        rotateLeftClockwise();
        rotateLeftClockwise();
    }

    public void rotateLeftTwice() {
        rotateLeftClockwise();
        rotateLeftClockwise();
    }

    public void rotateDownClockwise() {
        rotateFaceClockwise(down);

        Color frontLeft = front[6];
        Color frontMiddle = front[7];
        Color frontRight = front[8];

        front[6] = left[6];
        front[7] = left[7];
        front[8] = left[8];

        left[6] = back[6];
        left[7] = back[7];
        left[8] = back[8];

        back[6] = right[6];
        back[7] = right[7];
        back[8] = right[8];

        right[6] = frontLeft;
        right[7] = frontMiddle;
        right[8] = frontRight;
    }

    public void rotateDownCounterClockwise() {
        rotateDownClockwise();
        rotateDownClockwise();
        rotateDownClockwise();
    }

    public void rotateDownTwice() {
        rotateDownClockwise();
        rotateDownClockwise();
    }

    public void rotateBackClockwise() {
        rotateFaceClockwise(back);

        Color upLeft = up[0];
        Color upMiddle = up[1];
        Color upRight = up[2];

        up[0] = right[2];
        up[1] = right[5];
        up[2] = right[8];

        right[2] = down[6];
        right[5] = down[7];
        right[8] = down[8];

        down[6] = left[6];
        down[7] = left[3];
        down[8] = left[0];

        left[0] = upRight;
        left[3] = upMiddle;
        left[6] = upLeft;
    }

    public void rotateBackCounterClockwise() {
        rotateBackClockwise();
        rotateBackClockwise();
        rotateBackClockwise();
    }

    public void rotateBackTwice() {
        rotateBackClockwise();
        rotateBackClockwise();
    }

    public void rotateFaceClockwise(Color[] face) {
        Color[] copy = face.clone();

        face[0] = copy[6];
        face[1] = copy[3];
        face[2] = copy[0];
        face[3] = copy[7];
        face[4] = copy[4];
        face[5] = copy[1];
        face[6] = copy[8];
        face[7] = copy[5];
        face[8] = copy[2];
    }

    public void applyMove(Move move) {
        switch (move) {
            case UP -> rotateUpClockwise();
            case UP_PRIME -> rotateUpCounterClockwise();
            case UP2 -> rotateUpTwice();

            case RIGHT -> rotateRightClockwise();
            case RIGHT_PRIME -> rotateRightCounterClockwise();
            case RIGHT2 -> rotateRightTwice();

            case FRONT -> rotateFrontClockwise();
            case FRONT_PRIME -> rotateFrontCounterClockwise();
            case FRONT2 -> rotateFrontTwice();

            case DOWN -> rotateDownClockwise();
            case DOWN_PRIME -> rotateDownCounterClockwise();
            case DOWN2 -> rotateDownTwice();

            case LEFT -> rotateLeftClockwise();
            case LEFT_PRIME -> rotateLeftCounterClockwise();
            case LEFT2 -> rotateLeftTwice();

            case BACK -> rotateBackClockwise();
            case BACK_PRIME -> rotateBackCounterClockwise();
            case BACK2 -> rotateBackTwice();
        }
    }

    public String toKociembaString() {
        StringBuilder result = new StringBuilder(54);

        for (Face face : Face.values()) {
            for (Color sticker : getFaceStickers(face)) {
                result.append(findFaceByCenterColor(sticker).getNotation());
            }
        }

        return result.toString();
    }

    private Face findFaceByCenterColor(Color color) {
        for (Face face : Face.values()) {
            if (getFaceStickers(face)[4] == color) {
                return face;
            }
        }

        throw new IllegalStateException("Sticker color does not match any center color.");
    }
}
