package com.svetanis.datastructures.graph.undirected;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

// An undirected graph on the vertices 0 .. v - 1, kept as adjacency lists.
//
// The one idea: edges[i] is the list of the vertices joined to vertex i. addEdge(i, j) puts j in
// i's list and i in j's list, so one edge sits in two lists. Nothing is checked: a self-loop
// (i == j) sits twice in one list, and adding the same pair twice makes a parallel edge, which
// stays. degree(v) is the length of v's list, so a self-loop counts 2 and a parallel edge counts
// once per copy. The algorithms in this package assume a simple graph (no loops, no parallel
// edges) unless a header says otherwise.
//
// Time: addEdge, degree, adj and size are O(1) -- one list append or one field read; the copy
// constructor is O(V + E) -- it reads every list entry once.
// Space: O(V + E) -- one list per vertex, two entries per edge.
//
// Siblings: the Graph classes in the directed and graph packages are separate types (a directed
// edge goes in one list only; the graph package keeps a node list).

@SuppressWarnings("unchecked")
public final class Graph {

  public List<Integer>[] edges;
  public int v; // num of vertex
  public int e; // num of edges

  public Graph(int v) {
    this.v = v;
    this.e = 0;
    this.edges = new List[v];
    for (int i = 0; i < v; ++i) {
      this.edges[i] = new LinkedList<>(); // ONE EMPTY LIST per vertex
    }
  }

  // The copy has the same vertices and edges; each list comes out in reverse order, because the
  // entries are pushed on a stack and popped.
  public Graph(Graph g) {
    this.v = g.v;
    this.e = g.e;
    this.edges = new List[v]; // BASE: the lists must exist before they are filled
    for (int i = 0; i < v; i++) {
      this.edges[i] = new LinkedList<>();
      Deque<Integer> stack = new ArrayDeque<>();
      for (int u : g.edges[i]) {
        stack.push(u); // PUSH: first entry ends up at the bottom
      }
      while (!stack.isEmpty()) {
        edges[i].add(stack.pop()); // POP: last entry is copied first
      }
    }
  }

  public void addEdge(int i, int j) {
    edges[i].add(j); // RECORD: j in i's list
    edges[j].add(i); // RECORD: i in j's list
    e++;
  }

  public int size() {
    return v;
  }

  public int degree(int v) {
    return edges[v].size();
  }

  public List<Integer> adj(int v) {
    return edges[v];
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(v + " vertices, " + e + " edges " + "\n");
    for (int i = 0; i < v; i++) {
      sb.append(i + ": ");
      for (int u : edges[i]) {
        sb.append(u + " ");
      }
      sb.append("\n");
    }
    return sb.toString();
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

  public static void main(String[] args) {
    int v = 5;
    Graph g = new Graph(v);
    g.addEdge(0, 1);
    g.addEdge(0, 4);
    g.addEdge(1, 2);
    g.addEdge(1, 3);
    g.addEdge(1, 4);
    g.addEdge(2, 3);
    g.addEdge(3, 4);
    // "5 vertices, 7 edges ", then one line per vertex: "0: 1 4 ", "1: 0 2 3 4 ", "2: 1 3 ",
    // "3: 1 2 4 ", "4: 0 1 3 "
    System.out.println(g);
    Graph copy = new Graph(g);
    System.out.println(copy.adj(1)); // [4, 3, 2, 0]
    System.out.println(copy.e); // 7
  }
}
