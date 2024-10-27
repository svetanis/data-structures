package com.svetanis.datastructures.array.majority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 229. Majority Element II
// given an integer array of size n,
// find all elements that appear more 
// than n/3 times

public final class MajorityElementII {

	public static List<Integer> majorityElements(int[] a) {
		// Time Complexity: O(n)
		// Space Complexity: O(1)

		// 1. find candidate for majority
		List<Integer> candidates = candidates(a);
		Set<Integer> set = new HashSet<>();
		for (int candidate : candidates) {
			// 2. check if candidate is majority
			if (isMajority(a, candidate)) {
				set.add(candidate);
			}
		}
		return new ArrayList<>(set);
	}

	private static boolean isMajority(int[] a, int candidate) {
		int count = 0;
		for (int num : a) {
			if (num == candidate) {
				count++;
			}
		}
		return count > a.length / 3;
	}

	private static List<Integer> candidates(int[] a) {
		int major1 = 0;
		int count1 = 0;
		int major2 = 0;
		int count2 = 0;
		for (int num : a) {
			if (num == major1) {
				count1++;
			} else if (num == major2) {
				count2++;
			} else if (count1 == 0) {
				major1 = num;
				count1 = 1;
			} else if (count2 == 0) {
				major2 = num;
				count2 = 1;
			} else {
				count1--;
				count2--;
			}
		}
		return Arrays.asList(major1, major2);
	}

	public static void main(String[] args) {
		int[] a = { 3, 2, 3 };
		System.out.println(majorityElements(a)); // 3

		int[] a2 = { 1 };
		System.out.println(majorityElements(a2)); // 1

		int[] a3 = { 1, 2 };
		System.out.println(majorityElements(a3)); // 1, 2

		int[] a4 = { 0, 0, 0 };
		System.out.println(majorityElements(a4)); // 0
	}
}