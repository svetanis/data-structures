package com.svetanis.datastructures.graph.unionfind;

// Weighted union-find for the ratio problems, such as 399. Evaluate Division.
//
// Input: n variables numbered 0..n-1; union(a, b, ratio) records that a / b = ratio; ratio(a, b)
// asks for a / b. Returns the recorded quotient when a chain of records links the two variables,
// and -1.0 when it does not.
//
// The one idea: union-find where the parent pointer carries a RATIO rather than plain membership.
// weight[x] is x divided by its parent, so following the pointers up and multiplying the weights
// gives x divided by the root; the root cancels when two variables of one group are divided. The one
// structure in this package that cannot be replaced by the plain disjoint set: find() multiplies
// while it flattens, so it is a different algorithm, not a setting. Used by EvaluateDivisionIndexed.
//
// Time: O(n) per operation worst case, near O(1) amortised with compression -- there is no union by
// size here, on purpose (see the note at the end of the class).
// Space: O(n) -- the parent and weight arrays.

public final class WeightedDisjointSet {

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
		parent[pa] = pb; // JOIN: a's root under b's root
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
			parent[x] = find(px); // WALK up to the root first, so the path is known
			// weight[x] was x / px, and the call above made weight[px] mean px / root,
			// so the product is x / root and px cancels
			weight[x] = weight[x] * weight[px]; // COMPRESS: x now points at the root with x / root
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
		return weight[a] / weight[b]; // RETURN: (a / root) / (b / root)
	}

	// NOTE: there is deliberately no union by size here. It decides which root goes
	// underneath, and flipping that direction flips whether the stored ratio is
	// pa / pb or pb / pa -- a real source of silent error. Path compression alone
	// is enough at this problem's sizes, and compression is not optional here:
	// it is the step that updates the weights at all.
}
