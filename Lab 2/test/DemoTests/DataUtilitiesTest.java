package DemoTests;

import junit.framework.TestCase;
import org.jfree.data.*;
import org.junit.Before;
import org.junit.Test;

public class DataUtilitiesTest extends TestCase {
    private Values2D values2D;

    @Before
    public void setUp() {
        DefaultKeyedValues2D testValues = new DefaultKeyedValues2D();
        this.values2D = testValues;
        testValues.addValue(1, 0, 0);
        testValues.addValue(4, 1, 0);
    }

    @Test
    public void testGetCumulativePercentages() {
        DefaultKeyedValues keyValues = new DefaultKeyedValues();
        keyValues.addValue((Comparable) 0.0, 6.0);
        keyValues.addValue((Comparable) 1.0, 11.0);
        keyValues.addValue((Comparable) 2.0, 3.0);
        KeyedValues objectUnderTest = DataUtilities.getCumulativePercentages((KeyedValues) keyValues);

        assertEquals((double) objectUnderTest.getValue(2), 1.0, 0.0000001d);
    }

    @Test
    public void testNullDataColumnTotal(){
        try{
            DataUtilities.calculateColumnTotal(null, 0);
            fail("No exception thrown. The expected outcome was: a thrown exception of type IllegalArgumentException");
        } catch (Exception e){
            assertTrue("Incorrect exception type thrown", e.getClass().equals(IllegalArgumentException.class));
        }
    }

    @Test
    public void testValidDataAndColumnTotal(){
        assertEquals("Wrong sum returned. It should be 5.0", 5.0, DataUtilities.calculateColumnTotal(this.values2D, 0), 0.0000001d);
    }
}
