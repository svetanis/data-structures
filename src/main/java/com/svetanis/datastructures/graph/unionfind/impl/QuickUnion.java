package com.svetanis.datastructures.graph.unionfind.impl;

import java.util.Arrays;

// Quick-union: the second of four union-find structures.
//
// Input: n sites numbered 0..n-1, each in a component of its own; then any mix of union(p, q) --
// join the components of p and q -- and areConnected(p, q) -- are p and q in one component.
// count() is the number of components left.
//
// The one idea: parent[i] is a LINK, not an answer: a component is named by the root you reach by
// following the links. union becomes a single write -- hang one root under the other -- and the
// whole cost moves into find.
//
// Time: find O(depth), worst case O(n); union O(depth), worst case O(n). find walks to the root, so
// both cost the DEPTH of the tree. Nothing here keeps that depth down: union(i, i+1) for
// i = 0..n-2 re-roots every time and builds a chain of length n-1, which is what
// WeightedQuickUnion exists to prevent.
// Space: O(n) -- the parent array.
//
// RUNG 2 of four. Siblings: QuickFind (labels instead of links), WeightedQuickUnion (the smaller
// tree goes under the larger) and UF (weighted, with path halving).
public final class QuickUnion {

  private int[] parent;
  private int count; // num of components

  public QuickUnion(int N) {
    this.count = N;
    this.parent = new int[N];
    for (int i = 0; i < N; i++) {
      this.parent[i] = i;
    }
  }

  public int count() {
    return count;
  }

  public boolean areConnected(int p, int q) {
    return find(p) == find(q);
  }

  public int find(int p) {
    validate(p);
    // find component name
    while (p != parent[p]) {
      p = parent[p]; // WALK up to the root
    }
    return p;
  }

  private void validate(int p) {
    int N = parent.length;
    if (p < 0 || p >= N) {
      throw new IndexOutOfBoundsException();
    }
  }

  public void union(int p, int q) {
    // put p and q into the same components
    int rootP = find(p);
    int rootQ = find(q);
    if (rootP == rootQ) {
      return;
    }
    parent[rootP] = rootQ; // JOIN: p's root under q's root
    count--; // COUNT: one component fewer
  }

  public static void main(String[] args) {
    int n = 10;
    QuickUnion uf = new QuickUnion(n);
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
