import java.util.Arrays;
import java.util.List;

/**
 * Tests
 */
public class GraphTest {

	public static void main (String[] args) {
		testBasicActions();
		testNoPath();
		testDirectedPath();
		testUndirectedPath();
	}

	/**
	 *
	 *  +----+◀---------------- +----+
	 *  |  1 |                  |  3 |
	 *  +----+              --▶ +----+
	 *     |             --/    |   ▲
	 *     |          --/       |   |
	 *     |       --/          |   |
	 *     |    --/             |   |
	 *     ▼  -/                ▼   |
	 *  +----+                  +----+
	 *  |  2 |                  |1000|
	 *  +----+                  +----+
	 *
	 */
	private static void testBasicActions() {

		DirectedGraph<Integer> directedGraph = new DirectedGraph<>();
		directedGraph.addVertex(1);
		directedGraph.addVertex(2);
		directedGraph.addVertex(3);
		directedGraph.addVertex(1000);
		directedGraph.addEdge(1, 2);
		directedGraph.addEdge(2, 3);
		directedGraph.addEdge(3, 1);
		directedGraph.addEdge(3, 1000);
		directedGraph.addEdge(1000, 3);

		// exceptions
		try {
			directedGraph.addVertex(null);
			throw new IllegalStateException();
		} catch (IllegalArgumentException e) {
			// ok
		}

		try {
			directedGraph.addVertex(2);
			throw new IllegalStateException();
		} catch (IllegalArgumentException e) {
			// ok
		}

		try {
			directedGraph.addEdge(2, 3);
			throw new IllegalStateException();
		} catch (IllegalArgumentException e) {
			// ok
		}

		try {
			directedGraph.addEdge(2, null);
			throw new IllegalStateException();
		} catch (IllegalArgumentException e) {
			// ok
		}

		try {
			directedGraph.addEdge(null, 1);
			throw new IllegalStateException();
		} catch (IllegalArgumentException e) {
			// ok
		}

		try {
			directedGraph.addEdge(222, 3);
			throw new IllegalStateException();
		} catch (IllegalArgumentException e) {
			// ok
		}

		try {
			directedGraph.getPath(222, 3);
			throw new IllegalStateException();
		} catch (IllegalArgumentException e) {
			// ok
		}

		try {
			directedGraph.getPath(1, 2222);
			throw new IllegalStateException();
		} catch (IllegalArgumentException e) {
			// ok
		}

		try {
			directedGraph.getPath(null, 3);
			throw new IllegalStateException();
		} catch (IllegalArgumentException e) {
			// ok
		}

		try {
			directedGraph.getPath(1, null);
			throw new IllegalStateException();
		} catch (IllegalArgumentException e) {
			// ok
		}

		try {
			directedGraph.getPath(3, 3);
			throw new IllegalStateException();
		} catch (IllegalArgumentException e) {
			// ok
		}
	}

	/**
	 *
	 *  +----+◀---------------- +----+
	 *  |  1 |                  |  3 |
	 *  +----+              --▶ +----+
	 *     |             --/        ▲
	 *     |          --/           |
	 *     |       --/              |
	 *     |    --/                 |
	 *     ▼  -/                    |
	 *  +----+                  +----+
	 *  |  2 |                  |1000|
	 *  +----+                  +----+
	 *
	 *
	 *
	 *  +----+----------------- +----+        +----+
	 *  |  A |                  |  C |        |  E |
	 *  +----+               -- +----+        +----+
	 *     |              --/       |           |
	 *     |           --/          |           |
	 *     |       ---/             |           |
	 *     |    --/                 |           |
	 *     |  -/                    |           |               -
	 *  +----+                  +----+        +----+
	 *  |  B |                  |  D |        |  F |
	 *  +----+                  +----+        +----+
	 *
	 *
	 */
	private static void testNoPath() {

		DirectedGraph<Integer> directedGraph = new DirectedGraph<>();
		directedGraph.addVertex(1);
		directedGraph.addVertex(2);
		directedGraph.addVertex(3);
		directedGraph.addVertex(1000);
		directedGraph.addEdge(1, 2);
		directedGraph.addEdge(2, 3);
		directedGraph.addEdge(3, 1);
		directedGraph.addEdge(1000, 3);
		List<Integer> path = directedGraph.getPath(1, 1000);
		assert path == null;

		UndirectedGraph<String> undirectedGraph = new UndirectedGraph<>();
		undirectedGraph.addVertex("A");
		undirectedGraph.addVertex("B");
		undirectedGraph.addVertex("C");
		undirectedGraph.addVertex("D");
		undirectedGraph.addVertex("E");
		undirectedGraph.addVertex("F");
		undirectedGraph.addEdge("A", "B");
		undirectedGraph.addEdge("A", "C");
		undirectedGraph.addEdge("B", "C");
		undirectedGraph.addEdge("C", "D");
		undirectedGraph.addEdge("E", "F");
		List<String> pathUndirected = undirectedGraph.getPath("A", "F");
		assert pathUndirected == null;
		pathUndirected = undirectedGraph.getPath("F", "A");
		assert pathUndirected == null;
	}

