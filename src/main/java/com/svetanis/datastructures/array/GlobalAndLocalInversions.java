package com.svetanis.datastructures.array;

// 775. Global and Local Inversions
//
// Input: a -- a permutation of 0 to n - 1. A global inversion is a pair i < j with a[i] > a[j]; a
// local inversion is an i with a[i] > a[i + 1]. Returns true when the number of global inversions
// equals the number of local inversions. On LeetCode: n == a.length, 1 <= n <= 10^5,
// 0 <= a[i] < n, all values distinct.
//
// The one idea: every local inversion is also a global one (the pair i, i + 1), so the counts are
// equal exactly when there is no global inversion that is NOT local -- no pair i < j with
// j >= i + 2 and a[i] > a[j]. So for each j, look at the largest value among a[0..j - 2], the
// positions at least two to the left, and check it is not bigger than a[j]. The running maximum
// of a[0..j - 2] is kept as j moves right, so no pair is compared more than once.
//
// Time: O(n) -- one pass.
// Space: O(1) -- one maximum.

public final class GlobalAndLocalInversions {

	public static boolean isIdealPermutation(int[] a) {
		if (a.length < 2) {
			return true; // EDGE: no pairs at all
		}
		int max = 0; // MAX: the largest value among the positions at least two to the left
		for (int i = 2; i < a.length; i++) {
			max = Math.max(max, a[i - 2]); // WIDEN: a[i - 2] is now two or more to the left of i
			if (max > a[i]) {
				return false; // BREAK: a global inversion that is not a local one
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 0, 2 };
		System.out.println(isIdealPermutation(a1)); // true

		int[] a2 = { 1, 2, 0 };
		System.out.println(isIdealPermutation(a2)); // false
	}
}
