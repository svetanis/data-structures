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
// The query is one corner, so one stored point sits DIAGONALLY opposite it, and choosing that
// point fixes the whole square. So count tries every stored point (px, py) as the diagonal.
// It is one when the box between them is square, |px - x| == |py - y|, and not empty,
// px != x. The other two corners are then (px, y) and (x, py): each takes one coordinate
// from the query and one from the diagonal. Copies multiply: 2 copies at one corner and 3 at
// another make 2 x 3 = 6 squares, and a corner never added reads as 0, so it adds nothing.
//
// The key is a record, not an int[]: two arrays holding the same numbers are different map
// keys, while two Points with the same x and y are equal. One map then answers both
// questions count asks: how many copies sit at a spot (getOrDefault), and which points to
// try as the diagonal (a walk over its entries).
//
// Siblings, same problem:
//   DetectSquares        a map inside a map, x -> (y -> copies); walks the x values only and
//                        checks the two squares of side px - x, one above and one below
//   DetectSquaresRecord  one map from Point to copies; walks every distinct stored point as
//                        the diagonal corner (this file, the version derived in study)

public final class DetectSquaresRecord {
	// Time complexity: add O(1); count O(P), P = distinct points stored -- one walk, two
	// lookups per point that passes the square test
	// Auxiliary Space: O(P), one map entry per distinct point

	private record Point(int x, int y) {} // compares by x and y: equal points share a key

	private final Map<Point, Integer> map = new HashMap<>(); // POINTS: point -> copies added

	public void add(int[] point) {
		map.merge(new Point(point[0], point[1]), 1, Integer::sum); // COUNT: one more copy
	}

	public int count(int[] point) {
		int x = point[0];
		int y = point[1];
		int total = 0;
		for (Map.Entry<Point, Integer> e : map.entrySet()) { // DIAGONAL: each stored point in turn
			int px = e.getKey().x();
			int py = e.getKey().y();
			if (px == x || Math.abs(px - x) != Math.abs(py - y)) {
				continue; // SQUARE?: equal sides, and area above 0
			}
			total += e.getValue() // ADD: copies at the diagonal
					* map.getOrDefault(new Point(px, y), 0) // x copies at (px, y)
					* map.getOrDefault(new Point(x, py), 0); // x copies at (x, py); 0 if never added
		}
		return total;
	}

	public static void main(String[] args) {
		DetectSquaresRecord ds = new DetectSquaresRecord();
		ds.add(new int[] { 3, 10 });
		ds.add(new int[] { 11, 2 });
		ds.add(new int[] { 3, 2 });
		System.out.println(ds.count(new int[] { 11, 10 })); // 1
		System.out.println(ds.count(new int[] { 14, 8 })); // 0
		ds.add(new int[] { 11, 2 }); // a duplicate counts as another point
		System.out.println(ds.count(new int[] { 11, 10 })); // 2
	}
}
