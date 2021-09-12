package com.pluralsight.pension.setup;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TESTING JUNIT ANNOTATIONS")
public class BasicTestAnnotation{

	@BeforeAll
	public static void ComponentWillMount() {
		System.out.println("from one time setup");
	}

	@BeforeEach
	public void ShouldComponentUpdate() {
		System.out.println("From Set up");
	}

	@AfterEach
	public void ComponentDidUpdate() {
		System.out.println("CLEAR");
	}

	@Test
	@DisplayName("TESTING SAVE METHOD")
	public void testSave() {
		System.out.println("save...");
	}

	@Test
	@DisplayName("TESTING UPDATE METHOD")
	public void testUpdate() {
		System.out.println("update...");
	}

	@AfterAll
	public static void componentWillUnmount() {
		System.out.println("Alter all test cases");
	}
	
	
}
