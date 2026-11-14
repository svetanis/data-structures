package com.svetanis.datastructures.graph.bfs.bidirectional;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

// 752. Open the Lock
//
// Input: dst -- the combination to reach; excluded -- the deadends, combinations that jam the lock.
// The lock has 4 wheels, each showing a digit 0-9, and starts at "0000". One move turns one wheel
// one slot, up or down, and 9 wraps to 0. Returns the least number of moves to reach dst without
// ever landing on a deadend, or an empty Optional when it cannot be reached. On LeetCode:
// 1 <= deadends.length <= 500, every deadend and the target is 4 digits, target is not a deadend.
//
// The one idea: one queue entry holds one combination of the lock, a 4-character string. Nobody
// stores the graph; each combination's eight neighbours (4 wheels, up or down) are built from it
// when it is taken off the queue, and the deadends are filtered out as they are built. The map
// holds, for every combination seen, how many moves it took to first reach it. Because the queue
// hands out combinations nearest-first, the first time a combination is seen is also its
// shortest way there, so the map doubles as the "seen" mark. Only the neighbours are filtered, so
// the one combination never checked is the starting one: a jammed "0000" is tested before the search.
//
// Time: O(10^d * d) -- d = 4 wheels, so at most 10,000 combinations, each building 8 neighbours
// of d characters
// Space: O(10^d) -- the queue and the map hold each combination at most once
//
// Siblings: OpenLockBidirectional searches from both ends and meets in the middle;
// OpenLockFrontiers is the same meeting search holding each end as a set; OpenLockRings is this
// one-way search counting rings instead of keeping a depth per combination; OpenLockSubmit is the
// LeetCode submission, which takes the deadends as a list.
public final class OpenLock {

	private static final String COMBO = "%s%s%s";

	public static Optional<Integer> countSteps(String dst, Set<String> excluded) {
		String src = "0000";
		return bfs(src, dst, excluded);
	}

	private static Optional<Integer> bfs(String src, String dst, Set<String> excluded) {
		// every neighbour is filtered against the deadends, so the ONE
		// combination never checked is the starting one. A jammed lock
		// cannot be turned at all, whatever the target is
		if (excluded.contains(src)) {
			return Optional.empty(); // BASE: the start is jammed
		}
		Queue<String> queue = new ArrayDeque<>();
		Map<String, Integer> map = new HashMap<>();
		queue.add(src);
		map.put(src, 0); // RECORD: the start is 0 moves from itself
		while (!queue.isEmpty()) {
			String curr = queue.poll();
			if (curr.equals(dst)) {
				return Optional.of(map.get(curr)); // RETURN: first time dst comes off the queue is its shortest
			}
			List<String> neighbors = neighbors(curr, excluded);
			for (String neighbor : neighbors) {
				if (!map.containsKey(neighbor)) { // SKIP: already reached by a route at least as short
					queue.add(neighbor);
					map.put(neighbor, map.get(curr) + 1); // RECORD: one move more than its parent
				}
			}
		}
		return Optional.empty(); // RETURN: the queue ran dry, dst is walled off
	}

	private static List<String> neighbors(String s, Set<String> excluded) {
		Set<String> set = new HashSet<>();
		for (int i = 0; i < s.length(); i++) { // WALK: each wheel in turn
			char curr = s.charAt(i);
			char nd = (char) ('0' + (curr - '0' + 1) % 10); // the digit one slot up, 9 wraps to 0
			String next = nextCombo(i, nd, s);
			if (!excluded.contains(next)) { // SKIP: a deadend
				set.add(next);
			}
			char pd = (char) ('0' + (curr - '0' + 9) % 10); // the digit one slot down, 0 wraps to 9
			String prev = nextCombo(i, pd, s);
			if (!excluded.contains(prev)) { // SKIP: a deadend
				set.add(prev);
			}
		}
		return new ArrayList<>(set);
	}

	private static String nextCombo(int index, char c, String s) {
		String prefix = s.substring(0, index);
		String suffix = s.substring(index + 1);
		return COMBO.formatted(prefix, c, suffix); // RETURN: s with the wheel at index set to c
	}

	public static void main(String[] args) {
		Set<String> excluded1 = new HashSet<>(Arrays.asList("0201", "0101", "0102", "1212", "2002"));
		System.out.println(countSteps("0202", excluded1)); // Optional[6]

		Set<String> excluded2 = new HashSet<>(Arrays.asList("0009", "0090", "0900", "9000"));
		System.out.println(countSteps("9999", excluded2)); // Optional[6]

		Set<String> excluded3 = new HashSet<>(
				Arrays.asList("0111", "2111", "1011", "1211", "1101", "1121", "1110", "1112"));
		System.out.println(countSteps("1111", excluded3)); // Optional.empty

		// the lock starts jammed. Nothing can be reached, including a
		// target the wheels could otherwise walk to in 8 turns
		Set<String> excluded4 = new HashSet<>(Arrays.asList("0000"));
		System.out.println(countSteps("8888", excluded4)); // Optional.empty

		// LeetCode example 2: one turn of the last wheel, down from 0 to 9
		Set<String> excluded5 = new HashSet<>(Arrays.asList("8888"));
		System.out.println(countSteps("0009", excluded5)); // Optional[1]
	}
}
