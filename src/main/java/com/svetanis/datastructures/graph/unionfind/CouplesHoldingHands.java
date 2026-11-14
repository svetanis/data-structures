package com.svetanis.datastructures.graph.unionfind;

// 765. Couples Holding Hands
//
// Input: int[] row of 2n distinct people numbered 0..2n-1 sitting in a row; couple k is people
// 2k and 2k+1. Seats 2i and 2i+1 are side by side (a "pair of seats"). On LeetCode:
// 2 <= n <= 30, row.length == 2n.
// Returns the fewest swaps (any two people trade seats) so every couple sits side by side.
//
// The one idea: look at each pair of seats and the two people in it, who belong to couple a and
// couple b. Draw a link between a and b. If a == b the pair of seats is already a happy couple
// (a link from a couple to itself). Otherwise couples a and b are tangled together, and the
// links tie couples into groups. A group of g couples forms a ring: the seats of those g couples
// hold exactly those g couples' people, and one swap can fix one pair of seats for good and
// shrink the ring by one, so g - 1 swaps are needed and enough. A group of 1 needs 0. The total
// is (number of couples) - (number of groups), and union-find counts the groups.
//
// Time: O(n log n) -- one union per pair of seats, then one find per couple; paths are compressed
// but trees are not balanced, so a find is O(log n) amortised (n <= 30 here, so it hardly matters).
// Space: O(n) -- the parent array over the couples.

public final class CouplesHoldingHands {

	private int[] parent; // CARDS: parent[k] -- the couple above couple k in its group

	public int minSwapsCouples(int[] row) {
		int couples = row.length / 2;
		this.parent = init(couples);
		for (int i = 0; i < row.length - 1; i += 2) { // LINK: couple of the left seat -- couple of the right
			union(row[i] / 2, row[i + 1] / 2);
		}
		return swaps(couples);
	}

	private int swaps(int couples) {
		int swaps = couples; // START: one swap per couple, then give back one per group
		for (int i = 0; i < couples; i++) {
			if (i == find(i)) { // ROOT: a group is counted once, at its root
				swaps--;
			}
		}
		return swaps;
	}

	private int[] init(int n) {
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = i;
		}
		return a;
	}

	public int find(int x) {
		int y = parent[x];
		if (y != x) {
			y = find(y);
			parent[x] = y; // COMPRESS
		}
		return y;
	}

	private void union(int p, int q) {
		int rootP = find(p);
		int rootQ = find(q);
		parent[rootP] = rootQ; // JOIN
	}

	public static void main(String[] args) {
		CouplesHoldingHands chh = new CouplesHoldingHands();
		int[] row1 = { 0, 2, 1, 3 };
		System.out.println(chh.minSwapsCouples(row1)); // 1
		int[] row2 = { 3, 2, 0, 1 };
		System.out.println(chh.minSwapsCouples(row2)); // 0
	}
}
