package com.svetanis.datastructures.graph.bfs.bidirectional;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

// 752. Open the Lock
//
// Input: dst -- the combination to reach; trappedCombos -- the deadends, combinations that jam the
// lock. The lock has 4 wheels, each showing a digit 0-9, and starts at "0000"; one move turns one
// wheel one slot, up or down, and 9 wraps to 0. Returns the least number of moves to reach dst
// without landing on a deadend, or -1. On LeetCode: 1 <= deadends.length <= 500, every deadend and
// the target is 4 digits, target is not a deadend.
//
// The one idea: one queue entry holds one combination of the lock, a 4-character string. The map
// holds, for every combination seen, how many moves it took to first reach it. The queue hands out
// combinations nearest-first, so the first time a combination is seen is also its shortest way
// there, and the map doubles as the "seen" mark. The eight neighbours of a combination are built
// when it is taken off the queue and the deadends are filtered out. Only neighbours are filtered,
// so the start is the one combination never checked: a jammed "0000" is tested before the search.
//
// Time: O(10^d * d * k) -- d = 4 wheels, so at most 10,000 combinations; each builds 8 neighbours
// of d characters and tests each against the deadend LIST, which costs up to k = deadends.length
// Space: O(10^d) -- the queue and the map hold each combination at most once
//
// Siblings: OpenLock is the same search with the deadends in a set; OpenLockBidirectional,
// OpenLockFrontiers and OpenLockRings are the other ways to search it.
public final class OpenLockSubmit {

	private static Map<Character, Character> nextDigit = nextDigit();
	private static Map<Character, Character> prevDigit = nextDigit.entrySet().stream()
			.collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

	public static int countSteps(String dst, List<String> trappedCombos) {
		String src = "0000";
		return bfs(src, dst, trappedCombos);
	}

	private static int bfs(String src, String dst, List<String> excluded) {
		if (excluded.contains(src)) {
			return -1; // BASE: the start is jammed, so no turn is possible
		}
		Queue<String> queue = new ArrayDeque<>();
		Map<String, Integer> map = new HashMap<>();
		queue.add(src);
		map.put(src, 0); // RECORD: the start is 0 moves from itself
		while (!queue.isEmpty()) {
			String curr = queue.poll();
			if (curr.equals(dst)) {
				return map.get(curr); // RETURN: first time dst comes off the queue is its shortest
			}
			Set<String> neighbors = neighbors(curr, excluded);
			for (String neighbor : neighbors) {
				if (!map.containsKey(neighbor)) { // SKIP: already reached by a route at least as short
					queue.add(neighbor);
					map.put(neighbor, map.get(curr) + 1); // RECORD: one move more than its parent
				}
			}
		}
		return -1; // RETURN: the queue ran dry, dst is walled off
	}

	private static Set<String> neighbors(String s, List<String> excluded) {
		Set<String> set = new HashSet<>();
		for (int i = 0; i < s.length(); i++) { // WALK: each wheel in turn
			char c = s.charAt(i);
			char nd = nextDigit.get(c);
			char pd = prevDigit.get(c);
			String next = nextCombo(i, nd, s);
			if (!excluded.contains(next)) { // SKIP: a deadend
				set.add(next);
			}
			String prev = nextCombo(i, pd, s);
			if (!excluded.contains(prev)) { // SKIP: a deadend
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
		return joiner.toString(); // RETURN: s with the wheel at index set to c
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
		map.put('9', '0'); // 9 wraps to 0
		return map;
	}

	public static void main(String[] args) {
		List<String> set = Arrays.asList("0201", "0101", "0102", "1212", "2002");
		System.out.println(countSteps("0202", set)); // 6

		List<String> one = Arrays.asList("8888");
		System.out.println(countSteps("0009", one)); // 1

		List<String> boxed = Arrays.asList("8887", "8889", "8878", "8898", "8788", "8988", "7888", "9888");
		System.out.println(countSteps("8888", boxed)); // -1

		// the lock starts jammed
		List<String> jammed = Arrays.asList("0000");
		System.out.println(countSteps("8888", jammed)); // -1
	}
}
