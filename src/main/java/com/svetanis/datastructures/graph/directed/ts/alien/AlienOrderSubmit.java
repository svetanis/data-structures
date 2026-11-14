package com.svetanis.datastructures.graph.directed.ts.alien;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// 269. Alien Dictionary
//
// Input: words -- a list of words from an alien language, sorted in that language's alphabetical
// order, over lower-case letters. Returns: a string of every letter that appears in the words, in
// an order that makes the list sorted; any such order will do. Returns "" if no order can make the
// list sorted (the words contradict each other, or a word comes after a longer word it is a prefix
// of). On LeetCode: 1 <= words.length <= 100, 1 <= words[i].length <= 100.
//
// The one idea: two neighbouring words differ first at some position, and there the letter of the
// earlier word comes before the letter of the later word. Each such pair is an edge "x before y",
// and an order that puts every x before its y is a topological order of those edges, found by
// Kahn's method: letters with nothing pointing at them go first, and placing a letter frees the
// letters it pointed at. If the line does not take in every letter, the edges contain a cycle.
// A word that follows a longer word it is a prefix of gives no edge, so the prefix test is made on
// its own.
//
// Time: O(C) -- C is the total number of letters in the words; at most 26 letters are placed and
// there is at most one edge per neighbouring pair.
// Space: O(V + E) -- the maps hold at most 26 letters and at most one edge per neighbouring pair.
//
// Siblings: AlienDictionary is the same method with static helper methods and a main that tests the
// prefix case; AlienOrder returns an Optional and picks the smallest letter first; AlienOrderArrays
// uses arrays of 26 instead of maps. This class keeps its two maps in fields.

public final class AlienOrderSubmit {

	private Map<Character, Integer> inDegree;
	private Map<Character, List<Character>> graph;

	public String ado(String[] words) {
		init(words);
		// find all edges
		for (int i = 0; i < words.length - 1; i++) {
			String w1 = words[i];
			String w2 = words[i + 1];
			if (w1.length() > w2.length() && w1.startsWith(w2)) { // REJECT: longer word before its own prefix
				return "";
			}
			for (int j = 0; j < Math.min(w1.length(), w2.length()); j++) {
				char parent = w1.charAt(j);
				char child = w2.charAt(j);
				if (parent != child) {
					graph.get(parent).add(child); // EDGE: parent comes before child
					inDegree.put(child, inDegree.get(child) + 1); // COUNT the edge at its head
					break; // only the first difference tells anything
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		Queue<Character> queue = sources(inDegree);
		while (!queue.isEmpty()) {
			char src = queue.poll();
			sb.append(src); // PLACE src next
			for (char neighbor : graph.get(src)) {
				int f = inDegree.getOrDefault(neighbor, 0) - 1; // USE UP the edge src -> neighbor
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
				graph.put(c, new ArrayList<>()); // every letter that appears is a vertex
				inDegree.put(c, 0);
			}
		}
	}

	private static Queue<Character> sources(Map<Character, Integer> map) {
		Deque<Character> queue = new ArrayDeque<>();
		// all vertices with 0 in-degree
		for (char c : map.keySet()) {
			if (map.get(c) == 0) { // SOURCE: no letter has to come before it
				queue.add(c);
			}
		}
		return queue;
	}

	public static void main(String[] args) {
		AlienOrderSubmit ao = new AlienOrderSubmit();
		System.out.println(ao.ado(new String[] { "wrt", "wrf", "er", "ett", "rftt" })); // wertf
		System.out.println(ao.ado(new String[] { "z", "x" })); // zx
		System.out.println(ao.ado(new String[] { "z", "x", "z" })); // "" -- z before x and x before z
		System.out.println(ao.ado(new String[] { "abc", "ab" })); // "" -- a longer word before its prefix
	}
}
