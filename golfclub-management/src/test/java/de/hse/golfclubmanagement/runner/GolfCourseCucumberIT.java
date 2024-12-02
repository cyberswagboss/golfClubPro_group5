package de.hse.golfclubmanagement.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features", 
    glue = "de.hse.golfclubmanagement.steps", 
    plugin = {"pretty", "summary", "html:target/cucumber-report.html", "json:target/cucumber-report.json"}, 
    monochrome = true 
)
public class GolfCourseCucumberIT {
}

/* Executing the BDD tests during the integration stage of the Maven build  
ensures that the system is fully set up and avoids slowing down the unit test feedback loop */