package de.turnertech.measures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class UnitTests {
    
    @Test
    void kilometerTests() {
        assertSame(Unit.METRE, Unit.KILOMETRE.getBaseUnit());
        assertEquals(1.0, Unit.KILOMETRE.convertFromBaseUnit(1000.0));
        assertEquals(1000.0, Unit.KILOMETRE.convertToBaseUnit(1.0));
    }

    @Test
    void metreTests() {
        assertSame(Unit.METRE, Unit.METRE.getBaseUnit());
        assertEquals(1.0, Unit.METRE.convertFromBaseUnit(1.0));
        assertEquals(1.0, Unit.METRE.convertToBaseUnit(1.0));
    }

    @Test
    void centimetreTests() {
        assertSame(Unit.METRE, Unit.CENTIMETRE.getBaseUnit());
        assertEquals(100.0, Unit.CENTIMETRE.convertFromBaseUnit(1.0));
        assertEquals(1.0, Unit.CENTIMETRE.convertToBaseUnit(100.0));
    }

    @Test
    void nauticalMileTests() {
        assertSame(Unit.METRE, Unit.NAUTICAL_MILE.getBaseUnit());
        assertEquals(1.0, Unit.NAUTICAL_MILE.convertFromBaseUnit(1852.0));
        assertEquals(1852.0, Unit.NAUTICAL_MILE.convertToBaseUnit(1.0));
    }

    @Test
    void kelvinTests() {
        assertSame(Unit.KELVIN, Unit.KELVIN.getBaseUnit());
        assertEquals(1.0, Unit.KELVIN.convertFromBaseUnit(1.0));
        assertEquals(1.0, Unit.KELVIN.convertToBaseUnit(1.0));
    }

    @Test
    void celsiusTests() {
        assertSame(Unit.KELVIN, Unit.DEGREES_CELSIUS.getBaseUnit());
        assertEquals(1.0, Unit.DEGREES_CELSIUS.convertFromBaseUnit(274.15));
        assertEquals(274.15, Unit.DEGREES_CELSIUS.convertToBaseUnit(1.0));
    }

}
