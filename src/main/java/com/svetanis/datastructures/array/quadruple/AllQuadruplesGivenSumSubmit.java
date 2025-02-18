package com.svetanis.datastructures.array.quadruple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.svetanis.java.base.utils.Print;

// 18. 4Sum

public final class AllQuadruplesGivenSumSubmit {
	// Time Complexity: O(n^3)
	
	public static List<List<Integer>> quadruplets(int[] a, int target) {
		int n = a.length;
		List<List<Integer>> quadruplets = new ArrayList<>();
		if (n < 4) {
			return quadruplets;
		}
		Arrays.sort(a);
		for (int i = 0; i < n - 3; i++) {
			// skip duplicates
			if (i > 0 && a[i] == a[i - 1]) {
				continue;
			}
			for (int j = i + 1; j < n - 2; j++) {
				// skip duplicates
				if (j > i + 1 && a[j] == a[j - 1]) {
					continue;
				}
				int left = j + 1;
				int right = n - 1;
				while (left < right) {
					long sum = (long) a[i] + a[j] + a[left] + a[right];
					if (sum < target) {
						left++;
					} else if (sum > target) {
						right--;
					} else {
						quadruplets.add(Arrays.asList(a[i], a[j], a[left], a[right]));
						left++;
						right--;
						// skip duplicates
						while (left < right && a[left] == a[left - 1]) {
							left++;
						}
						while (left < right && a[right] == a[right + 1]) {
							right--;
						}
					}
				}
			}
		}
		return quadruplets;
	}

	public static void main(String[] args) {
		int[] a = { 1, 0, -1, 0, -2, 2 };
		Print.print(quadruplets(a, 0)); // [-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]

		int[] a1 = { 2, 2, 2, 2, 2 };
		Print.print(quadruplets(a1, 8)); // [2,2,2,2]
	}
}
