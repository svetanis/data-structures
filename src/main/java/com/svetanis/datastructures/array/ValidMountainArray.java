package com.svetanis.datastructures.array;

// 941. Valid Mountain Array
//
// Input: an integer array a. On LeetCode: 1 <= a.length <= 10^4, 0 <= a[i] <= 10^4. Returns true
// when a is a mountain: it has at least 3 numbers, and there is a peak index p with 0 < p < n - 1
// such that a[0] < a[1] < ... < a[p] and a[p] > a[p + 1] > ... > a[n - 1] -- strictly up, then
// strictly down.
//
// The one idea: walk it like a hiker. Climb while the next number is strictly higher; where that
// stops is the peak. A mountain needs the climb to have gone at least one step (the peak is not
// index 0) and to have stopped before the end (the peak is not the last index), or there is no
// descent. Then descend while the next number is strictly lower. It is a mountain only if the
// descent reaches the last index: any flat step or second rise leaves the walker short of it.
//
// Time: O(n) -- each index is passed once, by one of the two walks.
// Space: O(1) -- one index.

public final class ValidMountainArray {

	public static boolean validMountainArray(int[] a) {
		int n = a.length;
		if (n < 3) {
			return false;
		}
		int index = 0;
		while (index + 1 < n && a[index] < a[index + 1]) { // CLIMB: strictly up
			index += 1;
		}
		if (index == 0 || index == n - 1) { // PEAK: must have climbed, and must not be the end
			return false;
		}
		while (index + 1 < n && a[index] > a[index + 1]) { // DESCEND: strictly down
			index += 1;
		}
		return index == n - 1; // RETURN: the descent must reach the last index
	}

	public static void main(String[] args) {
		int[] a = { 2, 1 };
		System.out.println(validMountainArray(a)); // false

		int[] a1 = { 3, 5, 5 };
		System.out.println(validMountainArray(a1)); // false

		int[] a2 = { 0, 3, 2, 1 };
		System.out.println(validMountainArray(a2)); // true

		int[] a3 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		System.out.println(validMountainArray(a3)); // false

		int[] a4 = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };
		System.out.println(validMountainArray(a4)); // false
	}
}