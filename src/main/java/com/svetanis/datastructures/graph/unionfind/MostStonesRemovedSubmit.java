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
// RUNG 3: rung 2 with the parent array inlined, so this pastes on its own.
//
// Time: O(n log n) -- n unions, each find compressing as it goes (no union by size); plus the
// one-off 20020-slot parent array.
// Space: O(1) in n -- the 2 * 10010 slots do not depend on the number of stones.

public final class MostStonesRemovedSubmit {

	private int[] parent;

	public int removeStones(int[][] stones) {
		int n = 10010; // rows live at 0..n-1, columns at n..2n-1
		init(n);
		union(n, stones); // JOIN: every stone's row and column
		int components = components(stones);
		return stones.length - components; // RETURN: stones - groups
	}

	private int components(int[][] stones) {
		Set<Integer> components = new HashSet<>();
		for (int[] stone : stones) {
			components.add(find(stone[0])); // COUNT: one root per group
		}
		return components.size();
	}

	private void union(int n, int[][] stones) {
		for (int[] stone : stones) {
			int row = stone[0];
			int col = stone[1];
			parent[find(row)] = find(col + n); // JOIN: the row's root under the column's root
		}
	}

	private void init(int n) {
		this.parent = new int[n << 1];
		for (int i = 0; i < parent.length; i++) {
			parent[i] = i;
		}
	}

	private int find(int x) {
		int y = parent[x];
		if (y != x) {
			y = find(y); // WALK up to the root
			parent[x] = y; // COMPRESS: point straight at it
		}
		return y;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 2 }, { 2, 1 }, { 2, 2 } };
		MostStonesRemovedSubmit ldc = new MostStonesRemovedSubmit();
		System.out.println(ldc.removeStones(g1)); // 5 -- one group of six stones

		int[][] g2 = { { 0, 0 }, { 0, 2 }, { 1, 1 }, { 2, 0 }, { 2, 2 } };
		System.out.println(ldc.removeStones(g2)); // 3

		int[][] g3 = { { 0, 0 } };
		System.out.println(ldc.removeStones(g3)); // 0

		int[][] g4 = { { 0, 1 }, { 1, 0 } };
		System.out.println(ldc.removeStones(g4)); // 0
	}
}
