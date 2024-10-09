package com.svetanis.datastructures.graph.floodfill;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.utils.Print.print;
import static java.util.Arrays.asList;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.graph.Coordinate;

// In computer graphics, an uncompressed 
// raster image is presented as a matrix of numbers. 
// Each entry of the matrix represents the color of a pixel. 
// A flood fill algorithm takes a coordinate r, c and 
// a replacement color, and replaces all pixels connected to r, c 
// that have the same color (i.e., the pixels connected to 
// the given coordinate with same color and all the other pixels 
// connected to the those pixels of the same color) with the replacement color. 
// (e.g. MS-Paint's paint bucket tool).

public final class FloodFillSubmit {
	// Time Complexity: O(r * c)

	private static final int[] dx = { -1, 0, 1, 0 };
	private static final int[] dy = { 0, 1, 0, -1 };

	public static List<List<Integer>> floodFill(int r, int c, int rc, List<List<Integer>> image) {
		Coordinate src = new Coordinate(r, c);
		bfs(src, rc, image);
		return image;
	}

	private static void bfs(Coordinate src, int rc, List<List<Integer>> image) {
		int n = image.size();
		int m = image.get(0).size();
		boolean[][] visited = new boolean[n][m];
		Queue<Coordinate> queue = new ArrayDeque<>();
		queue.add(src);
		int r = src.getRow();
		int c = src.getCol();
		visited[r][c] = true;
		int color = image.get(r).get(c);
		image.get(r).set(c, rc);
		while (!queue.isEmpty()) {
			Coordinate curr = queue.poll();
			List<Coordinate> neighbors = neighbors(curr, color, image);
			for (Coordinate neighbor : neighbors) {
				int row = neighbor.getRow();
				int col = neighbor.getCol();
				if (!visited[row][col]) {
					image.get(row).set(col, rc);
					queue.add(neighbor);
					visited[row][col] = true;
				}
			}
		}
	}

	private static List<Coordinate> neighbors(Coordinate curr, int color, List<List<Integer>> image) {
		List<Coordinate> list = new ArrayList<>();
		for (int i = 0; i < dx.length; i++) {
			int row = curr.getRow() + dx[i];
			int col = curr.getCol() + dy[i];
			Coordinate next = new Coordinate(row, col);
			if (valid(next, image) && image.get(row).get(col) == color) {
				list.add(next);
			}
		}
		return list;
	}

	private static boolean valid(Coordinate c, List<List<Integer>> image) {
		int n = image.size();
		int m = image.get(0).size();
		int x = c.getRow();
		int y = c.getCol();
		return x < n && x >= 0 && y < m && y >= 0;
	}

	public static void main(String[] args) {
		List<List<Integer>> image1 = newArrayList();
		image1.add(asList(0, 1, 3, 4, 1));
		image1.add(asList(3, 8, 8, 3, 3));
		image1.add(asList(6, 7, 8, 8, 3));
		image1.add(asList(12, 2, 8, 9, 1));
		image1.add(asList(12, 3, 1, 3, 2));
		print(floodFill(2, 2, 9, image1));

		List<List<Integer>> image2 = newArrayList();
		image2.add(asList(0, 1, 6, 4));
		image2.add(asList(2, 3, 3, 5));
		image2.add(asList(3, 3, 3, 3));
		image2.add(asList(6, 4, 3, 4));
		print(floodFill(1, 1, 9, image2));
	}
}
