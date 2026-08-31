package com.solver.round_solve.cube;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CubeScrambleService {

    public ScrambleResult createScramble(int moveCount) {
        if (moveCount <= 0) {
            throw new IllegalArgumentException(
                    "Scramble move count must be greater than zero."
            );
        }

        CubeState cube = new CubeState();
        List<Move> scrambleMoves = new ArrayList<>();

        Move previousMove = null;

        while (scrambleMoves.size() < moveCount) {
            Move randomMove = randomMove();

            if (previousMove != null
                    && randomMove.getNotation().charAt(0)
                    == previousMove.getNotation().charAt(0)) {
                continue;
            }

            cube.applyMove(randomMove);
            scrambleMoves.add(randomMove);
            previousMove = randomMove;
        }

        return new ScrambleResult(cube, scrambleMoves);
    }

    private Move randomMove() {
        Move[] moves = Move.values();

        int randomIndex = ThreadLocalRandom.current().nextInt(moves.length);

        return moves[randomIndex];
    }
}