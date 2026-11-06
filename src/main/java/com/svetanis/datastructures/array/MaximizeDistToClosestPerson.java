package com.svetanis.datastructures.array;

// 849. Maximize Distance to Closest Person
//
// Input: a -- the seats in a row, 1 = taken, 0 = empty, with at least one of each. Returns the
// largest possible distance between the seat chosen for a new person and the nearest taken seat.
// On LeetCode: 2 <= a.length <= 2 * 10^4, a[i] is 0 or 1, at least one 0 and at least one 1.
//
// The one idea: the empty seats come in runs, and a run has one of two shapes. A run between two
// taken seats gives its best seat in the middle, at half the gap between the two taken seats,
// rounded down. A run touching a wall has only one taken seat beside it, so the best seat is the
// wall end, and the distance is the whole run. So one pass records the first taken seat (the left
// wall run is its index), the last taken seat (the right wall run is n - 1 - last), and the
// largest gap between neighbouring taken seats (halved at the end).
//
// Time: O(n) -- one pass.
// Space: O(1) -- first and last taken seats and the largest gap.

public final class MaximizeDistToClosestPerson {

	public static int maxDist(int[] a) {
		int firstOccupied = -1;
		int lastOccupied = -1;
		int maxDist = 0; // GAP: the largest distance between two neighbouring taken seats
		int n = a.length;
		for (int i = 0; i < n; i++) {
			if (a[i] == 1) {
				if (lastOccupied != -1) {
					maxDist = Math.max(maxDist, i - lastOccupied); // GAP between two taken seats
				}
				if (firstOccupied == -1) {
					firstOccupied = i; // FIRST taken seat: the left wall run has this length
				}
				lastOccupied = i;
			}
		}
		int end = n - lastOccupied - 1; // RIGHT wall run: the empty seats after the last taken seat
		return Math.max(Math.max(firstOccupied, end), maxDist / 2); // RETURN: wall runs whole, middle halved
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
