package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.List;

// 448. Find All Numbers Disappeared in an Array
//
// disappearedNums(nums) takes n ints, each between 1 and n, and returns every number from 1 to
// n that does not appear in nums, in increasing order. On LeetCode: 1 <= n <= 10^5,
// 1 <= nums[i] <= n.
//
// The values are bounded by the array's own length, so a count array of n + 1 slots holds one
// slot per possible value. Every value with a count of 0 is missing, and reading the slots in
// order returns them already sorted. (LeetCode's follow-up asks for no extra space, by marking
// slots inside nums itself; this version does not do that.)
//
// Siblings, same problem: none in this package.

public final class NumsDisappearInArray {
	// Time complexity: O(n), one pass to count and one over the n slots
	// Auxiliary Space: O(n), the count array; the result list is extra

	public static List<Integer> disappearedNums(int[] a) {
		int[] counts = new int[a.length + 1];
		for(int num : a) {
			counts[num]++; // COUNT: slot num is value num
		}
		List<Integer> list = new ArrayList<>();
		for(int i = 1; i < counts.length; i++) {
			if(counts[i] == 0) { // MISSING: never seen
				list.add(i);
			}
		}
		return list;
	}

	public static void main(String[] args) {
		int[] a1 = { 4, 3, 2, 7, 8, 2, 3, 1 };
		System.out.println(disappearedNums(a1)); // 5,6
		int[] a2 = { 1, 1 };
		System.out.println(disappearedNums(a2)); // 2
	}
}