package com.svetanis.datastructures.graph.bfs;

import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

// Shortest path length between two nodes of an unweighted graph.
//
// Input: g -- an adjacency list, g.get(i) holds the nodes that node i has an edge to; src and dst --
// two nodes. Returns the fewest edges on a path from src to dst (0 when they are the same node).
// Assumes the graph is connected, so a path always exists; if dst cannot be reached, the method
// returns the number of rounds the search ran, not -1.
//
// The one idea: one queue entry is a node that has been reached by a path of the current length and
// whose neighbours have not been looked at yet. Work in rounds: round 0 holds src alone, round 1 holds
// every node one edge from src, round 2 every node two edges away, and so on. With every edge costing
// the same, the first round in which dst comes off the queue is its shortest distance, because no
// shorter route could have reached it in an earlier round. A node is marked when it is queued, so it
// is never queued again by a longer route.
//
// Time: O(V + E) -- each node is queued once and each edge is looked at once from its tail.
// Space: O(V) -- the found set and the queue.

public final class ShortestPathLen {

	public static int sp(List<List<Integer>> g, int src, int dst) {
		return bfs(g, src, dst);
	}

	private static int bfs(List<List<Integer>> g, int src, int dst) {
		Set<Integer> set = new HashSet<>(); // FOUND: nodes that are on the queue or already visited
		Queue<Integer> queue = new ArrayDeque<>();
		queue.add(src);
		set.add(src);
		int level = 0; // LEVEL: edges from src to the nodes in the current round
		while (!queue.isEmpty()) {
			int size = queue.size(); // ROUND: only the nodes already queued belong to this level
			for (int i = 0; i < size; i++) {
				int node = queue.poll(); // TAKE: a node of this level
				if (node == dst) {
					return level; // RETURN: the first round that reaches dst is the shortest
				}
				for (int neighbour : g.get(node)) {
					if (!set.contains(neighbour)) { // SKIP: a node already found
						queue.add(neighbour);
						set.add(neighbour);
					}
				}
			}
			level += 1; // the next round is one edge further out
		}
		return level; // dst was never reached: outside the stated assumption
	}

	public static void main(String[] args) {
		List<List<Integer>> list = new ArrayList<>();
		list.add(asList(1, 2));
		list.add(asList(0, 2, 3));
		list.add(asList(0, 1));
		list.add(asList(1));
		System.out.println(bfs(list, 0, 3)); // 2

		List<List<Integer>> list1 = new ArrayList<>();
		list1.add(asList(1, 4));
		list1.add(asList(0, 2));
		list1.add(asList(1, 3));
		list1.add(asList(2, 4, 5));
		list1.add(asList(0, 3));
		list1.add(asList(3, 6, 9));
		list1.add(asList(5, 8));
		list1.add(asList(8, 9));
		list1.add(asList(6, 7));
		list1.add(asList(5, 7));
		System.out.println(bfs(list1, 0, 7)); // 5

		List<List<Integer>> list2 = new ArrayList<>();
		list2.add(asList(1, 2));
		list2.add(asList(0));
		list2.add(asList(0));
		System.out.println(bfs(list2, 1, 2)); // 2

		List<List<Integer>> list3 = new ArrayList<>();
		list3.add(asList(1));
		list3.add(asList(0));
		System.out.println(bfs(list3, 1, 0)); // 1
		System.out.println(bfs(list3, 0, 0)); // 0
	}
}
