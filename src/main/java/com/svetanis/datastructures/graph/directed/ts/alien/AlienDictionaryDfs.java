package com.svetanis.datastructures.graph.directed.ts.alien;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

// Order of the letters of an alien alphabet, from a dictionary sorted in that alphabet (depth first).
//
// Input: list -- the words, sorted in the alien order. It is assumed VALID: its letters can be
// ordered so that the list is sorted -- no contradiction between neighbouring words, and no word
// after a longer word it is a prefix of. Returns: every letter that appears, in an order
// consistent with the list. For an invalid dictionary the answer is meaningless: nothing here
// checks for a cycle or for the prefix case (AlienDictionary and AlienOrder do).
//
// The one idea: two neighbouring words differ first at some position, and there the letter of the
// earlier word comes before the letter of the later word. Each such pair is an edge "x before y".
// Walk depth first from every letter not yet met and push a letter on a stack when its walk
// FINISHES, after everything it points at has been pushed; popping the stack then gives every
// letter before the letters it points at. (The same finish-order idea as
// TopologicalSorting.topologicalSort, on a map of letters.)
//
// Time: O(C + V + E) -- C is the total number of letters read to build the map; the walk is
// O(V + E) with V <= 26 letters and at most one edge per neighbouring pair.
// Space: O(V + E) -- the map, the marks, the stack and the recursion (at most 26 deep).
//
// Siblings: OrderOfChars is the same on a graph of numbers; AlienDictionary, AlienOrder,
// AlienOrderSubmit and AlienOrderArrays use Kahn's method and reject invalid dictionaries.

public final class AlienDictionaryDfs {

	public static List<Character> sort(List<String> list) {
		Map<Character, List<Character>> map = toMap(list);
		return topologicalSort(map);
	}

	private static List<Character> topologicalSort(Map<Character, List<Character>> map) {
		Set<Character> visited = new HashSet<>();
		Stack<Character> stack = new Stack<>();
		for (char c : map.keySet()) {
			if (!visited.contains(c)) { // START a walk from a letter no earlier walk met
				visited.add(c);
				fillStack(map, c, visited, stack);
			}
		}
		List<Character> order = new ArrayList<>();
		while (!stack.isEmpty()) {
			order.add(stack.pop()); // POP: last finished first
		}
		return order;
	}

	// this is basically a topological sort recursive function
	// recur for all the vertices adjacent to this vertex
	public static void fillStack(Map<Character, List<Character>> map, char v, Set<Character> visited,
			Stack<Character> stack) {
		for (char u : map.get(v)) {
			if (!visited.contains(u)) { // SKIP a letter already met
				visited.add(u); // MARK on entry
				fillStack(map, u, visited, stack);
			}
		}

		// push current vertex to
		// stack which stores result
		stack.push(v); // PUSH at the finish
	}

	private static Map<Character, List<Character>> toMap(List<String> words) {
		int len = words.size();
		Map<Character, List<Character>> map = new HashMap<>();
		for (String word : words) {
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (!map.containsKey(c)) {
					map.put(c, new ArrayList<>()); // every letter that appears is a vertex
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
					map.get(c1).add(c2); // EDGE: c1 comes before c2
					break; // only the first difference tells anything
				}
			}
		}
		return map;
	}

	public static void main(String[] args) {
		List<String> words = Arrays.asList("caa", "aaa", "aab");
		System.out.println(sort(words)); // [c, a, b]
	}
}
