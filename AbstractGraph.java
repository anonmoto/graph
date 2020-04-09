import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This is a subset of vertices represented by unique identifiers of the same type.
 * Identifier type and values are selected by user. All public operations operate vertex identifiers
 * Identifier object must not be null and must implement "equals" method correctly, good examples: String, Integer etc.
 *
 * In some cases it would be right to force Comparable implementation by id object class
 *
 * No loops or edges with the same (x, y) is allowed
 *
 * @param <T> class of vertex identifiers
 */
abstract class AbstractGraph<T> {

	/**
	 * In multithreaded environment methods may be synchronized over this field
	 */
	protected Map<Object, Vertex<T>> verticesById = new HashMap<>();

	/**
	 * Add new vertex with the specified Id (must be unique and comparable by "equals")
	 *
	 * @param vertexId vertex id
	 */
	public void addVertex(T vertexId) {
		if (vertexId == null) {
			throw new IllegalArgumentException("Id must not be null");
		}
		if (verticesById.containsKey(vertexId)) {
			throw new IllegalArgumentException("This graph already contains vertex " + vertexId);
		}
		verticesById.put(vertexId, new Vertex<>(vertexId));
	}

	/**
	 * Add link between vertices, exact mechanics depends on implementation
	 *
	 * @param firstId first vertex
	 * @param secondId second vertex
	 */
	protected void addEdge(T firstId, T secondId) {
		if (firstId == null || secondId == null) {
			throw new IllegalArgumentException("Both ids must not be null");
		}
		Vertex<T> first = verticesById.get(firstId);
		if (first == null) {
			throw new IllegalArgumentException("There is no such first vertex in this graph: " + firstId);
		}
		if (!verticesById.containsKey(secondId)) {
			throw new IllegalArgumentException("There is no such second vertex in this graph: " + secondId);
		}
		first.connectTo(secondId);
	}

	/**
	 * Get the first path found connecting two vertices
	 *
	 * @param fromId "from" vertex id
	 * @param toId   "to" vertex id
	 * @return null if no path is present or List of intermediate vertex ids of some path found
	 */
    public List<T> getPath(T fromId, T toId) {
		if (fromId == null || toId == null) {
			throw new IllegalArgumentException("Both ids must not be null");
		}
		if (fromId.equals(toId)) {
			throw new IllegalArgumentException("Ids must be different");
		}
		Vertex<T> from = verticesById.get(fromId);
		if (from == null) {
			throw new IllegalArgumentException("There is no such \"from\" vertex in this graph: " + fromId);
		}
		if (!verticesById.containsKey(toId)) {
			throw new IllegalArgumentException("There is no such \"to\" vertex in this graph: " + toId);
		}
		List<T> path = new ArrayList<>();
		// Introducing this set to bypass cyclic paths
		Set<T> traversedVertices = new HashSet<>(Collections.singleton(fromId));
		if (isFound(path, traversedVertices, from, toId)) {
			Collections.reverse(path);
			return path;
		} else {
			return null;
		}
	}

	/**
	 * Recursive method to find some random path
	 *
	 * @param path              the list of intermediate vertices
	 * @param traversedVertices vertices whic are already checked
	 * @param from              the starting vertice
	 * @param toId              the target vertice
	 * @return enriched intermediate nodes list
	 */
	private boolean isFound(List<T> path, Set<T> traversedVertices, Vertex<T> from, T toId) {
    	if (from.getId().equals(toId)) {
    		return true;
		}
    	for (T childId : from.getConnectedTo()) {
    		if (traversedVertices.contains(childId)) {
    			continue;
			}
    		traversedVertices.add(childId);
    		if (isFound(path, traversedVertices, verticesById.get(childId), toId)) {
    			if (!childId.equals(toId)) {
    				path.add(childId);
				}
    			return true;
			}
		}
    	return false;
	}
}
