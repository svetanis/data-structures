package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 554. Brick Wall

public final class BrickWall {

	public static int leastBricks(List<List<Integer>> wall) {
		int max = 0;
		Map<Integer, Integer> map = new HashMap<>();
		for (List<Integer> row : wall) {
			int width = 0;
			for (int i = 0; i < row.size() - 1; i++) {
				width += row.get(i);
				int freq = map.getOrDefault(width, 0) + 1;
				map.put(width, freq);
				max = Math.max(max, freq);
			}
		}
		return wall.size() - max;
	}

	public static int leastBricks2(List<List<Integer>> wall) {
		Map<Integer, Integer> map = new HashMap<>();
		for (List<Integer> row : wall) {
			int width = 0;
			for (int i = 0; i < row.size() - 1; i++) {
				width += row.get(i);
				map.merge(width, 1, Integer::sum);
			}
		}
		int max = map.values().stream().max(Comparator.naturalOrder()).orElse(0);
		return wall.size() - max;
	}

	public static void main(String[] args) {
		List<List<Integer>> list = new ArrayList<>();
		list.add(Arrays.asList(1, 2, 2, 1));
		list.add(Arrays.asList(3, 1, 2));
		list.add(Arrays.asList(1, 3, 2));
		list.add(Arrays.asList(2, 4));
		list.add(Arrays.asList(3, 1, 2));
		list.add(Arrays.asList(1, 3, 1, 1));
		System.out.println(leastBricks(list)); // 2

		List<List<Integer>> list2 = new ArrayList<>();
		list2.add(Arrays.asList(1));
		list2.add(Arrays.asList(1));
		list2.add(Arrays.asList(1));
		System.out.println(leastBricks(list2)); // 3
	}
}