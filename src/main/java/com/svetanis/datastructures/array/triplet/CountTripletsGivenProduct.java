package com.svetanis.datastructures.array.triplet;

import static java.util.Arrays.sort;

// given array of distinct positive integers and a target
// count triplets with the product equal to target

public final class CountTripletsGivenProduct {

	public static int count(int[] a, int k) {
		// Time complexity: O(n^2)
		sort(a);

		int count = 0;
		for (int i = 0; i < a.length; i++) {
			if(a[i] != 0 && k % a[i] == 0) {
			  int target = k / a[i];
			  count += count(a, target, i);
			}
		}
		return count;
	}

	private static int count(int[] a, int target, int first) {
		int count = 0;
		int left = first + 1;
		int right = a.length - 1;
		while (left < right) {
			int prod = a[left] * a[right];
			if (prod > target) {
				right--;
			} else if (prod < target){
				left++;
			} else {
				left++;
				right--;
				count++;
			}
		}
		return count;

	}

	public static void main(String[] args) {
		int[] a = { 1, 1, 1, 1, 1, 1 };
		System.out.println(count(a, 1));

		int[] a1 = { 1, 4, 6, 2, 3, 8 };
		System.out.println(count(a1, 24));

		int[] a2 = { 0, 4, 6, 2, 3, 8 };
		System.out.println(count(a2, 18));

	}
}
