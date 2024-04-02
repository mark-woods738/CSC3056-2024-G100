package test;

import static org.junit.Assert.*;
import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.DefaultKeyedValues2D;
import org.jfree.data.KeyedValues;
import org.jfree.data.Values2D;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DataUtilitiesTest extends DataUtilities {

    private Values2D values2D;

    @Before
    public void setUp() throws Exception {
        DefaultKeyedValues2D defaultValues2D = new DefaultKeyedValues2D();
        defaultValues2D.addValue(1, 0, 0);
        defaultValues2D.addValue(2, 1, 0);
        defaultValues2D.addValue(3, 0, 1);
        defaultValues2D.addValue(4, 1, 1);
        values2D = defaultValues2D;

    }

    @After
    public void tearDown() throws Exception {
        values2D = null;
    }

	@Test
	public void testValidDataAndColumnColumnTotal() {
		assertEquals("Wrong sum returned. It should be 5.0", 
				5.0, DataUtilities.calculateColumnTotal(values2D, 0), 0.0000001d);
	}
	
	@Test
    public void testCalculateColumnTotal_ValidData() {
        double result = DataUtilities.calculateColumnTotal(values2D, 0);
        assertEquals("Column total should be 4.", 4.0, result, 0.0000001d);
    }

    @Test
    public void testCalculateColumnTotal_Null2DValue(){
        DefaultKeyedValues2D testValues2D = new DefaultKeyedValues2D();
        testValues2D.addValue(null, 0, 0);
        DataUtilities.calculateColumnTotal(testValues2D, 0);
    }

    @Test(expected = NullPointerException.class)
    public void testCalculateColumnTotal_NullData() {
        DataUtilities.calculateColumnTotal(null, 2);
    }

    @Test
    public void testCalculateRowTotal_ValidData() {
        double result = DataUtilities.calculateRowTotal(values2D, 0);
        assertEquals("Row total should be 4.", 4.0, result, 0.0000001d);
    }

    @Test
    public void testCalculateRowTotal_Null2DValue(){
        DefaultKeyedValues2D testValues2D = new DefaultKeyedValues2D();
        testValues2D.addValue(null, 0, 0);
        DataUtilities.calculateRowTotal(testValues2D, 0);
    }

    @Test(expected = NullPointerException.class)
    public void testCalculateRowTotal_NullData() {
        DataUtilities.calculateRowTotal(null, 0);
    }

    @Test
    public void testCreateNumberArray_ValidData() {
        double[] doubleArray = new double[]{1.0, 2.0, 3.0};
        Number[] result = DataUtilities.createNumberArray(doubleArray);
        Number[] expected = new Number[]{1.0, 2.0, 3.0};
        assertArrayEquals("Array of numbers should match expected.", expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNumberArray_NullData() {
        DataUtilities.createNumberArray(null);
    }

    @Test
    public void testCreateNumberArray2D_ValidData() {
        double[][] doubleArray2D = new double[][]{{1.0, 2.0}, {3.0, 4.0}};
        Number[][] result = DataUtilities.createNumberArray2D(doubleArray2D);
        Number[][] expected = new Number[][]{{1.0, 2.0}, {3.0, 4.0}};
        assertArrayEquals("2D array of numbers should match expected.", expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNumberArray2D_NullData() {
        DataUtilities.createNumberArray2D(null);
    }

    @Test
    public void testGetCumulativePercentages_NullKeyValue(){
        DefaultKeyedValues testValues = new DefaultKeyedValues();
        testValues.addValue("Key1", null);
        DataUtilities.getCumulativePercentages(testValues);
    }

    @Test
    public void testGetCumulativePercentages_ValidData() {
        DefaultKeyedValues keyedValues = new DefaultKeyedValues();
        keyedValues.addValue("Key1", 5);
        keyedValues.addValue("Key2", 9);
        keyedValues.addValue("Key3", 2);

        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValues);

        assertEquals("Cumulative percentage calculation for Key1 is incorrect.", 
                 0.3125, result.getValue("Key1").doubleValue(), 0.0000001d);
        assertEquals("Cumulative percentage calculation for Key2 is incorrect.", 
                 0.875, result.getValue("Key2").doubleValue(), 0.0000001d);
        assertEquals("Cumulative percentage calculation for Key3 is incorrect.", 
                 1.0, result.getValue("Key3").doubleValue(), 0.0000001d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCumulativePercentages_NullData() {
        DataUtilities.getCumulativePercentages(null);
    }
}
