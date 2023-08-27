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

    /**
     * Constructs an instance with the provided parameters.
     * @param quantity The quantity of Units in this Measure. Mutable.
     * @param unit The Unit of this Measure. This is immutable.
     */
    public Measure(final double quantity, final Unit unit) {
        this.quantity = quantity;
        this.unit = Objects.requireNonNull(unit, "Unit instance provided to Measure constructor may not be null");
    }

    /**
     * <p>Helper function for checking if the Measure is equal to within a supplied
     * tolerance. This is usefull in several situations as the Double Precision
     * mechanism can very quickly introduce innacuracies which prevent .equals() 
     * from providing a useful result.</p>
     * 
     * <p>If the Measures do not suare the same base unit, a conversion will 
     * first be attempted.</p>
     * 
     * @param other Measure to check against.
     * @param tolerance allowed difference between the numbers.
     * @return true or false.
     */
    public boolean equalsWithTolerance(final Measure other, final double tolerance) {
        final Measure otherInThisUnit = UnitConverter.convert(other.quantity, other.unit, unit);
        final double difference = Math.abs(this.quantity - otherInThisUnit.quantity);
        return difference <= tolerance;
    }

    /**
     * Gets the quantity of Units stored in this Measure.
     * @return the quantity of Units stored in this Measure.
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of Units stored in this Measure.
     * @param quantity the quantity of Units stored in this Measure.
     */
    public void setQuantity(final double quantity) {
        this.quantity = quantity;
    }

    /**
     * Sets the Unit of this Measure.
     * @return the Unit of this Measure.
     */
    public Unit getUnit() {
        return unit;
    }

    /**
     * <p>Helper function to convert this Measure to a new Measure with the 
     * supplied newUnit parameter. This function exists in place of a setter as
     * the unit member is immutable.</p>
     * 
     * <p>Internally this function simply delegates to 
     * {@link UnitConverter#convert(Measure, Unit)}</p>
     * 
     * @param newUnit to be used in the creation of the new Measure instance.
     * @return a new Measure instance in the supplied Unit.
     */
    public Measure convertTo(final Unit newUnit) {
        return UnitConverter.convert(this, newUnit);
    }

}
