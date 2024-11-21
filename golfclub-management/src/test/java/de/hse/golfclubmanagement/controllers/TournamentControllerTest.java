/** Copyright (c) 2024. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.  
 *
 * This code is distributed for educational purposes only, but WITHOUT
 * ANY WARRANTY; See the GNU General Public License version 3 for more 
 * details (a copy is included in the LICENSE file that
 * accompanied this code).
 */

package de.hse.golfclubmanagement.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import de.hse.golfclubmanagement.models.Tournament;
import de.hse.golfclubmanagement.services.TournamentService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Test class for testing the tournament controller.
 * 
 * @author Daniel Hammerschmidt
 */

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class TournamentControllerTest {

    // addTournament always ok?
    // getAllTournament always ok?
    // findByName dependant -> ok if there, not found if not

    @MockBean
    private TournamentService tournamentService;

    @Autowired
    private MockMvc mockMvc;

    private String apiPath = "/api/v1/tournaments";

    @Test
    public void testAddTournament_success() throws Exception {
        Tournament mockTournament = new Tournament();
        mockTournament.setName("Mock Tournament");

        when(tournamentService.addTournament(any(Tournament.class))).thenReturn(mockTournament);

        mockMvc.perform(post(apiPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Mock Tournament\"}")) // JSON payload
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mock Tournament"));
    }

    @Test
    public void testAddTournament_invalid() throws Exception {

        mockMvc.perform(post(apiPath).contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());

    }


}
