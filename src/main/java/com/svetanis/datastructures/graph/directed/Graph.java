package com.svetanis.datastructures.graph.directed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

// A directed graph on the vertices 0 .. v - 1, kept as adjacency lists.
//
// What is stored: edges[i] is the list of the vertices j for which there is an edge i -> j, in the
// order the edges were added. A vertex is just its number; there is no vertex object. The same
// edge may be added twice (the list then holds j twice), and i -> i is allowed.
//
// The one idea: to walk a graph you ask one question over and over -- "where can I go from i?" --
// and edges[i] answers it by itself, in time proportional to its own length. The opposite question,
// "who points at i?", has no list of its own, so inEdges(i) has to look through every list.
//
// Time: addEdge, adj, outEdges O(1); hasEdge and removeEdge O(deg(i)); inEdges O(V + E) -- every
// list is searched. Space: O(V + E).
//
// Note: the fields are public, as on a LeetCode node class; some algorithms in the sibling
// packages read edges[i] directly.

public final class Graph {

  public List<Integer>[] edges;
  public int v;

  @SuppressWarnings("unchecked")
  public Graph(int v) {
    this.v = v;
    this.edges = new List[v];
    for (int i = 0; i < v; ++i) {
      this.edges[i] = new LinkedList<>(); // one empty list per vertex
    }
  }

  public int size() {
    return v;
  }

  public void addEdge(int i, int j) {
    edges[i].add(j); // RECORD: i -> j
  }

  // removes one copy of the edge i -> j (the first in the list); a repeated edge keeps its others
  public void removeEdge(int i, int j) {
    if (isValid(i, j)) {
      Iterator<Integer> iter = edges[i].iterator();
      while (iter.hasNext()) {
        if (iter.next() == j) { // int unboxing: compares the numbers
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
      return false; // a vertex outside 0 .. v - 1 has no edges
    }
  }

  public List<Integer> adj(int i) {
    return edges[i];
  }

  // every j with an edge j -> i, in increasing order of j
  public List<Integer> inEdges(int i) {
    List<Integer> list = new ArrayList<>();
    for (int j = 0; j < v; ++j) { // SCAN: every list, because nothing records who points at i
      if (edges[j].contains(i)) {
        list.add(j);
      }
    }
    return Collections.unmodifiableList(list);
  }

  public List<Integer> outEdges(int i) {
    return edges[i];
  }

  private boolean isValid(int i, int j) {
    return i >= 0 && i < v && j >= 0 && j < v;
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
