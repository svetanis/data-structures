package com.svetanis.datastructures.graph.directed.ts.reconstruct;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// 444. Sequence Reconstruction
//
// Input: a list `sequence` (LeetCode's nums, a permutation of 1..n) and int[][] `sequences`, each
// row a subsequence of it. On LeetCode: 1 <= n <= 10^4, 1 <= sequences.length <= 10^4,
// 1 <= sum of the row lengths <= 10^5, values 1..n.
// Returns true when `sequence` is the ONLY shortest list that has every row as a subsequence.
//
// The one idea: two neighbours in a row, a then b, are a rule: a must come before b. One value is
// one graph node; one rule is one arrow a -> b. A value is READY when no rule still waits on it
// (its in-degree is 0). Take ready values one at a time. If two are ready at once, either could go
// next, so two different lists fit and the answer is false. If exactly one is ready, it is forced,
// and it must be the value `sequence` holds at that position. If every value is taken the answer
// is true; if the ready queue runs dry early, the rules form a loop and no list fits at all. A
// value that appears in no row has no rule at all, so it could sit anywhere -- the count of values
// seen in the rows must equal n.
//
// Time: O(V + E) -- V values, E neighbour pairs; each value and each arrow is handled once.
// Space: O(V + E) -- the arrows and the in-degree counts.
//
// Siblings: SequenceReconstruction and SequenceReconstructionSubmit take rows as lists and build the
// graph from the values of `sequence`; SequenceReconstructionLeet indexes arrays by value - 1;
// this one keeps arrows in a map built from the rows themselves.

public final class ReconstructingSequence {

	public static boolean reconstruct(List<Integer> sequence, int[][] sequences) {
		if (sequence.size() == 0) {
			return false;
		}
		Map<Integer, Integer> inDegree = inDegreeInit(sequences);
		Map<Integer, List<Integer>> graph = graphInit(sequences);
		buildGraph(sequences, inDegree, graph);
		// a value in no row has no rule, so it could go anywhere: not unique
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
			List<Integer> children = graph.get(src);
			for (int child : children) { // RELEASE: this value's rules are satisfied
				int f = inDegree.getOrDefault(child, 0) - 1;
				if (f == 0) {
					queue.add(child);
				}
				inDegree.put(child, f);
			}
		}
		// RETURN: fewer than all taken means a loop of rules, so no list fits
		return list.size() == sequence.size();
	}

	private static Queue<Integer> sources(Map<Integer, Integer> map) {
		Queue<Integer> queue = new ArrayDeque<>();
		for (int key : map.keySet()) { // READY: every value with in-degree 0
			if (map.get(key) == 0) {
				queue.add(key);
			}
		}
		return queue;
	}

	private static void buildGraph(int[][] sequences, Map<Integer, Integer> inDegree,
			Map<Integer, List<Integer>> map) {
		for (int[] s : sequences) {
			for (int i = 1; i < s.length; i++) {
				int parent = s[i - 1];
				int child = s[i];
				map.get(parent).add(child); // ARROW: parent must come before child
				int fc = inDegree.getOrDefault(child, 0);
				inDegree.put(child, fc + 1);
			}
		}
	}

	private static Map<Integer, Integer> inDegreeInit(int[][] sequences) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int[] s : sequences) {
			for (int i = 0; i < s.length; i++) {
				map.putIfAbsent(s[i], 0);
			}
		}
		return map;
	}

	private static Map<Integer, List<Integer>> graphInit(int[][] sequences) {
		Map<Integer, List<Integer>> map = new HashMap<>();
		for (int[] s : sequences) {
			for (int i = 0; i < s.length; i++) {
				map.put(s[i], new ArrayList<>());
			}
		}
		return map;
	}

	public static void main(String[] args) {
		List<Integer> s1 = new ArrayList<>(List.of(1, 2, 3, 4));
		List<Integer> s2 = new ArrayList<>(List.of(3, 1, 4, 2, 5));
		int[][] m0 = { { 1, 2 }, { 2, 3 }, { 3, 4 } };
		int[][] m1 = { { 1, 2 }, { 2, 3 }, { 2, 4 } };
		int[][] m2 = { { 3, 1, 5 }, { 1, 4, 2, 5 } };
		System.out.println(reconstruct(s1, m0)); // true
		System.out.println(reconstruct(s1, m1)); // false
		System.out.println(reconstruct(s2, m2)); // true

		// the first case again with every value pushed above 127, where Java stops
		// handing out the same Integer box for the same number. The answer must not
		// depend on how large the numbers are
		List<Integer> s3 = new ArrayList<>(List.of(201, 202, 203, 204));
		int[][] m3 = { { 201, 202 }, { 202, 203 }, { 203, 204 } };
		System.out.println(reconstruct(s3, m3)); // true
	}
}
