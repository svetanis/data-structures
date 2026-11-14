package com.svetanis.datastructures.graph.unionfind;

// 947. Most Stones Removed with Same Row or Column
//
// Input: stones -- stones[i] = [row, column], the cell of stone i on a grid; no two stones share
// a cell. A stone may be removed when another stone, not yet removed, is in its row or its column.
// Returns the largest number of stones that can be removed. On LeetCode: 1 <= stones.length <= 1000,
// 0 <= row, column <= 10^4, no two stones at the same point.
//
// The one idea: call two stones CONNECTED when they share a row or a column, directly or through
// other stones. Inside one connected group of k stones you can remove k - 1 of them -- take them in
// an order that always leaves the rest connected, such as farthest-first from one chosen stone --
// and the last stone of a group can never go, because nothing outside its group shares its row or
// column. So the answer is (number of stones) - (number of groups).
//
// The three 947 files, and the rungs differ by WHAT A NODE IS:
//   1. MostStonesRemovedPairwise -- STONES are nodes, every pair compared, O(n^2)
//   2. MostStonesRemoved         -- a ROW or a COLUMN is a node, a stone is the edge
//   3. MostStonesRemovedSubmit   -- rung 2 with the structure inlined, for pasting
// All three return the same answer. Rung 2 is the one to write, and it is the move
// that makes 827 writable: the nodes are not things the statement names.
//
// RUNG 1: the straightforward reading of the statement. Correct, and fast enough
// at n <= 1000, but it compares every pair of stones where rung 2 compares none.
// The stones are the nodes, joined pairwise when they share a row or a column.
// MostStonesRemoved takes the other model, where a node is a ROW or a COLUMN and a
// stone is the edge between them -- n unions and no comparisons. This one is the
// straightforward reading of the statement and is fast enough at n <= 1000.
// Read the two together: the difference is what a node is, and nothing else.
//
// Time: O(n^2) -- every pair of stones is compared once; MostStonesRemoved needs only n unions,
// because two stones in one row are joined THROUGH the row rather than by being compared.
// Space: O(n) -- one DisjointSet slot per stone.

public final class MostStonesRemovedPairwise {

	private DisjointSet ds;

	public int removeStones(int[][] stones) {
		int n = stones.length;
		this.ds = new DisjointSet(n); // one element per stone
		// every stone starts in its own group; each successful union destroys one group
		int groups = n;
		for (int i = 0; i < n; i++) {
			int[] a = stones[i];
			// j starts at i + 1: each unordered pair is examined once
			for (int j = i + 1; j < n; j++) {
				int[] b = stones[j];
				boolean sameRow = a[0] == b[0];
				boolean sameCol = a[1] == b[1];
				if ((sameRow || sameCol) && ds.union(i, j)) { // JOIN: two connected stones
					groups -= 1;
				}
			}
		}
		// one stone per group cannot be removed: nothing outside its group shares its
		// row or column, by the definition of a group
		return n - groups; // RETURN: stones - groups
	}

	public static void main(String[] args) {
		MostStonesRemovedPairwise msr = new MostStonesRemovedPairwise();
		int[][] stones1 = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 2 }, { 2, 1 }, { 2, 2 } };
		System.out.println(msr.removeStones(stones1)); // 5 -- one group of six stones

		int[][] stones2 = { { 0, 0 }, { 0, 2 }, { 1, 1 }, { 2, 0 }, { 2, 2 } };
		System.out.println(new MostStonesRemovedPairwise().removeStones(stones2)); // 3

		// a single stone shares nothing, so nothing can be removed
		int[][] stones3 = { { 0, 0 } };
		System.out.println(new MostStonesRemovedPairwise().removeStones(stones3)); // 0
	}

}
