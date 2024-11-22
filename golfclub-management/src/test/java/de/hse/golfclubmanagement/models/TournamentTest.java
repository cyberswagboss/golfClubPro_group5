package de.hse.golfclubmanagement.models;

import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class TournamentTest {

    @Test
    void testValidTournament() {
        Tournament tournament = new Tournament();
        tournament.setName("Spring Championship");

        // Set the date to a future date
        Calendar futureDate = Calendar.getInstance();
        futureDate.add(Calendar.DATE, 10);
        tournament.setDate(futureDate.getTime());

        assertNotNull(tournament.getName(), "Tournament name should not be null");
        assertFalse(tournament.getName().isEmpty(), "Tournament name should not be empty");
        assertTrue(tournament.getDate().after(new Date()), "Tournament date should be in the future");
    }

    @Test
    void testInvalidTournamentName() {
        Tournament tournament = new Tournament();
        tournament.setName(""); // Invalid name (empty)

        Calendar futureDate = Calendar.getInstance();
        futureDate.add(Calendar.DATE, 10);
        tournament.setDate(futureDate.getTime());

        assertTrue(tournament.getName().isEmpty(), "Tournament name should be invalid (empty)");
    }

    @Test
    void testInvalidPastTournamentDate() {
        Tournament tournament = new Tournament();
        tournament.setName("Autumn Tournament");

        // Set the date to a past date
        Calendar pastDate = Calendar.getInstance();
        pastDate.add(Calendar.DATE, -5);
        tournament.setDate(pastDate.getTime());

        assertNotNull(tournament.getName(), "Tournament name should not be null");
        assertFalse(tournament.getName().isEmpty(), "Tournament name should not be empty");
        assertTrue(tournament.getDate().before(new Date()), "Tournament date should be in the past");
    }

    @Test
    void testBoundaryTournamentDate() {
        Tournament tournament = new Tournament();
        tournament.setName("Boundary Tournament");

        // Set the date to today (boundary value)
        Date today = new Date();
        tournament.setDate(today);

        assertNotNull(tournament.getName(), "Tournament name should not be null");
        assertFalse(tournament.getName().isEmpty(), "Tournament name should not be empty");
        assertEquals(today, tournament.getDate(), "Tournament date should be today (boundary value)");
    }
}
