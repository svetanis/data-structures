package com.svetanis.datastructures.graph.unionfind;

import java.util.HashSet;
import java.util.Set;

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
// RUNG 2, and the one to write. A node is a ROW or a COLUMN -- never a cell, and
// never a stone. Three rows and three columns is SIX nodes, not nine: the grid's
// cells play no part, and most of them are empty anyway.
//
// A stone is then an EDGE, joining its row to its column. Two stones in one row are
// joined by both touching that row's node, so nothing ever compares two stones --
// which is where rung 1's O(n^2) goes.
//
// Time: O(n * alpha + R) -- n unions and n finds, each near-constant; R is the one-off cost of
// allocating the 2 * 10010 slots.
// Space: O(R) -- one band of 10010 slots for rows and one for columns, however few stones there are.

public final class MostStonesRemoved {

	// one past the largest coordinate the constraints allow, so the two bands
	// cannot collide: row x lives at x, column y lives at y + ROWS
	private static final int ROWS = 10010;

	public int removeStones(int[][] stones) {
		DisjointSet ds = new DisjointSet(ROWS * 2);
		for (int[] stone : stones) {
			ds.union(stone[0], ROWS + stone[1]); // JOIN: the stone's row and its column
		}
		// one stone per group cannot be removed, so the answer is stones - groups.
		// the count is driven from the STONES, not from the parent array: most of the
		// 20020 slots are rows and columns with no stone, and each would count itself.
		// and it is taken AFTER every union -- a root recorded during the loop can be
		// demoted by a later one and stop being a root.
		Set<Integer> groups = new HashSet<>();
		for (int[] stone : stones) {
			// only the row is asked about: the stone's own union already put its row
			// and column in one group, so find(col) would return the same number
			groups.add(ds.find(stone[0])); // COUNT: one root per group
		}
		return stones.length - groups.size(); // RETURN: stones - groups
	}

	public static void main(String[] args) {
		MostStonesRemoved msr = new MostStonesRemoved();
		int[][] g1 = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 2 }, { 2, 1 }, { 2, 2 } };
		System.out.println(msr.removeStones(g1)); // 5 -- one group of six stones

		int[][] g2 = { { 0, 0 }, { 0, 2 }, { 1, 1 }, { 2, 0 }, { 2, 2 } };
		System.out.println(msr.removeStones(g2)); // 3

		int[][] g3 = { { 0, 0 } };
		System.out.println(msr.removeStones(g3)); // 0

		// two stones sharing neither a row nor a column: two groups, nothing removable
		int[][] g4 = { { 0, 1 }, { 1, 0 } };
		System.out.println(msr.removeStones(g4)); // 0

		// the stale-root case: three stones where the last union fuses two groups
		int[][] g5 = { { 2, 2 }, { 1, 3 }, { 1, 2 } };
		System.out.println(msr.removeStones(g5)); // 2
	}
}
