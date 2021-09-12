package com.pluralsight.pension.setup;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * @Version JUnit 5.x = JUnit jupiter Engine
 * 
 * @Version JUnit 4.X = JUnit Vintage Engine
 * 
 *          Junit 5 Annotations
 * 
 * @Test
 * @DisplayName
 *              <p>
 *              Readable text inplace of actual method & class name
 * @BeforeEach
 *             <p>
 *             execute logic once per TEST METHOD before starting
 * @AfterEach
 *            <p>
 *            execute logic once per TEST METHOD after finish
 * @BeforeAll
 *            <p>
 *            execute logic once per TEST CASE before starting
 * @AfterAll
 *           <p>
 *           execute logic once per TEST CASE after finshing
 * @Disabled
 *           <p>
 *           ignore the test method.
 * @TestMethodOrder
 *                  <p>
 *                  Default Random, others are Alphanemeric, OrderAnnotation
 * @RepeatedTest
 *               <p>
 *               Execute any test method multiple time(like batch processing)
 * @Tag
 *      <p>
 *      Used to filter test methods for execution in different env.
 * 
 *
 *
 */

@TestMethodOrder(OrderAnnotation.class)
public class BasicTest {

	@Test
	@Order(3)
	public void testBasicSave() {
		System.out.println("Save...");
	}

	@Test
	@Order(2)
	public void testBasicUpdate() {
		System.out.println("Update...");
	}

	@Test
	@Order(1)
	public void testBasicDelete() {
		System.out.println("Delete...");
	}
}
