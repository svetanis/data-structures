package com.svetanis.datastructures.array;

// 1491. Average Salary Excluding the Minimum and Maximum Salary

public final class AverageSalary {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static double average(int[] salary) {
		if (salary.length <= 2) {
			return 0;
		}
		int sum = 0;
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < salary.length; i++) {
			sum += salary[i];
			min = Math.min(min, salary[i]);
			max = Math.max(max, salary[i]);
		}
		sum -= (min + max);
		return (sum * 1.0) / (salary.length - 2);
	}

	public static void main(String[] args) {
		int[] a1 = { 4000, 3000, 1000, 2000 };
		System.out.println(average(a1)); // 2500

		int[] a2 = { 1000, 2000, 3000 };
		System.out.println(average(a2)); // 2000
	}
}
