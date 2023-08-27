package de.turnertech.measures;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

/**
 * A Unit is a single instance of a Measurement, such as a Metre or a Degree Celsius.
 */
public class Unit {
    
    /** K */
    public static final Unit KELVIN = new Unit(null, (kelvin) -> kelvin, (kelvin) -> kelvin);
    
    /** °C */
    public static final Unit DEGREES_CELSIUS = new Unit(KELVIN, (celsius) -> celsius + 273.15, (kelvin) -> kelvin - 273.15);
    
    /** °F */
    public static final Unit DEGREES_FAHRENHEIT = new Unit(KELVIN, (fahrenheit) -> (fahrenheit - 32) * 5.0/9.0 + 273.15, (kelvin) -> (kelvin - 273.15) * 9/5 + 32);
    
    /** m */
    public static final Unit METRE = new Unit(null, (metre) -> metre, (kelvin) -> kelvin);
    
    /** cm */
    public static final Unit CENTIMETRE = new Unit(METRE, (centimetre) -> centimetre * 0.01, (metre) -> metre * 100.0);
    
    /** in_i */
    public static final Unit INCH = new Unit(METRE, (inch) -> inch * 0.0254, (metre) -> metre / 0.0254);
    
    /** ft_i */
    public static final Unit FOOT = new Unit(METRE, (foot) -> foot * 0.3048, (metre) -> metre / 0.3048);
    
    /** yd_i */
    public static final Unit YARD = new Unit(METRE, (yard) -> yard * 0.9144, (metre) -> metre / 0.9144);
    
    /** mi_i */
    public static final Unit MILE = new Unit(METRE, (mile) -> mile * 1609.344, (metre) -> metre / 1609.344);
    
    /** km */
    public static final Unit KILOMETRE = new Unit(METRE, (kilometer) -> kilometer * 1000.0, (metre) -> metre * 0.001);
    
    /** nmi_i */
    public static final Unit NAUTICAL_MILE = new Unit(METRE, (nmi_i) -> nmi_i * 1852.0, (metre) -> metre / 1852.0);
    
    /** s */
    public static final Unit SECOND = new Unit(null, (second) -> second, (second) -> second);
    
    /** g */
    public static final Unit GRAM = new Unit(null, (gram) -> gram, (gram) -> gram);
    
    /** lb_av */
    public static final Unit POUND = new Unit(Unit.GRAM, (gram) -> gram * 453.59237, (pound) -> pound / 453.59237);
    
    /** rad */
    public static final Unit RADIAN = new Unit(null, (rad) -> rad, (rad) -> rad);
    
    /** deg */
    public static final Unit DEGREE = new Unit(Unit.RADIAN, (deg) -> deg * Math.PI / 180.0, (rad) -> rad * 180 / Math.PI);

    private final Unit baseUnit;

    private final DoubleUnaryOperator toBaseUnitFunction;

    private final DoubleUnaryOperator fromBaseUnitFunction;

    private Unit(final Unit baseUnit, final DoubleUnaryOperator toBaseUnitFunction, final DoubleUnaryOperator fromBaseUnitFunction) {
        this.baseUnit = Objects.requireNonNullElse(baseUnit, this);
        this.toBaseUnitFunction = Objects.requireNonNull(toBaseUnitFunction);
        this.fromBaseUnitFunction = Objects.requireNonNull(fromBaseUnitFunction);
    }
    
    /**
     * Helper function for creating measures with a Unit.
     * 
     * @param quantity the quantity of this unit which should be in the Measure.
     * @return a new Measure instance.
     */
    public Measure createMeasure(final double quantity) {
        return new Measure(quantity, this);
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
     * Converts a supplied quantity of this Unit to a quantity of its base unit.
     * For example supplying a quantity of 1 to this function on an instance of 
     * {@link Unit#KILOMETRE} will return 1000 (metres).
     * 
     * @param quantity of this Unit to convert to the base unit.
     * @return the quantity of the Base Unit which the supplied quantity represents.
     */
    public Measure convertToBaseUnit(final double quantity) {
        final Measure resultingValue = new Measure(toBaseUnitFunction.applyAsDouble(quantity), this.baseUnit);
        if(quantity != Double.POSITIVE_INFINITY && quantity != Double.POSITIVE_INFINITY && (resultingValue.getQuantity() == Double.NEGATIVE_INFINITY || resultingValue.getQuantity() == Double.POSITIVE_INFINITY)) {
            throw new ArithmeticException("Conversion caused overflow.");
        }
        return resultingValue;
    }

    /**
     * Converts a supplied quantity of this Units base unit to a quantity of 
     * itself.
     * 
     * @param quantity of this Unit base unit to convert to this unit.
     * @return a measure with this Unit.
     */
    public Measure convertFromBaseUnit(final double quantity) {
        final Measure resultingValue = new Measure(fromBaseUnitFunction.applyAsDouble(quantity), this);
        if(quantity != Double.POSITIVE_INFINITY && quantity != Double.POSITIVE_INFINITY && (resultingValue.getQuantity() == Double.NEGATIVE_INFINITY || resultingValue.getQuantity() == Double.POSITIVE_INFINITY)) {
            throw new ArithmeticException("Conversion caused overflow.");
        }
        return resultingValue;
    }

}
