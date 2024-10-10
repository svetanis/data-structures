package com.svetanis.datastructures.graph.bfs.wordladder;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

// Word Ladder is "A puzzle begins with two words,
// and to solve the puzzle one must find a chain of
// other words to link the two, in which two adjacent
// words (that is, words in successive steps) differ
// by one letter"

// given a start word, and end word, and a list of
// dictionary words, determine the min number of steps
// to go from the start word to the end word using 
// only words from the dictionary.

public final class WordLadderLenHashMapSubmit {

	public static int ladderLen(String src, String dst, List<String> words) {
		return bfs(src, dst, words);
	}

	private static int bfs(String src, String dst, List<String> words) {
		Queue<String> queue = new ArrayDeque<>();
		Map<String, Integer> map = new HashMap<>();
		queue.add(src);
		map.put(src, 0);
		while (!queue.isEmpty()) {
			String curr = queue.poll();
			if (curr.equals(dst)) {
				return map.get(curr);
			}
			for (String neighbor : neighbors(curr, words)) {
				if (!map.containsKey(neighbor)) {
					queue.add(neighbor);
					map.put(neighbor, map.get(curr) + 1);
				}
			}
		}
		return -1;
	}

	private static List<String> neighbors(String src, List<String> words) {
		Set<String> set = newHashSet();
		for (int i = 0; i < src.length(); i++) {
			for (char c = 'a'; c <= 'z'; ++c) {
				if (src.charAt(i) != c) {
					String str = src.substring(0, i) + c + src.substring(i + 1);
					if (!src.equals(str) && words.contains(str)) {
						set.add(str);
					}
				}
			}
		}
		return new ArrayList<>(set);
	}

	public static void main(String[] args) {
		List<String> list1 = asList("hit", "hot", "dot", "dog", "dog", "cog");
		System.out.println(ladderLen("hit", "cog", list1)); // 4

		List<String> list2 = asList("cold", "gold", "cord", "sold", "card", "ward", "warm", "tard");
		System.out.println(ladderLen("cold", "warm", list2)); // 4

		List<String> list3 = asList("fool", "pool", "poll", "pole", "pale", "sale", "sage");
		System.out.println(ladderLen("fool", "sage", list3)); // 6
	}
}
