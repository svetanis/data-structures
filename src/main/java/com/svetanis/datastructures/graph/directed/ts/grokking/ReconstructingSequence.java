package com.svetanis.datastructures.graph.directed.ts.grokking;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Maps.filterValues;
import static com.google.common.collect.Maps.newHashMap;

import java.util.List;
import java.util.Map;
import java.util.Queue;

// given a sequence and an array of sequences
// find if original sequence can be uniquely
// reconstructed from the array of sequences

// unique reconstruction means that we need 
// to find if the original sequence is the 
// only sequence such that all sequences in
// the array are subsequences of it

public final class ReconstructingSequence {
	// Time Complexity: O(V + E)

	public static boolean reconstruct(List<Integer> sequence, int[][] sequences) {
		if (sequence.size() == 0) {
			return false;
		}
		Map<Integer, Integer> inDegree = inDegreeInit(sequences);
		Map<Integer, List<Integer>> graph = graphInit(sequences);
		buildGraph(sequences, inDegree, graph);
		// if we don't have ordering rules for
		// all the elements, we'll not be able
		// to uniquely reconstruct the sequence
		if (inDegree.size() != sequence.size()) {
			return false;
		}
		List<Integer> list = newArrayList();
		Queue<Integer> queue = sources(inDegree);
		while (!queue.isEmpty()) {
			// more than one source means
			// there is more than one way
			// to reconstruct the sequence
			if (queue.size() > 1) {
				return false;
			}
			// the next element is different
			// from the original sequence
			if (sequence.get(list.size()) != queue.peek()) {
				return false;
			}
			int src = queue.poll();
			list.add(src);
			List<Integer> children = graph.get(src);
			for (int child : children) {
				int f = inDegree.getOrDefault(child, 0) - 1;
				if (f == 0) {
					queue.add(child);
				}
				inDegree.put(child, f);
			}
		}
		// if topological ordering doesn't contain all elements
		// then there is a cyclic dependency between elements
		// therefore it will not be possible to reconstruct the
		// original sequence
		return list.size() == sequence.size();
	}

	private static Queue<Integer> sources(Map<Integer, Integer> map) {
		Queue<Integer> queue = newLinkedList();
		// all vertices with 0 in-degree
		Map<Integer, Integer> filtered = filterValues(map, v -> v == 0);
		queue.addAll(filtered.keySet());
		return queue;
	}

	private static void buildGraph(int[][] sequences, Map<Integer, Integer> inDegree, Map<Integer, List<Integer>> map) {
		for (int[] s : sequences) {
			for (int i = 1; i < s.length; i++) {
				int parent = s[i - 1];
				int child = s[i];
				map.get(parent).add(child);
				int fc = inDegree.getOrDefault(child, 0);
				inDegree.put(child, fc + 1);
			}
		}
	}

	private static Map<Integer, Integer> inDegreeInit(int[][] sequences) {
		Map<Integer, Integer> map = newHashMap();
		for (int[] s : sequences) {
			for (int i = 0; i < s.length; i++) {
				map.putIfAbsent(s[i], 0);
			}
		}
		return map;
	}

	private static Map<Integer, List<Integer>> graphInit(int[][] sequences) {
		Map<Integer, List<Integer>> map = newHashMap();
		for (int[] s : sequences) {
			for (int i = 0; i < s.length; i++) {
				map.put(s[i], newArrayList());
			}
		}
		return map;
	}

	public static void main(String[] args) {
		List<Integer> s1 = newArrayList(1, 2, 3, 4);
		List<Integer> s2 = newArrayList(3, 1, 4, 2, 5);
		int[][] m0 = { { 1, 2 }, { 2, 3 }, { 3, 4 } };
		int[][] m1 = { { 1, 2 }, { 2, 3 }, { 2, 4 } };
		int[][] m2 = { { 3, 1, 5 }, { 1, 4, 2, 5 } };
		System.out.println(reconstruct(s1, m0)); // true
		System.out.println(reconstruct(s1, m1)); // false
		System.out.println(reconstruct(s2, m2)); // true
	}
}
