package io.jton;

/**
 * A class representing a JTON {@code null} value.
 */
public final class JtonNull extends JtonElement {
  /**
   * singleton for {@code JtonNull}
   */
  public static final JtonNull INSTANCE = new JtonNull();

  /**
   * Creates a new {@code JtonNull} object.
   */
  private JtonNull() {
    // Do nothing
  }

  /**
   * Returns the same instance since it is an immutable value
   */
  @Override
  public JtonNull deepCopy() {
    return INSTANCE;
  }

  /**
   * All instances of {@code JtonNull} have the same hash code since they are
   * indistinguishable
   */
  @Override
  public int hashCode() {
    return JtonNull.class.hashCode();
  }

  /**
   * All instances of {@code JtonNull} are the same
   */
  @Override
  public boolean equals(Object other) {
    return this == other || other instanceof JtonNull;
  }
}
