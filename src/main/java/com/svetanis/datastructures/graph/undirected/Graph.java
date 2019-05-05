package com.svetanis.datastructures.graph.undirected;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

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
      this.edges[i] = newLinkedList();
    }
  }

  public Graph(Graph g) {
    this.v = g.v;
    this.e = g.e;
    for (int i = 0; i < v; i++) {
      Stack<Integer> stack = new Stack<Integer>();
      for (int u : g.edges[i]) {
        stack.push(u);
      }
      while (!stack.isEmpty()) {
        edges[i].add(stack.pop());
      }
    }
  }

  public void addEdge(int i, int j) {
    edges[i].add(j);
    edges[j].add(i);
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
    System.out.println(g);
  }
}
