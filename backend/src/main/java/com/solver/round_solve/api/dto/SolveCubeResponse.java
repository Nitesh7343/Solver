package com.solver.round_solve.api.dto;

import java.util.List;

public record SolveCubeResponse(
        String solution,
        List<String> moves,
        int moveCount
) {
}
