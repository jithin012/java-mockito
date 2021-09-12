package com.pluralsight.pension.setup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInfo;

@DisplayName("Repitation Test")
public class BasicTestAnnotationRepetation {

	@RepeatedTest(value = 3, name = "{displayName} - {currentRepetition}/{totalRepetitions}")
	@DisplayName("Multiple Test")
	public void testMultiple(TestInfo info) {
		System.out.println(info.getDisplayName());
	}
}
