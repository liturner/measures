package test.de.turnertech.measures;

import de.turnertech.measures.Measure;
import de.turnertech.measures.Unit;
import de.turnertech.measures.UnitConverter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UnitConverterTests {

    @Test
    void meterConversions() {
        final Measure m1 = new Measure(5000, Unit.METRE);
        final Measure m2 = new Measure(5, Unit.KILOMETRE);

        assertTrue(m1.equalsWithTolerance(m2, 0.0000000001));
        assertTrue(m2.equalsWithTolerance(m1, 0.0000000001));
    }

    @Test
    void nauticalMileOverflow() {
        assertEquals(Double.POSITIVE_INFINITY, Unit.NAUTICAL_MILE.convertToBaseUnit(Double.MAX_VALUE).getQuantity());
        assertTrue(UnitConverter.convert(Double.MAX_VALUE, Unit.KILOMETRE, Unit.NAUTICAL_MILE).getQuantity() < Double.MAX_VALUE);
    }

}
