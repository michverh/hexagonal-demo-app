package com.michel.hexagonaldemoapp.cucumber;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.FEATURES_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/michel/hexagonaldemoapp/cucumber")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "classpath:com.michel.hexagonaldemoapp.cucumber")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "src/test/resources/cucumber/application-core")
public class CucumberTest {
}