	/**
	 *
	 *
	 *                              /-----------------
	 *                             ▼                 \-----
	 *  +----+                  +----+     +----+           +----+
	 *  |  1 |                  |  3 |◀----|  6 |---------▶|  7 |
	 *  +----+              --▶ +----+     +----+           +----+
	 *     |             --/    |   ▲        ▲
	 *     |          --/       |   |         |             +----+
	 *     |       --/          |   |         |          --▶|  8 |
	 *     |    --/             |   |         |       --/   +----+
	 *    ▼  -/                ▼   |         |  /---/         ▼
	 *  +----+                  +----+     +----+           +----+
	 *  |  2 |◀-----------------|  4 |---▶|  5 |----------▶|  9 |
	 *  +----+                  +----+     +----+           +----+
	 *
	 */
	private static void testDirectedPath() {

		DirectedGraph<Integer> directedGraph = new DirectedGraph<>();
		directedGraph.addVertex(1);
		directedGraph.addVertex(2);
		directedGraph.addVertex(3);
		directedGraph.addVertex(4);
		directedGraph.addVertex(5);
		directedGraph.addVertex(6);
		directedGraph.addVertex(7);
		directedGraph.addVertex(8);
		directedGraph.addVertex(9);
		directedGraph.addEdge(1, 2);
		directedGraph.addEdge(2, 3);
		directedGraph.addEdge(3, 4);
		directedGraph.addEdge(4, 3);
		directedGraph.addEdge(4, 2);
		directedGraph.addEdge(4, 5);
		directedGraph.addEdge(5, 6);
		directedGraph.addEdge(5, 8);
		directedGraph.addEdge(5, 9);
		directedGraph.addEdge(6, 3);
		directedGraph.addEdge(6, 7);
		directedGraph.addEdge(7, 3);
		directedGraph.addEdge(8, 9);
		List<Integer> path = directedGraph.getPath(3, 1);
		assert path == null;
		path = directedGraph.getPath(7, 1);
		assert path == null;
		path = directedGraph.getPath(3, 4);
		assert path != null && path.isEmpty();
		path = directedGraph.getPath(1, 7);
		assert path.size() == 5 && path.containsAll(Arrays.asList(2, 3, 4, 5, 6));
		path = directedGraph.getPath(5, 3);
		assert path.size() == 1 && path.contains(6) || path.size() == 2 && path.containsAll(Arrays.asList(6, 7));
	}

	/**
	 *  +----+                  +----+     +----+           +----+
	 *  |  H |                  |  I |-----|  J |           |  K |
	 *  +----+                  +----+\    +----+           +----+
     *     |                           --     |                |
	 *     |                             \--  |                |
	 *  +----+ ---------------- +----+     +----+           +----+
	 *  |  A |                  |  C |     |  F |           |  G |
	 *  +----+              --  +----+     +----+           +----+
	 *                   --/               -/ |              /-
	 *                --/                -/   |            /-
	 *             --/                 -/     |          /-
	 *          --/                  -/       |        /-
	 *        -/                    /         |      /-
	 *  +----+                  +----+     +----+  /-
	 *  |  B |------------------|  D |     |  E |--
	 *  +----+                  +----+     +----+
	 *
	 */
	private static void testUndirectedPath() {
		UndirectedGraph<String> undirectedGraph = new UndirectedGraph<>();
		undirectedGraph.addVertex("A");
		undirectedGraph.addVertex("B");
		undirectedGraph.addVertex("C");
		undirectedGraph.addVertex("D");
		undirectedGraph.addVertex("E");
		undirectedGraph.addVertex("F");
		undirectedGraph.addVertex("G");
		undirectedGraph.addVertex("H");
		undirectedGraph.addVertex("I");
		undirectedGraph.addVertex("J");
		undirectedGraph.addVertex("K");
		undirectedGraph.addEdge("A", "C");
		undirectedGraph.addEdge("A", "H");
		undirectedGraph.addEdge("B", "C");
		undirectedGraph.addEdge("B", "D");
		undirectedGraph.addEdge("F", "D");
		undirectedGraph.addEdge("F", "E");
		undirectedGraph.addEdge("F", "I");
		undirectedGraph.addEdge("F", "J");
		undirectedGraph.addEdge("G", "E");
		undirectedGraph.addEdge("G", "K");
		undirectedGraph.addEdge("I", "J");
		List<String> path = undirectedGraph.getPath("A", "G");
		assert path.size() == 5 && path.containsAll(Arrays.asList("C", "B", "D", "F", "E"));
		path = undirectedGraph.getPath("G", "C");
		assert path.size() == 4 && path.containsAll(Arrays.asList("E", "F", "D", "B"));

	}
}
