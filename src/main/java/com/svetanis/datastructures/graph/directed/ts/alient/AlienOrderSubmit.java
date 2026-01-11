package com.svetanis.datastructures.graph.directed.ts.alient;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// 269. Alien Dictionary

public final class AlienOrderSubmit {
	// Time Complexity: O(V + E)

	private Map<Character, Integer> inDegree;
	private Map<Character, List<Character>> graph;

	public String ado(String[] words) {
		init(words);
		// find all edges
		for (int i = 0; i < words.length - 1; i++) {
			String w1 = words[i];
			String w2 = words[i + 1];
			if (w1.length() > w2.length() && w1.startsWith(w2)) {
				return "";
			}
			for (int j = 0; j < Math.min(w1.length(), w2.length()); j++) {
				char parent = w1.charAt(j);
				char child = w2.charAt(j);
				if (parent != child) {
					graph.get(parent).add(child);
					inDegree.put(child, inDegree.get(child) + 1);
					break;
				}
			}
		}

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
		if (sb.length() < inDegree.size()) {
			return "";
		}
		return sb.toString();
	}

	private void init(String[] words) {
		this.graph = new HashMap<>();
		this.inDegree = new HashMap<>();
		for (String word : words) {
			for (char c : word.toCharArray()) {
				graph.put(c, new ArrayList<>());
				inDegree.put(c, 0);
			}
		}
	}

	private static Queue<Character> sources(Map<Character, Integer> map) {
		Deque<Character> queue = new ArrayDeque<>();
		// all vertices with 0 in-degree
		for (char c : map.keySet()) {
			if (map.get(c) == 0) {
				queue.add(c);
			}
		}
		return queue;
	}

	public static void main(String[] args) {
		AlienOrderSubmit ao = new AlienOrderSubmit();
		System.out.println(ao.ado(new String[] { "wrt", "wrf", "er", "ett", "rftt" })); // wertf
		System.out.println(ao.ado(new String[] { "z", "x" })); // zx
		System.out.println(ao.ado(new String[] { "z", "x", "z" })); // ""
		System.out.println(ao.ado(new String[] { "abc", "ab" })); // ""
	}
}
