Feature: Golf Course Management
    As an administrator
    I want to manage golf courses
    So that I can keep the system up-to-date with accurate information

    Background:
        Given: the user is logged in
        And the user is an adminstrator

    Scenario: Create and add golf Course
        When the user creates a new golf Course with valid attributes
        Then the system should save the new golf course
        And it should be visible in the list of available course

    Scenario: Modify a existing golf course
        Given a golf course exists in the system
        When the administrator modifies the golf course details
        Then the changes should be saved successfully
        And the updated details should be visible when the course is viewed
    
    Scenatio: Oversee all existing golf courses
        When the administrator views the list of all golf courses
        Then the system should display all existing courses