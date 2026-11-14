package com.svetanis.datastructures.graph.unionfind.impl;

import java.util.Arrays;

// Weighted quick-union: the third of four union-find structures.
//
// Input: n sites numbered 0..n-1, each in a component of its own; then any mix of union(p, q) --
// join the components of p and q -- and areConnected(p, q) -- are p and q in one component.
// count() is the number of components left.
//
// The one idea: the same links as QuickUnion plus one rule that keeps the trees shallow: the root
// of the SMALLER tree goes under the root of the larger, never the other way round. size[root]
// is the NUMBER OF SITES in the tree under that root. Nothing is flattened -- that is rung 4.
//
// Time: find O(log n); union O(log n). The depth of any site in a forest built by weighted
// quick-union on n sites is at most lg n -- a site's depth grows only when its tree is attached
// under a tree at least as large, and a tree can double in size at most lg n times.
// Space: O(n) -- the parent and size arrays.
//
// RUNG 3 of four. Siblings: QuickFind, QuickUnion (no size rule) and UF (rank, not size, plus
// path halving).
public final class WeightedQuickUnion {

  private int[] parent; // parent[i] = parent of i
  private int[] size; // size[i] = number of sites
  // in subtree rooted at i
  private int count; // num of components

  public WeightedQuickUnion(int n) {
    this.count = n;
    this.parent = new int[n];
    this.size = new int[n];
    for (int i = 0; i < n; i++) {
      this.parent[i] = i;
      this.size[i] = 1;
    }
  }

  public int count() {
    return count;
  }

  public boolean areConnected(int p, int q) {
    return find(p) == find(q);
  }

  private void validate(int p) {
    int n = parent.length;
    if (p < 0 || p >= n)
      throw new IndexOutOfBoundsException();
  }

  public int find(int p) {
    validate(p);
    // find component name
    while (p != parent[p]) {
      p = parent[p]; // WALK up to the root
    }
    return p;
  }

  public void union(int p, int q) {
    // put p and q into the same components
    int rootP = find(p);
    int rootQ = find(q);

    if (rootP == rootQ) {
      return;
    }
    // make smaller root point to larger one
    if (size[rootP] < size[rootQ]) {
      parent[rootP] = rootQ; // JOIN: smaller under larger
      size[rootQ] += size[rootP]; // ADD: the site counts
    } else {
      parent[rootQ] = rootP;
      size[rootP] += size[rootQ];
    }
    count--; // COUNT: one component fewer
  }

  public static void main(String[] args) {
    int n = 10;
    WeightedQuickUnion uf = new WeightedQuickUnion(n);
    int[][] pairs = init();
    for (int[] pair : pairs) {
      int p = pair[0];
      int q = pair[1];
      // ignore if connected
      if (uf.areConnected(p, q)) {
        continue; // SKIP: the pair adds nothing
      }
      // combine components
      uf.union(p, q); // JOIN the two components
      // [4, 3] [3, 8] [6, 5] [9, 4] [2, 1] [5, 0] [7, 2] [6, 1], one per line
      System.out.println(Arrays.toString(pair));
    }
    System.out.println(uf.count() + " components"); // 2 components
  }

  // the 11 pairs of a 10-site example: 8 of them join two components, 3 are already joined
  private static int[][] init() {
    return new int[][] { { 4, 3 }, { 3, 8 }, { 6, 5 }, { 9, 4 }, { 2, 1 }, { 8, 9 }, { 5, 0 }, { 7, 2 }, { 6, 1 },
        { 1, 0 }, { 6, 7 } };
  }
}
