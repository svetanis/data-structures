package com.svetanis.datastructures.graph.directed.ts.alient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// 269. Alien Dictionary

// there is a dictionary containing words
// from an alien language for which we 
// don't know the ordering of the letters
// find the correct order of the letters
// in the alien language. it is given that
// the input is a valid dictionary and there
// exists an ordering among its letters

public final class AlienDictionary {
	// Time Complexity: O(V + E)

	public static String ado(String[] words) {
		// "abc" listed before "ab" cannot happen in any alphabet: the shorter
		// word is a prefix of the longer one, so it sorts first whatever the
		// letters mean. The character loop below cannot see it, because every
		// character it compares matches
		if (hasInvalidPrefix(words)) {
			return "";
		}
		Map<Character, Integer> inDegree = inDegreeInit(words);
		Map<Character, List<Character>> graph = graphInit(words);
		buildGraph(words, inDegree, graph);
		StringBuilder sb = new StringBuilder();
		Queue<Character> queue = sources(inDegree);
		while (!queue.isEmpty()) {
			char src = queue.poll();
			sb.append(src);
			List<Character> children = graph.get(src);
			for (char child : children) {
				int f = inDegree.getOrDefault(child, 0) - 1;
				if (f == 0) {
					queue.add(child);
				}
				inDegree.put(child, f);
			}
		}
		// if topological ordering doesn't contain all chars
		// then there is a cyclic dependency between chars
		// therefore it will not be possible to find the ordering
		if (sb.length() != inDegree.size()) {
			return "";
		}
		return sb.toString();
	}

	private static boolean hasInvalidPrefix(String[] words) {
		for (int i = 0; i < words.length - 1; i++) {
			String w1 = words[i];
			String w2 = words[i + 1];
			if (w1.length() > w2.length() && w1.startsWith(w2)) {
				return true;
			}
		}
		return false;
	}

	private static Queue<Character> sources(Map<Character, Integer> map) {
		Queue<Character> queue = new LinkedList<>();
		// all vertices with 0 in-degree
		for(char c : map.keySet()) {
			if(map.get(c) == 0) {
				queue.add(c);
			}
		}
		return queue;
	}

	private static void buildGraph(String[] words, Map<Character, Integer> inDegree,
			Map<Character, List<Character>> map) {
		for (int i = 0; i < words.length - 1; i++) {
			// find ordering of chars
			// from adjacent words
			String w1 = words[i];
			String w2 = words[i + 1];
			for (int j = 0; j < Math.min(w1.length(), w2.length()); j++) {
				char parent = w1.charAt(j);
				char child = w2.charAt(j);
				// if the two chars are different
				if (parent != child) {
					// put child into it's parent's list
					map.get(parent).add(child);
					// increment child's inDegree
					int fc = inDegree.getOrDefault(child, 0);
					inDegree.put(child, fc + 1);
					// only the first different char between
					// the two words helps to find the order
					break;
				}
			}
		}
	}

	private static Map<Character, Integer> inDegreeInit(String[] words) {
		Map<Character, Integer> map = new HashMap<>();
		for (String s : words) {
			for (char c : s.toCharArray()) {
				map.put(c, 0);
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
		String[] s1 = { "ba", "bc", "ac", "cab" };
		String[] s2 = { "cab", "aaa", "aab" };
		String[] s3 = { "ywz", "wz", "xww", "xz", "zyy", "zwz" };
		System.out.println(ado(s1)); // b,a,c
		System.out.println(ado(s2)); // c,a,b
		System.out.println(ado(s3)); // y,w,x,z

		// "abc" before "ab" is impossible in every alphabet. Without the
		// prefix guard this returns "abc", because the character loop
		// compares only the two positions where the words agree
		System.out.println("[" + ado(new String[] { "abc", "ab" }) + "]"); // []
	}
}
