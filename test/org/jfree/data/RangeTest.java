package org.jfree.data;

import static org.junit.jupiter.api.Assertions.*;

import java.security.InvalidParameterException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import original.Range;

class RangeTest {
	private Range exampleRange;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	//combine tests
	@ParameterizedTest
	@CsvSource({
		"0, 0, 1, 2, 0, 2",
		"-3,2,0,1,-3,2",
		"0,0,0,0,0,0",
		"500,500,500,500,500,500"
	})
	void testCombine(double lb1, double ub1, double lb2, double ub2, double lb, double ub) {
		Range range1 = new Range(lb1, ub1);
		Range range2 = new Range(lb2, ub2);
		Range actualRange = Range.combine(range1, range2);
		
		//verify
		Range expectedRange = new Range(lb, ub);
		assertEquals(expectedRange, actualRange);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1,2,'1,2'",
		"0,0,'0,0'",
		"100,100,'100,100'"
	})
	void testCombineNull(double lb1, double ub1, String msg) {
		Range range1 = new Range(lb1, ub1);
		Range actualRange = Range.combine(null, range1);
		//verify
		Range expectedRange = new Range(lb1, ub1);
		assertEquals(expectedRange, actualRange, msg);
	}
	
	@ParameterizedTest
	@CsvSource({
		"1,2",
		"0,0",
		"100,100"
	})
	void testCombineNullSecond(double lb1, double ub1) {
		Range range1 = new Range(lb1, ub1);
		Range actualRange = Range.combine(range1, null);
		//verify
		Range expectedRange = new Range(lb1, ub1);
		assertEquals(expectedRange, actualRange);
	}
	
	@Test
	void testCombineBothNull() {
		Range actualRange = Range.combine(null, null);
		//verify
		Range expectedRange = null;
		assertEquals(expectedRange, actualRange);
	}
	
	//Constrain tests
	@ParameterizedTest
	@CsvSource({
		"2,5,6,5",
		"2,5,0,2",
		"-3,4,0,0",
		"-7,-2,-9,-7",
		"-7,-2,-1,-2"
		})
	void testContrain(double lb1, double ub1, double con, double exp) {
		Range range1 = new Range(lb1, ub1);
		double actual = range1.constrain(con);
		
		//verify
		double expected = exp;
		assertEquals(expected, actual);
	}
	
	//Contains tests
	@ParameterizedTest
	@CsvSource({
		"0,9,10,false",
		"0,9,2,true",
		"0,9,-1,false",
		"0,9,0,true",
		"0,9,9,true",
		"0,9,-9,false",
		"-10,2,3,false",
		"-10,2,-10,true",
		"-10,2,-11,false",
		"-10,2,0,true",
		"-10,2,2,true"
		})
	void testContains(double lb1, double ub1, double con, boolean exp) {
		Range range1 = new Range(lb1, ub1);
		boolean actual = range1.contains(con);
		
		//verify
		boolean expected = exp;
		assertEquals(expected, actual);
	}
	
	//equals tests
	@ParameterizedTest
	@CsvSource({
		"5,5,5,5,true",
		"1,1,1,2,false",
		"1,2,1,1,false",
		"-1,1,1,1,false"
		})
	void testEquals(double lb1, double ub1, double lb2, double ub2, boolean exp) {
		Range range1 = new Range(lb1, ub1);
		Range range2 = new Range(lb2, ub2);
		boolean actual = range1.equals(range2);
		
		//verify
		boolean expected = exp;
		assertEquals(expected, actual);
	}
	
	void testEqualsBothNull() {
		Range range1 = null;
		Range range2 = null;
		boolean actual = range1.equals(range2);
		
		//verify
		boolean expected = true;
		assertEquals(expected, actual);
	}
	
	void testEqualsOneNull() {
		Range range1 = new Range(1,1);
		Range range2 = null;
		boolean actual = range1.equals(range2);
		
		//verify
		boolean expected = false;
		assertEquals(expected, actual);
	}
	
	//expand tests
	@Test
	void testNullRangeExpand() {
		Range range1 = null;
		double lowerMargin = 0.25;
        double upperMargin = 0.50;
        
        //verify
        assertThrows(InvalidParameterException.class, () -> Range.expand(range1,lowerMargin,upperMargin));
	}
	
	@ParameterizedTest
	@CsvSource({
		"2,6,0.25,0.5,1,8",
		"3,9,.5,.75,0,13.5",
		"-2,10,-.1,0,-3.2,10"
		})
	void testExpand(double lb1, double ub1, double lM, double uM, double lb2, double ub2) {
		Range range1 = new Range(lb1, ub1);
		Range actual = Range.expand(range1, lM, uM);
		
		//verify
		Range expected = new Range(lb2, ub2);
		assertEquals(expected, actual);
	}
	
	@ParameterizedTest
	@CsvSource({
		"0, 0, Range[0,0]",
		"-3,2, Range[-3,2]",
		"5,10, Range[5,10]"
	})
	void testToString(double lb1, double ub1, String expected) {
		Range range1 = new Range(lb1, ub1);
		String actual = range1.toString();
		assertEquals(expected, actual);
	}
	
	
	//tests to find the central value between the range
	@Test
	void testGetCentralValue() {
		exampleRange = new Range(-1, 1);
		assertEquals(0, exampleRange.getCentralValue(),0.1d,"The central value of (-1,1) is 0");
	}
	
	@Test
	void testGetCentralValue2() {
		exampleRange = new Range(1.0, 5.0);
		assertEquals(3.0, exampleRange.getCentralValue(),0.1d,"The central value of (1.0,5.0) is 3.0");
	}
	
	
	//testing to return all the values on the specified range and contain the specified value
	@Test
	void testExpandToInclude() {
		Range exampleRange = new Range(1, 3);
		Range newRange = Range.expandToInclude(exampleRange, 2);
		assertEquals(exampleRange, newRange);
		
	}
	
	@Test
	void testExpandToIncludeNegative() {
		Range exampleRange = new Range(-3, -1);
		Range newRange = Range.expandToInclude(exampleRange, -2);
		assertEquals(exampleRange, newRange);
		
	}
	
	
	//testing for the length of the range using numbers, letters and null instances
	@Test 
	void testGetLength() {
		double exampleRange = "1234567890".length();
		double expectedLength = 10; 
		assertEquals(exampleRange, expectedLength);
	}
	
	@Test 
	void testGetLengthCharacter() {
		double exampleRange = "@#$%^&*".length();
		double expectedLength = 7; 
		assertEquals(exampleRange, expectedLength);
	}
	
	void testGetLengthNull() {
		double exampleRange = "1234567890".length();
		String expectedLength = null; 
		assertEquals(exampleRange, expectedLength);
	}
	
	
	//testing for the lower bound of the range using both positive and negative values 
	@Test 
	void testGetLowerBound() {
		exampleRange = new Range(0.0, 10.0); 
		assertEquals(exampleRange.getLowerBound(), 0.0, 10.0);
		
	}
	
	@Test 
	void testGetLowerBound2() {
		exampleRange = new Range(-10.0, 1.0); 
		assertEquals(exampleRange.getLowerBound(), -10.0, 1.0);
		
	}
	

	//testing for the Upper bounds of the range using both positive and negative values 
	@Test 
	void testGetUpperBound() {
		exampleRange = new Range(0.0, 100.0);
		assertEquals(exampleRange.getUpperBound(), 0.0, 100.0);
		
	}
	
	@Test 
	void testGetUpperBound2() {
		exampleRange = new Range(-1.0, 1.0);
		assertEquals(exampleRange.getUpperBound(), -1.0, 1.0);
		
	}
	
	
	//testing for range if it intersected with the specified range and returns either false or true 
	@Test 
	void testIntersects() {
		Range exampleRange = new Range(1, 5);
		boolean outcome = exampleRange.intersects(7, 9);
		assertTrue(!outcome);
	}
	
	@Test 
	void testIntersects2() {
		Range exampleRange = new Range(2, 6);
		boolean outcome = exampleRange.intersects(2, 6);
		assertTrue(outcome);
	}
	
	
	//testing for right shift without zero crossing 
	@Test 
	void testShift() {
		Range exampleRange = new Range(1.0, 5.0);
		Range actual = Range.shift(exampleRange, 1.0);
		Range expected = new Range(2.0, 6.0);
		assertEquals(actual, expected);
	}
	
	@Test 
	void testShiftNegativeNumber() {
		Range exampleRange = new Range(-5.0, -3.0);
		Range actual = Range.shift(exampleRange, -2.0);
		Range expected = new Range(-7.0, -5.0);
		assertEquals(actual, expected);
	}
	
	@Test 
	void testShiftNull() {
		Range exampleRange = new Range(1.0, 5.0);
		Range actual = Range.shift(exampleRange, 1.0);
		Range expected = new Range(2.0, 6.0);
		
		//verify for null 
		boolean error = actual.equals(null);
		assertEquals(error, expected);
	}
	
	
	//testing for right shift using zero crossing 
	@Test
	void testShiftZeroCrossing() {
		Range exampleRange = new Range(1.0, 5.0);
		Range actual = Range.shift(exampleRange, 1.0, true);
		Range expected = new Range(2.0, 6.0);
		assertEquals(actual, expected);
	}
	
	@Test
	void testShiftZeroCrossingNegativeNumber() {
		Range exampleRange = new Range(-5.0, -3.0);
		Range actual = Range.shift(exampleRange, -1.0, true);
		Range expected = new Range(-6.0, -4.0);
		assertEquals(actual, expected);
	}
	
	@Test
	void testShiftZeroCrossingNull() {
		Range exampleRange = new Range(1.0, 5.0);
		Range actual = Range.shift(exampleRange, 1.0, true);
		Range expected = new Range(2.0, 6.0);
		
		//verify for null
		boolean error = actual.equals(null); 
		assertEquals(error, expected);
	}
}
