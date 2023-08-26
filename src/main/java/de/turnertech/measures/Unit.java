package de.turnertech.measures;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

/**
 * A Unit is a single instance of a Measurement, such as a Metre or a Degree Celsius.
 */
public class Unit {
    
    public static final Unit KELVIN = new Unit(null, (kelvin) -> kelvin, (kelvin) -> kelvin);
    public static final Unit DEGREES_CELSIUS = new Unit(KELVIN, (celsius) -> celsius + 273.15, (kelvin) -> kelvin - 273.15);
    public static final Unit METRE = new Unit(null, (metre) -> metre, (kelvin) -> kelvin);
    public static final Unit CENTIMETRE = new Unit(METRE, (centimetre) -> centimetre * 0.01, (metre) -> metre * 100.0);
    public static final Unit KILOMETRE = new Unit(METRE, (kilometer) -> kilometer * 1000.0, (metre) -> metre * 0.001);
    public static final Unit NAUTICAL_MILE = new Unit(METRE, (nmi_i) -> nmi_i * 1852.0, (metre) -> metre / 1852.0);

    private final Unit baseUnit;

    private final DoubleUnaryOperator toBaseUnitFunction;

    private final DoubleUnaryOperator fromBaseUnitFunction;

    private Unit(final Unit baseUnit, final DoubleUnaryOperator toBaseUnitFunction, final DoubleUnaryOperator fromBaseUnitFunction) {
        this.baseUnit = Objects.requireNonNullElse(baseUnit, this);
        this.toBaseUnitFunction = Objects.requireNonNull(toBaseUnitFunction);
        this.fromBaseUnitFunction = Objects.requireNonNull(fromBaseUnitFunction);
    }

    /**
     * Gets the common base unit for the family of units to which this Unit belongs. For example,
     * Metres are the base Unit for all distance Units. Even Feet, Inches and Nautical Miles will
     * share the same base unit of a Metre.
     * 
     * @return the base unit for this Unit.
     */
    public Unit getBaseUnit() {
        return baseUnit;
    }

    /**
     * Converts the supplied quantity of this Unit to its base unit. For example supplying a quantity
     * of 1 to this function on an instance of {@link Unit#KILOMETRE} will return 1000 (metres).
     * 
     * @param quantity of this Unit to convert to the base unit.
     * @return the quantity of the Base Unit which the supplied quantity represents.
     */
    public double convertToBaseUnit(final double quantity) {
        return toBaseUnitFunction.applyAsDouble(quantity);
    }

    public double convertFromBaseUnit(double quantity) {
        return fromBaseUnitFunction.applyAsDouble(quantity);
    }

}
