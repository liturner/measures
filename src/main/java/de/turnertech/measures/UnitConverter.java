package de.turnertech.measures;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

/**
 * The UnitConverter handles specialist conversions with more accuracy than would be possible
 * via conversions to base units. For example if converting from a unit like Kilometer to Nautical 
 * Mile, it is possble that converting first to meters would cause overrun errors or lose accuracy
 * due to binary limitations.
 * 
 * 1. Functions
 * 2. Scalars
 * 3. Divisors (Scalar)
 * 4. Unit Base Unit
 */
public class UnitConverter {
    
    private static final HashMap<AbstractMap.SimpleImmutableEntry<Object, Object>, Double> scalarMap = new HashMap<>();
    private static final HashMap<AbstractMap.SimpleImmutableEntry<Object, Object>, DoubleUnaryOperator> functionMap = new HashMap<>();

    static {
        scalarMap.put(new AbstractMap.SimpleImmutableEntry<>(Unit.NAUTICAL_MILE, Unit.CENTIMETRE), 185200.0);
        scalarMap.put(new AbstractMap.SimpleImmutableEntry<>(Unit.NAUTICAL_MILE, Unit.METRE), 1852.0);
        scalarMap.put(new AbstractMap.SimpleImmutableEntry<>(Unit.NAUTICAL_MILE, Unit.KILOMETRE), 1.852);
        scalarMap.put(new AbstractMap.SimpleImmutableEntry<>(Unit.KILOMETRE, Unit.METRE), 1000.0);
        scalarMap.put(new AbstractMap.SimpleImmutableEntry<>(Unit.KILOMETRE, Unit.CENTIMETRE), 100000.0);
    }

    public static Double putScalar(final Object unitIn, final Object unitOut, final double scalar) {
        if(scalar == 0.0) {
            throw new ArithmeticException("scalar values of 0 are not accepted in the UnitConverter.");
        }
        return scalarMap.put(new AbstractMap.SimpleImmutableEntry<>(Objects.requireNonNull(unitIn), Objects.requireNonNull(unitOut)), scalar);
    }

    public static DoubleUnaryOperator putFunction(final Object unitIn, final Object unitOut, final DoubleUnaryOperator function) {
        return functionMap.put(new AbstractMap.SimpleImmutableEntry<>(Objects.requireNonNull(unitIn), Objects.requireNonNull(unitOut)), Objects.requireNonNull(function));
    }

    public static Measure convert(final Measure in, final Unit unitOut) {
        Objects.requireNonNull(in);

        return convert(in.getQuantity(), in.getUnit(), unitOut);
    }

    public static Measure convert(final double in, final Unit unitIn, final Unit unitOut) {
        Objects.requireNonNull(unitIn);
        Objects.requireNonNull(unitOut);

        // Same Unit case
        if(unitIn == unitOut) {
            return new Measure(in, unitOut);
        }

        // Function case
        final DoubleUnaryOperator conversionFunction = functionMap.getOrDefault(new AbstractMap.SimpleImmutableEntry<>(unitIn, unitOut), null);
        if(conversionFunction != null) {
            return new Measure(conversionFunction.applyAsDouble(in), unitOut);
        }

        // Scalar case
        Double variable = scalarMap.getOrDefault(new AbstractMap.SimpleImmutableEntry<>(unitIn, unitOut), null);
        if(variable != null) {
            return new Measure(in * variable, unitOut);
        }

        // Divisor case
        variable = scalarMap.getOrDefault(new AbstractMap.SimpleImmutableEntry<>(unitOut, unitIn), null);
        if(variable != null) {
            return new Measure(in / variable, unitOut);
        }

        // Convert to shared base unit
        if(unitIn.getBaseUnit() == unitOut.getBaseUnit()) {
            final double inInBaseUnit = unitIn.convertToBaseUnit(in);
            return new Measure(unitOut.convertFromBaseUnit(inInBaseUnit), unitOut);
        }

        throw new UnsupportedOperationException("Conversion from " + unitIn.toString() + " to " + unitOut.toString() + " is not supported.");
    }

}
