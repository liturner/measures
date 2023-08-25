package de.turnertech.ucum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

}
