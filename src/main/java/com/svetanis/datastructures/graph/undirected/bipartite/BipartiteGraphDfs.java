package com.svetanis.datastructures.graph.undirected.bipartite;

import com.svetanis.datastructures.graph.undirected.Graph;

// Is the part of a graph reachable from a source vertex bipartite?
//
// Input: an undirected Graph g on the vertices 0 .. g.size() - 1, and a source vertex src. No
// statement fixes the sizes; this is not a LeetCode problem and has not been run against a judge
// (the LeetCode version, 785, is IsBipartiteDfs). Returns true when the vertices reachable from
// src can be split into two groups so that every edge joins one group to the other. Vertices
// that src cannot reach are never looked at. A self-loop makes the answer false.
//
// The one idea: colors[v] holds the group of vertex v, 0 or 1, and visited[v] says whether v has
// a group yet. Walk out from src; every neighbour of a vertex in group c has to be in group
// 1 - c. A neighbour with no group yet is given 1 - c. A neighbour that already has group c
// closes an odd-length loop (or is a self-loop), and no two-group split can exist, so the answer
// is false. If the walk ends with no clash, the groups it handed out are a valid split.
//
// Time: O(V + E) -- each vertex is entered once and each adjacency entry is read once.
// Space: O(V) -- the two arrays, plus the recursion, one call per vertex along a chain.
//
// Known limit: the recursion goes one call deeper per vertex along a chain, so a path of about
// 10,000 vertices overflows the default stack. BipartiteGraphDfsIterative is the same walk with
// an explicit stack.
//
// Siblings: BipartiteGraphDfsIterative -- same walk, explicit stack. IsBipartiteDfs and
// IsBipartiteBfs (785) take an adjacency array and colour every component, not just one.

public final class BipartiteGraphDfs {

  public static boolean isBipartite(Graph g, int src) {
    int n = g.size();
    boolean[] visited = new boolean[n + 1];
    visited[src] = true; // BASE: src is coloured first
    int[] colors = new int[n + 1];
    colors[src] = 0;
    return isBipartite(g, src, visited, colors);
  }

  private static boolean isBipartite(Graph g, int src, boolean[] visited, int[] colors) {
    for (int u : g.adj(src)) {
      if (!visited[u]) {
        visited[u] = true;
        colors[u] = 1 - colors[src]; // COLOR: the other group
        if (!isBipartite(g, u, visited, colors)) { // WALK
          return false;
        }
      } else if (colors[u] == colors[src]) { // CLASH: same group on both ends of an edge
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    Graph g = new Graph(7);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    g.addEdge(3, 4);
    g.addEdge(4, 5);
    g.addEdge(5, 6);
    g.addEdge(6, 1);

    System.out.println(isBipartite(g, 1)); // true, an even loop of six

    Graph triangle = new Graph(4);
    triangle.addEdge(1, 2);
    triangle.addEdge(2, 3);
    triangle.addEdge(3, 1);
    System.out.println(isBipartite(triangle, 1)); // false, an odd loop of three
  }
}
