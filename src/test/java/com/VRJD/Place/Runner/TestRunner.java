package com.VRJD.Place.Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = { "Features" }, 
		glue = { "com.VRJD.Place.StepDefinitions" }, 
		stepNotifications = true, 
		publish = true, 
		plugin = { "pretty", "html:test-output/HTMLReport.html", "json:target/jsonReports/Cucumber-Report.json",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", },
		tags = "@Regression", dryRun = false, monochrome = true)

public class TestRunner extends AbstractTestNGCucumberTests {
	
}