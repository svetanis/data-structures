package com.svetanis.datastructures.graph.directed.ts;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.svetanis.datastructures.graph.directed.Graph;

// Topological order of a directed acyclic graph, by depth-first finishing times.
//
// Input: g -- a directed graph on the vertices 0 .. n - 1. Returns: all the vertices in a line so
// that every edge u -> v has u before v. A topological order exists only for a graph with no cycle;
// for a cyclic graph topologicalSort throws IllegalArgumentException. (Several lines may be valid;
// this returns the one the walk produces.)
//
// The one idea: walk depth first from each vertex not yet reached, and push a vertex on a stack at
// the moment its walk FINISHES -- after everything it points at has been finished and pushed. So
// every vertex is pushed after all the vertices it points at, and popping the stack gives them in
// the opposite order: every vertex before the ones it points at. A cycle shows as an edge to a
// vertex whose walk is still going, i.e. one still on the path being walked; that needs three
// states for a vertex (not reached, on the current path, finished), which is what the colour array
// holds.
//
// Time: O(V + E) -- each vertex is entered once and each edge is followed once.
// Space: O(V) -- the colours, the stack and the recursion, up to V deep.
//
// Siblings: TopologicalSortingKahn builds the order by repeatedly taking a vertex nobody points at
// and gives a different valid order; SccKosarajuDfs uses fillStack for its first walk.

public final class TopologicalSorting {

  // the three colours. A vertex is unseen, or on the path being walked
  // right now, or finished -- and a plain boolean can only say the first
  private static final int ON_PATH = 1;
  private static final int FINISHED = 2;

  public static List<Integer> topologicalSort(Graph g) {
    int size = g.size();
    int[] colour = new int[size];
    Stack<Integer> stack = new Stack<>();
    for (int v = 0; v < size; ++v) {
      if (!fillStackOrDetectCycle(g, v, colour, stack)) {
        // the same contract as the sibling TopologicalSortingKahn.
        // a cyclic graph has no topological order, so there is nothing
        // honest to return -- the header comment above says so
        throw new IllegalArgumentException("cyclic graph");
      }
    }
    List<Integer> order = new ArrayList<>();
    while (!stack.isEmpty()) {
      order.add(stack.pop()); // POP: last finished first, so every vertex precedes what it points at
    }
    return order;
  }

  // false as soon as it steps into a vertex that is already on the current
  // recursion path. That edge points backwards along the path, and an edge
  // pointing backwards along a path IS the cycle. Only the third colour can
  // see it: with one boolean, a vertex reached twice looks the same whether
  // it is above me on the path or was finished by an earlier root
  private static boolean fillStackOrDetectCycle(Graph g, int v, int[] colour,
      Stack<Integer> stack) {
    if (colour[v] == ON_PATH) { // BACK EDGE: v is on the current path, so this edge closes a cycle
      return false;
    }
    if (colour[v] == FINISHED) { // SKIP: finished by an earlier walk
      return true;
    }
    colour[v] = ON_PATH;
    for (int u : g.adj(v)) {
      if (!fillStackOrDetectCycle(g, u, colour, stack)) {
        return false;
      }
    }
    colour[v] = FINISHED;
    stack.push(v); // PUSH at the finish, after everything v points at
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
    visited[v] = true; // MARK on entry

    // recur for all the vertices
    // adjacent to this vertex
    for (int u : g.adj(v)) {
      if (!visited[u]) { // SKIP a vertex already met -- a vertex on the path is skipped too, not reported
        fillStack(g, u, visited, stack);
      }
    }

    // push current vertex to
    // stack which stores result
    stack.push(v); // PUSH at the finish
  }

  public static void main(String[] args) {
    Graph g = new Graph(6);
    g.addEdge(5, 2);
    g.addEdge(5, 0);
    g.addEdge(4, 0);
    g.addEdge(4, 1);
    g.addEdge(2, 3);
    g.addEdge(3, 1);
    System.out.println(topologicalSort(g)); // [5, 4, 2, 3, 1, 0]
  }
}
