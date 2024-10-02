package com.svetanis.datastructures.array.twopointers;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.asList;

import java.util.List;

// Suppose you are given an array containing non-negative numbers 
// representing heights of a set of buildings. 
// Now, because of differences in heights of buildings water can be trapped between them. 
// Find the two buildings that will trap the most amount of water. 
// Write a function that will return the maximum volume of water 
// that will be trapped between these two buildings.

public final class MaxTrappingWater {
	// Time Complexity: O(n)

	public static int maxWater(List<Integer> list) {
		int max = 0;
		int left = 0;
		int right = list.size() - 1;
		while (left < right) {
			int width = right - left;
			int height = min(list.get(left), list.get(right));
			max = max(max, width * height);
			if (list.get(left) < list.get(right)) {
				left++;
			} else {
				right--;
			}
		}
		return max;
	}

	public static void main(String[] args) {
		System.out.println(maxWater(asList(2, 0, 2)));
		System.out.println(maxWater(asList(3, 0, 0, 2, 0, 4)));
		System.out.println(maxWater(asList(0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1)));
		System.out.println(maxWater(asList(1, 8, 6, 2, 5, 4, 8, 3, 7)));
	}
}
