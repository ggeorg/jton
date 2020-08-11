package io.g2tech.jton.internal.bind;

import java.io.IOException;
import java.util.Map;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import io.g2tech.jton.JtonArray;
import io.g2tech.jton.JtonElement;
import io.g2tech.jton.JtonNull;
import io.g2tech.jton.JtonObject;
import io.g2tech.jton.JtonPrimitive;

/**
 * Type adapters for basic types.
 */
public final class JtonTypeAdapter {
	private JtonTypeAdapter() {
		throw new UnsupportedOperationException();
	}

	public static final TypeAdapter<JtonElement> JTON_ELEMENT = new TypeAdapter<JtonElement>() {
		@Override
		public JtonElement read(JsonReader in) throws IOException {
			switch (in.peek()) {
			case STRING:
				return new JtonPrimitive(in.nextString());
			case NUMBER:
				String number = in.nextString();
				return new JtonPrimitive(new LazilyParsedNumber(number));
			case BOOLEAN:
				return new JtonPrimitive(in.nextBoolean());
			case NULL:
				in.nextNull();
				return JtonNull.INSTANCE;
			case BEGIN_ARRAY:
				JtonArray array = new JtonArray();
				in.beginArray();
				while (in.hasNext()) {
					array.add(read(in));
				}
				in.endArray();
				return array;
			case BEGIN_OBJECT:
				JtonObject object = new JtonObject();
				in.beginObject();
				while (in.hasNext()) {
					object.add(in.nextName(), read(in));
				}
				in.endObject();
				return object;
			case END_DOCUMENT:
			case NAME:
			case END_OBJECT:
			case END_ARRAY:
			default:
				throw new IllegalArgumentException();
			}
		}

		@Override
		public void write(JsonWriter out, JtonElement value) throws IOException {
			if (value == null || value.isJtonNull()) {
				out.nullValue();
			} else if (value.isJtonPrimitive()) {
				JtonPrimitive primitive = value.getAsJtonPrimitive();
				if (primitive.isJtonPrimitive()) {
					throw new IllegalStateException("transient type");
				} else {
					if (primitive.isNumber()) {
						out.value(primitive.getAsNumber());
					} else if (primitive.isBoolean()) {
						out.value(primitive.getAsBoolean());
					} else {
						out.value(primitive.getAsString());
					}
				}
			} else if (value.isJtonArray()) {
				out.beginArray();
				for (JtonElement e : value.getAsJtonArray()) {
					if (e.isJtonPrimitive() && e.getAsJtonPrimitive().isJtonTransient())
						continue;
					write(out, e);
				}
				out.endArray();

			} else if (value.isJtonObject()) {
				out.beginObject();
				for (Map.Entry<String, JtonElement> e : value.getAsJtonObject().entrySet()) {
					JtonElement jtonElement = e.getValue();
					if (jtonElement.isJtonPrimitive() && jtonElement.getAsJtonPrimitive().isJtonTransient())
						continue;
					out.name(e.getKey());
					write(out, jtonElement);
				}
				out.endObject();

			} else {
				throw new IllegalArgumentException("Couldn't write " + value.getClass());
			}
		}
	};

	public static final TypeAdapterFactory JTON_ELEMENT_FACTORY = TypeAdapters
			.newTypeHierarchyFactory(JtonElement.class, JTON_ELEMENT);

}
