package com.solver.round_solve.api;

import com.solver.round_solve.api.dto.SolveCubeRequest;
import com.solver.round_solve.api.dto.SolveCubeResponse;
import com.solver.round_solve.cube.CubeState;
import com.solver.round_solve.solver.KociembaSolverService;
import com.solver.round_solve.api.dto.ScrambleCubeResponse;
import com.solver.round_solve.cube.CubeScrambleService;
import com.solver.round_solve.cube.Move;
import com.solver.round_solve.cube.ScrambleResult;
import com.solver.round_solve.api.dto.ApplyMoveRequest;
import com.solver.round_solve.api.dto.CubeStateResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cube")
public class CubeSolverController {

    private final CubeScrambleService scrambleService;
    private final KociembaSolverService solverService;

    public CubeSolverController(KociembaSolverService solverService, CubeScrambleService scrambleService) {
        this.solverService = solverService;
        this.scrambleService = scrambleService;
    }

    @PostMapping("/solve")
    public SolveCubeResponse solve(@RequestBody SolveCubeRequest request ) {
        CubeState cube = CubeState.fromKociembaString(request.cubeState());
        String solution = solverService.solve(cube);

        List<String> moves = solution.isBlank()
                ? List.of()
                : List.of(solution.split("\\s+"));

        return new SolveCubeResponse(
                solution,
                moves,
                moves.size()
        );
    }

    @PostMapping("/scramble")
    public ScrambleCubeResponse scramble() {
        ScrambleResult result = scrambleService.createScramble(2);

        List<String> scrambleMoves = result.moves()
                .stream()
                .map(Move::getNotation)
                .toList();

        return new ScrambleCubeResponse(
                result.cube().toKociembaString(),
                scrambleMoves
        );
    }

    @PostMapping("/move")
    public CubeStateResponse applyMove(@RequestBody ApplyMoveRequest request) {
        CubeState cube = CubeState.fromKociembaString(request.cubeState());

        Move move = Move.fromNotation(request.move());

        cube.applyMove(move);

        return new CubeStateResponse(
                cube.toKociembaString(),
                cube.isSolved()
        );
    }

}
