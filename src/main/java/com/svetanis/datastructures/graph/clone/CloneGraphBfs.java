package com.svetanis.datastructures.graph.clone;

import static com.svetanis.datastructures.graph.bfs.BfsTraversal.bfs;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import com.svetanis.datastructures.graph.Node;

// 133. Clone Graph
//
// Input: start -- one node of a connected undirected graph, or null for the empty graph. A node
// holds a value and the list of the nodes it is joined to; an undirected edge appears in the lists
// of both its end nodes. Returns: the copy of start -- new nodes with the same values and the same
// neighbour order, sharing no node with the original (null for null). On LeetCode: 0 <= nodes <=
// 100, 1 <= value <= 100, values are unique, no repeated edges, no self-loops.
//
// The one idea: a copy of the graph is a copy of every node plus the same joins between the
// copies. The map holds, for each original node, its copy. A node goes into the map the moment it
// is first seen, and that is what stops the walk from copying a node twice or from circling a loop
// forever: "is this node in the map?" and "have I met this node?" are the same question. The queue
// holds the original nodes whose neighbour lists are still to be copied. Take one off, find its
// copy, and for each neighbour find that neighbour's copy (make it, and queue the neighbour, if it
// is new) and add it to the copy's list. The neighbours are taken in list order, so the copy's
// lists come out in the same order.
//
// Time: O(V + E) -- each node is queued once and each neighbour list is read once.
// Space: O(V) -- the map and the queue hold at most one entry per node.
//
// Known limit: the map is keyed by the node itself, and Node.equals compares values and then
// neighbour lists, so two different nodes with the same value joined to each other throw
// StackOverflowError on lookup. LeetCode promises unique values, which rules this out.
//
// Names, the same in every file of this package: node is an old node whose copy is getting its
// arrows; neighbor is one of node's neighbours; copy is node's copy; neighborCopy is neighbor's
// copy; map holds old -> copy. Here the handed-in node is start, and node is the one just taken off
// the queue.
//
// Siblings: CloneGraphDfs does the same job recursively and keys its map by the node's value;
// CloneGraphRecursive is the recursive form where the caller builds neighbor's copy; CloneGraphMemo
// is the recursive form keyed by the node itself, asking "already copied?" at the top of each call;
// graph/undirected/traverse/CloneGraphWithConnectedComponents runs this same breadth-first copy
// once per component.

public final class CloneGraphBfs {

	public static Node clone(Node start) {
		if (start == null) { // EMPTY: no graph at all
			return null;
		}
		Map<Node, Node> map = new HashMap<>(); // MAP: old node -> its copy
		Queue<Node> queue = new ArrayDeque<>(); // QUEUE: old nodes still to wire
		queue.offer(start);
		map.put(start, new Node(start.getData())); // MARK on entry: start is already copied
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			Node copy = map.get(node); // never null: every queued node was put in the map first
			for (Node neighbor : node.getNeighbors()) { // WALK the neighbours in list order
				Node neighborCopy = map.get(neighbor); // FOUND? asked before neighbor is queued
				if (neighborCopy == null) { // NEW: first time neighbor is seen
					neighborCopy = new Node(neighbor.getData());
					map.put(neighbor, neighborCopy); // MARK on entry
					queue.offer(neighbor);
				}
				copy.add(neighborCopy); // WIRE: an arrow from node's copy to neighbor's copy
			}
		}
		return map.get(start); // RETURN: the copy of start
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
