package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.List;

// 763. Partition Label

public final class PartitionLabel {
	// Time Complexity: O(n)

	public static List<Integer> partitionLabels(String s) {
		int len = s.length();
		int[] indeces = indeces(s);
		int max = 0;
		int start = 0;
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < len; i++) {
			int index = s.charAt(i) - 'a';
			max = Math.max(max, indeces[index]);
			if (max == i) {
				list.add(i - start + 1);
				start = i + 1;
			}
		}
		return list;
	}

	private static int[] indeces(String s) {
		int[] a = new int[26];
		for (int i = 0; i < s.length(); i++) {
			a[s.charAt(i) - 'a'] = i;
		}
		return a;
	}

	public static void main(String[] args) {
		System.out.println(partitionLabels("ababcbacadefegdehijhklij")); // 9, 7, 8
		System.out.println(partitionLabels("eccbbbbdec")); // 10
	}
}