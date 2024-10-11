package com.svetanis.datastructures.graph.directed.ts.alient;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.java.base.utils.Arrays.toList;
import static com.svetanis.java.base.utils.Print.print;
import static java.lang.Math.min;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.google.common.collect.ImmutableList;

// Given a sorted dictionary (array of words) of an alien language, 
// find order of characters in the language.

public final class AlienDictionaryDfs {

	public static ImmutableList<Character> sort(List<String> list) {
		Map<Character, List<Character>> map = toMap(list);
		return topologicalSort(map);
	}

	private static ImmutableList<Character> topologicalSort(Map<Character, List<Character>> map) {
		Set<Character> visited = newHashSet();
		Stack<Character> stack = new Stack<>();
		for (char c : map.keySet()) {
			if (!visited.contains(c)) {
				visited.add(c);
				fillStack(map, c, visited, stack);
			}
		}
		return toList(stack);
	}

	// this is basically a topological sort recursive function
	// recur for all the vertices adjacent to this vertex
	public static void fillStack(Map<Character, List<Character>> map, char v, Set<Character> visited,
			Stack<Character> stack) {
		for (char u : map.get(v)) {
			if (!visited.contains(u)) {
				visited.add(u);
				fillStack(map, u, visited, stack);
			}
		}

		// push current vertex to
		// stack which stores result
		stack.push(v);
	}

	private static Map<Character, List<Character>> toMap(List<String> words) {
		int len = words.size();
		Map<Character, List<Character>> map = newHashMap();
		for (String word : words) {
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (!map.containsKey(c)) {
					map.put(c, newArrayList());
				}
			}
		}

		// take the current two words and
		// find the first mismatching char
		for (int i = 0; i < len - 1; i++) {
			String w1 = words.get(i);
			String w2 = words.get(i + 1);
			for (int j = 0; j < min(w1.length(), w2.length()); j++) {
				// if we find a mismatching char, then add an edge
				// from char of w1 to that of w2
				if (w1.charAt(j) != w2.charAt(j)) {
					char c1 = w1.charAt(j);
					char c2 = w2.charAt(j);
					map.get(c1).add(c2);
					break;
				}
			}
		}
		return map;
	}

	public static void main(String[] args) {
		List<String> words = newArrayList("caa", "aaa", "aab");
		print(sort(words));
	}
}
