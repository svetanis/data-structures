package com.svetanis.datastructures.array;

// 1491. Average Salary Excluding the Minimum and Maximum Salary
//
// Input: salary -- the salaries of the employees, all different. Returns the average of the
// salaries once the smallest and the largest are left out. On LeetCode: 3 <= salary.length <= 100,
// 1000 <= salary[i] <= 10^6, all distinct. A shorter array has nothing left to average; this method
// returns 0 there, which LeetCode never asks for.
//
// The one idea: the sum of everything except the extremes is the total minus the minimum minus the
// maximum, over n - 2 employees. One pass finds the total, the minimum and the maximum together,
// so there is no sorting. The total fits in an int: at most 100 * 10^6.
//
// Time: O(n) -- one pass.
// Space: O(1) -- a sum, a minimum and a maximum.

public final class AverageSalary {

	public static double average(int[] salary) {
		if (salary.length <= 2) {
			return 0; // EDGE: nobody is left after leaving out two
		}
		int sum = 0;
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < salary.length; i++) {
			sum += salary[i]; // TOTAL
			min = Math.min(min, salary[i]); // TRACK the smallest
			max = Math.max(max, salary[i]); // TRACK the largest
		}
		sum -= (min + max); // LEAVE OUT the two extremes
		return (sum * 1.0) / (salary.length - 2); // RETURN: a double, not integer division
	}

	public static void main(String[] args) {
		int[] a1 = { 4000, 3000, 1000, 2000 };
		System.out.println(average(a1)); // 2500.0

		int[] a2 = { 1000, 2000, 3000 };
		System.out.println(average(a2)); // 2000.0
	}
}
