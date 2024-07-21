package com.svetanis.datastructures.array.twopointers;

import static com.svetanis.java.base.utils.Print.print;
import static java.util.Arrays.sort;

// Remove duplicates from Sorted Array
// Given a sorted array, remove the duplicates in place 
// such that each element appears only once and return the new length.

public final class RemoveDuplicatesSorted {

	public static int remove(int[] a) {
		// Time Complexity: O(n log n)
		// Space Complexity: O(1)

		sort(a);
		int next = 0; // index of the next non-duplicate element
		for (int i = 1; i < a.length; i++) {
			if (a[i] != a[next]) {
				next++;
				a[next] = a[i];
			}
		}
		return next + 1;
	}

	public static int remove2(int[] a) {
		// Time Complexity: O(n log n)
		// Space Complexity: O(1)

		sort(a);
		int next = 1; // index of the next non-duplicate element
		for (int i = 1; i < a.length; i++) {
			if (a[i] != a[next - 1]) {
				a[next] = a[i];
				next++;
			}
		}
		return next;
	}

	public static void main(String[] args) {
		int[] a = { 6, 7, 7, 3, 1, 5, 5, 9, 9, 3 };
		int last = remove(a);
		System.out.println(last);
		print(a, last);

		int[] a2 = { 5000, 5000, 5000 };
		int last2 = remove(a2);
		System.out.println(last2);
		print(a2, last2);
	}
}