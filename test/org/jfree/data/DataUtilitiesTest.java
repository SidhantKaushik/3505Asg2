package org.jfree.data;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import original.Range;

class DataUtilitiesTest {

	private Values2D value;
	private Values2D invalidValue;
	@BeforeEach
	void setUp() throws Exception {
		value = mock(Values2D.class);
		when(value.getColumnCount()).thenReturn(4);
		when(value.getRowCount()).thenReturn(3);
		when(value.getValue(0, 0)).thenReturn(3);
		when(value.getValue(0, 1)).thenReturn(1);
		when(value.getValue(0, 2)).thenReturn(5);
		when(value.getValue(1, 0)).thenReturn(4);
		when(value.getValue(1, 1)).thenReturn(2);
		when(value.getValue(1, 2)).thenReturn(7);
		when(value.getValue(2, 0)).thenReturn(1);
		when(value.getValue(2, 1)).thenReturn(3);
		when(value.getValue(2, 2)).thenReturn(1);
		
		invalidValue = mock(Values2D.class);
		when(invalidValue.getValue(0, 0)).thenReturn(null);
	}

	@Test
	void testCalculateColumnTotal() {
		assertEquals(13, DataUtilities.calculateColumnTotal(value, 2));
		verify(value, times(3)).getValue(anyInt(), anyInt());
	}
	@Test
	void testCalculateColumnTotalNull() {
		assertThrows(InvalidParameterException.class, () -> DataUtilities.calculateColumnTotal(invalidValue, 0));
	}
	
	@Test
	void testCreateNumberArray() {
		double a[];
		a = new double[4];
		a[0] = 1.0;
		a[1] = 2.0;
		a[2] = 3.5;
		a[3] = 4.2;
		Number[] expected = new Number[4];
		expected[0] = 1.0;
		expected[1] = 2.0;
		expected[2] = 3.5;
		expected[3] = 4.2;
		Number[] actual = DataUtilities.createNumberArray(a);
		assertEquals(expected,actual);
	}
	
	@Test
	void testCreateNumberArray2() {
		double a[];
		a = new double[3];
		a[0] = 1.0;
		a[1] = 2.0;
		a[2] = 3.5;
		Number[] expected = new Number[3];
		expected[0] = 1.0;
		expected[1] = 2.0;
		expected[2] = 3.5;
		Number[] actual = DataUtilities.createNumberArray(a);
		assertEquals(expected,actual);
	}
	
	@Test
	void testCreateNumberArrayNull() {
		assertThrows(InvalidParameterException.class, () -> DataUtilities.createNumberArray(null));
	}
}
