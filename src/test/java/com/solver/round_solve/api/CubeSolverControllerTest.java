package com.solver.round_solve.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CubeSolverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void solvedCubeShouldReturnAnEmptySolution() throws Exception {
        mockMvc.perform(post("/api/cube/solve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "cubeState": "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.solution").value(""))
                .andExpect(jsonPath("$.moves").isEmpty())
                .andExpect(jsonPath("$.moveCount").value(0));
    }

    @Test
    void invalidCubeStateShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/cube/solve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "cubeState": "NOT_A_VALID_CUBE"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value("Cube state must contain exactly 54 characters."));
    }
}