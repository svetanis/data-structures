package com.svetanis.datastructures.graph.unionfind;

import java.util.Arrays;

public final class MergingCommunities {

	private int[] parent;
	private int[] size;

	public MergingCommunities(int n) {
		this.parent = new int[n];
		this.size = new int[n];
		Arrays.fill(size, 1);
		for (int i = 0; i < n; i++) {
			this.parent[i] = i;
		}
	}

	public void connect(int x, int y) {
		int px = find(x);
		int py = find(y);
		if (px != py) {
			if (size(px) > size(py)) {
				parent[py] = px;
				size[px] += size[py];
			} else {
				parent[px] = py;
				size[py] += size[px];
			}
		}
	}

	public int size(int x) {
		return size[find(x)];
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
		MergingCommunities ss = new MergingCommunities(5);
		ss.connect(0, 1);
		ss.connect(1, 2);
		System.out.println(ss.size(3)); // 1
		System.out.println(ss.size(0)); // 3
		ss.connect(3, 4);
		System.out.println(ss.size(4)); // 2
	}
}
