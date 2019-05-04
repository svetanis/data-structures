package com.svetanis.datastructures.graph.unionfind.impl;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;

public final class QuickFind {
  // dynamic connectivity with
  // quick-find is O(n^2)

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
        id[i] = qId;
      }
    }
    count--;
  }

  public static void main(String[] args) {
    int n = 10;
    QuickFind uf = new QuickFind(n);
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
