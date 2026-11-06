package com.svetanis.datastructures.array;

// 414. Third Maximum Number
//
// Input: an integer array. Returns the third largest DISTINCT value; if there are fewer than three
// distinct values, returns the largest. On LeetCode: 1 <= nums.length <= 10^4, -2^31 <= nums[i] <=
// 2^31 - 1.
//
// The one idea: keep the three largest distinct values seen so far in three variables first >
// second > third, and let each number try to take a place. A number equal to one already held is
// skipped, which is what makes the values distinct. Otherwise it pushes the ones below it down by
// one place. The variables are long and start at Long.MIN_VALUE, a value no int can equal, so an
// empty place can never be mistaken for a real number (even Integer.MIN_VALUE). If third is still
// empty at the end there were fewer than three distinct values and the answer is first.
//
// Time: O(n) -- one pass, a constant number of comparisons per number.
// Space: O(1) -- three variables.

public final class ThirdMaxNumber {

	public static int thirdMaxNum(int[] nums) {
		final long infinity = Long.MIN_VALUE; // EMPTY: no int can equal it
		long first = infinity;
		long second = infinity;
		long third = infinity;
		for (int num : nums) {
			if (num == first || num == second || num == third) {
				continue; // SKIP: already held, keep the values distinct
			}
			if (num > first) { // PUSH DOWN: new largest
				third = second;
				second = first;
				first = num;
			} else if (num > second) { // PUSH DOWN: new second
				third = second;
				second = num;
			} else if (num > third) { // TAKE: new third
				third = num;
			}
		}
		return (int) (third == infinity ? first : third); // RETURN: third, or the largest if no third
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
