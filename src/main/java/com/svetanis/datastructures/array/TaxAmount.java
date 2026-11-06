package com.svetanis.datastructures.array;

// 2303. Calculate Amount Paid in Taxes
//
// Input: brackets[i] = { upper, percent }, sorted by upper, strictly increasing: income up to
// upper is taxed at percent in the part that lies above the previous bracket's upper (the first
// bracket starts at 0). And an income. Returns the tax paid, as a double. On LeetCode: 1 <=
// brackets.length <= 100, 1 <= upper <= 1000, 0 <= percent <= 100, 0 <= income <= 1000, the last
// upper is at least income, and answers within 10^-5 of the true value are accepted.
//
// The one idea: each bracket owns a slice of the income line: from the previous upper to this
// upper. The part of the income inside that slice is min(income, upper) - prev, or 0 if the income
// does not reach the slice. That part is taxed at the bracket's percent. Add up the slices; the sum
// is in percent-dollars, so divide by 100 once at the end.
//
// Time: O(n) -- one pass over the brackets.
// Space: O(1) -- a few variables.

public final class TaxAmount {

	public static double tax(int[][] brackets, int income) {
		double tax = 0;
		int prev = 0; // EDGE: where the current slice starts
		for (int[] bracket : brackets) {
			int upper = bracket[0];
			int percent = bracket[1];
			int amount = Math.max(0, Math.min(income, upper) - prev); // SLICE: income inside this bracket
			tax += amount * percent; // TAKE: in percent-dollars
			prev = upper;
		}
		return tax / 100; // RETURN: percent-dollars to dollars
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
