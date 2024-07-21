package com.svetanis.datastructures.array.twopointers;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Lists.sort;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;

import com.google.common.collect.ImmutableList;

// given a sorted array, create a new array
// containing squares of all the numbers
// of the input array in the sorted order

public final class SquaringSortedArray {
	// Time Complexity: O(n)
	// Space Complexity: O(n)

	public static ImmutableList<Integer> sortSquares(int[] a) {
		int n = a.length;
		int left = 0;
		int right = n - 1;
		List<Integer> list = newArrayList();
		while (left <= right) {
			int leftSquare = a[left] * a[left];
			int rightSquare = a[right] * a[right];
			if (leftSquare > rightSquare) {
				list.add(0, leftSquare);
				left++;
			} else {
				list.add(0, rightSquare);
				right--;
			}
		}
		return newList(list);
	}

	public static ImmutableList<Integer> sortSquares(List<Integer> list) {
		List<Integer> transformed = transform(list, i -> i * i);
		return sort(transformed);
	}

	public static void main(String[] args) {
		int[] a = { -2, -1, 0, 2, 3 };
		print(sortSquares(a));

		int[] a1 = { -3, -1, 0, 1, 2 };
		print(sortSquares(a1));

		List<Integer> list1 = newArrayList(-2, -1, 0, 2, 3);
		System.out.println(sortSquares(list1));

		List<Integer> list2 = newArrayList(-3, -1, 0, 1, 2);
		System.out.println(sortSquares(list2));
	}
}