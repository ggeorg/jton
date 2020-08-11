package io.g2tech.jton;

import com.google.gson.internal.$Gson$Preconditions;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.google.gson.internal.LazilyParsedNumber;

/**
 * A class representing a Jton primitive value. A primitive value is either a
 * String, a Java primitive, or a Java primitive wrapper type.
 */
public final class JtonPrimitive extends JtonElement {

	private final Object value;
	
	private boolean jtonTransient = false;

	/**
	 * Create a primitive containing a boolean value.
	 *
	 * @param bool the value to create the primitive with.
	 */
	public JtonPrimitive(Boolean bool) {
		value = $Gson$Preconditions.checkNotNull(bool);
	}

	/**
	 * Create a primitive containing a {@link Number}.
	 *
	 * @param number the value to create the primitive with.
	 */
	public JtonPrimitive(Number number) {
		value = $Gson$Preconditions.checkNotNull(number);
	}

	/**
	 * Create a primitive containing a String value.
	 *
	 * @param string the value to create the primitive with.
	 */
	public JtonPrimitive(String string) {
		value = $Gson$Preconditions.checkNotNull(string);
	}

	/**
	 * Create a primitive containing a character. The character is turned into a one
	 * character String since Json only supports String.
	 *
	 * @param c the value to create the primitive with.
	 */
	public JtonPrimitive(Character c) {
		// convert characters to strings since in JSON, characters are represented as a
		// single character string
		value = $Gson$Preconditions.checkNotNull(c).toString();
	}

	public JtonPrimitive(Object value, boolean jtonTransient) {
		if (jtonTransient) {
			this.value = value;
			this.jtonTransient = true;
		} else {
			$Gson$Preconditions.checkNotNull(value);
			$Gson$Preconditions.checkArgument(isPrimitiveOrString(value));
			
			if (value instanceof Character) {
				// convert characters to strings since in JSON, characters are represented as a
				// single character string
				this.value = value.toString();
			} else {
				this.value = value;
			}
		}
	}

	/**
	 * Returns the same value as primitives are immutable.
	 */
	@Override
	public JtonPrimitive deepCopy() {
		return this;
	}
	
	public boolean  isJtonTransient() {
		return jtonTransient;
	}
	
	public Object getValue() {
		return value;
	}

	/**
	 * Check whether this primitive contains a boolean value.
	 *
	 * @return true if this primitive contains a boolean value, false otherwise.
	 */
	public boolean isBoolean() {
		return value instanceof Boolean;
	}

	/**
	 * convenience method to get this element as a boolean value.
	 *
	 * @return get this element as a primitive boolean value.
	 */
	@Override
	public boolean getAsBoolean() {
		if (isBoolean()) {
			return ((Boolean) value).booleanValue();
		}
		// Check to see if the value as a String is "true" in any case.
		return Boolean.parseBoolean(getAsString());
	}

	/**
	 * Check whether this primitive contains a Number.
	 *
	 * @return true if this primitive contains a Number, false otherwise.
	 */
	public boolean isNumber() {
		return value instanceof Number;
	}

	/**
	 * convenience method to get this element as a Number.
	 *
	 * @return get this element as a Number.
	 * @throws NumberFormatException if the value contained is not a valid Number.
	 */
	@Override
	public Number getAsNumber() {
		return value instanceof String ? new LazilyParsedNumber((String) value) : (Number) value;
	}

	/**
	 * Check whether this primitive contains a String value.
	 *
	 * @return true if this primitive contains a String value, false otherwise.
	 */
	public boolean isString() {
		return value instanceof String;
	}

	/**
	 * convenience method to get this element as a String.
	 *
	 * @return get this element as a String.
	 */
	@Override
	public String getAsString() {
		if (isNumber()) {
			return getAsNumber().toString();
		} else if (isBoolean()) {
			return ((Boolean) value).toString();
		} else {
			return (String) value;
		}
	}

	/**
	 * convenience method to get this element as a primitive double.
	 *
	 * @return get this element as a primitive double.
	 * @throws NumberFormatException if the value contained is not a valid double.
	 */
	@Override
	public double getAsDouble() {
		return isNumber() ? getAsNumber().doubleValue() : Double.parseDouble(getAsString());
	}

