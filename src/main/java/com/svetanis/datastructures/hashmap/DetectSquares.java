package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 2013. Detect Squares
//
// add(point) stores a point { x, y }; the same point can be added many times, and each copy
// counts. count(point) returns how many ways three stored points, one copy each, make a
// square with the query point as the fourth corner, sides parallel to the axes and area
// above 0. On LeetCode: 0 <= x, y <= 1000, at most 3000 calls to add and count together.
//
// Keep a map inside a map, x -> (y -> copies), and walk only the x values. A square with the
// query (x, y) as one corner has its opposite side on another column px, so the side length
// is |px - x|, and that fixes the square up to one choice: one above the query row and one
// below. With d = px - x, one has corners (px, y), (px, y + d), (x, y + d) and the other uses
// y - d instead; which of the two is above depends only on the sign of d, so the same two
// lookups cover columns to the right and to the left. A corner never added reads as 0
// copies, and the copies at the three corners multiply. The query's own column is skipped:
// px == x would be a square of side 0.
//
// Siblings, same problem:
//   DetectSquaresRecord  one map from a Point record to copies; walks every distinct stored
//                        point as the diagonal corner, whose other corners are (px, y), (x, py)

public final class DetectSquares {
	// Time complexity: add O(1); count O(X), X = distinct x values stored -- five lookups per
	// column, whatever the number of points in it
	// Auxiliary Space: O(p), p = distinct points stored

	private Map<Integer, Map<Integer, Integer>> map; // POINTS: x -> (y -> copies)

	public DetectSquares() {
		this.map = new HashMap<>();
	}

	public void add(int[] point) {
		int x = point[0];
		int y = point[1];
		if (!map.containsKey(x)) {
			map.put(x, new HashMap<>()); // COLUMN: the first point with this x
		}
		Map<Integer, Integer> fm = map.get(x);
		fm.put(y, fm.getOrDefault(y, 0) + 1); // COUNT: one more copy
	}

	public int count(int[] point) {
		int x = point[0];
		int y = point[1];
		if (!map.containsKey(x)) {
			return 0; // EMPTY: no point shares the query's column, so no corner above or below it
		}
		int total = 0;
		for (int px : map.keySet()) {
			if (px == x) {
				continue; // SQUARE?: the same column would give side 0
			}
			// potential side length of the square
			int d = px - x; // SIDE: signed; negative for a column to the left
			Map<Integer, Integer> xym = map.get(x);
			Map<Integer, Integer> pxm = map.get(px);
			int one = pxm.getOrDefault(y, 0); // (px, y): beside the query, in both squares
			int two = pxm.getOrDefault(y + d, 0); // (px, y + d): the diagonal, square on the y + d side
			int three = pxm.getOrDefault(y - d, 0); // (px, y - d): the diagonal, square on the y - d side
			int four = xym.getOrDefault(y + d, 0); // (x, y + d): same column as the query
			int five = xym.getOrDefault(y - d, 0); // (x, y - d): same column as the query
			total += one * two * four; // ADD: the y + d square, copies multiply
			total += one * three * five; // ADD: the y - d square
		}
		return total;
	}

	public static void main(String[] args) {
		int[] p1 = { 3, 10 };
		int[] p2 = { 11, 2 };
		int[] p3 = { 3, 2 };
		int[] p4 = { 11, 10 };
		int[] p5 = { 14, 8 };
		int[] p6 = { 11, 2 };
		int[] p7 = { 11, 10 };

		DetectSquares ds = new DetectSquares();
		ds.add(p1);
		ds.add(p2);
		ds.add(p3);
		System.out.println(ds.count(p4)); // 1
		System.out.println(ds.count(p5)); // 0
		ds.add(p6);
		System.out.println(ds.count(p7)); // 2
	}
}