package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 2013. Detect Squares

public final class DetectSquares {
	// Time Complexity: O(n)
	// Space Complexity: O(p)

	private Map<Integer, Map<Integer, Integer>> map;

	public DetectSquares() {
		this.map = new HashMap<>();
	}

	public void add(int[] point) {
		int x = point[0];
		int y = point[1];
		if (!map.containsKey(x)) {
			map.put(x, new HashMap<>());
		}
		Map<Integer, Integer> fm = map.get(x);
		fm.put(y, fm.getOrDefault(y, 0) + 1);
	}

	public int count(int[] point) {
		int x = point[0];
		int y = point[1];
		if (!map.containsKey(x)) {
			return 0;
		}
		int total = 0;
		for (int px : map.keySet()) {
			if (px == x) {
				continue;
			}
			// potential side length of the square
			int d = px - x;
			Map<Integer, Integer> xym = map.get(x);
			Map<Integer, Integer> pxm = map.get(px);
			int one = pxm.getOrDefault(y, 0);
			int two = pxm.getOrDefault(y + d, 0);
			int three = pxm.getOrDefault(y - d, 0);
			int four = xym.getOrDefault(y + d, 0);
			int five = xym.getOrDefault(y - d, 0);
			total += one * two * four;
			total += one * three * five;
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