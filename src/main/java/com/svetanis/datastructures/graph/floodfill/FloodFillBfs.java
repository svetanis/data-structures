package com.svetanis.datastructures.graph.floodfill;

import static java.util.Arrays.asList;

import com.svetanis.datastructures.graph.Cell;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

// 733. Flood Fill (the version that takes the image as a list of lists)
//
// Input: a start cell (r, c), a replacement colour rc, and the image as a list of rows, each a list
// of colour numbers. On LeetCode: image is 1 <= m, n <= 50 and 0 <= colour < 2^16. In computer
// graphics, a flood fill repaints every pixel connected to the start pixel that has the same
// colour as it has (the paint bucket of MS-Paint). The lists must allow set; the rows are
// changed in place.
// Returns: the same list of lists, repainted.
//
// The one idea: this is a breadth-first walk over cells. One queue entry is a cell that has been
// painted rc but whose four neighbours have not been looked at yet. The start cell's old colour
// is remembered first; then every neighbour that is on the image and still has the old colour is
// painted and queued. A set of Cells records every cell that was queued, because when rc
// equals the old colour a painted cell still looks like an unpainted one, and without the set the
// walk would never end.
//
// Time: O(m * n) -- each cell is queued at most once and looked at by at most four neighbours,
// provided get and set on a row take constant time (they do for ArrayList and Arrays.asList).
// Space: O(m * n) -- the queue and the set.
//
// Siblings: FloodFillSubmit is the same walk with a boolean array in place of the set, which
// needs no Cell hashing. FloodFillDfs and FloodFillMatrix do the fill by recursion on int
// arrays.

public final class FloodFillBfs {

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, 1, 0, -1 };

	public static List<List<Integer>> floodFill(int r, int c, int rc, List<List<Integer>> image) {
		Cell src = new Cell(r, c);
		bfs(src, rc, image);
		return image;
	}

	private static void bfs(Cell src, int rc, List<List<Integer>> image) {
		Set<Cell> set = new HashSet<>(); // SEEN: cells already painted and queued
		Queue<Cell> queue = new ArrayDeque<>(); // QUEUE: painted cells whose neighbours are not yet looked at
		queue.add(src);
		set.add(src);
		int r = src.getRow();
		int c = src.getCol();
		int color = image.get(r).get(c); // OLD: the colour being replaced
		image.get(r).set(c, rc); // PAINT: the start cell
		while (!queue.isEmpty()) {
			Cell curr = queue.poll();
			List<Cell> neighbors = neighbors(curr, color, image);
			for (Cell neighbor : neighbors) {
				if (!set.contains(neighbor)) {
					int row = neighbor.getRow();
					int col = neighbor.getCol();
					image.get(row).set(col, rc); // PAINT: the neighbour
					queue.add(neighbor);
					set.add(neighbor);
				}
			}
		}
	}

	// the neighbours that are on the image and still hold the old colour
	private static List<Cell> neighbors(Cell curr, int color, List<List<Integer>> image) {
		List<Cell> list = new ArrayList<>();
		for (int i = 0; i < dx.length; i++) {
			int row = curr.getRow() + dx[i];
			int col = curr.getCol() + dy[i];
			Cell next = new Cell(row, col);
			if (valid(next, image) && image.get(row).get(col) == color) {
				list.add(next); // TAKE: on the image and the old colour
			}
		}
		return list;
	}

	private static boolean valid(Cell c, List<List<Integer>> image) {
		int n = image.size();
		int m = image.get(0).size();
		int x = c.getRow();
		int y = c.getCol();
		return x < n && x >= 0 && y < m && y >= 0; // RETURN: inside the image
	}

	public static void main(String[] args) {
		List<List<Integer>> image1 = new ArrayList<>();
		image1.add(asList(0, 1, 3, 4, 1));
		image1.add(asList(3, 8, 8, 3, 3));
		image1.add(asList(6, 7, 8, 8, 3));
		image1.add(asList(12, 2, 8, 9, 1));
		image1.add(asList(12, 3, 1, 3, 2));
		print(floodFill(2, 2, 9, image1)); // [0, 1, 3, 4, 1] [3, 9, 9, 3, 3] [6, 7, 9, 9, 3] [12, 2, 9, 9, 1] [12, 3, 1, 3, 2]

		List<List<Integer>> image2 = new ArrayList<>();
		image2.add(asList(0, 1, 6, 4));
		image2.add(asList(2, 3, 3, 5));
		image2.add(asList(3, 3, 3, 3));
		image2.add(asList(6, 4, 3, 4));
		print(floodFill(1, 1, 9, image2)); // [0, 1, 6, 4] [2, 9, 9, 5] [9, 9, 9, 9] [6, 4, 9, 4]
	}

	// one line, each row followed by a space
	private static void print(List<List<Integer>> image) {
		for (List<Integer> row : image) {
			System.out.print(row + " ");
		}
		System.out.println();
	}
}
