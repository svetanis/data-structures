package com.svetanis.datastructures.array;

import java.util.Arrays;

// 1470. Shuffle the Array
//
// Given an array of 2n numbers in the form [x1, x2, ..., xn, y1, y2, ..., yn], return them in the
// form [x1, y1, x2, y2, ..., xn, yn]. Nothing is random: the two halves are dealt out in turn.
//
// x(i+1) sits at nums[i] and y(i+1) at nums[i + n], so pair i of the answer is nums[i], nums[i + n].
// The two methods write the same answer with different bookkeeping:
//   shuffle()         one step per PAIR: write nums[i], then nums[i + n]; k is the next free slot
//   shuffleEvenOdd()  one step per SLOT: even slots take the next x, odd slots the next y; two
//                     read positions, left over the x half and right over the y half

public final class ShuffleArray1470 {
	// Time Complexity: O(n), each of the 2n numbers is copied once
	// Space Complexity: O(n), the answer array; nothing else is kept

	public int[] shuffle(int[] nums, int n) {
		int[] shuffled = new int[2 * n];
		for (int i = 0, k = 0; i < n; i++) { // i runs over the pairs, k over the slots
			shuffled[k++] = nums[i]; // x(i+1)
			shuffled[k++] = nums[i + n]; // y(i+1): the y half starts at n
		}
		return shuffled;
	}

	public int[] shuffleEvenOdd(int[] nums, int n) {
		int left = 0; // the next x to deal
		int right = n; // the next y to deal: the y half starts at n
		int[] shuffled = new int[2 * n];
		for (int i = 0; i < 2 * n; i++) { // i runs over the slots
			shuffled[i] = i % 2 == 0 ? nums[left++] : nums[right++]; // x on even slots, y on odd
		}
		return shuffled;
	}

	public static void main(String[] args) {
		ShuffleArray1470 sa = new ShuffleArray1470();
		System.out.println(Arrays.toString(sa.shuffle(new int[] { 2, 5, 1, 3, 4, 7 }, 3))); // [2, 3, 5, 4, 1, 7]
		System.out.println(Arrays.toString(sa.shuffle(new int[] { 1, 2, 3, 4, 4, 3, 2, 1 }, 4))); // [1, 4, 2, 3, 3, 2, 4, 1]
		System.out.println(Arrays.toString(sa.shuffleEvenOdd(new int[] { 1, 1, 2, 2 }, 2))); // [1, 2, 1, 2]
	}
}
