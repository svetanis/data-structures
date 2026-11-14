package com.svetanis.datastructures.graph.bfs.wordladder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

// 127. Word Ladder
//
// Input: beginWord, endWord, wordList. A ladder changes one letter at a time, and every word after
// beginWord must be in wordList. Returns the number of words on the shortest ladder, beginWord and
// endWord both counted, or 0 if there is none. On LeetCode: 1 <= beginWord.length <= 10,
// endWord.length == beginWord.length, 1 <= wordList.length <= 5000, lowercase letters,
// beginWord != endWord, all words in wordList are different. The caller's list is left unchanged.
//
// The one idea: a word is a node and one changed letter is an edge, so the shortest ladder is a
// breadth-first search one level at a time, and the level number is the number of words in the
// chain -- words, not steps: hit -> hot -> dot -> dog -> cog is 5 words and 4 steps. The
// neighbours are generated, not compared: each position changed to each of the other 25 letters,
// every candidate one lookup in a set. Removing a word from that set when it is queued is the
// visited mark, so each word is queued once.
//
// Time: O(n * 25 * m * m) -- each of the n words is queued once; it makes 25 * m candidates, each
// an m-letter string built and looked up (m is the word length).
// Space: O(n * m) -- the set and the queue.
//
// Siblings: WordLadderLenEfficientSubmit is the same search with a map of word -> ladder length,
// written for two sides but running only the start side; WordLadder returns the ladder itself.

public final class WordLadderLength {

	public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
		Set<String> words = new HashSet<>(wordList); // a SET: one step per lookup, never the List
		if (!words.contains(endWord)) { // NO PATH: endWord is not in the list
			return 0;
		}
		Queue<String> queue = new ArrayDeque<>();
		queue.offer(beginWord); // START: beginWord is level 1
		int level = 1;
		while (!queue.isEmpty()) {
			int size = queue.size(); // LEVEL: exactly the words on this level
			for (int i = 0; i < size; i++) {
				String word = queue.poll();
				if (word.equals(endWord)) { // FOUND: the chain has level words
					return level;
				}
				for (String neighbor : neighbors(word)) {
					if (words.contains(neighbor)) { // in the list, not queued before
						queue.offer(neighbor);
						words.remove(neighbor); // MARK: never queued again
					}
				}
			}
			level += 1; // NEXT LEVEL: one more word in the chain
		}
		return 0; // NO PATH: the queue ran dry
	}

	private static List<String> neighbors(String word) {
		List<String> out = new ArrayList<>();
		char[] letters = word.toCharArray(); // a char[] can be changed; a String cannot
		for (int pos = 0; pos < letters.length; pos++) {
			char letter = letters[pos]; // SAVE: the letter now at pos
			for (char c = 'a'; c <= 'z'; c++) {
				if (c == letter) { // SKIP: that candidate is the word itself
					continue;
				}
				letters[pos] = c; // SWAP
				out.add(new String(letters)); // the candidate, as a String
			}
			letters[pos] = letter; // RESTORE: before the next position
		}
		return out;
	}

	public static void main(String[] args) {
		List<String> list = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
		System.out.println(ladderLength("hit", "cog", list)); // 5

		// the same list without the end word: no ladder, because "cog" is not a word you may reach
		List<String> noEnd = Arrays.asList("hot", "dot", "dog", "lot", "log");
		System.out.println(ladderLength("hit", "cog", noEnd)); // 0

		// the list is the caller's, and it comes back untouched
		System.out.println(list.size()); // 6
	}
}
