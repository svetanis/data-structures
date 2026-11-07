package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 2506. Count Pairs Of Similar Strings
//
// Input: an array of words. Two words are similar if they are made of the same set of letters,
// however often each letter repeats ("aabb" and "ab" are similar). Returns the number of index
// pairs (i, j), i < j, whose words are similar. On LeetCode: 1 <= words.length <= 100,
// 1 <= words[i].length <= 100, lowercase letters.
//
// The one idea: reduce each word to the set of letters it uses, written as 26 bits -- bit c is 1
// when letter c appears. Two words are similar exactly when their 26-bit numbers are equal. Walk
// the words in order and keep, for each number, how many earlier words had it: a new word with
// that number is similar to every one of them, which makes that many new pairs. Each pair (i, j)
// is counted once, when j is reached.
//
// Time: O(n * k) -- n words of up to k letters each, one hash lookup per word
// Space: O(n) -- at most one map entry per word

public final class CountPairsOfSimilarStrings {

	public static int similarPairs(String[] words) {
		int count = 0;
		Map<Integer, Integer> map = new HashMap<>(); // SEEN: letter set (as bits) -> earlier words with it
		for (String word : words) {
			int bitmask = 0;
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				bitmask |= 1 << (c - 'a'); // SET: add letter c to the word's letter set
			}
			count += map.getOrDefault(bitmask, 0); // PAIR: with every earlier word of the same set
			map.merge(bitmask, 1, Integer::sum); // RECORD: this word is now an earlier word
		}
		return count;
	}

	public static void main(String[] args) {
		String[] w1 = { "aba", "aabb", "abcd", "bac", "aabc" };
		System.out.println(similarPairs(w1)); // 2
		String[] w2 = { "aabb", "ab", "ba" };
		System.out.println(similarPairs(w2)); // 3
		String[] w3 = { "nba", "cba", "dba" };
		System.out.println(similarPairs(w3)); // 0
	}
}
