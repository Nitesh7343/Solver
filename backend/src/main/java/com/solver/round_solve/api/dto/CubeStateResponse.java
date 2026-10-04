package com.solver.round_solve.api.dto;

public record CubeStateResponse(
        String cubeState,
        boolean solved
) {
}