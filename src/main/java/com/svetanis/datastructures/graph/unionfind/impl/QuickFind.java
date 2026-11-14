package com.svetanis.datastructures.graph.unionfind.impl;

import java.util.Arrays;

// Quick-find: the first of four union-find structures.
//
// Input: n sites numbered 0..n-1, each in a component of its own; then any mix of union(p, q) --
// join the components of p and q -- and areConnected(p, q) -- are p and q in one component.
// count() is the number of components left.
//
// The one idea: id[i] IS the component label of site i, so find is one array read -- and that is
// exactly why union is expensive: to merge two components, every member of one of them has to be
// relabelled, which means scanning the whole array.
//
// Time: find O(1); union O(n) -- scans the whole array; n unions therefore cost O(n^2), which is
// the reason the next rung exists.
// Space: O(n) -- the id array.
//
// RUNG 1 of four. Siblings: QuickUnion (links instead of labels), WeightedQuickUnion (the smaller
// tree goes under the larger) and UF (weighted, with path halving).
public final class QuickFind {

  private int[] id;
  private int count; // num of components

  public QuickFind(int n) {
    this.count = n;
    this.id = new int[n];
    for (int i = 0; i < n; i++) {
      this.id[i] = i;
    }
  }

  public int count() {
    return count;
  }

  public boolean areConnected(int p, int q) {
    validate(p);
    validate(q);
    return find(p) == find(q);
  }

  public int find(int p) {
    validate(p);
    return id[p];
  }

  private void validate(int p) {
    int n = id.length;
    if (p < 0 || p >= n) {
      throw new IndexOutOfBoundsException();
    }
  }

  public void union(int p, int q) {
    validate(p);
    validate(q);

    // put p and q into the same components
    int pId = find(p);
    int qId = find(q);

    if (pId == qId) {
      return;
    }
    // rename p's component to q's name
    for (int i = 0; i < id.length; i++) {
      if (id[i] == pId) {
        id[i] = qId; // RELABEL: every member of p's component
      }
    }
    count--; // COUNT: one component fewer
  }

  public static void main(String[] args) {
    int n = 10;
    QuickFind uf = new QuickFind(n);
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
