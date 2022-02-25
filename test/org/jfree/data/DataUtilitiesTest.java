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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

	@ParameterizedTest
	@CsvSource({
		"9,0",
		"13,1",
		"5,2",
		}) 
	void testCalculateColumnTotal(int Expected, int Column) {
		assertEquals(Expected, DataUtilities.calculateColumnTotal(value, Column));
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
	
	
	//test number array 2D
	@Test 
	void createNumberArray2D() {
		double[][] data = new double [2][];
		data[0] = new double[] {1.0, 2.0, 2.5}; 
		data[1] = new double[] {1.1, 2.1, 2.6, 3.3}; 
		
		//verify
		Number[][] actual = DataUtilities.createNumberArray2D(data);
		assertEquals(2, actual.length);
		assertEquals(3, actual[0].length);
		assertEquals(4, actual[1].length);
		
	}
	
	@Test 
	void createNumberArray2D2() {
		double[][] data = new double [3][];
		data[0] = new double[] {-1.0, -2.5}; 
		data[1] = new double[] {-1.1, -2.1, -2.6}; 
		data[2] = new double[] {-1.4, -2.9, -4.2}; 
		
		//verify
		Number[][] actual = DataUtilities.createNumberArray2D(data);
		assertEquals(3, actual.length);
		assertEquals(2, actual[0].length);
		assertEquals(3, actual[1].length);
		assertEquals(3, actual[2].length);
		
	}
	
	//test the NULL method for createNumberArray2D
	@Test
	void createNumberArray2DNull() {
		assertThrows(InvalidParameterException.class, () -> DataUtilities.createNumberArray2D(null));
	}
	
	@ParameterizedTest
	@CsvSource({
		"8,0",
		"6,1",
		"13,2",
		}) 
	void testcalculateRowTotal(int expected, int row) { 
		assertEquals(expected, DataUtilities.calculateRowTotal(value, row));
		verify(value, times(3)).getValue(anyInt(), anyInt());
	}
	@Test
	void testCalculateRowTotalNull() {
		assertThrows(InvalidParameterException.class, () -> DataUtilities.calculateRowTotal(invalidValue, 0));
	}
	
	//test number array 2D
	@Test 
	void createNumberArray2D() {
		double[][] data = new double [2][];
		data[0] = new double[] {1.0, 2.0, 2.5}; 
		data[1] = new double[] {1.1, 2.1, 2.6, 3.3}; 

		//verify
		Number[][] actual = DataUtilities.createNumberArray2D(data);
		assertEquals(2, actual.length);
		assertEquals(3, actual[0].length);
		assertEquals(4, actual[1].length);

	}

	@Test 
	void createNumberArray2D2() {
		double[][] data = new double [3][];
		data[0] = new double[] {-1.0, -2.5}; 
		data[1] = new double[] {-1.1, -2.1, -2.6}; 
		data[2] = new double[] {-1.4, -2.9, -4.2}; 

		//verify
		Number[][] actual = DataUtilities.createNumberArray2D(data);
		assertEquals(3, actual.length);
		assertEquals(2, actual[0].length);
		assertEquals(3, actual[1].length);
		assertEquals(3, actual[2].length);

	}

	//test the NULL method for createNumberArray2D
	@Test
	void createNumberArray2DNull() {
		assertThrows(InvalidParameterException.class, () -> DataUtilities.createNumberArray2D(null));
	}


	//test cumulative percentages 
	@Test
	void getCumulativePercentages() {
		KeyedValues value = mock(KeyedValues.class);
		when(value.getValue(0)).thenReturn(5);
		when(value.getValue(1)).thenReturn(9);
		when(value.getValue(2)).thenReturn(2);

		KeyedValues actual = DataUtilities.getCumulativePercentages(value);
		ArrayList<Integer> keys = (ArrayList<Integer>) actual.getKeys();
		assertEquals(4, keys.size());

		List<Integer> keysExpected = actual.getKeys();
		assertEquals(0, keysExpected.get(0));
		assertEquals(1, keysExpected.get(1));
		assertEquals(2, keysExpected.get(2));

	}

	@Test
	void getCumulativePercentagesNull() {
		KeyedValues value = null; 
		assertThrows(InvalidParameterException.class, () -> DataUtilities.getCumulativePercentages(value));

	}
}
