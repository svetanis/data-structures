package com.svetanis.datastructures.graph.unionfind;

// Disjoint Set Union over the node ids 0..n-1.
// parent + size, union by size, and find compressing the node it was asked about.
// Derived in pages/20-union-find/the-parent-array-from-scratch.md.
//
// It carries nothing problem-specific. A problem with string keys maps them to ids
// first (MergeUserAccountsByEmail); one whose pointer carries a ratio needs a different
// structure, not a setting -- see WeightedDisjointSet.
//
// impl/QuickFind, impl/QuickUnion, impl/WeightedQuickUnion and impl/UF are deliberately
// NOT this class: they are the four rungs, each missing one of the repairs here.

public final class DisjointSet {
	// Time Complexity: O(log n) per operation, amortised lower as the trees flatten
	// Space Complexity: O(n)

	private final int[] parent;
	// size[root] is the NUMBER OF ELEMENTS in that group, and it is read and written
	// AT ROOTS ONLY -- size[find(x)], never size[x].
	// The alternative is union by RANK, which stores an upper bound on the tree's
	// HEIGHT instead. Both keep find short and neither is measurably faster, but
	// they are not interchangeable to read: rank is not a count of anything, so
	// there is no rank version of size(x). See MinCostToConnectAllPointsKruskalSubmit
	// for the rank spelling, and pages/20-union-find/the-four-rungs.md trap 2.
	private final int[] size;

	// everyone starts alone
	public DisjointSet(int n) {
		this.parent = new int[n];
		this.size = new int[n];
		for (int i = 0; i < n; i++) {
			parent[i] = i;
			size[i] = 1;
		}
	}

	// true if they were in different groups and are now joined,
	// false if they were already together. The caller counts with it:
	// start at n and drop one per true -- see NumberOfConnectedComponents323.
	public boolean union(int a, int b) {
		int pa = find(a);
		int pb = find(b);
		if (pa == pb) {
			return false;
		}
		// the smaller tree goes underneath, which is what keeps find short.
		// the update is UNCONDITIONAL -- every merge changes the element count.
		// (union by rank updates only when the two ranks were equal.)
		if (size[pa] > size[pb]) {
			parent[pb] = pa;
			size[pa] += size[pb];
		} else {
			parent[pa] = pb;
			size[pb] += size[pa];
		}
		return true;
	}

	// iterative, so depth can never reach the call stack
	public int find(int x) {
		while (parent[x] != x) {
			// path halving: point at the grandparent on the way up. Every node on the
			// route gets shortened, not just the one that was asked about, and it needs
			// no second pass down.
			parent[x] = parent[parent[x]];
			x = parent[x];
		}
		return x;
	}

	public boolean sameGroup(int a, int b) {
		return find(a) == find(b);
	}

	// how many nodes are in the group containing x
	public int size(int x) {
		return size[find(x)];
	}


	public static void main(String[] args) {
		DisjointSet ds = new DisjointSet(5);
		System.out.println(ds.union(0, 1)); // true
		System.out.println(ds.union(1, 2)); // true
		System.out.println(ds.union(0, 2)); // false -- already together, so this edge is a cycle
		System.out.println(ds.size(2)); // 3
		System.out.println(ds.size(3)); // 1
		System.out.println(ds.sameGroup(0, 2)); // true
		System.out.println(ds.sameGroup(0, 3)); // false
	}
}
