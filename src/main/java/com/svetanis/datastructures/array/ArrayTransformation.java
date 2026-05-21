package com.svetanis.datastructures.array;

import java.util.Arrays;
import java.util.List;

// 1243. Array Transformation

public final class ArrayTransformation {
	// Time Complexity: O(n * m)
	// Space Complexity: O(n)

	public static List<Integer> transformArray(int[] a) {
		boolean updated = true;
		while (updated) {
			updated = false;
			int[] copy = a.clone();
			for (int i = 1; i < copy.length - 1; i++) {
				if (copy[i] > copy[i - 1] && copy[i] > copy[i + 1]) {
					a[i] -= 1;
					updated = true;
				}

				if (copy[i] < copy[i - 1] && copy[i] < copy[i + 1]) {
					a[i] += 1;
					updated = true;
				}
			}
		}
		return Arrays.stream(a).boxed().toList();
	}

	public static void main(String[] args) {
		int[] a = { 1, 6, 3, 4, 3, 5 };
		System.out.println(transformArray(a)); // 1 4 4 4 4 5
	}
}
