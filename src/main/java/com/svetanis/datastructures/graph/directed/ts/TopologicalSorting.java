package com.svetanis.datastructures.graph.directed.ts;

import static com.svetanis.java.base.utils.Arrays.toList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.Stack;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.graph.directed.Graph;

// Topological sorting for Directed Acyclic Graph (DAG) is a linear ordering of vertices 
// such that for every directed edge uv, vertex u comes before v in the ordering. 
// Topological Sorting for a graph is not possible if the graph is not a DAG.

public final class TopologicalSorting {

  public static ImmutableList<Integer> topologicalSort(Graph g) {
    int size = g.size();
    boolean[] visited = new boolean[size];
    Stack<Integer> stack = new Stack<>();
    fillStack(g, visited, stack);
    return toList(stack);
  }

  public static void fillStack(Graph g, boolean[] visited, Stack<Integer> stack) {
    int size = g.size();
    for (int v = 0; v < size; ++v) {
      if (!visited[v]) {
        fillStack(g, v, visited, stack);
      }
    }
  }

  public static void fillStack(Graph g, int v, boolean[] visited, Stack<Integer> stack) {
    // this is basically a topological sort recursive function

    // mark the current node as visited
    visited[v] = true;

    // recur for all the vertices
    // adjacent to this vertex
    for (int u : g.adj(v)) {
      if (!visited[u]) {
        fillStack(g, u, visited, stack);
      }
    }

    // push current vertex to
    // stack which stores result
    stack.push(v);
  }

  public static void main(String[] args) {
    Graph g = new Graph(6);
    g.addEdge(5, 2);
    g.addEdge(5, 0);
    g.addEdge(4, 0);
    g.addEdge(4, 1);
    g.addEdge(2, 3);
    g.addEdge(3, 1);
    print(topologicalSort(g));
  }
}
