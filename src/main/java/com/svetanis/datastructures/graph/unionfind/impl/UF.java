package com.svetanis.datastructures.graph.unionfind.impl;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;

public final class UF {
  // depth of any node in forest built by
  // weighted quick-union for N sites
  // is at most lg N

  private int[] parent; // parent link (site indexed)
  // parent[i] = parent of i
  private byte[] rank; // size of component for roots
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
      parent[p] = parent[parent[p]];
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
      parent[rootP] = rootQ;
    } else if (rank[rootP] > rank[rootQ]) {
      parent[rootQ] = rootP;
    } else {
      parent[rootQ] = rootP;
      rank[rootP]++;
    }
    count--;
  }

  public static void main(String[] args) {
    int N = 10;
    UF uf = new UF(N);
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
