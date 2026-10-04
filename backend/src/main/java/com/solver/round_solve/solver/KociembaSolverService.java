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
            throw new IllegalArgumentException("Cube cannot be solved: " + solution);
        }

        return solution.trim();
    }
}
