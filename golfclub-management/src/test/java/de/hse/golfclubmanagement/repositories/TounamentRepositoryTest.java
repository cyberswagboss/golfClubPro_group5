package de.hse.golfclubmanagement.repositories;

import de.hse.golfclubmanagement.models.Tournament;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


/**
 * This class tests the TournamentRepository class.
 * Author: Mason Schönherr
 */

@DataJpaTest
@ActiveProfiles("test")
public class TounamentRepositoryTest {
    
    @Mock
    private TournamentRepository tournamentRepository;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test finding the Tournament by name
    @Test
    public void testFindByName() {
        Tournament tournament = new Tournament();
        tournament.setName("Highwind");
        
        when(tournamentRepository.findByName("Highwind")).thenReturn(tournament);
        
        
        Tournament foundTournament = tournamentRepository.findByName("Highwind");
        
        
        assertNotNull(foundTournament, "The found Tournament should not be null");
        assertEquals("Highwind", foundTournament.getName(), "The found Tournament name should match");
        verify(tournamentRepository, times(1)).findByName(any(String.class));
    }

    // Test finding the Tournament by non-existent name
    @Test
    public void testFindByNameNotFound() {
        
        when(tournamentRepository.findByName("Unknown")).thenReturn(null);
        
        
        Tournament notFoundTournament = tournamentRepository.findByName("Unknown");
        
        
        assertNull(notFoundTournament, "The found Tournament should be null");
    }

    // Test finding the Tournament by id
    @Test
    public void testFindById() {
        
        Tournament tournament = new Tournament();
        tournament.setId(1L);
        when(tournamentRepository.findById(1L)).thenReturn(Optional.of(tournament));
        
        
        Optional<Tournament> foundTournament = tournamentRepository.findById(1L);
        
        
        assertNotNull(tournament);
        assertTrue(foundTournament.isPresent(), "The found Tournament should be present");
        assertEquals(1L, foundTournament.get().getId(), "The found Tournament ID should match");

        verify(tournamentRepository, times(1)).findById(any(Long.class));
    }

    // Test finding the Tournament by non-existent id
    @Test
    public void testFindByIdNotFound() {
        
        when(tournamentRepository.findById(55L)).thenReturn(Optional.empty());
        
        
        Optional<Tournament> notFoundTournament = tournamentRepository.findById(55L);
        
        
        assertFalse(notFoundTournament.isPresent(), "The found Tournament should not be present");
    }   

    // Test finding the Tournament by date
    @Test
    public void testFindByDate() {
        
        Tournament tournament = new Tournament();
        tournament.setDate(java.sql.Date.valueOf("2024-12-24"));
        when(tournamentRepository.findByDate(java.sql.Date.valueOf("2024-12-24"))).thenReturn(tournament);
        
        
        Tournament foundTournament = tournamentRepository.findByDate(java.sql.Date.valueOf("2024-12-24"));
        
        
        assertNotNull(tournament);
        assertTrue(foundTournament.getDate().equals(java.sql.Date.valueOf("2024-12-24")));
        assertEquals(java.sql.Date.valueOf("2024-12-24"), foundTournament.getDate(), "The found Tournament date should match");
        verify(tournamentRepository, times(1)).findByDate(java.sql.Date.valueOf("2024-12-24"));
    }

    // Test finding the Tournament by non-existent date
    @Test
    public void testFindByDateNotFound() {
        
        when(tournamentRepository.findByDate(java.sql.Date.valueOf("2095-12-05"))).thenReturn(null);
        
        
        Tournament notFoundtournament = tournamentRepository.findByDate(java.sql.Date.valueOf("2095-12-05"));
        
        
        assertNull(notFoundtournament, "The found Tournament should be null");
    }

    // Test saving a tournament
    @Test
    public void testSaveTournament() {
        
        Tournament tournament = new Tournament();
        tournament.setName("Highwind");
        tournament.setDate(java.sql.Date.valueOf("2024-12-24"));
        when(tournamentRepository.save(any(Tournament.class))).thenReturn(tournament);
        
        
        Tournament savedTournament = tournamentRepository.save(tournament);
        
        
        assertNotNull(savedTournament, "The saved Tournament should not be null");
        assertEquals("Highwind", savedTournament.getName(), "The saved Tournament name should match");
        verify(tournamentRepository, times(1)).save(any());
    }
    
    // Test deleting a tournament
    @Test
    public void testDeleteTournament() {
        
        Tournament tournament = new Tournament();
        tournament.setName("Highwind");
        tournament.setDate(java.sql.Date.valueOf("2024-12-24"));
        doNothing().when(tournamentRepository).delete(any(Tournament.class));
        
        
        tournamentRepository.delete(tournament);
        
        
        verify(tournamentRepository, times(1)).delete(tournament);
    }

    // Test updating a tournament
    @Test
    public void testUpdate() {
        
        Tournament tournament = new Tournament();
        tournament.setName("Highwind");
        when(tournamentRepository.save(any(Tournament.class))).thenReturn(tournament);
        
        
        Tournament updatedTournament = tournamentRepository.save(tournament);
        
        
        assertNotNull(updatedTournament);
        verify(tournamentRepository, times(1)).save(any());
    }

    // Test finding all tournaments
    @Test
    public void testFindAll() {
        
        when(tournamentRepository.findAll()).thenReturn(List.of(new Tournament(), new Tournament()));
        
        
        List<Tournament> tournaments = tournamentRepository.findAll();
        
        
        assertNotNull(tournaments);
        assertEquals(2, tournaments.size());
        verify(tournamentRepository, times(1)).findAll();
    }
}
