package test.de.turnertech.measures;

import de.turnertech.measures.Measure;
import de.turnertech.measures.Unit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MeasureTests {
    
    @Test
    void equalsWithToleranceTests() {
        final Measure m1 = new Measure(10.0, Unit.METRE);
        final Measure m2 = new Measure(10.0005, Unit.METRE);

        assertFalse(m1.equalsWithTolerance(m2, 0.0000000001));
        assertTrue(m1.equalsWithTolerance(m2, 0.0006));
        assertTrue(m1.equalsWithTolerance(m2, 0.00051111111));
        assertFalse(m1.equalsWithTolerance(m2, 0.0004));
        assertFalse(m1.equalsWithTolerance(m2, 0.00049999999));
    }
    
    @Test
    void equality() {
        assertEquals(Unit.KILOMETRE.createMeasure(1.0), Unit.METRE.createMeasure(1000.0).convertTo(Unit.KILOMETRE));
    }

}
