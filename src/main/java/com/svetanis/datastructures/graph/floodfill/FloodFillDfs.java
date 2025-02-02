package com.svetanis.datastructures.graph.floodfill;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.utils.Print.print;
import static java.util.Arrays.asList;

import java.util.List;

// In computer graphics, an uncompressed 
// raster image is presented as a matrix of numbers. 
// Each entry of the matrix represents the color of a pixel. 
// A flood fill algorithm takes a coordinate r, c and 
// a replacement color, and replaces all pixels connected to r, c 
// that have the same color (i.e., the pixels connected to 
// the given coordinate with same color and all the other pixels 
// connected to the those pixels of the same color) with the replacement color. 
// (e.g. MS-Paint's paint bucket tool).

public final class FloodFillDfs {
	// Time Complexity: O(r * c)

	public static List<List<Integer>> floodFill(int r, int c, int rc, List<List<Integer>> image) {
		int prev = image.get(r).get(c);
		dfs(r, c, rc, prev, image);
		return image;
	}

	private static void dfs(int r, int c, int rc, int prev, List<List<Integer>> image) {
		int n = image.size();
		int m = image.get(0).size();
		if (r > n - 1 || r < 0 || c > m - 1 || c < 0) {
			return;
		}
		if (image.get(r).get(c) != prev || image.get(r).get(c) == c) {
			return;
		}
		image.get(r).set(c, rc);
		dfs(r + 1, c, rc, prev, image);
		dfs(r - 1, c, rc, prev, image);
		dfs(r, c + 1, rc, prev, image);
		dfs(r, c - 1, rc, prev, image);
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
