package com.svetanis.datastructures.graph.directed.ts.alient;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.filterValues;
import static com.google.common.collect.Maps.newHashMap;
import static java.lang.Math.min;
import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.google.common.base.Optional;

// there is a dictionary containing words
// from an alien language for which we 
// don't know the ordering of the letters
// find the correct order of the letters
// in the alien language. it is given that
// the input is a valid dictionary and there
// exists an ordering among its letters

// You may assume all letters are in lowercase.
// Every letter that appears in the input must also appear in the output, 
// and your output cannot have characters not in the input.
// If no ordering of letters makes the dictionary sorted lexicographically, 
// return an empty string.
// There may be multiple valid orders. If that's the case, return the smallest 
// in normal lexicographical order.

public final class AlienOrder {
	// Time Complexity: O(V + E)

	public static Optional<String> ado(List<String> words) {
		Map<Character, List<Character>> graph = graph(words);
		Map<Character, Integer> inDegree = inDegree(graph);
		StringBuilder sb = new StringBuilder();
		Queue<Character> queue = sources(inDegree);
		while (!queue.isEmpty()) {
			char src = queue.poll();
			sb.append(src);
			for (char neighbor : graph.get(src)) {
				int f = inDegree.getOrDefault(neighbor, 0) - 1;
				if (f == 0) {
					queue.add(neighbor);
				}
				inDegree.put(neighbor, f);
			}
		}
		// if topological ordering doesn't contain all chars
		// then there is a cyclic dependency between chars
		// therefore it will not be possible to find the ordering
		if (sb.length() != inDegree.size()) {
			return absent();
		}
		return of(sb.toString());
	}

	private static Queue<Character> sources(Map<Character, Integer> map) {
		PriorityQueue<Character> queue = new PriorityQueue<>();
		// all vertices with 0 in-degree
		Map<Character, Integer> filtered = filterValues(map, v -> v == 0);
		queue.addAll(filtered.keySet());
		return queue;
	}

	private static Map<Character, Integer> inDegree(Map<Character, List<Character>> graph) {
		Map<Character, Integer> map = inDegreeInit(graph);
		for (char parent : graph.keySet()) {
			for (char child : graph.get(parent)) {
				map.put(child, map.get(child) + 1);
			}
		}
		return map;
	}

	private static Map<Character, Integer> inDegreeInit(Map<Character, List<Character>> graph) {
		Map<Character, Integer> map = newHashMap();
		for (char c : graph.keySet()) {
			map.put(c, 0);
		}
		return map;
	}

	private static Map<Character, List<Character>> graph(List<String> words) {
		Map<Character, List<Character>> map = graphInit(words);
		for (int i = 1; i < words.size(); i++) {
			String w1 = words.get(i - 1);
			String w2 = words.get(i);
			for (int j = 0; j < min(w1.length(), w2.length()); j++) {
				char parent = w1.charAt(j);
				char child = w2.charAt(j);
				if (parent != child) {
					map.get(parent).add(child);
					break;
				}
			}
		}
		return map;
	}

	private static Map<Character, List<Character>> graphInit(List<String> words) {
		Map<Character, List<Character>> map = newHashMap();
		for (String word : words) {
			for (char c : word.toCharArray()) {
				map.put(c, newArrayList());
			}
		}
		return map;
	}

	public static void main(String[] args) {
		System.out.println(ado(asList("wrt", "wrf", "er", "ett", "rftt"))); // wertf
		System.out.println(ado(asList("z", "x"))); // zx
		System.out.println(ado(asList("she", "sell", "seashell", "seashore", "seahorse", "on", "a"))); // lnrsheoa
		System.out.println(ado(asList("stdlib", "stl", "scanf", "sscanf", "printf"))); // abdfilnrtcsp
		System.out.println(
				ado(asList("neat", "net", "nest", "ante", "one", "oil", "innit", "ian", "isotope", "rat", "reer", "rest"))); // lnaeoiprts
		System.out.println(
				ado(asList("da", "la", "na", "fa", "fei", "jia", "ha", "hai", "hang", "hua", "ta", "sha", "shi", "si", "ba"))); // ""
	}
}
