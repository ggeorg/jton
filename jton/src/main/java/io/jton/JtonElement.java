package io.jton;

import com.google.gson.stream.JsonWriter;

import io.jton.internal.Streams;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

/**
 * A class representing an element of Jton. It could either be a
 * {@link JtonObject}, a {@link JtonArray}, a {@link JtonPrimitive} or a
 * {@link JtonNull}.
 */
public abstract class JtonElement {
  /**
   * Returns a deep copy of this element. Immutable elements like primitives and
   * nulls are not copied.
   */
  public abstract JtonElement deepCopy();

  /**
   * provides check for verifying if this element is an array or not.
   *
   * @return true if this element is of type {@link JtonArray}, false otherwise.
   */
  public boolean isJtonArray() {
    return this instanceof JtonArray;
  }

  /**
   * provides check for verifying if this element is a Jton object or not.
   *
   * @return true if this element is of type {@link JtonObject}, false otherwise.
   */
  public boolean isJtonObject() {
    return this instanceof JtonObject;
  }

  /**
   * provides check for verifying if this element is a primitive or not.
   *
   * @return true if this element is of type {@link JtonPrimitive}, false
   *         otherwise.
   */
  public boolean isJtonPrimitive() {
    return this instanceof JtonPrimitive;
  }

  /**
   * provides check for verifying if this element represents a null value or not.
   *
   * @return true if this element is of type {@link JtonNull}, false otherwise.
   */
  public boolean isJtonNull() {
    return this instanceof JtonNull;
  }

  /**
   * convenience method to get this element as a {@link JtonObject}. If the
   * element is of some other type, a {@link IllegalStateException} will result.
   * Hence it is best to use this method after ensuring that this element is of
   * the desired type by calling {@link #isJtonObject()} first.
   *
   * @return get this element as a {@link JtonObject}.
   * @throws IllegalStateException if the element is of another type.
   */
  public JtonObject getAsJtonObject() {
    if (isJtonObject()) {
      return (JtonObject) this;
    }
    throw new IllegalStateException("Not a JSON Object: " + this);
  }

  /**
   * convenience method to get this element as a {@link JtonArray}. If the element
   * is of some other type, a {@link IllegalStateException} will result. Hence it
   * is best to use this method after ensuring that this element is of the desired
   * type by calling {@link #isJtonArray()} first.
   *
   * @return get this element as a {@link JtonArray}.
   * @throws IllegalStateException if the element is of another type.
   */
  public JtonArray getAsJtonArray() {
    if (isJtonArray()) {
      return (JtonArray) this;
    }
    throw new IllegalStateException("Not a JSON Array: " + this);
  }

  /**
   * convenience method to get this element as a {@link JtonPrimitive}. If the
   * element is of some other type, a {@link IllegalStateException} will result.
   * Hence it is best to use this method after ensuring that this element is of
   * the desired type by calling {@link #isJtonPrimitive()} first.
   *
   * @return get this element as a {@link JtonPrimitive}.
   * @throws IllegalStateException if the element is of another type.
   */
  public JtonPrimitive getAsJtonPrimitive() {
    if (isJtonPrimitive()) {
      return (JtonPrimitive) this;
    }
    throw new IllegalStateException("Not a JSON Primitive: " + this);
  }

  /**
   * convenience method to get this element as a {@link JtonNull}. If the element
   * is of some other type, a {@link IllegalStateException} will result. Hence it
   * is best to use this method after ensuring that this element is of the desired
   * type by calling {@link #isJtonNull()} first.
   *
   * @return get this element as a {@link JtonNull}.
   * @throws IllegalStateException if the element is of another type.
   */
  public JtonNull getAsJtonNull() {
    if (isJtonNull()) {
      return (JtonNull) this;
    }
    throw new IllegalStateException("Not a JTON Null: " + this);
  }

