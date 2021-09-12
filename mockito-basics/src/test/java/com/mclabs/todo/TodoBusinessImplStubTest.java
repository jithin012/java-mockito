package com.mclabs.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TodoBusinessImplStubTest {

	@Test
	public void usingAStub() {
		
		TodoService todoService = new TodoServiceStub();
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);
		java.util.List<String> todos = todoBusinessImpl.retrieveTodosRelatedToSpring("Jithin");
		
		assertEquals(2, todos.size());
	}
}
