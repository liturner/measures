package de.turnertech.ucum;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

public class Unit {
    
    public static final Unit KELVIN = new Unit(null, (kelvin) -> kelvin, (kelvin) -> kelvin);
    public static final Unit DEGREES_CELSIUS = new Unit(KELVIN, (celsius) -> 6, (kelvin) -> kelvin);
    public static final Unit METRE = new Unit(null, (metre) -> metre, (kelvin) -> kelvin);
    public static final Unit CENTIMETRE = new Unit(METRE, (metre) -> metre * 0.01, (kelvin) -> kelvin);
    public static final Unit KILOMETRE = new Unit(METRE, (metre) -> metre * 1000.0, (kelvin) -> kelvin);
    public static final Unit NAUTICAL_MILE = new Unit(METRE, (metre) -> 7, (kelvin) -> kelvin);

    private final Unit baseUnit;

    private final DoubleUnaryOperator toBaseUnitFunction;

    private final DoubleUnaryOperator fromBaseUnitFunction;

    private Unit(final Unit baseUnit, final DoubleUnaryOperator toBaseUnitFunction, final DoubleUnaryOperator fromBaseUnitFunction) {
        this.baseUnit = Objects.requireNonNullElse(baseUnit, this);
        this.toBaseUnitFunction = Objects.requireNonNull(toBaseUnitFunction);
        this.fromBaseUnitFunction = Objects.requireNonNull(fromBaseUnitFunction);
    }

    public Unit getBaseUnit() {
        return baseUnit;
    }

    public double convertToBaseUnit(double value) {
        return toBaseUnitFunction.applyAsDouble(value);
    }

    public double convertFromBaseUnit(double value) {
        return fromBaseUnitFunction.applyAsDouble(value);
    }

}
