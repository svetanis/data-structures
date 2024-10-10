package com.svetanis.datastructures.graph.bfs.wordladder;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public final class OpenLockSubmit {

	private static Map<Character, Character> nextDigit = nextDigit();
	private static Map<Character, Character> prevDigit = nextDigit.entrySet().stream()
			.collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

	public static int countSteps(String dst, List<String> trappedCombos) {
		String src = "0000";
		return bfs(src, dst, trappedCombos);
	}

	private static int bfs(String src, String dst, List<String> excluded) {
		Queue<String> queue = new ArrayDeque<>();
		Map<String, Integer> map = new HashMap<>();
		queue.add(src);
		map.put(src, 0);
		while (!queue.isEmpty()) {
			String curr = queue.poll();
			if (curr.equals(dst)) {
				return map.get(curr);
			}
			Set<String> neighbors = neighbors(curr, excluded);
			for (String neighbor : neighbors) {
				if (!map.containsKey(neighbor)) {
					queue.add(neighbor);
					map.put(neighbor, map.get(curr) + 1);
				}
			}
		}
		return -1;
	}

	private static Set<String> neighbors(String s, List<String> excluded) {
		Set<String> set = new HashSet<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			char nd = nextDigit.get(c);
			char pd = prevDigit.get(c);
			String next = nextCombo(i, nd, s);
			if (!excluded.contains(next)) {
				set.add(next);
			}
			String prev = nextCombo(i, pd, s);
			if (!excluded.contains(prev)) {
				set.add(prev);
			}
		}
		return set;
	}

	private static String nextCombo(int index, char c, String s) {
		String prefix = s.substring(0, index);
		String suffix = s.substring(index + 1);
		StringJoiner joiner = new StringJoiner("");
		joiner.add(prefix).add(c + "").add(suffix);
		return joiner.toString();
	}

	private static Map<Character, Character> nextDigit() {
		Map<Character, Character> map = new HashMap<>();
		map.put('0', '1');
		map.put('1', '2');
		map.put('2', '3');
		map.put('3', '4');
		map.put('4', '5');
		map.put('5', '6');
		map.put('6', '7');
		map.put('7', '8');
		map.put('8', '9');
		map.put('9', '0');
		return map;
	}

	public static void main(String[] args) {
		List<String> set = newArrayList("0201", "0101", "0102", "1212", "2002");
		System.out.println(countSteps("0202", set));
	}
}
