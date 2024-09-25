package com.svetanis.datastructures.graph.unionfind;

// complete the class to support 
// the following operations:
// 1. merge(x, y) merges the sets 
// to which x and y belong
// 2. isSame(x, y) determines if
// x and y belong to the same set

public final class SameSet {
	// Time Complexity: O(q * log n)

	private UnionFind<Integer> dsu;

	public SameSet() {
		this.dsu = new UnionFind<>();
	}

	public void merge(int x, int y) {
		dsu.union(x, y);
	}

	public boolean isSame(int x, int y) {
		return dsu.find(x) == dsu.find(y);
	}

	public static void main(String[] args) {
		SameSet ss = new SameSet();
		ss.merge(1, 2);
		ss.merge(2, 3);
		System.out.println(ss.isSame(1, 3)); // true
		System.out.println(ss.isSame(2, 4)); // false
	}
}
