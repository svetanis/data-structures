package com.svetanis.datastructures.graph.directed.ts.alien;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// 269. Alien Dictionary
//
// Input: words -- a list of words from an alien language, sorted in that language's alphabetical
// order, over lower-case letters. We do not know the alphabet's order. Returns: a string of every
// letter that appears in the words, in an order that makes the list sorted; any such order will do
// when there are several. Returns "" if no order can make the list sorted (the words contradict
// each other, or a word comes after a longer word it is a prefix of). On LeetCode: 1 <=
// words.length <= 100, 1 <= words[i].length <= 100.
//
// The one idea: two neighbouring words differ first at some position, and there the letter of the
// earlier word comes before the letter of the later word; nothing else can be read from the pair.
// Each such pair is an edge "x before y". An order that puts every x before its y is a topological
// order of those edges, found by Kahn's method: letters with nothing pointing at them go first,
// and placing a letter frees the letters it pointed at. If the line does not take in every letter
// the edges contain a cycle, so no order exists. A word that follows a longer word it is a prefix
// of gives no edge, so it has to be caught on its own.
//
// Time: O(C) -- C is the total number of letters in the words; every letter is read, at most 26
// letters are placed, and there is at most one edge per neighbouring pair of words.
// Space: O(V + E) -- the maps hold at most 26 letters and at most one edge per neighbouring pair.
//
// Siblings: AlienOrderSubmit is the same method as a class with fields; AlienOrder returns an
// Optional and picks the smallest letter first; AlienOrderArrays uses arrays of 26 instead of
// maps; AlienDictionaryDfs and OrderOfChars order the letters by depth-first finishing times and
// assume the dictionary is valid.

public final class AlienDictionary {

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
			sb.append(src); // PLACE src next
			List<Character> children = graph.get(src);
			for (char child : children) {
				int f = inDegree.getOrDefault(child, 0) - 1; // USE UP the edge src -> child
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
			if (w1.length() > w2.length() && w1.startsWith(w2)) { // REJECT: longer word before its own prefix
				return true;
			}
		}
		return false;
	}

	private static Queue<Character> sources(Map<Character, Integer> map) {
		Queue<Character> queue = new LinkedList<>();
		// all vertices with 0 in-degree
		for (char c : map.keySet()) {
			if (map.get(c) == 0) { // SOURCE: no letter has to come before it
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
					map.get(parent).add(child); // EDGE: parent comes before child
					// increment child's inDegree
					int fc = inDegree.getOrDefault(child, 0);
					inDegree.put(child, fc + 1); // COUNT the edge at its head
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
				map.put(c, 0); // every letter that appears is a vertex
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
		System.out.println(ado(s1)); // bac
		System.out.println(ado(s2)); // cab
		System.out.println(ado(s3)); // ywxz

		// "abc" before "ab" is impossible in every alphabet. Without the
		// prefix guard this returns "abc", because the character loop
		// compares only the two positions where the words agree
		System.out.println("[" + ado(new String[] { "abc", "ab" }) + "]"); // []
	}
}
