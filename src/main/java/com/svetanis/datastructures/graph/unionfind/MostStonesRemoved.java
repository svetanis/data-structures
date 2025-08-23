package com.svetanis.datastructures.graph.unionfind;

import java.util.HashSet;
import java.util.Set;

// 947. Most Stones Removed with Same Row or Column

public final class MostStonesRemoved {

	private int[] parent;

	public int removeStones(int[][] stones) {
		int n = 10010;
		init(n);
		union(n, stones);
		int components = components(stones);
		return stones.length - components;
	}

	private int components(int[][] stones) {
		Set<Integer> components = new HashSet<>();
		for (int[] stone : stones) {
			components.add(find(stone[0]));
		}
		return components.size();
	}

	private void union(int n, int[][] stones) {
		for (int[] stone : stones) {
			int row = stone[0];
			int col = stone[1];
			parent[find(row)] = find(col + n);
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
			y = find(y);
			parent[x] = y;
		}
		return y;
	}

	public static void main(String[] args) {
		int[][] g1 = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 2 }, { 2, 1 }, { 2, 2 } };
		MostStonesRemoved ldc = new MostStonesRemoved();
		System.out.println(ldc.removeStones(g1)); // 5

		int[][] g2 = { { 0, 0 }, { 0, 2 }, { 1, 1 }, { 2, 0 }, { 2, 2 } };
		System.out.println(ldc.removeStones(g2)); // 3

		int[][] g3 = { { 0, 0 } };
		System.out.println(ldc.removeStones(g3)); // 0

		int[][] g4 = { { 0, 1 }, { 1, 0 } };
		System.out.println(ldc.removeStones(g4)); // 0
	}
}
