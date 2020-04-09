/**
 * Directed graph
 */
public class DirectedGraph<T> extends AbstractGraph<T> {

	/**
	 * Create unidirectional link between the vertices identified by the specified ids
	 * (if they are present in this graph)
	 *
	 * @param fromId "from" vertex id
	 * @param toId   "to" vertex id
	 */
	@Override
	public void addEdge(T fromId, T toId) {
		super.addEdge(fromId, toId);
	}
}
