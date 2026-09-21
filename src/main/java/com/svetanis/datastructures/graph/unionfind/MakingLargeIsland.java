package com.svetanis.datastructures.graph.unionfind;

import java.util.HashSet;
import java.util.Set;

// 827. Making A Large Island
// The three 827 files:
//   1. MakingLargeIsland       -- union-find on the shared DisjointSet, three methods
//   2. MakingLargeIslandSubmit -- the same, structure inlined, for pasting
//   3. MakingLargeIslandDfs    -- labels the islands in the grid itself, no union-find
// All three return the same answer. Rung 1 is the one to write.
//
// RUNG 1. Written as THREE methods on purpose: in one loop the two phases merge, and
// the merge is the bug -- a budget counter appears that flips the first zero it meets
// and commits to it.
//
// The move is 947's, one step harder. There a node was a row or a column; here the
// nodes are land cells and the answer is asked of the WATER cells, which are not
// nodes at all. Neither is a thing the statement names.

public final class MakingLargeIsland {
	// Time Complexity: O(rows * cols)
	// Space Complexity: O(rows * cols)

	// phase 1 looks right and down only: every adjacent pair is then seen exactly
	// once, because the other half of the pair was handled from the other cell
	private static final int[] DR = { 0, 1 };
	private static final int[] DC = { 1, 0 };

	// phase 2 needs all four: it stands on the water cell and asks who touches it
	private static final int[] DR4 = { -1, 1, 0, 0 };
	private static final int[] DC4 = { 0, 0, -1, 1 };

	private DisjointSet ds;

	public int largestIsland(int[][] grid) {
		int rows = grid.length, cols = grid[0].length;
		this.ds = new DisjointSet(rows * cols);
		buildIslands(grid);
		int best = 0;
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				int k = row * cols + col;
				// the land branch is not decorative: drop it and a grid with no
				// zeros at all returns 0, because nothing is ever recorded
				int size = grid[row][col] == 0
						? sizeIfFlipped(grid, row, col)
						: ds.size(ds.find(k));
				best = Math.max(best, size);
			}
		}
		return best;
	}

	// what the island would be if this water cell became land. NOTHING IS FLIPPED:
	// the statement's "at most one" is honoured by trying each zero and committing
	// to none, which is why no budget counter is needed anywhere.
	private int sizeIfFlipped(int[][] grid, int row, int col) {
		int rows = grid.length, cols = grid[0].length;
		// the DISTINCT islands touching this cell. Two of its four neighbours can
		// be in one island, and counting that island twice is the trap this set
		// exists for. Rebuilt for each water cell.
		Set<Integer> islands = new HashSet<>();
		for (int d = 0; d < 4; d++) {
			int x = row + DR4[d], y = col + DC4[d];
			if (x >= 0 && x < rows && y >= 0 && y < cols && grid[x][y] == 1) {
				// find on the NEIGHBOUR, never on the water cell -- that cell was
				// never unioned with anything, so its own size is always 1
				islands.add(ds.find(x * cols + y));
			}
		}
		// 1 for the flipped cell itself, then the SIZE of each island -- not the
		// number of islands, which is what islands.size() would give
		int total = 1;
		for (int root : islands) {
			total += ds.size(root);
		}
		return total;
	}

	// phase 1 knows only the 1s. It does not know that flipping exists.
	private void buildIslands(int[][] grid) {
		int rows = grid.length, cols = grid[0].length;
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				// the cell being STOOD ON has to be land too; checking only the
				// neighbour joins a water cell to an island
				if (grid[row][col] == 0) {
					continue;
				}
				int k = row * cols + col;
				for (int d = 0; d < 2; d++) {
					int x = row + DR[d], y = col + DC[d];
					if (x >= 0 && x < rows && y >= 0 && y < cols && grid[x][y] == 1) {
						ds.union(k, x * cols + y);
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(new MakingLargeIsland().largestIsland(new int[][] { { 1, 0 }, { 0, 1 } })); // 3
		System.out.println(new MakingLargeIsland().largestIsland(new int[][] { { 1, 1 }, { 1, 0 } })); // 4

		// no zeros at all: the land branch is the only thing that reports anything
		System.out.println(new MakingLargeIsland().largestIsland(new int[][] { { 1, 1 }, { 1, 1 } })); // 4

		// no ones at all: every zero is worth exactly itself
		System.out.println(new MakingLargeIsland().largestIsland(new int[][] { { 0, 0 }, { 0, 0 } })); // 1

		// two of the flipped cell's neighbours sit in ONE island -- counted once
		int[][] corner = { { 0, 0, 0 }, { 0, 1, 1 }, { 0, 1, 1 } };
		System.out.println(new MakingLargeIsland().largestIsland(corner)); // 5
	}
}
