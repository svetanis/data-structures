package com.svetanis.datastructures.graph.bfs.wordladder;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

// Word Ladder is "A puzzle begins with two words,
// and to solve the puzzle one must find a chain of
// other words to link the two, in which two adjacent
// words (that is, words in successive steps) differ
// by one letter"

// given a start word, and end word, and a list of
// dictionary words, determine the min number of steps
// to go from the start word to the end word using 
// only words from the dictionary.

public final class WordLadderLenHashMap {

	public static Optional<Integer> ladderLen(String src, String dst, Set<String> words) {
		return bfs(src, dst, words);
	}

	private static Optional<Integer> bfs(String src, String dst, Set<String> words) {
		Queue<String> queue = new ArrayDeque<>();
		Map<String, Integer> map = newHashMap();
		queue.add(src);
		map.put(src, 0);
		while (!queue.isEmpty()) {
			String curr = queue.poll();
			if (curr.equals(dst)) {
				return of(map.get(curr));
			}
			for (String neighbor : neighbors(curr, words)) {
				if (!map.containsKey(neighbor)) {
					queue.add(neighbor);
					map.put(neighbor, map.get(curr) + 1);
				}
			}
		}
		return absent();
	}

	private static ImmutableList<String> neighbors(String src, Set<String> words) {
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
		return newList(set);
	}

	public static void main(String[] args) {
		Set<String> set1 = newHashSet("hit", "hot", "dot", "dog", "dog", "cog");
		System.out.println(ladderLen("hit", "cog", set1)); // 4

		Set<String> set2 = newHashSet("fool", "pool", "poll", "pole", "pale", "sale", "sage");
		System.out.println(ladderLen("fool", "sage", set2)); // 6

		Set<String> set3 = newHashSet("cold", "gold", "cord", "sold", "card", "ward", "warm", "tard");
		System.out.println(ladderLen("cold", "warm", set3)); // 4
	}
}
