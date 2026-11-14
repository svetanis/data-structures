package com.svetanis.datastructures.graph.directed.ts.reconstruct;

import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

// 444. Sequence Reconstruction
//
// Input: int[] `sequence` (LeetCode's nums, a permutation of 1..n) and a list of lists
// `sequences`, each one a subsequence of it. On LeetCode: 1 <= n <= 10^4,
// 1 <= sequences.length <= 10^4, 1 <= sum of the list lengths <= 10^5, values 1..n.
// Returns true when `sequence` is the ONLY shortest list that has every list as a subsequence.
//
// The one idea: two neighbours in a list, a then b, are a rule: a must come before b. One value
// is one graph node (stored at index value - 1); one rule is one arrow a -> b. A value is READY
// when no rule still waits on it (in-degree 0). Take ready values one at a time. If two are ready
// at once, either could go next, so two different lists fit: false. If exactly one is ready it is
// forced, and it must be the value `sequence` holds at that position. If all n are taken the
// answer is true; if the queue runs dry early the rules form a loop and no list fits.
//
// Indexing by value - 1 works because LeetCode guarantees the values are exactly 1..n; a value
// above n would be out of range.
//
// Time: O(n + E) -- E neighbour pairs; each value and each arrow is handled once.
// Space: O(n + E) -- the arrows and the in-degree array.
//
// Siblings: SequenceReconstruction, SequenceReconstructionSubmit and ReconstructingSequence do the
// same with hash maps keyed by the value itself, which needs no 1..n guarantee.

public final class SequenceReconstructionLeet {

	public static boolean reconstruct(int[] sequence, List<List<Integer>> sequences) {
		if (sequence.length == 0) {
			return false;
		}
		int n = sequence.length;
		int[] inDegree = new int[n]; // CARDS: inDegree[v] -- rules still waiting on value v + 1
		List<Integer>[] graph = new List[n];
		Arrays.setAll(graph, k -> new ArrayList<>());
		for (List<Integer> seq : sequences) {
			for (int i = 1; i < seq.size(); i++) {
				int from = seq.get(i - 1) - 1;
				int to = seq.get(i) - 1;
				inDegree[to]++; // ARROW: from must come before to
				graph[from].add(to);
			}
		}
		Deque<Integer> dq = new ArrayDeque<>();
		for (int i = 0; i < n; i++) { // READY: every value with in-degree 0
			if (inDegree[i] == 0) {
				dq.offer(i);
			}
		}
		int emitted = 0;
		while (!dq.isEmpty()) {
			if (dq.size() > 1) { // TWO READY: either could go next
				return false;
			}
			int src = dq.poll();
			// a forced order is not enough: the order the graph forces has to
			// BE the given sequence. {2, 1} as the only pair forces 2 then 1,
			// which is unique but is not [1, 2] -- so the answer is false
			if (src != sequence[emitted] - 1) { // WRONG ONE
				return false;
			}
			emitted++;
			for (int adj : graph[src]) { // RELEASE: this value's rules are satisfied
				inDegree[adj]--;
				if (inDegree[adj] == 0) {
					dq.offer(adj);
				}
			}
		}
		// RETURN: fewer than n emitted means a cycle, so no order exists at all
		return emitted == n;
	}

	public static void main(String[] args) {
		int[] s1 = { 1, 2, 3 };
		List<List<Integer>> list1 = new ArrayList<>();
		list1.add(asList(1, 2));
		list1.add(asList(1, 3));
		System.out.println(reconstruct(s1, list1)); // false

		List<List<Integer>> list2 = new ArrayList<>();
		list2.add(asList(1, 2));
		System.out.println(reconstruct(s1, list2)); // false

		List<List<Integer>> list3 = new ArrayList<>();
		list3.add(asList(1, 2));
		list3.add(asList(1, 3));
		list3.add(asList(2, 3));
		System.out.println(reconstruct(s1, list3)); // true

		int[] s2 = { 4, 1, 5, 2, 6, 3 };
		List<List<Integer>> list4 = new ArrayList<>();
		list4.add(asList(5, 2, 6, 3));
		list4.add(asList(4, 1, 5, 2));
		System.out.println(reconstruct(s2, list4)); // true

		// the pairs force 2 before 1: the order is unique, but it is not this
		// sequence, so the answer is false even though the queue never holds
		// two nodes at once
		int[] s3 = { 1, 2 };
		List<List<Integer>> list5 = new ArrayList<>();
		list5.add(asList(2, 1));
		System.out.println(reconstruct(s3, list5)); // false

		// and a cycle: 1 -> 2 -> 1 leaves the queue empty from the start
		int[] s4 = { 1, 2 };
		List<List<Integer>> list6 = new ArrayList<>();
		list6.add(asList(1, 2));
		list6.add(asList(2, 1));
		System.out.println(reconstruct(s4, list6)); // false
	}
}