	/**
	 * convenience method to get this element as a {@link BigDecimal}.
	 *
	 * @return get this element as a {@link BigDecimal}.
	 * @throws NumberFormatException if the value contained is not a valid
	 *                               {@link BigDecimal}.
	 */
	@Override
	public BigDecimal getAsBigDecimal() {
		return value instanceof BigDecimal ? (BigDecimal) value : new BigDecimal(value.toString());
	}

	/**
	 * convenience method to get this element as a {@link BigInteger}.
	 *
	 * @return get this element as a {@link BigInteger}.
	 * @throws NumberFormatException if the value contained is not a valid
	 *                               {@link BigInteger}.
	 */
	@Override
	public BigInteger getAsBigInteger() {
		return value instanceof BigInteger ? (BigInteger) value : new BigInteger(value.toString());
	}

	/**
	 * convenience method to get this element as a float.
	 *
	 * @return get this element as a float.
	 * @throws NumberFormatException if the value contained is not a valid float.
	 */
	@Override
	public float getAsFloat() {
		return isNumber() ? getAsNumber().floatValue() : Float.parseFloat(getAsString());
	}

	/**
	 * convenience method to get this element as a primitive long.
	 *
	 * @return get this element as a primitive long.
	 * @throws NumberFormatException if the value contained is not a valid long.
	 */
	@Override
	public long getAsLong() {
		return isNumber() ? getAsNumber().longValue() : Long.parseLong(getAsString());
	}

	/**
	 * convenience method to get this element as a primitive short.
	 *
	 * @return get this element as a primitive short.
	 * @throws NumberFormatException if the value contained is not a valid short
	 *                               value.
	 */
	@Override
	public short getAsShort() {
		return isNumber() ? getAsNumber().shortValue() : Short.parseShort(getAsString());
	}

	/**
	 * convenience method to get this element as a primitive integer.
	 *
	 * @return get this element as a primitive integer.
	 * @throws NumberFormatException if the value contained is not a valid integer.
	 */
	@Override
	public int getAsInt() {
		return isNumber() ? getAsNumber().intValue() : Integer.parseInt(getAsString());
	}

	@Override
	public byte getAsByte() {
		return isNumber() ? getAsNumber().byteValue() : Byte.parseByte(getAsString());
	}

	@Override
	public char getAsCharacter() {
		return getAsString().charAt(0);
	}

	@Override
	public int hashCode() {
		if (value == null) {
			return 31;
		}
		// Using recommended hashing algorithm from Effective Java for longs and doubles
		if (isIntegral(this)) {
			long value = getAsNumber().longValue();
			return (int) (value ^ (value >>> 32));
		}
		if (value instanceof Number) {
			long value = Double.doubleToLongBits(getAsNumber().doubleValue());
			return (int) (value ^ (value >>> 32));
		}
		return value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		JtonPrimitive other = (JtonPrimitive) obj;
		if (value == null) {
			return other.value == null;
		}
		if (isIntegral(this) && isIntegral(other)) {
			return getAsNumber().longValue() == other.getAsNumber().longValue();
		}
		if (value instanceof Number && other.value instanceof Number) {
			double a = getAsNumber().doubleValue();
			// Java standard types other than double return true for two NaN. So, need
			// special handling for double.
			double b = other.getAsNumber().doubleValue();
			return a == b || (Double.isNaN(a) && Double.isNaN(b));
		}
		return value.equals(other.value);
	}

	/**
	 * Returns true if the specified number is an integral type (Long, Integer,
	 * Short, Byte, BigInteger)
	 */
	private static boolean isIntegral(JtonPrimitive primitive) {
		if (primitive.value instanceof Number) {
			Number number = (Number) primitive.value;
			return number instanceof BigInteger || number instanceof Long || number instanceof Integer
					|| number instanceof Short || number instanceof Byte;
		}
		return false;
	}

	private static final Class<?>[] PRIMITIVE_TYPES = { int.class, long.class, short.class, float.class, double.class,
			byte.class, byte[].class, boolean.class, char.class, Number.class, Boolean.class, Character.class };

	private static boolean isPrimitiveOrString(Object target) {
		if (target instanceof String) {
			return true;
		}

		Class<?> classOfPrimitive = target.getClass();
		for (Class<?> standardPrimitive : PRIMITIVE_TYPES) {
			if (standardPrimitive.isAssignableFrom(classOfPrimitive)) {
				return true;
			}
		}

		return false;
	}
}
