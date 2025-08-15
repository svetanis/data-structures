package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.java.base.utils.Print;

// 3159. Find Occurrences of an Element in an Array

public final class ElementOccurrencesInArray {
	// Time Complexity: O(n + m)
	// Space Complexity: O(n + m)

	public static int[] occurrencesSimple(int[] a, int[] queries, int x) {
		int count = 0;
		int n = a.length;
		int[] indexes = new int[n];
		for (int i = 0; i < n; i++) {
			if (a[i] == x) {
				indexes[count] = i;
				count++;
			}
		}
		int m = queries.length;
		int[] result = new int[m];
		for (int i = 0; i < m; i++) {
			int index = queries[i] - 1;
			result[i] = queries[i] <= count ? indexes[index] : -1;
		}
		return result;
	}

	public static int[] occurrences(int[] a, int[] queries, int x) {
		int n = queries.length;
		int[] result = new int[n];
		List<Integer> list = indexes(a, x);
		for (int i = 0; i < n; i++) {
			int index = queries[i] - 1;
			result[i] = index < list.size() ? list.get(index) : -1;
		}
		return result;
	}

	private static List<Integer> indexes(int[] a, int x) {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < a.length; i++) {
			if (a[i] == x) {
				list.add(i);
			}
		}
		return list;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 3, 1, 7 };
		int[] q1 = { 1, 3, 2, 4 };
		Print.print(occurrences(a1, q1, 1)); // 0, -1, 2, -1
		Print.print(occurrencesSimple(a1, q1, 1)); // 0, -1, 2, -1

		int[] a2 = { 1, 2, 3 };
		int[] q2 = { 10 };
		Print.print(occurrences(a2, q2, 5)); // -1
		Print.print(occurrencesSimple(a2, q2, 5)); // -1
	}
}