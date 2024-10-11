package com.svetanis.datastructures.graph.directed.ts.reconstruct;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.filterValues;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

// A sequence s is a list of integers. 
// Its subsequence is a new sequence that 
// can be made up by deleting elements from s, 
// without changing the order of integers.

// We are given an original sequence and a list of subsequences seqs.

// Determine whether original is the only sequence that 
// can be reconstructed from seqs. 
// Reconstruction means building the shortest sequence 
// so that all sequences in seqs are subsequences of it.

public final class SequenceReconstruction {
	// Time Complexity: O(V + E)

	public static boolean reconstruct(List<Integer> sequence, List<List<Integer>> sequences) {
		if (sequence.size() == 0) {
			return false;
		}
		Map<Integer, Set<Integer>> graph = graph(sequence, sequences);
		return topoSort(sequence, graph);
	}

	private static boolean topoSort(List<Integer> sequence, Map<Integer, Set<Integer>> graph) {
		Map<Integer, Integer> inDegree = inDegree(graph);
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
			for (int neighbor : graph.get(src)) {
				int ind = inDegree.get(neighbor) - 1;
				inDegree.put(neighbor, ind);
				if (ind == 0) {
					queue.add(neighbor);
				}
			}
		}
		return list.equals(sequence);
	}

	private static Queue<Integer> sources(Map<Integer, Integer> inDegree) {
		Queue<Integer> queue = new ArrayDeque<>();
		// all vertices with 0 in-degree
		Map<Integer, Integer> filtered = filterValues(inDegree, v -> v == 0);
		queue.addAll(filtered.keySet());
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
		Map<Integer, Integer> map = newHashMap();
		for (int key : graph.keySet()) {
			map.put(key, 0);
		}
		return map;
	}

	private static Map<Integer, Set<Integer>> graph(List<Integer> sequence, List<List<Integer>> sequences) {
		Map<Integer, Set<Integer>> map = graphInit(sequence);
		for (List<Integer> s : sequences) {
			for (int i = 0; i < s.size() - 1; i++) {
				map.get(s.get(i)).add(s.get(i + 1));
			}
		}
		return map;
	}

	private static Map<Integer, Set<Integer>> graphInit(List<Integer> sequence) {
		Map<Integer, Set<Integer>> map = newHashMap();
		for (int s : sequence) {
			map.put(s, newHashSet());
		}
		return map;
	}

	public static void main(String[] args) {
		List<Integer> s1 = newArrayList(1, 2, 3);
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

		List<Integer> s2 = newArrayList(4, 1, 5, 2, 6, 3);
		List<List<Integer>> list4 = newArrayList();
		list4.add(asList(5, 2, 6, 3));
		list4.add(asList(4, 1, 5, 2));
		System.out.println(reconstruct(s2, list4)); // true
	}
}
