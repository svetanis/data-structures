package com.svetanis.datastructures.graph.bfs.wordladder;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;

// given a 4-wheel lock where each wheel
// contains the numbers '0' through '9'
// turning a wheel can either increase or
// decrease its number by one, wrapping 
// around from '9' to '0' or vice versa.
// a single move involves rotating any one
// of the wheels by one slot. 

// the lock starts with the combination '0000'.
// however, there are specific combinations termed
// as "deadends". if the lock lands on any of these
// deadend combinations, where wheels jam, making it
// impossible to proceed.

// find the least number of moves needed to reach 
// a given target combination from the starting point
// without hitting any deadend. if reaching the target
// is impossible due to deadends, return -1.

public final class OpenLock {
	// Time Complexity: O(n)
	// Space Complexity: O(n)
	
	public static Optional<Integer> countSteps(String dst, Set<String> excluded) {
		String src = "0000";
		BiMap<Character, Character> bimap = bimap();
		return bfs(src, dst, excluded, bimap);
	}

	private static Optional<Integer> bfs(String src, String dst, Set<String> excluded,
			BiMap<Character, Character> bimap) {
		Queue<String> queue = new ArrayDeque<>();
		Map<String, Integer> map = newHashMap();
		queue.add(src);
		map.put(src, 0);
		while (!queue.isEmpty()) {
			String curr = queue.poll();
			if (curr.equals(dst)) {
				return of(map.get(curr));
			}
			List<String> neighbors = neighbors(curr, excluded, bimap);
			for (String neighbor : neighbors) {
				if (!map.containsKey(neighbor)) {
					queue.add(neighbor);
					map.put(neighbor, map.get(curr) + 1);
				}
			}
		}
		return absent();
	}

	private static ImmutableList<String> neighbors(String s, Set<String> excluded, BiMap<Character, Character> bimap) {
		Set<String> set = newHashSet();
		for (int i = 0; i < s.length(); i++) {
			char curr = s.charAt(i);
			char nd = bimap.get(curr);
			String next = nextCombo(i, nd, s);
			if (!excluded.contains(next)) {
				set.add(next);
			}
			char pd = bimap.inverse().get(curr);
			String prev = nextCombo(i, pd, s);
			if (!excluded.contains(prev)) {
				set.add(prev);
			}
		}
		return newList(set);
	}

	private static String nextCombo(int index, char c, String s) {
		String prefix = s.substring(0, index);
		String suffix = s.substring(index + 1);
		return Joiner.on("").join(prefix, c, suffix);
	}

	private static BiMap<Character, Character> bimap() {
		BiMap<Character, Character> bimap = HashBiMap.create();
		bimap.put('0', '1');
		bimap.put('1', '2');
		bimap.put('2', '3');
		bimap.put('3', '4');
		bimap.put('4', '5');
		bimap.put('5', '6');
		bimap.put('6', '7');
		bimap.put('7', '8');
		bimap.put('8', '9');
		bimap.put('9', '0');
		return bimap;
	}

	public static void main(String[] args) {
		Set<String> excluded1 = newHashSet("0201", "0101", "0102", "1212", "2002");
		System.out.println(countSteps("0202", excluded1)); // 6

		Set<String> excluded2 = newHashSet("0009", "0090", "0900", "9000");
		System.out.println(countSteps("9999", excluded2)); // 6

		Set<String> excluded3 = newHashSet("0111", "2111", "1011", "1211", "1101", "1121", "1110", "1112");
		System.out.println(countSteps("1111", excluded3)); // -1
	}
}
