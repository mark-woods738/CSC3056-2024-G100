package DemoTests;

import org.jfree.data.Range;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RangeTest {
    private Range range;

    @Before
    public void setUp() throws Exception {
        this.range = new Range(-1, 1);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRange() {
        assertEquals("The central value of -1 and 1 should be 0", 0, this.range.getCentralValue(), 0.0000001d);
    }
}
