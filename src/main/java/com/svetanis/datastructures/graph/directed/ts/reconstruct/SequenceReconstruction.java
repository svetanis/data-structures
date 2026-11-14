package com.svetanis.datastructures.graph.directed.ts.reconstruct;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

// 444. Sequence Reconstruction
//
// Input: a list `sequence` (LeetCode's nums, a permutation of 1..n) and a list of lists
// `sequences`, each one a subsequence of it (delete some values, keep the order). On LeetCode:
// 1 <= n <= 10^4, 1 <= sequences.length <= 10^4, 1 <= sum of the list lengths <= 10^5,
// values 1..n.
// Returns true when `sequence` is the ONLY shortest list that has every list as a subsequence.
//
// The one idea: two neighbours in a list, a then b, are a rule: a must come before b. One value
// is one graph node; one rule is one arrow a -> b. A value is READY when no rule still waits on
// it (in-degree 0). Take ready values one at a time. If two are ready at once, either could go
// next, so two different lists fit and the answer is false. If exactly one is ready it is forced,
// and it must be the value `sequence` holds at that position. A value that appears in no list has
// no rule at all, so it is ready from the start and sits beside another ready value: false.
//
// Time: O(V + E) -- V values, E neighbour pairs; each value and each arrow is handled once.
// Space: O(V + E) -- the arrows (as sets, so a repeated pair counts once) and the in-degrees.
//
// Siblings: SequenceReconstructionSubmit is the same method; ReconstructingSequence takes int[][];
// SequenceReconstructionLeet indexes arrays by value - 1.

public final class SequenceReconstruction {

	public static boolean reconstruct(List<Integer> sequence, List<List<Integer>> sequences) {
		if (sequence.size() == 0) {
			return false;
		}
		Map<Integer, Set<Integer>> graph = graph(sequence, sequences);
		return topoSort(sequence, graph);
	}

	private static boolean topoSort(List<Integer> sequence, Map<Integer, Set<Integer>> graph) {
		Map<Integer, Integer> inDegree = inDegree(graph);
		// every value of `sequence` is a node, so this differs only if `sequence` repeats a value
		if (inDegree.size() != sequence.size()) {
			return false;
		}
		List<Integer> list = new ArrayList<>(); // TAKEN: the forced order so far
		Queue<Integer> queue = sources(inDegree);
		while (!queue.isEmpty()) {
			// TWO READY: either could go next, so more than one list fits
			if (queue.size() > 1) {
				return false;
			}
			// WRONG ONE: the forced value is not the one `sequence` holds here.
			// Both sides are Integer, so != would compare identity rather than value:
			// Java caches the boxes for -128..127 only, so that spelling agrees with
			// equals() on small inputs and stops agreeing above 127
			if (!sequence.get(list.size()).equals(queue.peek())) {
				return false;
			}
			int src = queue.poll();
			list.add(src);
			for (int neighbor : graph.get(src)) { // RELEASE: this value's rules are satisfied
				int ind = inDegree.get(neighbor) - 1;
				inDegree.put(neighbor, ind);
				if (ind == 0) {
					queue.add(neighbor);
				}
			}
		}
		return list.equals(sequence); // RETURN: a loop of rules would leave `list` short
	}

	private static Queue<Integer> sources(Map<Integer, Integer> inDegree) {
		Queue<Integer> queue = new ArrayDeque<>();
		for (int key : inDegree.keySet()) { // READY: every value with in-degree 0
			if (inDegree.get(key) == 0) {
				queue.add(key);
			}
		}
		return queue;
	}

	private static Map<Integer, Integer> inDegree(Map<Integer, Set<Integer>> graph) {
		Map<Integer, Integer> map = inDegreeInit(graph);
		for (int node : graph.keySet()) {
			for (int neighbor : graph.get(node)) {
				map.put(neighbor, map.get(neighbor) + 1);
			}
		}
		return map;
	}

	private static Map<Integer, Integer> inDegreeInit(Map<Integer, Set<Integer>> graph) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int key : graph.keySet()) {
			map.put(key, 0);
		}
		return map;
	}

	private static Map<Integer, Set<Integer>> graph(List<Integer> sequence, List<List<Integer>> sequences) {
		Map<Integer, Set<Integer>> map = graphInit(sequence);
		for (List<Integer> s : sequences) {
			for (int i = 0; i < s.size() - 1; i++) {
				map.get(s.get(i)).add(s.get(i + 1)); // ARROW: s[i] must come before s[i + 1]
			}
		}
		return map;
	}

	private static Map<Integer, Set<Integer>> graphInit(List<Integer> sequence) {
		Map<Integer, Set<Integer>> map = new HashMap<>();
		for (int s : sequence) {
			map.put(s, new HashSet<>());
		}
		return map;
	}

	public static void main(String[] args) {
		List<Integer> s1 = new ArrayList<>(Arrays.asList(1, 2, 3));
		List<List<Integer>> list1 = new ArrayList<>();
		list1.add(Arrays.asList(1, 2));
		list1.add(Arrays.asList(1, 3));
		System.out.println(reconstruct(s1, list1)); // false

		List<List<Integer>> list2 = new ArrayList<>();
		list2.add(Arrays.asList(1, 2));
		System.out.println(reconstruct(s1, list2)); // false

		List<List<Integer>> list3 = new ArrayList<>();
		list3.add(Arrays.asList(1, 2));
		list3.add(Arrays.asList(1, 3));
		list3.add(Arrays.asList(2, 3));
		System.out.println(reconstruct(s1, list3)); // true

		List<Integer> s2 = new ArrayList<>(Arrays.asList(4, 1, 5, 2, 6, 3));
		List<List<Integer>> list4 = new ArrayList<>();
		list4.add(Arrays.asList(5, 2, 6, 3));
		list4.add(Arrays.asList(4, 1, 5, 2));
		System.out.println(reconstruct(s2, list4)); // true

		// case 3 again with every value pushed above 127, where Java stops handing out
		// the same Integer box for the same number. The answer must not depend on how
		// large the numbers are
		List<Integer> s3 = new ArrayList<>(Arrays.asList(200, 201, 202));
		List<List<Integer>> list5 = new ArrayList<>();
		list5.add(Arrays.asList(200, 201));
		list5.add(Arrays.asList(200, 202));
		list5.add(Arrays.asList(201, 202));
		System.out.println(reconstruct(s3, list5)); // true
	}
}
