package com.svetanis.datastructures.graph.unionfind;

// Union-Find where the parent pointer carries a RATIO rather than plain membership.
// The one structure in this package that cannot be replaced by the plain disjoint set:
// find() multiplies while it flattens, so it is a different algorithm, not a setting.
// Used by EvaluateDivisionIndexed (LC 399).

public final class WeightedDisjointSet {
	// Time Complexity: O(n) per operation worst case, near O(1) amortised with compression
	// Space Complexity: O(n)

	private final int[] parent;
	// weight[x] = the value of x divided by the value of parent[x].
	// 1.0 at the start because every node is its own parent and x / x = 1.
	private final double[] weight;

	public WeightedDisjointSet(int n) {
		this.parent = new int[n];
		this.weight = new double[n];
		for (int i = 0; i < n; i++) {
			parent[i] = i;
			weight[i] = 1.0;
		}
	}

	// a / b = ratio. hangs a's root under b's root and records the ratio between them.
	// find(a) and find(b) must run first: they are what make weight[a] mean a / root.
	public boolean union(int a, int b, double ratio) {
		int pa = find(a);
		int pb = find(b);
		if (pa == pb) {
			return false;
		}
		parent[pa] = pb;
		// a = weight[a] * pa and b = weight[b] * pb, and a / b = ratio,
		// so pa / pb = ratio * weight[b] / weight[a]
		weight[pa] = ratio * weight[b] / weight[a];
		return true;
	}

	// returns the root, and leaves weight[x] meaning x / root.
	// recursive so the path is available on the way back down: the iterative loop
	// reaches the root but discards the path, and the path is what has to be multiplied.
	// depth is bounded by the caller's input, which LC 399 caps at 40 variables.
	public int find(int x) {
		int px = parent[x];
		if (x != px) {
			parent[x] = find(px);
			// weight[x] was x / px, and the call above made weight[px] mean px / root,
			// so the product is x / root and px cancels
			weight[x] = weight[x] * weight[px];
		}
		return parent[x];
	}

	// a / b, or -1.0 when they are in different groups and no chain connects them.
	// weight[a] / weight[b] is (a / root) / (b / root); the root cancels.
	// NOT weight[pa] / weight[pb]: a root is its own parent, so those are both 1.0.
	public double ratio(int a, int b) {
		int pa = find(a);
		int pb = find(b);
		if (pa != pb) {
			return -1.0;
		}
		return weight[a] / weight[b];
	}

	// NOTE: there is deliberately no union by size here. It decides which root goes
	// underneath, and flipping that direction flips whether the stored ratio is
	// pa / pb or pb / pa -- a real source of silent error. Path compression alone
	// is enough at this problem's sizes, and compression is not optional here:
	// it is the step that updates the weights at all.
}
