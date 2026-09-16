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
		int emitted = 0;
		while (!dq.isEmpty()) {
			if (dq.size() > 1) {
				return false;
			}
			int src = dq.poll();
			// a forced order is not enough: the order the graph forces has to
			// BE the given sequence. {2, 1} as the only pair forces 2 then 1,
			// which is unique but is not [1, 2] -- so the answer is false
			if (src != sequence[emitted] - 1) {
				return false;
			}
			emitted++;
			for (int adj : graph[src]) {
				inDegree[adj]--;
				if (inDegree[adj] == 0) {
					dq.offer(adj);
				}
			}
		}
		// fewer than n emitted means a cycle, so no order exists at all
		return emitted == n;
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

		// the pairs force 2 before 1, which is a unique order but not this
		// sequence. Without the position check this reports true, because
		// the queue never holds two nodes at once
		int[] s3 = { 1, 2 };
		List<List<Integer>> list5 = newArrayList();
		list5.add(asList(2, 1));
		System.out.println(reconstruct(s3, list5)); // false

		// and a cycle: 1 -> 2 -> 1 leaves the queue empty from the start
		int[] s4 = { 1, 2 };
		List<List<Integer>> list6 = newArrayList();
		list6.add(asList(1, 2));
		list6.add(asList(2, 1));
		System.out.println(reconstruct(s4, list6)); // false
	}
}
