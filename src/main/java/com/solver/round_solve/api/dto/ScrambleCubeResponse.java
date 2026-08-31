package com.solver.round_solve.api.dto;

import java.util.List;

public record ScrambleCubeResponse(
        String cubeState,
        List<String> scrambleMoves
) {
}