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
// Input: beginWord, endWord, wordList. A ladder changes one letter at a time, and every word after
// beginWord must be in the word list (beginWord itself need not be). Returns the number of words on
// the shortest ladder, both ends counted, or 0 if there is none. On LeetCode:
// 1 <= beginWord.length <= 10, endWord.length == beginWord.length, 1 <= wordList.length <= 5000,
// lowercase letters, beginWord != endWord, all words in wordList are different.
//
// The one idea: search from both ends at once, one level per turn, and always grow the side with
// the smaller queue. front/back map each word reached to the number of words on the ladder from
// that end (1 for the end word itself). One call of expand takes exactly the words that were in the
// queue when it started -- one level -- and then hands the turn back. Before a side keeps a new
// word, it asks the OTHER side's map: a hit is the meeting, and the answer is this side's count for
// the word it stands on plus the other side's count for the new word -- the new word counted once.
// The meeting test comes BEFORE the word-list test: beginWord sits in the front map but not in the
// word list, so a back side that reaches it must still meet it. (With the word-list test first,
// "hit" -> "hot" with list [hot] returns 0 whenever a tie in queue sizes goes to the back side.)
//
// Why the first meeting is the shortest: every word the other side reached at an earlier level has
// already been expanded, so its neighbours sit in the other map too. A word on this side's current
// level that touched one of them would have met it when it was put in a map. So any meeting found
// now lands on the other side's last level, and every meeting in this level gives the same sum.
//
// Time: O(n * 26 * m * m) in the worst case, the same as one side -- each of the n words is queued
// at most once per side; it makes 26 * m candidates, each a word of m letters built and looked up
// (m is the word length). On a typical dictionary the two searches stop near the middle, so far
// fewer words are reached than by one search alone (accepted on LeetCode in 16 ms).
// Space: O(n * m) -- the set, the maps and the queues.
//
// Siblings: WordLadderLenEfficientSubmit has the same two queues and maps, but its bfs empties its
// whole queue in one call, so only the beginWord side ever runs. WordLadderLength is the one-sided
// search one level at a time.

public final class WordLadderLenTwoSided {

	public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
		Set<String> words = new HashSet<>(wordList);
		if (!words.contains(endWord)) {
			return 0; // BASE: endWord is not a word you may reach
		}
		Map<String, Integer> front = new HashMap<>(); // word -> words on the ladder from beginWord
		Map<String, Integer> back = new HashMap<>(); // word -> words on the ladder from endWord
		Queue<String> qf = new ArrayDeque<>();
		Queue<String> qb = new ArrayDeque<>();
		front.put(beginWord, 1); // SEED: one word on each side, each a ladder of 1 word
		qf.offer(beginWord);
		back.put(endWord, 1);
		qb.offer(endWord);
		while (!qf.isEmpty() && !qb.isEmpty()) { // one side empty: no ladder can join them
			int met = qf.size() < qb.size() ? expand(qf, front, back, words) : expand(qb, back, front, words);
			if (met > 0) {
				return met; // RETURN: the halves met
			}
		}
		return 0; // RETURN: one side ran out of words, no ladder
	}

	// Grows one side by exactly one level. Returns the ladder length if the halves meet, else 0.
	private static int expand(Queue<String> queue, Map<String, Integer> mine, Map<String, Integer> other,
			Set<String> words) {
		for (int k = queue.size(); k > 0; k--) { // LEVEL: only the words queued before this call
			String word = queue.poll();
			char[] letters = word.toCharArray();
			for (int pos = 0; pos < letters.length; pos++) {
				char letter = letters[pos]; // SAVE
				for (char c = 'a'; c <= 'z'; c++) {
					if (c == letter) {
						continue; // SKIP: that candidate is the word itself
					}
					letters[pos] = c; // SWAP
					String next = new String(letters);
					if (other.containsKey(next)) { // MEET first: beginWord is in a map, not in words
						return mine.get(word) + other.get(next);
					}
					if (!words.contains(next)) {
						continue; // SKIP: not a word
					}
					if (!mine.containsKey(next)) { // RECORD: one word longer than word's ladder
						mine.put(next, mine.get(word) + 1);
						queue.offer(next);
					}
				}
				letters[pos] = letter; // RESTORE
			}
		}
		return 0; // the level is done and the halves have not met: hand the turn back
	}

	public static void main(String[] args) {
		List<String> list = asList("hot", "dot", "dog", "lot", "log", "cog");
		System.out.println(ladderLength("hit", "cog", list)); // 5

		List<String> list0 = asList("hot", "dot", "dog", "lot", "log");
		System.out.println(ladderLength("hit", "cog", list0)); // 0

		System.out.println(ladderLength("hit", "hot", asList("hot"))); // 2 -- the back side meets "hit"
	}
}
