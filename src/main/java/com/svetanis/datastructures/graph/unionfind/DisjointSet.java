package com.svetanis.datastructures.graph.unionfind;

// Disjoint Set Union (union-find) over the node ids 0..n-1
//
// Input: n, the number of nodes; then calls union(a, b), sameGroup(a, b), size(x) with ids in
// 0..n-1. Every node starts alone in a group of its own.
// union(a, b) joins the groups of a and b and says whether they were apart; sameGroup(a, b) says
// whether a and b are in one group; size(x) is how many nodes share x's group.
//
// The one idea: every node holds one arrow, parent[x], pointing at another node of its group; a
// node whose arrow points at itself is the ROOT, and the root names the group. To find a node's
// group, follow arrows until one points at itself. Two nodes are together exactly when they reach
// the same root. To join two groups, make one root point at the other. Two repairs keep the
// arrow chains short: the smaller group goes UNDER the larger (so a node is never more than
// log2(n) arrows from its root), and find shortens the chain it walks (path halving: each node it
// passes is re-pointed at its grandparent).
//
// It carries nothing problem-specific. A problem with string keys maps them to ids first
// (MergeUserAccountsByEmail); one whose arrow carries a ratio needs a different structure, not a
// setting -- see WeightedDisjointSet.
//
// Time: O(alpha(n)) amortised per call -- union by size plus path halving; alpha(n) is below 5 for
// any n that fits in memory.
// Space: O(n) -- the parent and size arrays.
//
// Siblings: impl/QuickFind, impl/QuickUnion, impl/WeightedQuickUnion and impl/UF are deliberately
// NOT this class: they are the four rungs, each missing one of the repairs here.

public final class DisjointSet {
	private final int[] parent; // CARDS: parent[x] -- the node above x; a root points at itself
	// size[root] is the NUMBER OF ELEMENTS in that group, and it is read and written
	// AT ROOTS ONLY -- size[find(x)], never size[x].
	// The alternative is union by RANK, which stores an upper bound on the tree's
	// HEIGHT instead. Both keep find short with the same bound, but they are not
	// interchangeable to read: rank is not a count of anything, so there is no
	// rank version of size(x). See MinCostToConnectAllPointsKruskalSubmit for the
	// rank spelling.
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
		int pa = find(a); // FIND: the two roots
		int pb = find(b);
		if (pa == pb) {
			return false; // SKIP: already together
		}
		// the smaller tree goes underneath, which is what keeps find short.
		// the update is UNCONDITIONAL -- every merge changes the element count.
		// (union by rank updates only when the two ranks were equal.)
		if (size[pa] > size[pb]) { // JOIN: the smaller group goes under the larger
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
		while (parent[x] != x) { // WALK: up to the root
			// path halving: point at the grandparent on the way up. Every node on the
			// route gets shortened, not just the one that was asked about, and it needs
			// no second pass down.
			parent[x] = parent[parent[x]];
			x = parent[x];
		}
		return x;
	}

	public boolean sameGroup(int a, int b) {
		return find(a) == find(b); // SAME ROOT == SAME GROUP
	}

	// how many nodes are in the group containing x
	public int size(int x) {
		return size[find(x)]; // READ the size at the root
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
