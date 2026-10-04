package com.solver.round_solve.solver;

import com.solver.round_solve.cube.CubeState;
import com.weijiekeji.kociemba.twophase.SearchThreadSafe;
import org.springframework.stereotype.Service;

@Service
public class KociembaSolverService {

    private final SearchThreadSafe solver = new SearchThreadSafe();

    public String solve(CubeState cube) {

        if (!cube.hasCorrectColorCounts() || !cube.hasUniqueCenterColors()) {
            throw new IllegalArgumentException("Cube state has invalid colors or centers.");
        }

        if (cube.isSolved()) {
            return "";
        }

        String solution = solver.solution(cube.toKociembaString(),22,5,false);

        if (solution.startsWith("Error")) {
            throw new IllegalArgumentException(describeSolverError(solution));
        }

        return solution.trim();
    }

    private String describeSolverError(String solverError) {
        if (solverError.startsWith("Error 3")) {
            return "Invalid cube: one or more edge pieces are flipped. Check both stickers on each edge.";
        }
        if (solverError.startsWith("Error 4")) {
            return "Invalid cube: one or more corner pieces are twisted. Check the three stickers on each corner.";
        }
        if (solverError.startsWith("Error 5")) {
            return "Invalid cube: a corner piece is duplicated or missing.";
        }
        if (solverError.startsWith("Error 6")) {
            return "Invalid cube: corner and edge pieces have different swap parity. Two pieces were likely swapped.";
        }
        if (solverError.startsWith("Error 7")) {
            return "Invalid cube: an edge piece is duplicated or missing.";
        }
        if (solverError.startsWith("Error 8")) {
            return "The solver could not find a solution within its configured search limit.";
        }

        return "Cube cannot be solved: " + solverError;
    }
}
