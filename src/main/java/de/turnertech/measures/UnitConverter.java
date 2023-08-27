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
    
    private static final HashMap<AbstractMap.SimpleImmutableEntry<Unit, Unit>, Double> scalarMap = new HashMap<>();
    private static final HashMap<AbstractMap.SimpleImmutableEntry<Unit, Unit>, DoubleUnaryOperator> functionMap = new HashMap<>();

    static {
        scalarMap.put(new AbstractMap.SimpleImmutableEntry<>(Unit.NAUTICAL_MILE, Unit.CENTIMETRE), 185200.0);
        scalarMap.put(new AbstractMap.SimpleImmutableEntry<>(Unit.NAUTICAL_MILE, Unit.METRE), 1852.0);
        scalarMap.put(new AbstractMap.SimpleImmutableEntry<>(Unit.NAUTICAL_MILE, Unit.KILOMETRE), 1.852);
        scalarMap.put(new AbstractMap.SimpleImmutableEntry<>(Unit.KILOMETRE, Unit.METRE), 1000.0);
        scalarMap.put(new AbstractMap.SimpleImmutableEntry<>(Unit.KILOMETRE, Unit.CENTIMETRE), 100000.0);
    }

    /**
     * <p>Adds a scalar function for transitioning from the unitIn, to the 
     * unitOut. This scalar will also be used as a divisor in the reverse 
     * direction.</p>
     * 
     * @param unitIn the Unit in which the in parameter is represented.
     * @param unitOut the desired Unit which should be present in the returned.
     * @param scalar the scalar.
     * @return the result of Map.put(...)
     */
    public static Double putScalar(final Unit unitIn, final Unit unitOut, final double scalar) {
        if(scalar == 0.0) {
            throw new ArithmeticException("scalar values of 0 are not accepted in the UnitConverter.");
        }
        return scalarMap.put(new AbstractMap.SimpleImmutableEntry<>(Objects.requireNonNull(unitIn), Objects.requireNonNull(unitOut)), scalar);
    }

    /**
     * <p>Adds a conversion function for transitioning from the unitIn, to the
     * unitOut. This should be the highest accuracy possible and will be 
     * prioritised over other conversion methods.</p>
     * 
     * @param unitIn the Unit in which the in parameter is represented.
     * @param unitOut the desired Unit which should be present in the returned.
     * @param function the conversion function.
     * @return the response to Map.put(...)
     */
    public static DoubleUnaryOperator putFunction(final Unit unitIn, final Unit unitOut, final DoubleUnaryOperator function) {
        return functionMap.put(new AbstractMap.SimpleImmutableEntry<>(Objects.requireNonNull(unitIn), Objects.requireNonNull(unitOut)), Objects.requireNonNull(function));
    }

    /**
     * <p>Converts between Units using one of a number of conversion options. 
     * The conversion method used is prioritised to enable accuracy.</p>
     * 
     * @param in Measure to convert.
     * @param unitOut Desired output Unit
     * @return a measure containing the in parameter represented in the unitOut.
     */
    public static Measure convert(final Measure in, final Unit unitOut) {
        Objects.requireNonNull(in);

        return convert(in.getQuantity(), in.getUnit(), unitOut);
    }

    /**
     * <p>Converts between Units using one of a number of conversion options. 
     * The conversion method used is prioritised to enable accuracy.</p>
     * 
     * <ol>
     * <li>Conversion Function stored in this class</li>
     * <li>Conversion Scalar stored in this class</li>
     * <li>Conversion Divisor using the Scalar stored in this class</li>
     * <li>Conversion to/from the base Units stored in the Unit class</li>
     * </ol>
     * 
     * @param in value to convert between Units.
     * @param unitIn the Unit in which the in parameter is represented.
     * @param unitOut the desired Unit which should be present in the returned
     * measure
     * @return a measure containing the in parameter represented in the unitOut.
     * @throws UnsupportedOperationException if conversion is not supported
     */
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
            final Measure inInBaseUnit = unitIn.convertToBaseUnit(in);
            return unitOut.convertFromBaseUnit(inInBaseUnit.getQuantity());
        }

        throw new UnsupportedOperationException("Conversion from " + unitIn.toString() + " to " + unitOut.toString() + " is not supported.");
    }

}
