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
// The one idea: the caller makes the copy of a node and hands it down; the recursion fills in that
// copy's neighbour list. For each neighbour it either finds the copy already made (the map holds
// one copy per value) or makes the copy, records it, and recurses into it. Every undirected edge
// shows up in both end nodes' lists, so the graph is full of loops, and a walk with no record of
// the copies already made would go round the first loop forever. The copy is recorded before it is
// recursed into for the same reason.
//
// The map is keyed by the node's value, which is correct only because LeetCode promises the values
// are unique.
//
// Time: O(V + E) -- each node is copied once and each neighbour list is read once.
// Space: O(V) -- the map, and the recursion is as deep as V in the worst case (a long path).
//
// Names, the same in every file of this package: node is an old node whose copy is getting its
// arrows; neighbor is one of node's neighbours; copy is node's copy; neighborCopy is neighbor's
// copy; map holds old -> copy.
//
// Siblings: CloneGraphDfs is the same walk with neighbor's copy built by the call on neighbor
// instead of by the caller; CloneGraphMemo asks "already copied?" at the top of each call, keyed by
// the node itself; CloneGraphBfs uses a queue instead of recursion.

public final class CloneGraphRecursive {

	public static Node clone(Node node) {
		if (node == null) { // EMPTY: no graph at all
			return null;
		}
		Node copy = new Node(node.getData()); // NEW: the first copy is built here, by the caller
		Map<Integer, Node> map = new HashMap<>(); // MAP: value -> the copy already made
		map.put(node.getData(), copy); // MARK on entry
		clone(node, copy, map);
		return copy; // RETURN: the copy of node
	}

	private static void clone(Node node, Node copy, Map<Integer, Node> map) {
		for (Node neighbor : node.getNeighbors()) { // WALK the neighbours in list order
			Node neighborCopy = map.get(neighbor.getData()); // FOUND? asked by the caller
			if (neighborCopy == null) { // NEW: the caller builds neighbor's copy and hands it down
				neighborCopy = new Node(neighbor.getData());
				map.put(neighbor.getData(), neighborCopy); // MARK on entry, before recursing
				clone(neighbor, neighborCopy, map);
			}
			copy.add(neighborCopy); // WIRE: an arrow from node's copy to neighbor's copy
		}
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
