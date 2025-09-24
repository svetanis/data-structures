package com.svetanis.datastructures.graph.directed.ts.reconstruct;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

// 444. Sequence Reconstruction

public final class SequenceReconstructionLeet {
	// Time Complexity: O(V + E)

	public static boolean reconstruct(int[] sequence, List<List<Integer>> sequences) {
		if (sequence.length == 0) {
			return false;
		}
		int n = sequence.length;
		int[] inDegree = new int[n];
		List<Integer>[] graph = new List[n];
		Arrays.setAll(graph, k -> new ArrayList<>());
		for (List<Integer> seq : sequences) {
			for (int i = 1; i < seq.size(); i++) {
				int from = seq.get(i - 1) - 1;
				int to = seq.get(i) - 1;
				inDegree[to]++;
				graph[from].add(to);
			}
		}
		Deque<Integer> dq = new ArrayDeque<>();
		for (int i = 0; i < n; i++) {
			if (inDegree[i] == 0) {
				dq.offer(i);
			}
		}
		while (!dq.isEmpty()) {
			if (dq.size() > 1) {
				return false;
			}
			int src = dq.poll();
			for (int adj : graph[src]) {
				inDegree[adj]--;
				if (inDegree[adj] == 0) {
					dq.offer(adj);
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int[] s1 = { 1, 2, 3 };
		List<List<Integer>> list1 = newArrayList();
		list1.add(asList(1, 2));
		list1.add(asList(1, 3));
		System.out.println(reconstruct(s1, list1)); // false

		List<List<Integer>> list2 = newArrayList();
		list2.add(asList(1, 2));
		System.out.println(reconstruct(s1, list2)); // false

		List<List<Integer>> list3 = newArrayList();
		list3.add(asList(1, 2));
		list3.add(asList(1, 3));
		list3.add(asList(2, 3));
		System.out.println(reconstruct(s1, list3)); // true

		int[] s2 = { 4, 1, 5, 2, 6, 3 };
		List<List<Integer>> list4 = newArrayList();
		list4.add(asList(5, 2, 6, 3));
		list4.add(asList(4, 1, 5, 2));
		System.out.println(reconstruct(s2, list4)); // true
	}
}
