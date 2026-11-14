package com.svetanis.datastructures.graph.unionfind;

import java.util.HashMap;
import java.util.Map;

// Connected Component Size (a union-find that also reports group sizes)
//
// Input: calls to merge(x, y) and count(x), on any integer labels. A label never merged is a
// group of its own.
// merge(x, y) joins the groups holding x and y; count(x) returns how many labels are in the
// group holding x.
//
// The one idea: union-find answers "who is in my group?" by following parents to a root, one root
// per group. To also answer "how many?", keep a number on each ROOT only: the size of its group.
// A label with no entry has never been merged, so its group is just itself and the size is 1.
// When two different groups merge, the new size is the sum of the two sizes, read BEFORE the
// union, and it is stored under whichever root the union leaves on top.
//
// Time: O(log n) amortised per call -- find compresses paths but this UnionFind does not balance
// the trees, so n calls cost O(n log n) in total.
// Space: O(n) -- the parent map and the size map.

public final class ConnectedComponentSize {

	private UnionFind<Integer> dsu;
	private Map<Integer, Integer> map; // CARDS: map.get(root) -- labels in the group of that root

	public ConnectedComponentSize() {
		this.map = new HashMap<>();
		this.dsu = new UnionFind<>();
	}

	public void merge(int x, int y) {
		if (!dsu.isSame(x, y)) { // SKIP: already together, the size does not change
			int size = count(x) + count(y); // ADD: both sizes, read before the union
			dsu.union(x, y);
			map.put(dsu.find(x), size); // RECORD: the size lives at the new root
		}
	}

	public int count(int x) {
		return map.getOrDefault(dsu.find(x), 1); // no entry == never merged == alone
	}

	public static void main(String[] args) {
		ConnectedComponentSize ss = new ConnectedComponentSize();
		ss.merge(1, 2);
		ss.merge(2, 3);
		System.out.println(ss.count(3)); // 3
		System.out.println(ss.count(4)); // 1
	}
}
