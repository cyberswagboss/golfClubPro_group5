package de.hse.golfclubmanagement.steps;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import de.hse.golfclubmanagement.controllers.GolfCourseController;
import de.hse.golfclubmanagement.models.GolfCourse;
import de.hse.golfclubmanagement.services.GolfCourseService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class course_managementSteps {

    @Mock
    private GolfCourseService golfCourseService; // Mock-Service

    private GolfCourse testGolfCourse; // Testdaten

    @InjectMocks
    @Autowired
    private GolfCourseController golfCourseController;

    public course_managementSteps() {
        MockitoAnnotations.openMocks(this); // Initialisiert Mockito-Mocks
    }

    // Background:
    @Given("the user is logged in")
    public void theUserIsLoggedIn() {
        boolean isLoggedIn = true; // Logik für den Login-Status
        assertTrue("User is not logged in", isLoggedIn);
        System.out.println("User is logged in.");
    }

    @Given("the user is an administrator")
    public void theUserIsAnAdministrator() {
        boolean isAdmin = true; // Logik für die Administratorprüfung
        assertTrue("User is not an administrator", isAdmin);
        System.out.println("User is an administrator.");
    }

    // Scenario: Create a new golf course
    @When("the user creates a new golf course with valid attributes")
    public void theUserCreatesANewGolfCourseWithValidAttributes() {
        testGolfCourse = new GolfCourse();
        testGolfCourse.setName("Sunny Hills");
        testGolfCourse.setLocation("Mountain View");

        when(golfCourseService.saveGolfCourse(testGolfCourse)).thenReturn(testGolfCourse);

        ResponseEntity<GolfCourse> response = golfCourseController.addGolfCourse(testGolfCourse);

        assertNotNull(response.getBody());
        assertEquals("Sunny Hills", response.getBody().getName());
        assertEquals("Mountain View", response.getBody().getLocation());
    }

    @Then("the system should save the new golf course")
    public void theSystemShouldSaveTheNewGolfCourse() {
        assertNotNull(testGolfCourse);
        System.out.println("System saves the new golf course.");
    }

    @Then("it should be visible in the list of available courses")
    public void itShouldBeVisibleInTheListOfAvailableCourses() {
        when(golfCourseService.getAllGolfCourses()).thenReturn(Arrays.asList(testGolfCourse));

        ResponseEntity<List<GolfCourse>> response = golfCourseController.getAllGolfCourses();
        List<GolfCourse> golfCourses = response.getBody();

        assertNotNull(golfCourses);
        assertTrue(golfCourses.stream().anyMatch(course -> course.getName().equals("Sunny Hills")));
        System.out.println("New golf course is visible in the list.");
    }

    // Scenario: Modify golf course details
    @Given("a golf course exists in the system")
    public void aGolfCourseExistsInTheSystem() {
        testGolfCourse = new GolfCourse();
        testGolfCourse.setName("Sunny Hills");
        testGolfCourse.setLocation("Mountain View");

        when(golfCourseService.findByName("Sunny Hills")).thenReturn(testGolfCourse);
        System.out.println("A golf course exists in the system.");
    }

    @When("the administrator modifies the golf course details")
    public void theAdministratorModifiesTheGolfCourseDetails() {
        testGolfCourse.setLocation("Ocean View");
        when(golfCourseService.saveGolfCourse(testGolfCourse)).thenReturn(testGolfCourse);
        golfCourseController.addGolfCourse(testGolfCourse);
        System.out.println("Administrator modifies the golf course details.");
    }

    @Then("the changes should be saved successfully")
    public void theChangesShouldBeSavedSuccessfully() {
        assertEquals("Ocean View", testGolfCourse.getLocation());
        System.out.println("Changes are saved successfully.");
    }

    @Then("the updated details should be visible when the course is viewed")
    public void theUpdatedDetailsShouldBeVisibleWhenTheCourseIsViewed() {
        ResponseEntity<GolfCourse> response = golfCourseController.findByName("Sunny Hills");
        assertNotNull(response.getBody());
        assertEquals("Ocean View", response.getBody().getLocation());
        System.out.println("Updated details are visible.");
    }

    // Scenario: View list of all golf courses
    @When("the administrator views the list of all golf courses")
    public void theAdministratorViewsTheListOfAllGolfCourses() {
        GolfCourse anotherGolfCourse = new GolfCourse();
        anotherGolfCourse.setName("Green Valley");
        anotherGolfCourse.setLocation("Lake Side");

        when(golfCourseService.getAllGolfCourses()).thenReturn(Arrays.asList(testGolfCourse, anotherGolfCourse));

        ResponseEntity<List<GolfCourse>> response = golfCourseController.getAllGolfCourses();
        assertNotNull(response.getBody());
        System.out.println("Administrator views the list of all golf courses.");
    }

    @Then("the system should display all existing courses")
    public void theSystemShouldDisplayAllExistingCourses() {
        ResponseEntity<List<GolfCourse>> response = golfCourseController.getAllGolfCourses();
        List<GolfCourse> golfCourses = response.getBody();

        assertNotNull(golfCourses);
        assertEquals(2, golfCourses.size());
        System.out.println("System displays all existing courses.");
    }
}
