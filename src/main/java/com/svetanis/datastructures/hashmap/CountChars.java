package com.svetanis.datastructures.hashmap;

// 1160. Find Words That Can Be Formed by Characters
//
// countChars(words, s) returns the total length of the words that can be spelled from the
// letters of s, each letter of s used at most once per word (every word starts again from all
// of s). On LeetCode: 1 <= words.length <= 1000, every word and s 1..100 letters, lowercase
// English only.
//
// Count the letters of s once. For each word, count its letters as they are read; the word
// fails the moment one letter's count goes above what s has. A word that gets to its end
// never asked for more than s has, so its length is added.
//
// Siblings, same problem: none in this package.

public final class CountChars {
	// Time complexity: O(L), L = the length of s plus the lengths of all the words -- each
	// letter is read once; the 26-slot array per word is a fixed cost
	// Auxiliary Space: O(1) -- two arrays of 26 counts

	public static int countChars(String[] words, String s) {
		int[] available = new int[26]; // SUPPLY: copies of each letter in s
		for (char c : s.toCharArray()) {
			available[c - 'a'] += 1;
		}
		int total = 0;
		for (String word : words) {
			int[] chars = new int[26]; // NEED: fresh per word, s is reused in full
			boolean canForm = true;
			for (char c : word.toCharArray()) {
				int index = c - 'a';
				chars[index] += 1; // COUNT: one more of this letter needed
				if (chars[index] > available[index]) { // SHORT?: more than s has
					canForm = false;
					break;
				}
			}
			if (canForm) {
				total += word.length(); // ADD: the whole word's length
			}
		}
		return total;
	}

	public static void main(String[] args) {
		String[] a1 = { "cat", "bt", "hat", "tree" };
		System.out.println(countChars(a1, "atach")); // 6
		String[] a2 = { "hello", "world", "leetcode" };
		System.out.println(countChars(a2, "welldonehoneyr")); // 10
	}
}