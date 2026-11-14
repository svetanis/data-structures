package com.svetanis.datastructures.graph.unionfind;

// Same Set (not a LeetCode problem)
//
// complete the class to support
// the following operations:
// 1. merge(x, y) merges the sets
// to which x and y belong
// 2. isSame(x, y) determines if
// x and y belong to the same set
//
// The one idea: a set is a group in a union-find, and one element is one number, whatever its
// size. The groups are kept as a tree of "who points at whom"; two numbers are in the same set
// exactly when following the pointers from each ends at the same root. merge points one root at the
// other, isSame compares roots.
//
// Time: O(q * log n) -- q calls on n numbers; UnionFind compresses paths but does not pick the
// smaller tree, so a call is O(log n) amortised.
// Space: O(n) -- one map entry per number that has been merged.
//
// Siblings: SameSetSubmit is the same with the map and find inlined.

public final class SameSet {

	private UnionFind<Integer> dsu;

	public SameSet() {
		this.dsu = new UnionFind<>();
	}

	public void merge(int x, int y) {
		dsu.union(x, y); // JOIN the two sets
	}

	public boolean isSame(int x, int y) {
		return dsu.isSame(x, y); // RETURN: same root, same set
	}

	public static void main(String[] args) {
		SameSet ss = new SameSet();
		ss.merge(1, 2);
		ss.merge(2, 3);
		System.out.println(ss.isSame(1, 3)); // true -- 1, 2, 3 are one set
		System.out.println(ss.isSame(2, 4)); // false -- 4 was never merged
	}
}
