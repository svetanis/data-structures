package com.svetanis.datastructures.array;

// 849. Maximize Distance to Closest Person

public final class MaximizeDistToClosestPerson {
	// Time Complexity: O(n)
	// Space Complexity: O(1)

	public static int maxDist(int[] a) {
		int firstOccupied = -1;
		int lastOccupied = -1;
		int maxDist = 0;
		int n = a.length;
		for (int i = 0; i < n; i++) {
			if (a[i] == 1) {
				if (lastOccupied != -1) {
					maxDist = Math.max(maxDist, i - lastOccupied);
				}
				if (firstOccupied == -1) {
					firstOccupied = i;
				}
				lastOccupied = i;
			}
		}
		int end = n - lastOccupied - 1;
		return Math.max(Math.max(firstOccupied, end), maxDist / 2);
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 0, 0, 0, 1, 0, 1 };
		System.out.println(maxDist(a1)); // 2

		int[] a2 = { 1, 0, 0, 0 };
		System.out.println(maxDist(a2)); // 3

		int[] a3 = { 0, 1 };
		System.out.println(maxDist(a3)); // 1
	}
}
