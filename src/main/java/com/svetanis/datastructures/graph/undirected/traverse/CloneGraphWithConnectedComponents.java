package com.svetanis.datastructures.graph.undirected.traverse;

import static com.svetanis.datastructures.graph.undirected.traverse.BfsTraversalWithConnectedComponents.bfs;
import static com.svetanis.datastructures.graph.undirected.traverse.DfsTraversalWithConnectedComponents.dfs;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import com.svetanis.datastructures.graph.Graph;
import com.svetanis.datastructures.graph.Node;

// Clone a graph that has several connected components (the variation of 133. Clone Graph where the
// input is the list of all the nodes, not one node of a single component).
//
// Input: g -- a graph given as a list of nodes; each node holds a value and the list of nodes it is
// joined to. The nodes may fall into several separate pieces (connected components). Values are
// unique. Returns: a new graph whose list holds one node per piece -- the copy of the first node of
// that piece, in the order the pieces first appear in g's list -- with every node reachable from it
// copied, sharing no node with the original.
//
// The one idea: copying one piece is the breadth-first copy of CloneGraphBfs: a map from each
// original node to its copy, filled the moment a node is first seen, and a queue of originals whose
// neighbour lists are still to be copied. A graph with several pieces is that copy run once for
// every node of the list that is not yet in the map; a node already in the map was copied as part
// of an earlier piece, so it is skipped.
//
// Time: O(V + E) -- each node is queued once and each neighbour list is read once.
// Space: O(V) -- the map and the queue hold at most one entry per node.
//
// Siblings: CloneGraphBfs is the one-piece version (LeetCode 133); this file's private clone(Node,
// Map) is its body, and the map is shared between the pieces.

public final class CloneGraphWithConnectedComponents {

	public static Graph clone(Graph g) {
		Graph cloned = new Graph();
		Map<Node, Node> map = new HashMap<>(); // MAP: original node -> its copy, shared by all pieces
		for (Node node : g.getNodes()) {
			if (map.get(node) == null) { // NEW PIECE: this node was not reached from an earlier one
				Node clone = clone(node, map);
				cloned.addNode(clone);
			}
		}
		return cloned; // RETURN: one copied node per piece
	}

	private static Node clone(Node src, Map<Node, Node> map) {
		Queue<Node> queue = new ArrayDeque<>(); // QUEUE: originals whose neighbours are still to be copied
		queue.offer(src);
		map.put(src, new Node(src.getData())); // MARK on entry: src is already copied
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			Node copy = map.get(node);
			if (copy == null) { // never true here: every queued node was put in the map first
				copy = new Node(node.getData());
				map.put(node, copy);
			}
			for (Node neighbor : node.getNeighbors()) { // WALK the neighbours in list order
				Node clone = map.get(neighbor);
				if (clone == null) { // NEW: first time this node is seen
					queue.offer(neighbor);
					clone = new Node(neighbor.getData());
					map.put(neighbor, clone); // MARK on entry
				}
				copy.add(clone); // WIRE: the copy points at the neighbour's copy
			}
		}
		return map.get(src); // RETURN: the copy of src
	}

	public static void main(String[] args) {
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		Node node5 = new Node(5);
		Node node6 = new Node(6);

		// piece one: the four-cycle 1 - 2 - 4 - 3 - 1; piece two: the single edge 5 - 6
		node1.add(node2);
		node1.add(node3);
		node2.add(node1);
		node2.add(node4);
		node3.add(node1);
		node3.add(node4);
		node4.add(node2);
		node4.add(node3);
		node5.add(node6);
		node6.add(node5);

		Graph g = new Graph();
		g.addNode(node1);
		g.addNode(node2);
		g.addNode(node3);
		g.addNode(node4);
		g.addNode(node5);
		g.addNode(node6);

		Graph clone = clone(g);

		System.out.println(dfs(g)); // [[1, 2, 4, 3], [5, 6]]
		System.out.println(dfs(clone)); // [[1, 2, 4, 3], [5, 6]]
		System.out.println();
		System.out.println(bfs(g)); // [[1, 2, 3, 4], [5, 6]]
		System.out.println(bfs(clone)); // [[1, 2, 3, 4], [5, 6]]
	}
}
