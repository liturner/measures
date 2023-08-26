package de.turnertech.measures;

import java.util.Objects;

/**
 * <p>A Measure is a description of some quantity of a {@link Unit}, where a {@link Unit} is a standardised and
 * accepted quantity, size, ammount, period or other kind of measurement. For example, a {@link Unit#METRE} 
 * represents a standardised length.</p>
 * 
 * <p>This implementation stores an immutable {@link Unit} instance. To obtain a {@link Measure} with another
 * {@link Unit} you must either explicitely instantiate a new instance, or convert the Measure using one of the
 * either {@link #convertTo(Unit)} or the class {@link UnitConverter}</p>
 */
public class Measure {
    
    private double quantity;

    private final Unit unit;

    public Measure(final double value, final Unit unit) {
        this.quantity = value;
        this.unit = Objects.requireNonNull(unit, "Unit instance provided to Measure constructor may not be null");
    }

    public boolean equalsWithTolerance(final Measure other, final double tolerance) {
        final Measure otherInThisUnit = UnitConverter.convert(other.quantity, other.unit, unit);
        final double difference = Math.abs(this.quantity - otherInThisUnit.quantity);
        return difference <= tolerance;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(final double quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public Measure convertTo(final Unit newUnit) {
        return UnitConverter.convert(this, newUnit);
    }

}
