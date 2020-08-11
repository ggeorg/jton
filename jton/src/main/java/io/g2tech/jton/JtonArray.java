package io.g2tech.jton;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * A class representing an array type in Jton. An array is a list of
 * {@link JtonElement}s each of which can be of a different type. This is an
 * ordered list, meaning that the order in which elements are added is
 * preserved.
 */
public final class JtonArray extends JtonElement implements Iterable<JtonElement> {
  private final List<JtonElement> elements;

  /**
   * Creates an empty JsonArray.
   */
  public JtonArray() {
    elements = new ArrayList<JtonElement>();
  }

  public JtonArray(int capacity) {
    elements = new ArrayList<JtonElement>(capacity);
  }

  /**
   * Creates a deep copy of this element and all its children
   */
  @Override
  public JtonArray deepCopy() {
    if (!elements.isEmpty()) {
      JtonArray result = new JtonArray(elements.size());
      for (JtonElement element : elements) {
        result.add(element.deepCopy());
      }
      return result;
    }
    return new JtonArray();
  }

  /**
   * Adds the specified boolean to self.
   *
   * @param bool the boolean that needs to be added to the array.
   * @return
   */
  public JtonArray add(Boolean bool) {
    elements.add(bool == null ? JtonNull.INSTANCE : new JtonPrimitive(bool));
    return this;
  }

  /**
   * Adds the specified character to self.
   *
   * @param character the character that needs to be added to the array.
   * @return
   */
  public JtonArray add(Character character) {
    elements.add(character == null ? JtonNull.INSTANCE : new JtonPrimitive(character));
    return this;
  }

  /**
   * Adds the specified number to self.
   *
   * @param number the number that needs to be added to the array.
   * @return
   */
  public JtonArray add(Number number) {
    elements.add(number == null ? JtonNull.INSTANCE : new JtonPrimitive(number));
    return this;
  }

  /**
   * Adds the specified string to self.
   *
   * @param string the string that needs to be added to the array.
   * @return
   */
  public JtonArray add(String string) {
    elements.add(string == null ? JtonNull.INSTANCE : new JtonPrimitive(string));
    return this;
  }

  /**
   * Adds the specified element to self.
   *
   * @param element the element that needs to be added to the array.
   * @return
   */
  public JtonArray add(JtonElement element) {
    elements.add(element == null ? JtonNull.INSTANCE : element);
    return this;
  }

  /**
   * Adds all the elements of the specified array to self.
   *
   * @param array the array whose elements need to be added to the array.
   * @return
   */
  public JtonArray addAll(JtonArray array) {
    elements.addAll(array.elements);
    return this;
  }

  /**
   * Replaces the element at the specified position in this array with the
   * specified element. Element can be null.
   * 
   * @param index   index of the element to replace
   * @param element element to be stored at the specified position
   * @return the element previously at the specified position
   * @throws IndexOutOfBoundsException if the specified index is outside the array
   *                                   bounds
   */
  public JtonElement set(int index, JtonElement element) {
    return elements.set(index, element == null ? JtonNull.INSTANCE : element);
  }

  /**
   * Removes the first occurrence of the specified element from this array, if it
   * is present. If the array does not contain the element, it is unchanged.
   * 
   * @param element element to be removed from this array, if present
   * @return true if this array contained the specified element, false otherwise
   */
  public boolean remove(JtonElement element) {
    return elements.remove(element);
  }

  /**
   * Removes the element at the specified position in this array. Shifts any
   * subsequent elements to the left (subtracts one from their indices). Returns
   * the element that was removed from the array.
   * 
   * @param index index the index of the element to be removed
   * @return the element previously at the specified position
   * @throws IndexOutOfBoundsException if the specified index is outside the array
   *                                   bounds
   */
  public JtonElement remove(int index) {
    return elements.remove(index);
  }

  /**
   * Returns true if this array contains the specified element.
   * 
   * @return true if this array contains the specified element.
   * @param element whose presence in this array is to be tested
   */
  public boolean contains(JtonElement element) {
    return elements.contains(element);
  }

  /**
   * Returns the number of elements in the array.
   *
   * @return the number of elements in the array.
   */
  public int size() {
    return elements.size();
  }

  /**
   * Returns an iterator to navigate the elements of the array. Since the array is
   * an ordered list, the iterator navigates the elements in the order they were
   * inserted.
   *
   * @return an iterator to navigate the elements of the array.
   */
  public Iterator<JtonElement> iterator() {
    return elements.iterator();
  }

  /**
   * Returns the ith element of the array.
   *
   * @param i the index of the element that is being sought.
   * @return the element present at the ith index.
   * @throws IndexOutOfBoundsException if i is negative or greater than or equal
   *                                   to the {@link #size()} of the array.
   */
  public JtonElement get(int i) {
    return elements.get(i);
  }

  /**
   * convenience method to get this array as a {@link Number} if it contains a
   * single element.
   *
   * @return get this element as a number if it is single element array.
   * @throws ClassCastException    if the element in the array is of not a
   *                               {@link JtonPrimitive} and is not a valid
   *                               Number.
   * @throws IllegalStateException if the array has more than one element.
   */
  @Override
  public Number getAsNumber() {
    if (elements.size() == 1) {
      return elements.get(0).getAsNumber();
    }
    throw new IllegalStateException();
  }

  /**
   * convenience method to get this array as a {@link String} if it contains a
   * single element.
   *
   * @return get this element as a String if it is single element array.
   * @throws ClassCastException    if the element in the array is of not a
   *                               {@link JtonPrimitive} and is not a valid
   *                               String.
   * @throws IllegalStateException if the array has more than one element.
   */
  @Override
  public String getAsString() {
    if (elements.size() == 1) {
      return elements.get(0).getAsString();
    }
    throw new IllegalStateException();
  }

