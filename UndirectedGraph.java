/**
 * Undirected graph
 */
public class UndirectedGraph<T> extends AbstractGraph<T> {

	/**
	 * Add bidirectional link between two vertices
	 * (if they are present in this graph)
	 *
	 * @param firstId  first vertex id
	 * @param secondId second vertex id
	 */
	@Override
	public void addEdge(T firstId, T secondId) {
		super.addEdge(firstId, secondId);
		verticesById.get(secondId).connectTo(firstId);
	}
}
