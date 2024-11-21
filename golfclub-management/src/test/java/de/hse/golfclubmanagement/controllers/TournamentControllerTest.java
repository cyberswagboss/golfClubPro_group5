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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import de.hse.golfclubmanagement.models.Tournament;
import de.hse.golfclubmanagement.services.TournamentService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
    private String apiFind = "/findByName";

    /**
     * Test case: sending a tournament entry to the api
     * 
     * @throws Exception
     */
    @Test
    public void testAddTournament_success() throws Exception {
        Tournament mockTournament = new Tournament();
        mockTournament.setName("Mock Tournament");

        when(tournamentService.addTournament(any(Tournament.class))).thenReturn(mockTournament);

        mockMvc.perform(post(apiPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Mock Tournament\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mock Tournament"));
    }

    /**
     * Test case: sending an empty request to the api
     * 
     * @throws Exception
     */
    @Test
    public void testAddTournament_empty() throws Exception {
        mockMvc.perform(post(apiPath).contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    /**
     * Test case: sending an empty request body and expecting a filled list in
     * response
     * 
     * @throws Exception
     * 
     */
    @Test
    public void testGetAllTournaments_filledListEmptyBody() throws Exception {
        List<Tournament> tournaments = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            Tournament newTournament = new Tournament();
            newTournament.setName("Tournament " + Integer.toString(i));
            tournaments.add(newTournament);
        }

        when(tournamentService.getAllTournaments()).thenReturn(tournaments);

        mockMvc.perform(get(apiPath).contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tournament 0"))
                .andExpect(jsonPath("$[1].name").value("Tournament 1"))
                .andExpect(jsonPath("$.length()").value(2));

    }

    /**
     * Test case: retrieving all tournaments with a payload in the request
     * 
     * @throws Exception
     */
    @Test
    public void testGetAllTournaments_filledListFilledBody() throws Exception {
        List<Tournament> tournaments = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            Tournament newTournament = new Tournament();
            newTournament.setName("Tournament " + Integer.toString(i));
            tournaments.add(newTournament);
        }

        when(tournamentService.getAllTournaments()).thenReturn(tournaments);

        mockMvc.perform(get(apiPath).contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Mock Tournament\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tournament 0"))
                .andExpect(jsonPath("$[1].name").value("Tournament 1"))
                .andExpect(jsonPath("$.length()").value(2));

    }

    /**
     * Test case: retrieving an empty list with an empty request body
     * 
     * @throws Exception
     */
    @Test
    public void testGetAllTournaments_emptyListEmptyBody() throws Exception {
        // returning an empty list
        when(tournamentService.getAllTournaments()).thenReturn(new ArrayList<Tournament>());

        mockMvc.perform(get(apiPath).contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    /**
     * Test case: retrieving an empty tournament list with a filled request body
     * 
     * @throws Exception
     */
    @Test
    public void testGetAllTournaments_emptyListFilledBody() throws Exception {
        when(tournamentService.getAllTournaments()).thenReturn(new ArrayList<Tournament>());

        mockMvc.perform(get(apiPath).contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Mock\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    /**
     * Test case: retrieving a tournament by name
     * 
     * @throws Exception
     */
    @Test
    public void testFindByName_existingTournament() throws Exception {
        String tournamentName = "mock tournament";

        Tournament mockTournament = new Tournament();
        mockTournament.setName(tournamentName);

        when(tournamentService.findByName(tournamentName)).thenReturn(mockTournament);

        mockMvc.perform(get(apiPath + apiFind).param("name", tournamentName).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(tournamentName));

    }

    /**
     * Test case: retrieving a name which does not exist
     * 
     * @throws Exception
     */
    @Test
    public void testFindByName_nonExisting() throws Exception {
        String tournamentName = "mock tournament";

        when(tournamentService.findByName(tournamentName)).thenReturn(null);

        mockMvc.perform(get(apiPath + apiFind).param("name", tournamentName).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Test case: trying to retrieve a tournament with an empty name
     * 
     * @throws Exception
     */
    @Test
    public void testFindByName_nonExistingEmptyParam() throws Exception {
        mockMvc.perform(get(apiPath + apiFind).param("name", "").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Test case: trying to retrieve a tournament with the name parameter not present
     * 
     * @throws Exception
     */
    @Test
    public void testFindByName_nonExistingNullParam() throws Exception {
        mockMvc.perform(get(apiPath + apiFind).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    
}
