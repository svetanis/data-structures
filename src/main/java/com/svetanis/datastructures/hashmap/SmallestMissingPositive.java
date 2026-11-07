package com.svetanis.datastructures.hashmap;

// 2598. Smallest Missing Non-negative Integer After Operations
//
// smp(nums, value) may add value to, or subtract it from, any element any number of times. It
// returns the largest possible MEX: the smallest non-negative integer missing from nums, made
// as large as the operations allow. On LeetCode: 1 <= nums.length, value <= 10^5,
// -10^9 <= nums[i] <= 10^9.
//
// An element can become any number with its own remainder modulo value, and nothing else, so
// all that matters is how many elements have each remainder. Java's % keeps the sign of a
// negative number, so (num % value + value) % value brings it into 0 to value - 1. Covering
// 0, 1, 2, ... uses one element of remainder i % value for each i. The first i whose
// remainder has none left is the answer: the remainder with the fewest elements, c of them,
// runs out first, at c * value + r, and among remainders tied on c the smallest r runs out
// first.
//
// Two methods, the same remainder counts. They differ in how the answer is read off:
//   smp   the formula: the smallest remainder with the fewest elements, c * value + r
//   smp2  the walk: spend one element per number i until a remainder has none; it always
//         ends, because each step spends one of the n elements

public final class SmallestMissingPositive {
	// Time complexity: O(n + value), one pass to count and one over the value remainders; smp2
	//   walks at most n + 1 numbers
	// Auxiliary Space: O(value), one count per remainder

	public static int smp(int[] a, int val) {
		int[] countMod = new int[val];
		for (int num : a) {
			int reminder = num % val;
			int positive = (reminder + val) % val; // REMAINDER: in 0..val - 1, also for negatives
			countMod[positive]++; // COUNT
		}
		int min = 0;
		for (int i = 0; i < val; i++) {
			if (countMod[i] < countMod[min]) { // FEWEST: strict <, so a tie keeps the smaller r
				min = i;
			}
		}
		return val * countMod[min] + min; // FIRST GAP: r's elements cover r, r + val, ...
	}

	public static int smp2(int[] a, int val) {
		int[] countMod = new int[val];
		for (int num : a) {
			int reminder = num % val;
			int positive = (reminder + val) % val; // REMAINDER: in 0..val - 1, also for negatives
			countMod[positive]++; // COUNT
		}
		for (int i = 0;; i++) { // WALK: ends within n + 1 steps
			if (countMod[i % val] == 0) { // GAP: no element left that can become i
				return i;
			}
			countMod[i % val]--; // SPEND: one element becomes i
		}
	}

	public static void main(String[] args) {
		int[] a = { 1, -10, 7, 13, 6, 8 };
		System.out.println(smp(a, 5)); // 4

		int[] a1 = { 1, -10, 7, 13, 6, 8 };
		System.out.println(smp(a1, 7)); // 2

		int[] a2 = { 1, 3, 5, 7 };
		System.out.println(smp(a2, 2)); // 0

		int[] a3 = { 3, 0, 3, 2, 4, 2, 1, 1, 0, 4 };
		System.out.println(smp(a3, 5)); // 10

	}
}
