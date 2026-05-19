package com.svetanis.datastructures.array;

// 2303. Calculate Amount Paid in Taxes

public final class TaxAmount {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static double tax(int[][] brackets, int income) {
		double tax = 0;
		int prev = 0;
		for (int[] bracket : brackets) {
			int upper = bracket[0];
			int percent = bracket[1];
			int amount = Math.max(0, Math.min(income, upper) - prev);
			tax += amount * percent;
			prev = upper;
		}
		return tax / 100;
	}

	public static void main(String[] args) {
		int[][] b1 = { { 3, 50 }, { 7, 10 }, { 12, 25 } };
		System.out.println(tax(b1, 10)); // 2.65

		int[][] b2 = { { 1, 0 }, { 4, 25 }, { 5, 50 } };
		System.out.println(tax(b2, 2)); // 0.25

		int[][] b3 = { { 2, 50 } };
		System.out.println(tax(b3, 0)); // 0.0
	}
}
