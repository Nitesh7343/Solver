package com.solver.round_solve.cube;

import java.util.List;

public record ScrambleResult(
        CubeState cube,
        List<Move> moves
) {
}