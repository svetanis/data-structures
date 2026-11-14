package com.svetanis.datastructures.graph.unionfind.impl;

import java.util.Arrays;

// Union-find with union by rank and path halving: the fourth of four union-find structures.
//
// Input: n sites numbered 0..n-1, each in a component of its own; then any mix of union(p, q) --
// join the components of p and q -- and areConnected(p, q) -- are p and q in one component.
// count() is the number of components left.
//
// The one idea: TWO ideas, not one:
//   union by RANK -- the shallower tree goes underneath. rank is an
//     upper bound on depth, not a count of nodes, which is where it
//     differs from WeightedQuickUnion's size
//   path compression -- find rewrites the links it walks over, so the
//     next find on the same node is shorter. this file compresses by
//     HALVING: parent[p] = parent[parent[p]], one extra write per step,
//     no second pass and no recursion
// once compression starts shortening paths, rank stops being an exact
// depth. that is why it is only ever compared, never read as a
// measurement.
//
// Time: find O(alpha(n)) amortised, union O(alpha(n)) amortised -- alpha is the inverse Ackermann
// function, at most 4 or 5 for any n that fits in this universe. NOT the lg n bound of
// WeightedQuickUnion, which this file improves on by flattening the path it just walked.
// Space: O(n) -- the parent and rank arrays.
//
// RUNG 4 of four, and the one to use. Siblings: QuickFind, QuickUnion, WeightedQuickUnion.
public final class UF {

  private int[] parent; // parent link (site indexed)
  // parent[i] = parent of i
  private byte[] rank; // rank of the tree for roots (an upper bound on its height)
  // rank[i] = rank of subtree rooted at i (never more than 31)
  private int count; // num of components

  public UF(int n) {
    if (n < 0) {
      throw new IllegalArgumentException();
    }
    this.count = n;
    this.parent = new int[n];
    this.rank = new byte[n];
    for (int i = 0; i < n; i++) {
      this.parent[i] = i;
      this.rank[i] = 0;
    }
  }

  public int count() {
    return count;
  }

  public boolean areConnected(int p, int q) {
    return find(p) == find(q);
  }

  private void validate(int p) {
    int N = parent.length;
    if (p < 0 || p >= N) {
      throw new IndexOutOfBoundsException();
    }
  }

  public int find(int p) {
    validate(p);
    // find component name
    while (p != parent[p]) {
      // path compression by halving
      parent[p] = parent[parent[p]]; // HALVE: point at the grandparent
      p = parent[p];
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
    // make root of smaller rank point
    // to root of larger rank
    if (rank[rootP] < rank[rootQ]) {
      parent[rootP] = rootQ; // JOIN: lower rank under higher
    } else if (rank[rootP] > rank[rootQ]) {
      parent[rootQ] = rootP;
    } else {
      parent[rootQ] = rootP;
      rank[rootP]++; // RAISE: only when equal ranks meet
    }
    count--; // COUNT: one component fewer
  }

  public static void main(String[] args) {
    int N = 10;
    UF uf = new UF(N);
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
