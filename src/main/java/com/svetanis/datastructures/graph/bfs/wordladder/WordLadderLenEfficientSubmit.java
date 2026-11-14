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
//
// Input: src (beginWord), dst (endWord), words (wordList). A ladder changes one letter at a time,
// and every word after src must be in the word list. Returns the number of words on the shortest
// ladder, src and dst both counted, or 0 if there is none. dst must be in the word list. On
// LeetCode: 1 <= beginWord.length <= 10, endWord.length == beginWord.length,
// 1 <= wordList.length <= 5000, lowercase letters, beginWord != endWord, all words in wordList
// are different.
//
// The one idea: the words are the places and one changed letter is a step, so the shortest ladder
// is the fewest steps, which a breadth-first search finds. The code is set up for a search from
// both ends: sq/sm are the queue and the map of words reached from src, eq/em the same from dst,
// and each map says how many words long the ladder from that end is (1 for the end word itself).
// A queue entry is one word. A neighbour that is in the other end's map joins the two halves,
// and the answer is the two lengths added. In practice only the src side ever runs: both queues
// start with one word, the src side is chosen when the sizes are equal, and bfs keeps taking
// words from its queue until the queue is empty or the halves meet, so a call that does not meet
// leaves sq empty and the loop ends. The result is still the shortest ladder, because that one
// search runs in order of length and em holds only dst.
//
// Time: O(n * 26 * m * m) -- each of the n words is queued once; it makes 26 * m candidates,
// each a word of m letters built and looked up (m is the word length).
// Space: O(n * m) -- the set, the maps and the queues.
//
// Siblings: WordLadderLength is the same search one level at a time, with a copy of the list
// that loses each word as it is queued in place of the map.

public final class WordLadderLenEfficientSubmit {

	public static int ladderLen(String src, String dst, List<String> words) {
		Set<String> set = new HashSet<>(words);
		if (!set.contains(dst)) {
			return 0; // BASE: dst is not a word you may reach
		}
		Queue<String> sq = new ArrayDeque<>();
		Map<String, Integer> sm = new HashMap<>();
		Queue<String> eq = new ArrayDeque<>();
		Map<String, Integer> em = new HashMap<>();
		sq.offer(src); // SEED: one word on each side
		eq.offer(dst);
		sm.put(src, 1);
		em.put(dst, 1);
		while (!sq.isEmpty() && !eq.isEmpty()) {
			boolean small = sq.size() <= eq.size(); // the side with the shorter queue goes
			int len = small ? bfs(sq, sm, em, set) : bfs(eq, em, sm, set);
			if (len != -1) { // RETURN: the halves met
				return len;
			}
		}
		return 0; // RETURN: no ladder
	}

	private static int bfs(Queue<String> queue, Map<String, Integer> map, Map<String, Integer> other, Set<String> words) {
		while (!queue.isEmpty()) {
			String word = queue.poll();
			int step = map.get(word);
			char[] chars = word.toCharArray();
			for (int i = 0; i < word.length(); i++) { // MUTATE: each position
				char curr = chars[i];
				for (char c = 'a'; c <= 'z'; ++c) { // MUTATE: each letter
					if (curr != c) {
						chars[i] = c;
						String s = new String(chars);
						if (!words.contains(s) || map.containsKey(s)) {
							continue; // SKIP: not a word, or this side has reached it
						}
						if (other.containsKey(s)) { // RETURN: the two halves meet at s
							return step + other.get(s);
						}
						queue.offer(s);
						map.put(s, step + 1); // RECORD: one word longer than word's ladder
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
