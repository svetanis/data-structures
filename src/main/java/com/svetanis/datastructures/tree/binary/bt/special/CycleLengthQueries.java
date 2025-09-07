package com.svetanis.datastructures.tree.binary.bt.special;

import com.svetanis.java.base.utils.Print;

// 2509. Cycle Length Queries in a Tree 

public final class CycleLengthQueries {
	// Time complexity: O(q log n)

	public static int[] cycleLenQueries(int n, int[][] queries) {
		int m = queries.length;
		int[] cycles = new int[m];
		for (int i = 0; i < m; i++) {
			int start = queries[i][0];
			int end = queries[i][1];
			int len = 1;
			while (start != end) {
				if (start > end) {
					start >>= 1;
				} else {
					end >>= 1;
				}
				len++;
			}
			cycles[i] = len;
		}
		return cycles;
	}

	public static void main(String[] args) {
		int[][] q1 = { { 5, 3 }, { 4, 7 }, { 2, 3 } };
		Print.print(cycleLenQueries(3, q1)); // 4,5,3

		int[][] q2 = { { 1, 2 } };
		Print.print(cycleLenQueries(2, q2)); // 2
	}
}
