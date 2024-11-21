package de.hse.golfclubmanagement.services;

import de.hse.golfclubmanagement.models.Tournament;
import de.hse.golfclubmanagement.repositories.TournamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for TournamentService.
 * Verifies the behavior of the TournamentService methods using mock testing.
 */
public class TournamentServiceTest {

    @Mock
    private TournamentRepository tournamentRepository; // Mocked repository for Tournament

    @InjectMocks
    private TournamentService tournamentService; // Service instance with mocked repository injected

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test
    }

    @Test
    public void testAddTournamentValid() {
        Tournament tournament = new Tournament();
        tournament.setName("Championship");

        // Mocking save method
        when(tournamentRepository.save(any(Tournament.class))).thenReturn(tournament);

        // Call service method
        Tournament savedTournament = tournamentService.addTournament(tournament);

        // Verifying the results
        assertNotNull(savedTournament, "Saved tournament should not be null");
        assertEquals("Championship", savedTournament.getName(), "Tournament name should match");
        verify(tournamentRepository, times(1)).save(tournament);
    }

    @Test
    public void testAddTournamentNull() {
        // Mocking save with null
        when(tournamentRepository.save(null)).thenThrow(new IllegalArgumentException("Tournament cannot be null"));

        // Expect exception for null input
        assertThrows(IllegalArgumentException.class, () -> tournamentService.addTournament(null), "Should throw IllegalArgumentException for null input");
    }

    @Test
    public void testGetAllTournaments() {
        // Mocking findAll
        List<Tournament> tournaments = List.of(new Tournament(), new Tournament());
        when(tournamentRepository.findAll()).thenReturn(tournaments);

        // Call service method
        List<Tournament> result = tournamentService.getAllTournaments();

        // Verifying the results
        assertNotNull(result, "Result list should not be null");
        assertEquals(2, result.size(), "Result list size should match");
        verify(tournamentRepository, times(1)).findAll();
    }

    @Test
    public void testFindByNameValid() {
        Tournament tournament = new Tournament();
        tournament.setName("Championship");

        // Mocking findByName
        when(tournamentRepository.findByName("Championship")).thenReturn(tournament);

        // Call service method
        Tournament foundTournament = tournamentService.findByName("Championship");

        // Verifying the results
        assertNotNull(foundTournament, "Found tournament should not be null");
        assertEquals("Championship", foundTournament.getName(), "Tournament name should match");
        verify(tournamentRepository, times(1)).findByName("Championship");
    }

    @Test
    public void testFindByNameNotFound() {
        // Mocking findByName for non-existent tournament
        when(tournamentRepository.findByName("NonExistent")).thenReturn(null);

        // Call service method
        Tournament notFound = tournamentService.findByName("NonExistent");

        // Verifying the results
        assertNull(notFound, "Result should be null for non-existent tournament");
        verify(tournamentRepository, times(1)).findByName("NonExistent");
    }
}
