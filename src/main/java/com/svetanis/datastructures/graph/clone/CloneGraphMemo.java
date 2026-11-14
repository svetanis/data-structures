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
// The one idea: one map, old node -> its copy, does two jobs. It is the visited mark (is this old
// node in the map?) and the finder (map.get gives its copy, which no arrow connects to the old
// node). Each call checks the map first and hands back the copy it finds, so the call has the shape
// of a memoised recursion -- with one difference: the copy is put in the map BEFORE the neighbours
// are walked, because every undirected edge leads straight back to the node being copied, and that
// return visit must find the copy instead of building a second one.
//
// Inside one call, node stays the same old node on every turn of the loop; neighbor walks its
// neighbour list. The arrow goes FROM node's copy (map.get(node)) TO neighbor's copy (what the call
// on neighbor hands back). Putting it on map.get(neighbor) instead throws NullPointerException on
// the first neighbour, whose copy does not exist yet.
//
// The map is keyed by the node itself. Node's hashCode depends on its value alone and its equals
// returns true at once for the same object, so with unique values every old node is its own key.
// Known limit: two different nodes with the same value make Node.equals compare their neighbour
// lists, which lead back to each other, and the lookup throws StackOverflowError -- CloneGraphBfs
// has the same limit. LeetCode's own Node does not override equals or hashCode, so there every
// node is a different key whatever its value.
//
// Time: O(V + E) -- each node is copied once and each neighbour list is read once.
// Space: O(V) -- the map, and the recursion is as deep as V in the worst case: a line of 10,000
// nodes throws StackOverflowError (CloneGraphBfs has no such limit).
//
// Names, the same in every file of this package: node is an old node whose copy is getting its
// arrows; neighbor is one of node's neighbours; copy is node's copy; neighborCopy is neighbor's
// copy; map holds old -> copy.
//
// Siblings: CloneGraphDfs asks "already copied?" in the caller's loop and builds node's copy in the
// call on node, keyed by value; CloneGraphRecursive asks it in the caller's loop, where the caller
// builds neighbor's copy and hands it down, keyed by value; CloneGraphBfs uses a queue instead of
// recursion.

public final class CloneGraphMemo {

	public static Node clone(Node node) {
		return clone(node, new HashMap<>());
	}

	private static Node clone(Node node, Map<Node, Node> map) {
		if (node == null) { // EMPTY: no graph at all
			return null;
		}
		if (map.containsKey(node)) { // FOUND: copied already -> hand back its copy
			return map.get(node);
		}
		map.put(node, new Node(node.getData())); // NEW: written to the map BEFORE the walk
		for (Node neighbor : node.getNeighbors()) { // WIRE: one arrow per old neighbour
			Node copy = map.get(node); // GETS the arrow: this call's own copy, not neighbor's
			copy.add(clone(neighbor, map)); // LANDS on the neighbour's copy, found or built
		}
		return map.get(node); // RETURN: the copy of the node we were handed
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
