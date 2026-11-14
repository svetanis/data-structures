package com.svetanis.datastructures.graph.bfs.wordladder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Helper for WordLadder: the words in the dictionary that are one letter
// away from a given word, found in one of two ways.
//
// Input: a word src, the dictionary words, and a flag saying which way to use.
// Returns the dictionary words that differ from src in exactly one position.
//
// The one idea: there are two ways to find the neighbours, and each is cheap when the other is
// expensive. MUTATE changes each of the m letters of src to each of the 26 letters and keeps
// the results that are in the dictionary: 26 * m candidates, however big the dictionary is.
// SCAN compares src with every dictionary word and keeps those one letter away: one comparison
// per dictionary word, however long the word is. So when the dictionary holds more than
// 26 * m words, mutating is cheaper (largeDictionary), and otherwise scanning is.
//
// Time: neighbors with MUTATE is O(26 * m * m) -- 26 * m candidates, each built and looked up in
// O(m); with SCAN it is O(n * m) -- n dictionary words compared in O(m) each.
// Space: O(k) -- k is the number of neighbours found.

public final class WordLadderUtil {

	public static boolean largeDictionary(String src, Set<String> words) {
		int size = words.size();
		int len = src.length();
		int charComparisons = len * 26;
		return size > charComparisons; // RETURN: true when mutating beats scanning
	}

	public static List<String> neighbors(String src, Set<String> words, boolean largeDict) {
		if (largeDict) {
			return neighbors(src, words); // MUTATE
		}
		return oneCharAway(src, words); // SCAN
	}

	private static List<String> neighbors(String src, Set<String> words) {
		List<String> out = new ArrayList<>();
		char[] letters = src.toCharArray(); // a char[] can be changed; a String cannot
		for (int pos = 0; pos < letters.length; pos++) { // MUTATE: each position
			char letter = letters[pos]; // SAVE: the letter now at pos
			for (char c = 'a'; c <= 'z'; c++) { // MUTATE: each letter
				if (c == letter) { // SKIP: that candidate is the word itself
					continue;
				}
				letters[pos] = c; // SWAP
				String candidate = new String(letters);
				if (words.contains(candidate)) { // TAKE: only real words
					out.add(candidate);
				}
			}
			letters[pos] = letter; // RESTORE: before the next position
		}
		return out;
	}

	private static List<String> oneCharAway(String src, Set<String> words) {
		Set<String> set = new HashSet<>();
		for (String word : words) { // SCAN: every dictionary word
			if (!word.equals(src)) {
				if (oneCharAway(word, src)) { // TAKE: exactly one letter different
					set.add(word);
				}
			}
		}
		return new ArrayList<>(set);
	}

	private static boolean oneCharAway(String src, String dst) {
		if (src.length() != dst.length() || src.equals(dst)) {
			return false; // SKIP: different length or the same word
		}
		int count = 0;
		for (int i = 0; i < src.length(); i++) {
			if (src.charAt(i) != dst.charAt(i)) {
				count++;
				if (count > 1) {
					return false; // SKIP: two letters differ
				}
			}
		}
		return true;
	}
}
