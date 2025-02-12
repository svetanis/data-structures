package com.svetanis.datastructures.array;

// 414. Third Maximum Number

public final class ThirdMaxNumber {
	// Time Complexity: O(n)

	public static int thirdMaxNum(int[] nums) {
		final long infinity = Long.MIN_VALUE;
		long first = infinity;
		long second = infinity;
		long third = infinity;
		for (int num : nums) {
			if (num == first || num == second || num == third) {
				continue;
			}
			if (num > first) {
				third = second;
				second = first;
				first = num;
			} else if (num > second) {
				third = second;
				second = num;
			} else if (num > third) {
				third = num;
			}
		}
		return (int) (third == infinity ? first : third);
	}

	public static void main(String[] args) {
		int[] a1 = { 3, 2, 1 };
		System.out.println(thirdMaxNum(a1)); // 1

		int[] a2 = { 1, 2 };
		System.out.println(thirdMaxNum(a2)); // 2

		int[] a3 = { 2, 2, 3, 1 };
		System.out.println(thirdMaxNum(a3)); // 1
	}
}
