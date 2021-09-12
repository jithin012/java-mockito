package com.mclabs.todo;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ListTest {

	@Test
	public void letsMockListSize() {
		List listMock = mock(List.class);
		when(listMock.size()).thenReturn(4);
		assertEquals(4, listMock.size());
	}

	@Test
	public void letsMockListSizeWithMultipleReturnValues() {
		List listMock = mock(List.class);
		when(listMock.size()).thenReturn(10).thenReturn(20);
		assertEquals(10, listMock.size()); // first call
		assertEquals(20, listMock.size()); // second call
	}

	@Test()
	public void letsMockListGet() {
		List listMock = mock(List.class);
		when(listMock.get(0)).thenReturn("return 0 index data");

		assertEquals("return 0 index data", listMock.get(0));
		assertNull(listMock.get(1));
	}

	@Test()
	public void letsMockListAnyInt() {
		List listMock = mock(List.class);
		when(listMock.get(Mockito.anyInt())).thenReturn("return same value everywhere");

		assertEquals("return same value everywhere", listMock.get(0));
		assertEquals("return same value everywhere", listMock.get(1));
	}

	@Test // (expected = RuntimeException.class)
	public void letsMockListGetToThrowException() {
		List<String> list = mock(List.class);

		when(list.get(Mockito.anyInt())).thenThrow(new RuntimeException("custom throw error"));

		list.get(0);
	}
}