  /**
   * convenience method to get this element as a boolean value.
   *
   * @return get this element as a primitive boolean value.
   * @throws ClassCastException    if the element is of not a
   *                               {@link JtonPrimitive} and is not a valid
   *                               boolean value.
   * @throws IllegalStateException if the element is of the type {@link JtonArray}
   *                               but contains more than a single element.
   */
  public boolean getAsBoolean() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }

  /**
   * convenience method to get this element as a {@link Number}.
   *
   * @return get this element as a {@link Number}.
   * @throws ClassCastException    if the element is of not a
   *                               {@link JtonPrimitive} and is not a valid
   *                               number.
   * @throws IllegalStateException if the element is of the type {@link JtonArray}
   *                               but contains more than a single element.
   */
  public Number getAsNumber() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }

  /**
   * convenience method to get this element as a string value.
   *
   * @return get this element as a string value.
   * @throws ClassCastException    if the element is of not a
   *                               {@link JtonPrimitive} and is not a valid string
   *                               value.
   * @throws IllegalStateException if the element is of the type {@link JtonArray}
   *                               but contains more than a single element.
   */
  public String getAsString() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }

  /**
   * convenience method to get this element as a primitive double value.
   *
   * @return get this element as a primitive double value.
   * @throws ClassCastException    if the element is of not a
   *                               {@link JtonPrimitive} and is not a valid double
   *                               value.
   * @throws IllegalStateException if the element is of the type {@link JtonArray}
   *                               but contains more than a single element.
   */
  public double getAsDouble() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }

  /**
   * convenience method to get this element as a primitive float value.
   *
   * @return get this element as a primitive float value.
   * @throws ClassCastException    if the element is of not a
   *                               {@link JtonPrimitive} and is not a valid float
   *                               value.
   * @throws IllegalStateException if the element is of the type {@link JtonArray}
   *                               but contains more than a single element.
   */
  public float getAsFloat() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }

  /**
   * convenience method to get this element as a primitive long value.
   *
   * @return get this element as a primitive long value.
   * @throws ClassCastException    if the element is of not a
   *                               {@link JtonPrimitive} and is not a valid long
   *                               value.
   * @throws IllegalStateException if the element is of the type {@link JtonArray}
   *                               but contains more than a single element.
   */
  public long getAsLong() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }

  /**
   * convenience method to get this element as a primitive integer value.
   *
   * @return get this element as a primitive integer value.
   * @throws ClassCastException    if the element is of not a
   *                               {@link JtonPrimitive} and is not a valid
   *                               integer value.
   * @throws IllegalStateException if the element is of the type {@link JtonArray}
   *                               but contains more than a single element.
   */
  public int getAsInt() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }

  /**
   * convenience method to get this element as a primitive byte value.
   *
   * @return get this element as a primitive byte value.
   * @throws ClassCastException    if the element is of not a
   *                               {@link JtonPrimitive} and is not a valid byte
   *                               value.
   * @throws IllegalStateException if the element is of the type {@link JtonArray}
   *                               but contains more than a single element.
   */
  public byte getAsByte() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }

  /**
   * convenience method to get the first character of this element as a string or
   * the first character of this array's first element as a string.
   *
   * @return the first character of the string.
   * @throws ClassCastException    if the element is of not a
   *                               {@link JtonPrimitive} and is not a valid string
   *                               value.
   * @throws IllegalStateException if the element is of the type {@link JtonArray}
   *                               but contains more than a single element.
   * @deprecated This method is misleading, as it does not get this element as a
   *             char but rather as a string's first character.
   */
  @Deprecated
  public char getAsCharacter() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }

  /**
   * convenience method to get this element as a {@link BigDecimal}.
   *
   * @return get this element as a {@link BigDecimal}.
   * @throws ClassCastException    if the element is of not a
   *                               {@link JtonPrimitive}. * @throws
   *                               NumberFormatException if the element is not a
   *                               valid {@link BigDecimal}.
   * @throws IllegalStateException if the element is of the type {@link JtonArray}
   *                               but contains more than a single element.
   */
  public BigDecimal getAsBigDecimal() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }

  /**
   * convenience method to get this element as a {@link BigInteger}.
   *
   * @return get this element as a {@link BigInteger}.
   * @throws ClassCastException    if the element is of not a
   *                               {@link JtonPrimitive}.
   * @throws NumberFormatException if the element is not a valid
   *                               {@link BigInteger}.
   * @throws IllegalStateException if the element is of the type {@link JtonArray}
   *                               but contains more than a single element.
   */
  public BigInteger getAsBigInteger() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }

  /**
   * convenience method to get this element as a primitive short value.
   *
   * @return get this element as a primitive short value.
   * @throws ClassCastException    if the element is of not a
   *                               {@link JtonPrimitive} and is not a valid short
   *                               value.
   * @throws IllegalStateException if the element is of the type {@link JtonArray}
   *                               but contains more than a single element.
   */
  public short getAsShort() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }

  /**
   * Returns a String representation of this element.
   */
  @Override
  public String toString() {
    return toString(null);
  }

  /**
   * Returns a String representation of this element.
   * <p>
   * Sets the indentation string to be repeated for each level of indentation in
   * the encoded document. If {@code indent.isEmpty()} the encoded document will
   * be compact. Otherwise the encoded document will be more human-readable.
   * 
   * @param indent a string containing only whitespace.
   * @return a string representation of this element.
   */
  public String toString(String indent) {
    try {
      StringWriter stringWriter = new StringWriter();
      JsonWriter jsonWriter = new JsonWriter(stringWriter);
      jsonWriter.setIndent(Optional.ofNullable(indent).orElse(""));
      jsonWriter.setLenient(true);
      Streams.write(this, jsonWriter);
      return stringWriter.toString();
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }
}
