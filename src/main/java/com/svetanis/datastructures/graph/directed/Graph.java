package com.svetanis.datastructures.graph.directed;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableList;

@SuppressWarnings("unchecked")
public final class Graph {

  public List<Integer>[] edges;
  public int v;

  public Graph(int v) {
    this.v = v;
    this.edges = new List[v];
    for (int i = 0; i < v; ++i) {
      this.edges[i] = newLinkedList();
    }
  }

  public int size() {
    return v;
  }

  public void addEdge(int i, int j) {
    edges[i].add(j);
  }

  public void removeEdge(int i, int j) {
    if (isValid(i, j)) {
      Iterator<Integer> iter = edges[i].iterator();
      while (iter.hasNext()) {
        if (iter.next() == j) {
          iter.remove();
          return;
        }
      }
    }
  }

  public boolean hasEdge(int i, int j) {
    if (isValid(i, j)) {
      return edges[i].contains(j);
    } else {
      return false;
    }
  }

  public List<Integer> adj(int i) {
    return edges[i];
  }

  public ImmutableList<Integer> inEdges(int i) {
    List<Integer> list = newArrayList();
    for (int j = 0; j < v; ++j) {
      if (edges[j].contains(i)) {
        list.add(j);
      }
    }
    return newList(list);
  }

  public List<Integer> outEdges(int i) {
    return edges[i];
  }

  private boolean isValid(int i, int j) {
    return i >= 0 && i < v && j > 0 && j < v;
  }

  public void print() {
    for (int i = 0; i < size(); ++i) {
      Iterator<Integer> iter = edges[i].iterator();
      System.out.print(i + ": ");
      while (iter.hasNext()) {
        System.out.print(iter.next() + " ");
      }
      System.out.println();
    }
  }
}
