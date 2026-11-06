package com.svetanis.datastructures.array;

// 565. Array Nesting
//
// Input: a -- an array of length n holding every number 0 to n - 1 exactly once. Starting at index
// start, build the set s(start) = { a[start], a[a[start]], a[a[a[start]]], ... } and stop just
// before a value repeats. Returns the largest size of any such set. On LeetCode: 1 <= n <= 10^5,
// 0 <= a[i] < n, all values distinct. The method overwrites a.
//
// The one idea: one slot says "from index i, go to index a[i]". Because every number appears
// exactly once, every index has exactly one way in and one way out, so the indexes split into
// separate loops (cycles), and s(start) is exactly the loop that start sits on, whichever index
// of that loop start is. The answer is the longest loop. To find every loop once, walk each loop
// from its first unvisited index and mark each index as visited by overwriting its slot with n, a value no slot
// can hold; the walk of a loop already marked stops at once with a count of 0.
//
// Time: O(n) -- each index is walked once; the outer loop adds n cheap checks.
// Space: O(1) -- the visited mark lives in a itself, which is why the input is destroyed.

public final class ArrayNesting {

	public static int nest(int[] a) {
		int n = a.length;
		int max = Integer.MIN_VALUE;
		for (int start = 0; start < n; start++) {
			int count = 0;
			int curr = start;
			while (a[curr] < n) { // WALK: stop at an index that is already marked
				int next = a[curr];
				a[curr] = n; // MARK: n cannot be a real value, so this index is visited
				curr = next;
				count++;
			}
			max = Math.max(max, count); // RECORD: the longest loop so far
		}
		return max;
	}

	public static void main(String[] args) {
		int[] a1 = { 5, 4, 0, 3, 1, 6, 2 };
		System.out.println(nest(a1)); // 4

		int[] a2 = { 0, 1, 2 };
		System.out.println(nest(a2)); // 1
	}
}
