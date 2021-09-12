package com.mclabs.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * 
 * Mocking
 * 
 * Mocking is creating objects that simulate the behaviour of real objects/
 * Unlike stubs, mocks can be dynamically created from code - at runtime. Mocks
 * offer more functionality than stubbing. You can verify method calls and a lot
 * of other things.
 * 
 *
 */
public class TodoBusinessImplMockitoTest {

	@Test
	public void usingAStub() {
		
		TodoService todoServiceMock = mock(TodoService.class);
		List<String> todos = Arrays.asList();

		// TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		// todos will be empty. nice mock
		// java.util.List<String> todos =
		// todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");

		// stubing here
		when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(todos);
		assertEquals(0, todos.size());
	}
}
