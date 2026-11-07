package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.List;

// 763. Partition Labels
//
// partitionLabels(s) takes a string of lowercase letters and cuts it into as many pieces as
// possible so that each letter appears in at most one piece. It returns the pieces' lengths,
// left to right. On LeetCode: 1 <= s.length <= 500.
//
// First record where each letter appears for the LAST time. Then walk the string, keeping the
// furthest last position of any letter seen since the current piece began. When the walk
// reaches that position, no letter in the piece appears again later, so the piece can end
// there. Cutting at the first such moment is what makes the pieces as many as possible.
//
// Siblings, same problem: none in this package.

public final class PartitionLabel {
	// Time complexity: O(n), one pass for the last positions and one to cut
	// Auxiliary Space: O(1), 26 ints; the result list is extra

	public static List<Integer> partitionLabels(String s) {
		int len = s.length();
		int[] indeces = indeces(s);
		int max = 0; // the furthest last position of any letter in the current piece
		int start = 0; // where the current piece began
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < len; i++) {
			int index = s.charAt(i) - 'a';
			max = Math.max(max, indeces[index]); // REACH: the piece must cover this letter's last place
			if (max == i) { // CUT: nothing in the piece appears after i
				list.add(i - start + 1);
				start = i + 1;
			}
		}
		return list;
	}

	private static int[] indeces(String s) {
		int[] a = new int[26];
		for (int i = 0; i < s.length(); i++) {
			a[s.charAt(i) - 'a'] = i; // LAST: a later place overwrites an earlier one
		}
		return a;
	}

	public static void main(String[] args) {
		System.out.println(partitionLabels("ababcbacadefegdehijhklij")); // 9, 7, 8
		System.out.println(partitionLabels("eccbbbbdec")); // 10
	}
}