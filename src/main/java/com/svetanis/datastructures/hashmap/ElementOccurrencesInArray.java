package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 3159. Find Occurrences of an Element in an Array
//
// Both methods take nums, queries and x, and return one answer per query: the index of the
// queries[i]-th occurrence of x in nums (counting from 1), or -1 if x occurs fewer than
// queries[i] times. On LeetCode: 1 <= nums.length, queries.length <= 10^5,
// 1 <= queries[i] <= 10^5, 1 <= nums[i], x <= 10^4.
//
// One pass writes down where x occurs, in order, so the q-th occurrence is entry q - 1 of
// that record. A query then costs one comparison with the count and one lookup, instead of
// a scan of nums per query.
//
// Two methods, same idea:
//   occurrences        records the indexes in an ArrayList, sized as it grows
//   occurrencesSimple  records them in an int[] of length n, and a count of entries used

public final class ElementOccurrencesInArray {
	// Time complexity: O(n + q), one pass over nums, then one lookup per query
	// Auxiliary Space: O(n) for the recorded indexes (x may fill all of nums), plus the answer

	public static int[] occurrencesSimple(int[] a, int[] queries, int x) {
		int count = 0;
		int n = a.length;
		int[] indexes = new int[n];
		for (int i = 0; i < n; i++) {
			if (a[i] == x) {
				indexes[count] = i; // RECORD: the (count + 1)-th occurrence is at i
				count++;
			}
		}
		int m = queries.length;
		int[] result = new int[m];
		for (int i = 0; i < m; i++) {
			int index = queries[i] - 1; // FIND: occurrences count from 1, entries from 0
			result[i] = queries[i] <= count ? indexes[index] : -1; // ANSWER, or -1 if too few
		}
		return result;
	}

	public static int[] occurrences(int[] a, int[] queries, int x) {
		int n = queries.length;
		int[] result = new int[n];
		List<Integer> list = indexes(a, x);
		for (int i = 0; i < n; i++) {
			int index = queries[i] - 1; // FIND: occurrences count from 1, entries from 0
			result[i] = index < list.size() ? list.get(index) : -1; // ANSWER, or -1 if too few
		}
		return result;
	}

	private static List<Integer> indexes(int[] a, int x) {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < a.length; i++) {
			if (a[i] == x) {
				list.add(i); // RECORD: in increasing order of index
			}
		}
		return list;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 3, 1, 7 };
		int[] q1 = { 1, 3, 2, 4 };
		System.out.println(Arrays.toString(occurrences(a1, q1, 1))); // 0, -1, 2, -1
		System.out.println(Arrays.toString(occurrencesSimple(a1, q1, 1))); // 0, -1, 2, -1

		int[] a2 = { 1, 2, 3 };
		int[] q2 = { 10 };
		System.out.println(Arrays.toString(occurrences(a2, q2, 5))); // -1
		System.out.println(Arrays.toString(occurrencesSimple(a2, q2, 5))); // -1
	}
}