  /**
   * convenience method to get this array as a double if it contains a single
   * element.
   *
   * @return get this element as a double if it is single element array.
   * @throws ClassCastException    if the element in the array is of not a
   *                               {@link JtonPrimitive} and is not a valid
   *                               double.
   * @throws IllegalStateException if the array has more than one element.
   */
  @Override
  public double getAsDouble() {
    if (elements.size() == 1) {
      return elements.get(0).getAsDouble();
    }
    throw new IllegalStateException();
  }

  /**
   * convenience method to get this array as a {@link BigDecimal} if it contains a
   * single element.
   *
   * @return get this element as a {@link BigDecimal} if it is single element
   *         array.
   * @throws ClassCastException    if the element in the array is of not a
   *                               {@link JtonPrimitive}.
   * @throws NumberFormatException if the element at index 0 is not a valid
   *                               {@link BigDecimal}.
   * @throws IllegalStateException if the array has more than one element.
   */
  @Override
  public BigDecimal getAsBigDecimal() {
    if (elements.size() == 1) {
      return elements.get(0).getAsBigDecimal();
    }
    throw new IllegalStateException();
  }

  /**
   * convenience method to get this array as a {@link BigInteger} if it contains a
   * single element.
   *
   * @return get this element as a {@link BigInteger} if it is single element
   *         array.
   * @throws ClassCastException    if the element in the array is of not a
   *                               {@link JtonPrimitive}.
   * @throws NumberFormatException if the element at index 0 is not a valid
   *                               {@link BigInteger}.
   * @throws IllegalStateException if the array has more than one element.
   */
  @Override
  public BigInteger getAsBigInteger() {
    if (elements.size() == 1) {
      return elements.get(0).getAsBigInteger();
    }
    throw new IllegalStateException();
  }

  /**
   * convenience method to get this array as a float if it contains a single
   * element.
   *
   * @return get this element as a float if it is single element array.
   * @throws ClassCastException    if the element in the array is of not a
   *                               {@link JtonPrimitive} and is not a valid float.
   * @throws IllegalStateException if the array has more than one element.
   */
  @Override
  public float getAsFloat() {
    if (elements.size() == 1) {
      return elements.get(0).getAsFloat();
    }
    throw new IllegalStateException();
  }

  /**
   * convenience method to get this array as a long if it contains a single
   * element.
   *
   * @return get this element as a long if it is single element array.
   * @throws ClassCastException    if the element in the array is of not a
   *                               {@link JtonPrimitive} and is not a valid long.
   * @throws IllegalStateException if the array has more than one element.
   */
  @Override
  public long getAsLong() {
    if (elements.size() == 1) {
      return elements.get(0).getAsLong();
    }
    throw new IllegalStateException();
  }

  /**
   * convenience method to get this array as an integer if it contains a single
   * element.
   *
   * @return get this element as an integer if it is single element array.
   * @throws ClassCastException    if the element in the array is of not a
   *                               {@link JtonPrimitive} and is not a valid
   *                               integer.
   * @throws IllegalStateException if the array has more than one element.
   */
  @Override
  public int getAsInt() {
    if (elements.size() == 1) {
      return elements.get(0).getAsInt();
    }
    throw new IllegalStateException();
  }

  @Override
  public byte getAsByte() {
    if (elements.size() == 1) {
      return elements.get(0).getAsByte();
    }
    throw new IllegalStateException();
  }

  @Override
  public char getAsCharacter() {
    if (elements.size() == 1) {
      return elements.get(0).getAsCharacter();
    }
    throw new IllegalStateException();
  }

  /**
   * convenience method to get this array as a primitive short if it contains a
   * single element.
   *
   * @return get this element as a primitive short if it is single element array.
   * @throws ClassCastException    if the element in the array is of not a
   *                               {@link JtonPrimitive} and is not a valid short.
   * @throws IllegalStateException if the array has more than one element.
   */
  @Override
  public short getAsShort() {
    if (elements.size() == 1) {
      return elements.get(0).getAsShort();
    }
    throw new IllegalStateException();
  }

  /**
   * convenience method to get this array as a boolean if it contains a single
   * element.
   *
   * @return get this element as a boolean if it is single element array.
   * @throws ClassCastException    if the element in the array is of not a
   *                               {@link JtonPrimitive} and is not a valid
   *                               boolean.
   * @throws IllegalStateException if the array has more than one element.
   */
  @Override
  public boolean getAsBoolean() {
    if (elements.size() == 1) {
      return elements.get(0).getAsBoolean();
    }
    throw new IllegalStateException();
  }

  /**
   * Returns a sequential {@code Stream} with this collection as its source.
   *
   * @return a sequential {@code Stream} over the elements in this collection
   * @see List#stream()
   */
  public Stream<JtonElement> stream() {
    return elements.stream();
  }

  /**
   * Returns a possibly parallel {@code Stream} with this collection as its
   * source. It is allowable for this method to return a sequential stream.
   *
   * @return a possibly parallel {@code Stream} over the elements in this
   *         collection
   */
  public Stream<JtonElement> parallelStream() {
    return elements.parallelStream();
  }

  @Override
  public boolean equals(Object o) {
    return (o == this) || (o instanceof JtonArray && ((JtonArray) o).elements.equals(elements));
  }

  @Override
  public int hashCode() {
    return elements.hashCode();
  }
}
