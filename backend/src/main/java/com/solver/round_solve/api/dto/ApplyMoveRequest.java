package com.solver.round_solve.api.dto;

public record ApplyMoveRequest(
        String cubeState,
        String move
) {
}