# Measures

The Measures package consists of three core concepts. Units, Measures and 
Conversion. In our package, a Unit is considered a single instance of an agreed 
measure, such as a "Metre" or a "Gram". These units are generally globally 
agreed upon, we use the definitions as per [UCUM](https://ucum.org/). A Measure
is some quantity of a Unit. For example where "cm" is a Unit, "1.5cm" is a 
Measure. It is possible to convert between Measures and Units. For example, 
100cm == 1m.

Our toolset is expandable. You can define new Units and create conversions 
between them. 

Our toolset enables accuracy. You can define Conversions in multiple methods.

Our toolset supports edge cases. You can solve problems with limitations of the 
Java language by providing complex implementations of conversion functions.

# Examples

Convert from metres to kilometres using the Measures API:

```java
Measure myMetres = new Measure(Unit.METRE, 1.85);
Measure myCentimetres = myMetres.convertTo(Unit.CENTIMETRE);
myCentimetres.getQuantity(); // 185.0
```

Create a new Unit and convert to it using the Unit API:

```java
// A Unit is defined with a "Base Unit" and conversion functions too and from 
// your Unit definition and the base unit.
Unit MILLIMETRE = new Unit(Unit.METRE, (millimetre) -> millimetre * 0.001, (metre) -> metre * 1000.0);
Measure myMillimetres = new Measure(MILLIMETRE, 1337);

// This conversion is possible as the custom unit shares the same base unit.
Measure myCentimetres = myMillimetres.convertTo(Unit.CENTIMETRE);
myCentimetres.getQuantity(); // 133.7
```

Create an optimised conversion between units to prevent issues with java double
limitations:

```java
Measure lotsOfKilometers = new Measure(Unit.KILOMETER, Double.MAX_VALUE);
lotsOfKilometers.convertTo(Unit.NAUTICAL_MILE); // Throws an exception.

// By default, Unit Conversions go to the base unit. This is a problem in our
// case, as lotsOfKilometers.convertTo(Unit.METRE) will return 
// Double.POSITIVE_INFINITY. To resolve this, we can add a specialised 
// conversion which goes directly to the desired unit.

UnitConverter.putScalar(Unit.NAUTICAL_MILE, Unit.KILOMETRE, 1.852);
lotsOfKilometers.convertTo(Unit.NAUTICAL_MILE); // Succeeds

// There is also a putFunction feature for more complicated conversions. For
// example if you need a conversion with maximum accuracy, then you could
// write functions which utilise BigDecimal.
```