package com.svetanis.datastructures.array.quadruple;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.max;

import com.svetanis.java.base.utils.Quadruple;

// given an array of unsorted numbers
// find a max product of a quadruple

public final class MaxProductQuadruple {
	// Time complexity: O(n)

	public static int quadruple(int[] a) {
		int n = a.length;
		if (n < 4) {
			return -1;
		}
		// compute max, second max, third max and fourth max
		Quadruple<Integer, Integer, Integer, Integer> max = maxQuadruple(a);
		// compute min, second min, third min and fourth min
		Quadruple<Integer, Integer, Integer, Integer> min = minQuadruple(a);

		// x is a product of first, second, third and fourth max
		int x = max.getFirst() * max.getSecond() * max.getThird() * max.getLast();
		// y is a product of first, second, third and fourth min
		int y = min.getFirst() * min.getSecond() * min.getThird() * min.getLast();
		// z is a product of first and second min and first and second max
		int z = min.getFirst() * min.getSecond() * max.getFirst() * max.getSecond();
		return max(x, max(y, z));
	}

	private static Quadruple<Integer, Integer, Integer, Integer> minQuadruple(int[] a) {
		int first = MAX_VALUE;
		int second = MAX_VALUE;
		int third = MAX_VALUE;
		int fourth = MAX_VALUE;
		for (int i = 0; i < a.length; i++) {
			if (a[i] < first) {
				fourth = third;
				third = second;
				second = first;
				first = a[i];
			} else if (a[i] < second) {
				fourth = third;
				third = second;
				second = a[i];
			} else if (a[i] < third) {
				fourth = third;
				third = a[i];
			} else if (a[i] < fourth) {
				fourth = a[i];
			}
		}
		return Quadruple.build(first, second, third, fourth);
	}

	private static Quadruple<Integer, Integer, Integer, Integer> maxQuadruple(int[] a) {
		int first = MIN_VALUE;
		int second = MIN_VALUE;
		int third = MIN_VALUE;
		int fourth = MIN_VALUE;
		for (int i = 0; i < a.length; i++) {
			if (a[i] > first) {
				fourth = third;
				third = second;
				second = first;
				first = a[i];
			} else if (a[i] > second) {
				fourth = third;
				third = second;
				second = a[i];
			} else if (a[i] > third) {
				fourth = third;
				third = a[i];
			} else if (a[i] > fourth) {
				fourth = a[i];
			}
		}
		return Quadruple.build(first, second, third, fourth);
	}

	public static void main(String[] args) {
		int[] a = { 10, 3, 5, 6, 20 };
		System.out.println(quadruple(a));

		int[] a1 = { -10, -3, -5, -6, -20 };
		System.out.println(quadruple(a1));

		int[] a2 = { 1, -4, 3, -6, 7, 0 };
		System.out.println(quadruple(a2));
	}
}
