package com.svetanis.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.TreeMap;

// 726. Number of Atoms
//
// Input: a chemical formula such as K4(ON(SO3)2)2. Returns each element name in sorted order,
// followed by its total count when that count is more than 1.
//
// Each '(' opens a group, and the stack holds one map of element counts per group still open,
// the innermost on top. An element and its count are added to the map on top. A ')' pops that
// group's map, multiplies every count by the number written after the ')', and adds the
// results into the enclosing group's map. The map left at the bottom is the whole formula.

public final class CountAtoms {
	// Time Complexity: O(n^2 log n) worst case, n = formula length: each ')' copies every element
	//   of its group into the enclosing map, deep nesting repeats that copy at every level, and
	//   each TreeMap update is O(log n)
	// Space Complexity: O(n), the open groups' maps hold at most one entry per element written

	public static String countAtoms(String s) {
		Deque<Map<String, Integer>> dq = new ArrayDeque<>();
		dq.push(new TreeMap<>()); // the whole formula; TreeMap keeps element names sorted
		for (int i = 0; i < s.length();) {
			if (s.charAt(i) == '(') {
				dq.push(new TreeMap<>());
				i++;
			} else if (s.charAt(i) == ')') {
				i = subFormula(s, i, dq);
			} else {
				i = doFormula(s, i, dq);
			}
		}
		return concat(dq.peek());
	}

	private static int doFormula(String s, int index, Deque<Map<String, Integer>> dq) {
		int start = index;
		index++;
		while (index < s.length() && Character.isLowerCase(s.charAt(index))) {
			index++;
		}
		String element = s.substring(start, index);
		start = index;
		while (index < s.length() && Character.isDigit(s.charAt(index))) {
			index++;
		}
		int count = start < index ? Integer.parseInt(s.substring(start, index)) : 1; // no digits: 1
		int prev = dq.peek().getOrDefault(element, 0);
		dq.peek().put(element, prev + count);
		return index;
	}

	private static int subFormula(String s, int index, Deque<Map<String, Integer>> dq) {
		Map<String, Integer> top = dq.pop();
		index++;
		int start = index;
		while (index < s.length() && Character.isDigit(s.charAt(index))) {
			index++;
		}
		int count = start < index ? Integer.parseInt(s.substring(start, index)) : 1;
		for (String element : top.keySet()) {
			int freq = top.get(element) * count; // the whole group repeats 'count' times
			int prev = dq.peek().getOrDefault(element, 0);
			dq.peek().put(element, prev + freq);
		}
		return index;
	}

	private static String concat(Map<String, Integer> map) {
		StringBuilder sb = new StringBuilder();
		for (String s : map.keySet()) {
			sb.append(s);
			int count = map.get(s);
			if (count > 1) {
				sb.append(count);
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(countAtoms("H2O")); // H2O
		System.out.println(countAtoms("Mg(OH)2")); // H2MgO2
		System.out.println(countAtoms("K4(ON(SO3)2)2")); // K4N2O14S4
	}
}
