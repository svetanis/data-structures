package com.svetanis.datastructures.graph.unionfind;

import java.util.Arrays;

// Merging Communities (not a LeetCode problem)
//
// Input: n people numbered 0..n-1, each starting in a community of their own; then any mix of
// connect(x, y) -- the communities of x and y become one -- and size(x) -- how many people are in
// x's community. Returns the community size for each size(x) call.
//
// The one idea: a community is a group in a union-find, and one element is one person. Every
// person points at a parent; following the parents leads to the group's root, and the root is the
// only place that remembers the group's head count: size[root]. connect hangs the root of the
// smaller group under the root of the larger, so size is read at the root and the trees stay
// shallow.
//
// Time: O(log n) per call -- the smaller group always goes under the larger, so a person is at most
// log2(n) links from the root; find also compresses what it walks over.
// Space: O(n) -- parent and size, one slot per person.

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
		if (px != py) { // SKIP when they are already one community
			if (size(px) > size(py)) { // the larger root stays on top
				parent[py] = px; // JOIN: smaller under larger
				size[px] += size[py]; // ADD: the head counts
			} else {
				parent[px] = py;
				size[py] += size[px];
			}
		}
	}

	public int size(int x) {
		return size[find(x)]; // READ: the count lives at the root only
	}

	private int find(int x) {
		int y = parent[x];
		if (y != x) {
			y = find(y); // WALK up to the root
			parent[x] = y; // COMPRESS: point straight at it
		}
		return y;
	}

	public static void main(String[] args) {
		MergingCommunities ss = new MergingCommunities(5);
		ss.connect(0, 1);
		ss.connect(1, 2);
		System.out.println(ss.size(3)); // 1 -- alone
		System.out.println(ss.size(0)); // 3 -- 0, 1 and 2
		ss.connect(3, 4);
		System.out.println(ss.size(4)); // 2 -- 3 and 4
	}
}
