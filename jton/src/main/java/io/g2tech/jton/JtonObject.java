package io.g2tech.jton;

import com.google.gson.internal.LinkedTreeMap;

import java.util.Map;
import java.util.Set;

/**
 * A class representing an object type in Jton. An object consists of name-value
 * pairs where names are strings, and values are any other type of
 * {@link JtonElement}. This allows for a creating a tree of JtonElements. The
 * member elements of this object are maintained in order they were added.
 */
public final class JtonObject extends JtonElement {
	private final LinkedTreeMap<String, JtonElement> members = new LinkedTreeMap<String, JtonElement>();

	/**
	 * Creates a deep copy of this element and all its children
	 */
	@Override
	public JtonObject deepCopy() {
		JtonObject result = new JtonObject();
		for (Map.Entry<String, JtonElement> entry : members.entrySet()) {
			result.add(entry.getKey(), entry.getValue().deepCopy());
		}
		return result;
	}

	/**
	 * Adds a member, which is a name-value pair, to self. The name must be a
	 * String, but the value can be an arbitrary {@link JtonElement}, thereby
	 * allowing you to build a full tree of {@link JtonElement}s rooted at this
	 * node.
	 *
	 * @param property name of the member.
	 * @param value    the member object.
	 * @return 
	 */
	public JtonObject add(String property, JtonElement value) {
		members.put(property, value == null ? JtonNull.INSTANCE : value);
		return this;
	}

	/**
	 * Removes the {@code property} from this {@link JtonObject}.
	 *
	 * @param property name of the member that should be removed.
	 * @return the {@link JtonElement} object that is being removed.
	 * @since 1.3
	 */
	public JtonElement remove(String property) {
		return members.remove(property);
	}

	/**
	 * Convenience method to add a primitive member. The specified value is
	 * converted to a {@link JtonPrimitive} of String.
	 *
	 * @param property name of the member.
	 * @param value    the string value associated with the member.
	 * @return 
	 */
	public JtonObject addProperty(String property, String value) {
		add(property, value == null ? JtonNull.INSTANCE : new JtonPrimitive(value));
		return this;
	}

	/**
	 * Convenience method to add a primitive member. The specified value is
	 * converted to a {@link JtonPrimitive} of Number.
	 *
	 * @param property name of the member.
	 * @param value    the number value associated with the member.
	 * @return 
	 */
	public JtonObject addProperty(String property, Number value) {
		add(property, value == null ? JtonNull.INSTANCE : new JtonPrimitive(value));
		return this;
	}

	/**
	 * Convenience method to add a boolean member. The specified value is converted
	 * to a {@link JtonPrimitive} of Boolean.
	 *
	 * @param property name of the member.
	 * @param value    the number value associated with the member.
	 * @return 
	 */
	public JtonObject addProperty(String property, Boolean value) {
		add(property, value == null ? JtonNull.INSTANCE : new JtonPrimitive(value));
		return this;
	}

	/**
	 * Convenience method to add a char member. The specified value is converted to
	 * a {@link JtonPrimitive} of Character.
	 *
	 * @param property name of the member.
	 * @param value    the number value associated with the member.
	 * @return 
	 */
	public JtonObject addProperty(String property, Character value) {
		add(property, value == null ? JtonNull.INSTANCE : new JtonPrimitive(value));
		return this;
	}

	/**
	 * Returns a set of members of this object. The set is ordered, and the order is
	 * in which the elements were added.
	 *
	 * @return a set of members of this object.
	 */
	public Set<Map.Entry<String, JtonElement>> entrySet() {
		return members.entrySet();
	}

	/**
	 * Returns a set of members key values.
	 *
	 * @return a set of member keys as Strings
	 */
	public Set<String> keySet() {
		return members.keySet();
	}

	/**
	 * Returns the number of key/value pairs in the object.
	 *
	 * @return the number of key/value pairs in the object.
	 */
	public int size() {
		return members.size();
	}

	/**
	 * Convenience method to check if a member with the specified name is present in
	 * this object.
	 *
	 * @param memberName name of the member that is being checked for presence.
	 * @return true if there is a member with the specified name, false otherwise.
	 */
	public boolean has(String memberName) {
		return members.containsKey(memberName);
	}

	/**
	 * Returns the member with the specified name.
	 *
	 * @param memberName name of the member that is being requested.
	 * @return the member matching the name. Null if no such member exists.
	 */
	public JtonElement get(String memberName) {
		return members.get(memberName);
	}

	/**
	 * Convenience method to get the specified member as a JsonPrimitive element.
	 *
	 * @param memberName name of the member being requested.
	 * @return the {@link JtonPrimitive} corresponding to the specified member.
	 */
	public JtonPrimitive getAsJsonPrimitive(String memberName) {
		return (JtonPrimitive) members.get(memberName);
	}

	/**
	 * Convenience method to get the specified member as a JsonArray.
	 *
	 * @param memberName name of the member being requested.
	 * @return the {@link JsonArray} corresponding to the specified member.
	 */
	public JtonArray getAsJsonArray(String memberName) {
		return (JtonArray) members.get(memberName);
	}

	/**
	 * Convenience method to get the specified member as a JsonObject.
	 *
	 * @param memberName name of the member being requested.
	 * @return the {@link JsonObject} corresponding to the specified member.
	 */
	public JtonObject getAsJsonObject(String memberName) {
		return (JtonObject) members.get(memberName);
	}

	@Override
	public boolean equals(Object o) {
		return (o == this) || (o instanceof JtonObject && ((JtonObject) o).members.equals(members));
	}

	@Override
	public int hashCode() {
		return members.hashCode();
	}
}
