package com.svetanis.datastructures.graph.directed.ts.alien;

import static java.lang.Math.min;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;

// 269. Alien Dictionary, with the smallest valid order
//
// Input: words -- a list of words from an alien language, sorted in that language's alphabetical
// order, over lower-case letters. Returns: the letters that appear in the words, as one string, in
// an order that makes the list sorted -- and, when several orders do, the smallest in ordinary
// alphabetical order of the strings ("abc" before "abd"). Optional.empty() if no order makes the
// list sorted (the words contradict each other, or a word comes after a longer word it is a prefix
// of). On LeetCode: 1 <= words.length <= 100, 1 <= words[i].length <= 100.
//
// The one idea: two neighbouring words differ first at some position, and there the letter of the
// earlier word comes before the letter of the later word. Each such pair is an edge "x before y".
// An order that puts every x before its y is a topological order of those edges, found by Kahn's
// method: letters with nothing pointing at them are ready to go, and placing a letter frees the
// letters it pointed at. To get the SMALLEST order, the ready letters wait in a priority queue and
// the smallest ready letter is always placed next: placing anything larger first could only make
// the string larger at that position. If the line does not take in every letter, the edges contain
// a cycle. A word that follows a longer word it is a prefix of gives no edge, so the prefix test is
// made on its own.
//
// Time: O(C) -- C is the total number of letters in the words; at most 26 letters pass through the
// priority queue and there is at most one edge per neighbouring pair.
// Space: O(V + E) -- the maps hold at most 26 letters and at most one edge per neighbouring pair.
//
// Siblings: AlienDictionary, AlienOrderSubmit and AlienOrderArrays return any valid order (a plain
// queue, so the order among ready letters is arbitrary); this file is the only one that returns
// the smallest.

public final class AlienOrder {

	public static Optional<String> ado(List<String> words) {
		// "abc" listed before "ab" cannot happen in any alphabet: the shorter
		// word is a prefix of the longer one, so it sorts first whatever the
		// letters mean. The character loop in graph() cannot see it, because
		// every character it compares matches
		if (hasInvalidPrefix(words)) {
			return Optional.empty();
		}
		Map<Character, List<Character>> graph = graph(words);
		Map<Character, Integer> inDegree = inDegree(graph);
		StringBuilder sb = new StringBuilder();
		Queue<Character> queue = sources(inDegree);
		while (!queue.isEmpty()) {
			char src = queue.poll(); // SMALLEST ready letter
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
		if (sb.length() != inDegree.size()) {
			return Optional.empty();
		}
		return Optional.of(sb.toString());
	}

	private static boolean hasInvalidPrefix(List<String> words) {
		for (int i = 1; i < words.size(); i++) {
			String w1 = words.get(i - 1);
			String w2 = words.get(i);
			if (w1.length() > w2.length() && w1.startsWith(w2)) { // REJECT: longer word before its own prefix
				return true;
			}
		}
		return false;
	}

	private static Queue<Character> sources(Map<Character, Integer> map) {
		PriorityQueue<Character> queue = new PriorityQueue<>();
		// all vertices with 0 in-degree
		for (Map.Entry<Character, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 0) { // SOURCE: no letter has to come before it
				queue.add(entry.getKey());
			}
		}
		return queue;
	}

	private static Map<Character, Integer> inDegree(Map<Character, List<Character>> graph) {
		Map<Character, Integer> map = inDegreeInit(graph);
		for (char parent : graph.keySet()) {
			for (char child : graph.get(parent)) {
				map.put(child, map.get(child) + 1); // COUNT the edge at its head
			}
		}
		return map;
	}

	private static Map<Character, Integer> inDegreeInit(Map<Character, List<Character>> graph) {
		Map<Character, Integer> map = new HashMap<>();
		for (char c : graph.keySet()) {
			map.put(c, 0); // every letter that appears is a vertex
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
					map.get(parent).add(child); // EDGE: parent comes before child
					break; // only the first difference tells anything
				}
			}
		}
		return map;
	}

	private static Map<Character, List<Character>> graphInit(List<String> words) {
		Map<Character, List<Character>> map = new HashMap<>();
		for (String word : words) {
			for (char c : word.toCharArray()) {
				map.put(c, new ArrayList<>());
			}
		}
		return map;
	}

	public static void main(String[] args) {
		System.out.println(ado(asList("wrt", "wrf", "er", "ett", "rftt"))); // Optional[wertf]
		System.out.println(ado(asList("z", "x"))); // Optional[zx]
		System.out.println(ado(asList("she", "sell", "seashell", "seashore", "seahorse", "on", "a"))); // Optional[lnrsheoa]
		System.out.println(ado(asList("stdlib", "stl", "scanf", "sscanf", "printf"))); // Optional[abdfilnrtcsp]
		System.out.println(
				ado(asList("neat", "net", "nest", "ante", "one", "oil", "innit", "ian", "isotope", "rat", "reer", "rest"))); // Optional[lnaeoiprts]
		System.out.println(
				ado(asList("da", "la", "na", "fa", "fei", "jia", "ha", "hai", "hang", "hua", "ta", "sha", "shi", "si", "ba"))); // Optional.empty -- a cycle

		// "abc" before "ab" is impossible in every alphabet. Without the
		// prefix guard this returns Optional[abc], because the character
		// loop compares only the two positions where the words agree
		System.out.println(ado(asList("abc", "ab"))); // Optional.empty
	}
}
