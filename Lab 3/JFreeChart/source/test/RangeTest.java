package test;

import org.jfree.data.Range;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RangeTest {
    private Range range;

    @Before
    public void setUp() throws Exception {
        this.range = new Range(1.0, 3.0);
    }

    @After
    public void tearDown() throws Exception {
        this.range = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void rangeConstructorInvalidData() {
        new Range(3.0, 1.0);
    }

    @Test
    public void combineRangeALessThanRangeB() {
        assertEquals("The output range should be -10, 5", new Range(-10, 5),
                Range.combine(new Range(-10, -5), new Range(0, 5)));
    }

    @Test
    public void combineRangeAUpperBoundEqualsRangeBLowerBound() {
        assertEquals("The output range should be -11.5, 0.2", new Range(-11.5, 0.2),
                Range.combine(new Range(-11.5, -5.2), new Range(-5.2, 0.2)));
    }

    @Test
    public void combineRangeAUpperBoundIntersectsRangeB() {
        assertEquals("The output range should be -20.2, -7.6", new Range(-20.2, -7.6),
                Range.combine(new Range(-20.2, -10), new Range(-12, -7.6)));
    }

    @Test
    public void combineRangeAContainsRangeB() {
        assertEquals("The output range should be 0, 20", new Range(0, 30),
                Range.combine(new Range(10, 20), new Range(0, 30)));
    }

    @Test
    public void combineRangeALowerBoundIntersectsRangeB() {
        assertEquals("The output range should be 5.2, 12.6", new Range(5.2, 12.6),
                Range.combine(new Range(7.5, 12.6), new Range(5.2, 10.2)));
    }

    @Test
    public void combineRangeALowerBoundEqualsRangeBUpperBound() {
        assertEquals("The output range should be -10, 20.5", new Range(-10, 20.5),
                Range.combine(new Range(10, 20.5), new Range(-10, 0)));
    }

    @Test
    public void combineRangeAGreaterThanRangeB() {
        assertEquals("The output range should be -10, 20", new Range(-10, 20),
                Range.combine(new Range(10, 20), new Range(-10, 0)));
    }

    @Test
    public void combineRangeAIsNull() {
        assertEquals("The output range should be -12.2, 50.9", new Range(-12.2, 50.9),
                Range.combine(null, new Range(-12.2, 50.9)));
    }

    @Test
    public void combineRangeBIsNull() {
        assertEquals("The output range should be --7.5, 2.2", new Range(-7.5, 2.2),
                Range.combine(new Range(-7.5, 2.2), null));
    }

    @Test
    public void constrainValueGreaterThanRangeUpperBound() {
        assertEquals("The output value should be 20.2", 20.2,
                new Range(10.5, 20.2).constrain(30.6), 0.0000001d);
    }

    @Test
    public void constrainValueEqualsRangeUpperBound() {
        assertEquals("The output value should be -5.6", -5.6,
                new Range(-10.4, -5.6).constrain(-5.6), 0.0000001d);
    }

    @Test
    public void constrainValueContainedInRange() {
        assertEquals("The output value should be 7", 7,
                new Range(5, 10).constrain(7), 0.0000001d);
    }

    @Test
    public void constrainValueEqualsRangeLowerBound() {
        assertEquals("The output value should be -10", -10,
                new Range(-10, -5).constrain(-10), 0.0000001d);
    }

    @Test
    public void constrainValueLessThanRangeLowerBound() {
        assertEquals("The output value should be 2", 2,
                new Range(2, 5).constrain(0), 0.0000001d);
    }

    @Test
    public void constrainRangeHasZeroLength() {
        assertEquals("The output value should be 2", 2,
                new Range(2, 2).constrain(6), 0.0000001d);
    }

    @Test
    public void expandToIncludeValueGreaterThanRangeUpperBound() {
        assertEquals("The output range should be 1.0, 3.0", this.range,
                Range.expandToInclude(this.range, 2.0));
    }

    @Test
    public void expandToIncludeValueEqualsRangeUpperBound() {
        assertEquals("The output range should be 0.5, 3.0", new Range(0.5, 3.0),
                Range.expandToInclude(this.range, 0.5));
    }

    @Test
    public void expandToIncludeValueContainedInRange() {
        assertEquals("The output range should be -3.0, 4.0", new Range(-3.0, 4.0),
                Range.expandToInclude(new Range(-3.0, -1.0), 4.0));
    }

    @Test
    public void expandToIncludeValueEqualsRangeLowerBound() {
        assertEquals("The output range should be -1.0, 3.0", new Range(-1.0, 3.0),
                Range.expandToInclude(new Range(-1.0, 3.0), -1.0));
    }

    @Test
    public void expandToIncludeValueLessThanRangeLowerBound() {
        assertEquals("The output range should be -1.5, 0", new Range(-1.5, 0),
                Range.expandToInclude(new Range(-1.5, 0), 0));
    }

    @Test
    public void expandToIncludeRangeHasZeroLength() {
        assertEquals("The output range should be 3.0, 5.0", new Range(3.0, 5.0),
                Range.expandToInclude(new Range(5.0, 5.0), 3.0));
    }

    @Test
    public void expandToIncludeRangeIsNull() {
        assertEquals("The output range should be 5.0, 5.0", new Range(5.0, 5.0),
                Range.expandToInclude(null, 5.0));
    }

    @Test
    public void getLengthPositiveBounds() {
        assertEquals("The output length should be 5.0", 5.0,
                new Range(5.0, 10).getLength(), 0.0000001d);
    }

    @Test
    public void getLengthNegativeBounds() {
        assertEquals("The output length should be 5.0", 5.0,
                new Range(-10.0, -5.0).getLength(), 0.0000001d);
    }

    @Test
    public void getLengthZeroBounds() {
        assertEquals("The output length should be 0.0", 0.0,
                new Range(0, 0).getLength(), 0.0000001d);
    }

    @Test(expected = NullPointerException.class)
    public void getLengthRangeIsNull() {
        ((Range) null).getLength();
    }

    @Test
    public void toStringPositiveBounds() {
        assertEquals("The output string should be \"Range[5.0, 10.0]\"", "Range[5.0,10.0]",
                new Range(5.0, 10).toString());
    }

    @Test
    public void toStringNegativeBounds() {
        assertEquals("The output string should be \"Range[-10.0, -5.0]\"", "Range[-10.0,-5.0]",
                new Range(-10.0, -5.0).toString());
    }

    @Test
    public void toStringZeroBounds() {
        assertEquals("The output string should be \"Range[0.0, 0.0]\"", "Range[0.0,0.0]",
                new Range(0, 0).toString());
    }

    @Test(expected = NullPointerException.class)
    public void toStringRangeIsNull() {
        ((Range) null).toString();
    }

    @Test
    public void expandValidData() {
        assertEquals("The output range should be 0, 4", new Range(0, 4),
                Range.expand(this.range, 0, 4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void expandNullRange() {
        Range.expand(null, 0, 4);
    }

    @Test
    public void shiftAllowCrossing() {
        assertEquals("The output range should be 2, 4", new Range(2, 4),
                Range.shift(this.range, 1, true));
    }

    @Test
    public void shiftDontAllowCrossing() {
        assertEquals("The output range should be 0, 1", new Range(0, 1),
                Range.shift(this.range, -2, false));
    }

    @Test
    public void shiftNegativeRange() {
        assertEquals("The output range should be -2, 1", new Range(-2, 1),
                Range.shift(new Range(-3, 0), 1));
    }

    @Test
    public void testHashCodeConsistency() {
        Range range1 = new Range(0, 10);
        Range range2 = new Range(0, 10);

        assertEquals("Hash code should be consistent for equal objects",
                range1.hashCode(), range2.hashCode());
    }

    @Test
    public void testHashCodeInconsistency() {
        Range range1 = new Range(0, 10);
        Range range2 = new Range(5, 15);

        assertNotEquals("Hash code should be different for different objects",
                range1.hashCode(), range2.hashCode());
    }

}
