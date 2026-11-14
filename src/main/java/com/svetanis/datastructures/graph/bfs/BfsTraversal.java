package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.svetanis.datastructures.graph.Node;

// Breadth-first traversal of a graph from one source node.
//
// Input: src -- a node; each node holds an integer (getData) and the list of nodes it has an edge to
// (getNeighbors). Returns the integers of every node reachable from src, in the order they are
// visited: src first, then its neighbours, then their neighbours, and so on. Assumes the integers
// held by the nodes are distinct, because a node is looked up in a set by its contents.
//
// The one idea: one queue entry is a node that has been found but whose neighbours have not been
// looked at yet. Take the oldest entry, write its integer down, and put each neighbour that has not
// been found before on the queue. A node is marked as found at the moment it goes on the queue, not
// when it comes off, so no node is queued twice even when the graph has cycles. Because the queue
// serves the oldest entry first, every node at distance 1 from src is visited before any at
// distance 2, and so on.
//
// Time: O(V + E) -- each node is queued once, and each edge is looked at once from its tail.
// Space: O(V) -- the found set, the queue, and the answer.
//
// Siblings: BfsTraversalWithConnectedComponents runs this same walk from every node not yet found,
// so it also covers the parts of the graph that src cannot reach.

public final class BfsTraversal {

	public static List<Integer> bfs(Node src) {
		Set<Node> set = new HashSet<>(); // FOUND: nodes that are on the queue or already visited
		List<Integer> list = new ArrayList<>();
		Queue<Node> queue = new ArrayDeque<>();
		queue.add(src);
		set.add(src); // MARK: when queued, so a node is never queued twice
		while (!queue.isEmpty()) {
			Node node = queue.poll(); // TAKE: the oldest found node
			list.add(node.getData()); // RECORD: visiting order
			for (Node neighbour : node.getNeighbors()) {
				if (!set.contains(neighbour)) { // SKIP: a node already found
					queue.add(neighbour);
					set.add(neighbour);
				}
			}
		}
		return list; // RETURN: integers in visiting order
	}

	public static void main(String[] args) {
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		node1.add(node2);
		node1.add(node3);
		System.out.println(bfs(node1)); // [1, 2, 3]

		// a cycle: 1 -> 2 -> 3 -> 1 and 2 -> 4
		Node a = new Node(1);
		Node b = new Node(2);
		Node c = new Node(3);
		Node d = new Node(4);
		a.add(b);
		b.add(c);
		b.add(d);
		c.add(a);
		System.out.println(bfs(a)); // [1, 2, 3, 4]
	}
}
