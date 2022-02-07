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
	
}
