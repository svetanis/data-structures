package com.svetanis.datastructures.graph.clone;

import static com.svetanis.datastructures.graph.bfs.BfsTraversal.bfs;

import java.util.HashMap;
import java.util.Map;

import com.svetanis.datastructures.graph.Node;

// 133. Clone Graph
//
// Input: node -- one node of a connected undirected graph, or null for the empty graph. A node
// holds a value and the list of the nodes it is joined to; an undirected edge appears in the lists
// of both its end nodes. Returns: the copy of node -- new nodes with the same values and the same
// neighbour order, sharing no node with the original (null for null). On LeetCode: 0 <= nodes <=
// 100, 1 <= value <= 100, values are unique, no repeated edges, no self-loops.
//
// The one idea: the map holds, for each value, the copy already made for the node with that value,
// and it does two jobs at once: it is the answer to "what is this node's copy?" and the mark for
// "have I been here?". The copy is put in the map BEFORE its neighbours are walked, so a walk that
// comes round a loop back to this node finds the copy and stops instead of starting a second one.
// Put the copy in the map after the loop and the first loop would never end.
//
// The map is keyed by the node's value, which is correct only because LeetCode promises the values
// are unique: two different nodes holding the same value would be merged into one copy.
//
// Time: O(V + E) -- each node is copied once and each neighbour list is read once.
// Space: O(V) -- the map, and the recursion is as deep as V in the worst case (a long path).
//
// Names, the same in every file of this package: node is an old node whose copy is getting its
// arrows; neighbor is one of node's neighbours; copy is node's copy; neighborCopy is neighbor's
// copy; map holds old -> copy.
//
// Siblings: CloneGraphMemo asks "already copied?" at the top of each call instead of in the
// caller's loop, keyed by the node itself; CloneGraphRecursive asks it in the caller's loop too,
// but there the caller builds neighbor's copy and hands it down; CloneGraphBfs uses a queue instead
// of recursion, keyed by the node itself. Keying by the node does not remove the need for unique
// values: Node.equals compares values and then neighbour lists, and two nodes with the same value
// joined to each other throw StackOverflowError in CloneGraphMemo and CloneGraphBfs.

public final class CloneGraphDfs {

	public static Node clone(Node node) {
		if (node == null) { // EMPTY: no graph at all
			return null;
		}
		return clone(node, new HashMap<>());
	}

	private static Node clone(Node node, Map<Integer, Node> map) {
		Node copy = new Node(node.getData()); // NEW: the call on node builds node's copy
		map.put(node.getData(), copy); // MARK on entry: before the walk
		for (Node neighbor : node.getNeighbors()) { // WALK the neighbours in list order
			Node neighborCopy = map.get(neighbor.getData()); // FOUND? asked by the caller
			if (neighborCopy == null) { // not copied yet: the call on neighbor builds it
				neighborCopy = clone(neighbor, map);
			}
			copy.add(neighborCopy); // WIRE: an arrow from node's copy to neighbor's copy
		}
		return copy; // RETURN: the copy of node
	}

	public static void main(String[] args) {
		// one-way edges 1 -> 2 and 1 -> 3, enough to see the copy
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		node1.add(node2);
		node1.add(node3);
		System.out.println(bfs(node1)); // [1, 2, 3]
		Node clone = clone(node1);
		System.out.println(bfs(clone)); // [1, 2, 3]

		// LeetCode example 1: the four-cycle 1 - 2 - 3 - 4 - 1, every edge in both lists
		Node a = new Node(1);
		Node b = new Node(2);
		Node c = new Node(3);
		Node d = new Node(4);
		a.add(b);
		a.add(d);
		b.add(a);
		b.add(c);
		c.add(b);
		c.add(d);
		d.add(a);
		d.add(c);
		System.out.println(bfs(a)); // [1, 2, 4, 3]
		System.out.println(bfs(clone(a))); // [1, 2, 4, 3]
		System.out.println(clone(null)); // null
	}
}
