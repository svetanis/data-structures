package com.svetanis.datastructures.hashmap;

import java.util.HashSet;
import java.util.Set;

// 1525. Number of Good Ways to Split a String
//
// count(s) returns how many ways there are to cut s into a non-empty left part and a
// non-empty right part that hold the same number of distinct letters. On LeetCode:
// 1 <= s.length <= 10^5, lowercase English letters only.
//
// Move the cut one letter at a time from the left end. Each step moves one letter from the
// right part to the left part, so both distinct counts can be kept as the cut moves, with no
// rescan: the left part is a set of the letters it holds; the right part is an array of copies
// per letter, plus total, the number of letters that still have a copy there. total drops by
// one exactly when a letter's last copy crosses. The last step leaves total at 0 and the left
// part with at least 1, so the cut after the whole string is never counted.
//
// Siblings, same problem:
//   CountGoodWaysToSplitString  the right part as a map of letter -> copies, a letter removed
//                               when its copies reach 0, so the map's size is the count

public final class CountGoodWaysToSplitStringSimple {
	// Time complexity: O(n) -- two passes over s and one over the 26 counts
	// Auxiliary Space: O(1) -- 26 counts and a set of at most 26 letters

	public static int count(String s) {
		int[] chars = freq(s); // RIGHT: copies of each letter right of the cut
		int total = right(chars); // DISTINCT: letters with a copy on the right
		int count = 0;
		Set<Character> set = new HashSet<>(); // LEFT: the letters left of the cut
		for(char c : s.toCharArray()) {
			set.add(c); // MOVE: c crosses to the left
			chars[c - 'a']--;
			if(chars[c - 'a'] == 0) {
				total--; // DROP: that was the last copy on the right
			}
			if(set.size() == total) { // GOOD?: the same number of distinct letters
				count++;
			}
		}
		return count;
	}

	private static int right(int[] chars) {
		int count = 0;
		for(int val : chars) {
			if(val > 0) {
				count++; // COUNT: a letter present at all
			}
		}
		return count;
	}

	private static int[] freq(String s) {
		int[] chars = new int[26];
		for(char c : s.toCharArray()) {
			chars[c - 'a']++; // COUNT: copies of each letter
		}
		return chars;
	}

	public static void main(String[] args) {
		System.out.println(count("aacaba")); // 2
		System.out.println(count("abcd")); // 1
	}
}