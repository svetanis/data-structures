package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// 1525. Number of Good Ways to Split a String
//
// count(s) returns how many ways there are to cut s into a non-empty left part and a
// non-empty right part that hold the same number of distinct letters. On LeetCode:
// 1 <= s.length <= 10^5, lowercase English letters only.
//
// Move the cut one letter at a time from the left end. Each step moves one letter from the
// right part to the left part, so both distinct counts can be kept as the cut moves, with no
// rescan: the left part is a set of the letters it holds, and the right part is a map of
// letter -> copies, where a letter whose copies reach 0 is removed so that the map's size is
// the right part's distinct count. The last step leaves the right part empty: its count is 0
// and the left part's is at least 1, so the cut after the whole string is never counted.
//
// Siblings, same problem:
//   CountGoodWaysToSplitStringSimple  the right part as a 26-slot count array plus a running
//                                     count of the letters still in it, instead of a map

public final class CountGoodWaysToSplitString {
	// Time complexity: O(n) -- two passes, one map step and one set step per letter
	// Auxiliary Space: O(1) -- the set and the map hold at most 26 letters each

	public static int count(String s) {
		int count = 0;
		Set<Character> set = new HashSet<>(); // LEFT: the letters left of the cut
		Map<Character, Integer> map = new HashMap<>(); // RIGHT: letter -> copies right of the cut
		for (char c : s.toCharArray()) {
			map.merge(c, 1, Integer::sum); // COUNT: at first everything is right of the cut
		}
		for (char c : s.toCharArray()) {
			set.add(c); // MOVE: c crosses to the left
			if (map.merge(c, -1, Integer::sum) == 0) {
				map.remove(c); // DROP: no copy left on the right, so size stays the distinct count
			}
			if (set.size() == map.size()) { // GOOD?: the same number of distinct letters
				count++;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		System.out.println(count("aacaba")); // 2
		System.out.println(count("abcd")); // 1
	}
}