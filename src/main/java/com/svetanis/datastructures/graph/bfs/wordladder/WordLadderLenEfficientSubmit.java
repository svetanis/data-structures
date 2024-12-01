package com.svetanis.datastructures.graph.bfs.wordladder;

import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

// 127. Word Ladder

public final class WordLadderLenEfficientSubmit {
	// Time Complexity: O(n * length)
	// Space Complexity: O(n)

	public static int ladderLen(String src, String dst, List<String> words) {
		Set<String> set = new HashSet<>(words);
		if (!set.contains(dst)) {
			return 0;
		}
		Queue<String> sq = new ArrayDeque<>();
		Map<String, Integer> sm = new HashMap<>();
		Queue<String> eq = new ArrayDeque<>();
		Map<String, Integer> em = new HashMap<>();
		sq.offer(src);
		eq.offer(dst);
		sm.put(src, 1);
		em.put(dst, 1);
		while (!sq.isEmpty() && !eq.isEmpty()) {
			boolean small = sq.size() <= eq.size();
			int len = small ? bfs(sq, sm, em, set) : bfs(eq, em, sm, set);
			if (len != -1) {
				return len;
			}
		}
		return 0;
	}

	private static int bfs(Queue<String> queue, Map<String, Integer> map, Map<String, Integer> other, Set<String> words) {
		while (!queue.isEmpty()) {
			String word = queue.poll();
			int step = map.get(word);
			char[] chars = word.toCharArray();
			for (int i = 0; i < word.length(); i++) {
				char curr = chars[i];
				for (char c = 'a'; c <= 'z'; ++c) {
					if (curr != c) {
						chars[i] = c;
						String s = new String(chars);
						if (!words.contains(s) || map.containsKey(s)) {
							continue;
						}
						if (other.containsKey(s)) {
							return step + other.get(s);
						}
						queue.offer(s);
						map.put(s, step + 1);
					}
				}
				chars[i] = curr;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		List<String> list = asList("hot", "dot", "dog", "lot", "log", "cog");
		System.out.println(ladderLen("hit", "cog", list)); // 5

		List<String> list0 = asList("hot", "dot", "dog", "lot", "log");
		System.out.println(ladderLen("hit", "cog", list0)); // 0
	}
}
