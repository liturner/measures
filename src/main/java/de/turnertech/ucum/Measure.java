package de.turnertech.ucum;

import java.util.Objects;

public class Measure {
    
    private double value;

    private Unit unit;

    public Measure(final double value, final Unit unit) {
        this.value = value;
        this.unit = Objects.requireNonNull(unit, "Unit instance provided to Measure constructor may not be null");
    }

    public boolean equalsWithTolerance(final Measure other, final double tolerance) {
        final Measure otherInThisUnit = UnitConverter.convert(other.value, other.unit, unit);
        final double difference = Math.abs(this.value - otherInThisUnit.value);
        return difference <= tolerance;
    }

}
