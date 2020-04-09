import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Vertex
 *
 * @param <T> identifier class
 */
public class Vertex<T> {

	/**
	 * Identifier field
	 */
	private T id;

	/**
	 * List of vertices this one is connected to
	 */
	private List<T> connectedTo = new ArrayList<>();

	/**
	 * Identifier object must not be null and must implement "equals" method correctly, good examples: String, Integer etc.
	 *
	 * @param id identifier object
	 */
	Vertex(T id) {
		if (id == null) {
			throw new IllegalArgumentException("Identifier must not be null");
		}
		this.id = id;
	}

	void connectTo(T toId) {
		if (connectedTo.contains(toId)) {
			throw new IllegalArgumentException("This vertex is already connected to " + toId);
		}
		connectedTo.add(toId);
	}

	List<T> getConnectedTo() {
		return connectedTo;
	}

	T getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Vertex)) return false;
		Vertex vertex = (Vertex) o;
		return id.equals(vertex.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
