package com.svetanis.datastructures.hashmap;

import java.util.HashSet;
import java.util.Set;

// 961. N-Repeated Element in Size 2N Array
//
// repeated takes an array of length 2n holding n + 1 distinct values, one of which occurs
// n times, and returns that value. On LeetCode: 2 <= n <= 5000, 0 <= nums[i] <= 10^4.
//
// The other n values fill the remaining n places, so each of them occurs exactly once,
// and the only value that can ever be seen twice is the repeated one: the first value the
// set already holds is the answer.

public final class NRepeatedElement {
	// Time complexity: O(n), one pass, stopping at the first value seen twice
	// Auxiliary Space: O(n) for the set of values seen

	public static int repeated(int[] a) {
		Set<Integer> set = new HashSet<>();
		for(int element : a) {
			if(!set.add(element)) {
				return element; // FOUND: seen before, and only the repeated value can be
			}
		}
		return -1; // NONE: unreachable when the input keeps the promise
	}

	public static void main(String[] args) {
		int[] a = { 1, 2, 3, 3 };
		System.out.println(repeated(a)); // 3
		int[] a1 = { 2, 1, 2, 5, 3, 2 };
		System.out.println(repeated(a1)); // 2
		int[] a2 = { 5, 1, 5, 2, 5, 3, 5, 4 };
		System.out.println(repeated(a2)); // 5
	}
}