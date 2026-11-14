package com.svetanis.datastructures.graph.undirected.traverse;

import com.svetanis.datastructures.graph.undirected.Graph;

// Count the vertices that cannot be reached from a given vertex.
//
// Input: a Graph g on the vertices 0 .. g.size() - 1 and a source vertex src. No statement fixes
// the sizes; this is not a LeetCode problem and has not been run against a judge. Returns the
// number of vertices with no path from src. src itself is reachable, so it is not counted.
//
// The one idea: the vertices that can be reached from src are exactly the ones a walk out from
// src marks, so mark them and count the unmarked ones. visited[v] says whether the walk has
// reached v.
//
// Time: O(V + E) -- the walk enters each reachable vertex once and reads each adjacency entry
// once; counting the unmarked vertices is one more pass of V.
// Space: O(V) -- the visited flags, plus the recursion, one call per vertex along a chain.
//
// Known limit: the recursion goes one call deeper per vertex along a chain, so a path of about
// 10,000 vertices overflows the default stack. CountNonReachableNodesIterative is the same count
// with an explicit stack.
//
// Siblings: CountNonReachableNodesIterative -- same count, explicit stack.

public final class CountNonReachableNodes {

  public static int countNonReachable(Graph g, int src) {
    int n = g.size();
    int count = 0;
    boolean[] visited = new boolean[n];
    explore(g, src, visited); // WALK: mark everything src can reach
    for (int v = 0; v < n; ++v) {
      if (!visited[v]) {
        count++; // COUNT: no walk reached v
      }
    }
    return count;
  }

  private static void explore(Graph g, int v, boolean[] visited) {
    visited[v] = true;
    for (int u : g.adj(v)) {
      if (!visited[u]) {
        explore(g, u, visited);
      }
    }
  }

  public static void main(String[] args) {
    Graph g = new Graph(8);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 2);
    g.addEdge(3, 4);
    g.addEdge(4, 5);
    g.addEdge(6, 7);

    // from 2 the walk reaches 0, 1 and 2; 3, 4, 5, 6 and 7 are left
    System.out.println(countNonReachable(g, 2)); // 5
  }
}
