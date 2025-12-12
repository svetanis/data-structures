package com.svetanis.datastructures.graph.directed.ts.alient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

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

//269. Alien Dictionary

public final class AlienOrderSubmit {
	// Time Complexity: O(V + E)
// not correct
	public static String ado(String[] words) {
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
		if (sb.length() < inDegree.size()) {
			return "";
		}
		return sb.toString();
	}

	private static Queue<Character> sources(Map<Character, Integer> map) {
		PriorityQueue<Character> queue = new PriorityQueue<>();
		// all vertices with 0 in-degree
		for (char c : map.keySet()) {
			if (map.get(c) == 0) {
				queue.add(c);
			}
		}
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
		Map<Character, Integer> map = new HashMap<>();
		for (char c : graph.keySet()) {
			map.put(c, 0);
		}
		return map;
	}

	private static Map<Character, List<Character>> graph(String[] words) {
		Map<Character, List<Character>> map = graphInit(words);
		for (int i = 0; i < words.length - 1; i++) {
			String w1 = words[i];
			String w2 = words[i + 1];
			for (int j = 0; j < Math.min(w1.length(), w2.length()); j++) {
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

	private static Map<Character, List<Character>> graphInit(String[] words) {
		Map<Character, List<Character>> map = new HashMap<>();
		for (String word : words) {
			for (char c : word.toCharArray()) {
				map.put(c, new ArrayList<>());
			}
		}
		return map;
	}

	public static void main(String[] args) {
		System.out.println(ado(new String[] { "wrt", "wrf", "er", "ett", "rftt" })); // wertf
		System.out.println(ado(new String[] { "z", "x" })); // zx
		System.out.println(ado(new String[] { "z", "x", "z" })); // ""
		
		
		System.out.println(ado(new String[] { "she", "sell", "seashell", "seashore", "seahorse", "on", "a" })); // lnrsheoa
		System.out.println(ado(new String[] { "stdlib", "stl", "scanf", "sscanf", "printf" })); // abdfilnrtcsp
		System.out.println(ado(new String[] { "neat", "net", "nest", "ante", "one", "oil", "innit", "ian", "isotope", "rat",
				"reer", "rest" })); // lnaeoiprts
		System.out.println(ado(new String[] { "da", "la", "na", "fa", "fei", "jia", "ha", "hai", "hang", "hua", "ta", "sha",
				"shi", "si", "ba" })); // ""
	}
}
