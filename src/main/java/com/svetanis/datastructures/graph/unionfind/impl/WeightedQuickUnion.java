package com.svetanis.datastructures.graph.unionfind.impl;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;

public final class WeightedQuickUnion {
  // depth of any node in forest built by
  // weighted quick-union for N sites
  // is at most lg N

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
    // make smaller root point to larger one
    if (size[rootP] < size[rootQ]) {
      parent[rootP] = rootQ;
      size[rootQ] += size[rootP];
    } else {
      parent[rootQ] = rootP;
      size[rootP] += size[rootQ];
    }
    count--;
  }

  public static void main(String[] args) {
    int n = 10;
    WeightedQuickUnion uf = new WeightedQuickUnion(n);
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
