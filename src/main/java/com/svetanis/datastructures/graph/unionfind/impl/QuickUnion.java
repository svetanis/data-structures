package com.svetanis.datastructures.graph.unionfind.impl;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;

// RUNG 2 of four. parent[i] is a LINK, not an answer: a component is
// named by the root you reach by following the links. union becomes a
// single write, and the whole cost moves into find.
public final class QuickUnion {
  // find:  O(depth), worst case O(n)
  // union: O(depth), worst case O(n)
  // find walks to the root, so both find and union cost the DEPTH of
  // the tree. nothing here keeps that depth down: union(i, i+1) for
  // i = 0..n-2 re-roots every time and builds a chain of length n-1,
  // which is what WeightedQuickUnion.java exists to prevent

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
      p = parent[p];
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
    parent[rootP] = rootQ;
    count--;
  }

  public static void main(String[] args) {
    int n = 10;
    QuickUnion uf = new QuickUnion(n);
    List<Pair<Integer, Integer>> pairs = init();
    for (Pair<Integer, Integer> pair : pairs) {
      int p = pair.getLeft();
      int q = pair.getRight();
      // ignore if connected
      if (uf.areConnected(p, q)) {
        continue;
      }
      // combine components
      uf.union(p, q);
      System.out.println(pair);
    }
    System.out.println(uf.count() + " components");
  }

  private static ImmutableList<Pair<Integer, Integer>> init() {
    List<Pair<Integer, Integer>> list = newArrayList();
    list.add(Pair.build(4, 3));
    list.add(Pair.build(3, 8));
    list.add(Pair.build(6, 5));
    list.add(Pair.build(9, 4));
    list.add(Pair.build(2, 1));
    list.add(Pair.build(8, 9));
    list.add(Pair.build(5, 0));
    list.add(Pair.build(7, 2));
    list.add(Pair.build(6, 1));
    list.add(Pair.build(1, 0));
    list.add(Pair.build(6, 7));
    return copyOf(list);
  }
}
