package com.svetanis.datastructures.graph.bfs;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

// given an unweighted connected graph,
// return the length of the shortest path
// between two nodes A and B, in terms of
// the number of edges

// assume there always exists a path
// between nodes A and B

public final class ShortestPathLen {
	// Time Complexity: O(V + E)
	// Space Complexity: O(V)

	public static int sp(List<List<Integer>> g, int src, int dst) {
		return bfs(g, src, dst);
	}

	private static int bfs(List<List<Integer>> g, int src, int dst) {
		Set<Integer> set = new HashSet<>();
		Queue<Integer> queue = new ArrayDeque<>();
		queue.add(src);
		set.add(src);
		int level = 0;
		while (!queue.isEmpty()) {
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				int node = queue.poll();
				if (node == dst) {
					return level;
				}
				for (int neighbour : g.get(node)) {
					if (!set.contains(neighbour)) {
						queue.add(neighbour);
						set.add(neighbour);
					}
				}
			}
			level += 1;
		}
		return level;
	}

	public static void main(String[] args) {
		List<List<Integer>> list = newArrayList();
		list.add(asList(1, 2));
		list.add(asList(0, 2, 3));
		list.add(asList(0, 1));
		list.add(asList(1));
		System.out.println(bfs(list, 0, 3)); // 2

		List<List<Integer>> list1 = newArrayList();
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

		List<List<Integer>> list2 = newArrayList();
		list2.add(asList(1, 2));
		list2.add(asList(0));
		list2.add(asList(0));
		System.out.println(bfs(list2, 1, 2)); // 2

		List<List<Integer>> list3 = newArrayList();
		list3.add(asList(1));
		list3.add(asList(0));
		System.out.println(bfs(list3, 1, 0)); // 1
	}
}
