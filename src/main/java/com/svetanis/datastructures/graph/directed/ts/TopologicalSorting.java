package com.svetanis.datastructures.graph.directed.ts;

import static com.svetanis.java.base.Exceptions.illegalArgument;
import static com.svetanis.java.base.utils.Arrays.toList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.Stack;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.graph.directed.Graph;

// Topological sorting for Directed Acyclic Graph (DAG) is a linear ordering of vertices 
// such that for every directed edge uv, vertex u comes before v in the ordering. 
// Topological Sorting for a graph is not possible if the graph is not a DAG.

public final class TopologicalSorting {

  // the three colours. A vertex is unseen, or on the path being walked
  // right now, or finished -- and a plain boolean can only say the first
  private static final int ON_PATH = 1;
  private static final int FINISHED = 2;

  public static ImmutableList<Integer> topologicalSort(Graph g) {
    int size = g.size();
    int[] colour = new int[size];
    Stack<Integer> stack = new Stack<>();
    for (int v = 0; v < size; ++v) {
      if (!fillStackOrDetectCycle(g, v, colour, stack)) {
        // the same contract as the sibling TopologicalSortingKahn.
        // a cyclic graph has no topological order, so there is nothing
        // honest to return -- the header comment above says so
        throw illegalArgument("cyclic graph");
      }
    }
    return toList(stack);
  }

  // false as soon as it steps into a vertex that is already on the current
  // recursion path. That edge points backwards along the path, and an edge
  // pointing backwards along a path IS the cycle. Only the third colour can
  // see it: with one boolean, a vertex reached twice looks the same whether
  // it is above me on the path or was finished by an earlier root
  private static boolean fillStackOrDetectCycle(Graph g, int v, int[] colour,
      Stack<Integer> stack) {
    if (colour[v] == ON_PATH) {
      return false;
    }
    if (colour[v] == FINISHED) {
      return true;
    }
    colour[v] = ON_PATH;
    for (int u : g.adj(v)) {
      if (!fillStackOrDetectCycle(g, u, colour, stack)) {
        return false;
      }
    }
    colour[v] = FINISHED;
    stack.push(v);
    return true;
  }

  // The two fillStack methods below are the plain finish-order fill, with no
  // cycle check, and that is deliberate -- they are not the version above with
  // the check left out. SccKosarajuDfs calls fillStack(g, visited, stack) for
  // its first pass, and a graph with strongly connected components is cyclic by
  // definition: rejecting cycles there would reject the only input Kosaraju's
  // algorithm exists to handle
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
    print(topologicalSort(g)); // 5 4 2 3 1 0
  }
}
