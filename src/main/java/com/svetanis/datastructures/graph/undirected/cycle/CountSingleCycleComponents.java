package com.svetanis.datastructures.graph.undirected.cycle;

import com.svetanis.datastructures.graph.undirected.Graph;
import java.util.ArrayList;
import java.util.List;

// Count the single-cycle components of an undirected simple graph.
//
// Input: a Graph g with n vertices and m edges, simple (no parallel edges, no self-loops). No
// statement fixes the sizes; this is not a LeetCode problem and has not been run against a
// judge. A single-cycle component is a connected component in which every vertex has degree 2,
// which in a simple graph is one cycle through all the vertices of the component. Returns how
// many there are. An isolated vertex has degree 0, so it is not one.
//
// The one idea: a component is a single cycle exactly when every vertex in it has two
// neighbours -- each vertex sits on the cycle with one edge in and one edge out, and a vertex of
// degree 1 or 3 would be a dead end or a branch. So walk each component once, collect its
// vertices, and test whether all their degrees are 2.
//
// Time: O(V + E) -- each vertex is entered once, each adjacency entry is read once, and the
// degree test reads each collected vertex once.
// Space: O(V) -- the visited flags and the vertex list of one component.
//
// Known limit: the walk is recursive, one call deeper per vertex along a chain, so a component
// of about 10,000 vertices overflows the default stack. CountSingleCycleComponentsIterative is
// the same count with an explicit stack.
//
// Siblings: CountSingleCycleComponentsIterative -- same count, explicit stack.

public final class CountSingleCycleComponents {

  public static int count(Graph g) {
    int size = g.size();
    boolean[] visited = new boolean[size];

    int count = 0;
    for (int v = 0; v < size; ++v) {
      if (!visited[v]) { // NEW COMPONENT: v is the first vertex of one not yet seen
        List<Integer> list = new ArrayList<>();
        dfs(g, v, visited, list);
        if (degreeTwo(g, list)) {
          count++; // RECORD: every vertex of the component has degree 2
        }
      }
    }
    return count;
  }

  private static boolean degreeTwo(Graph g, List<Integer> list) {
    for (int v : list) {
      if (g.degree(v) != 2) { // CLASH: a dead end or a branch
        return false;
      }
    }
    return true;
  }

  private static void dfs(Graph g, int v, boolean[] visited, List<Integer> list) {
    list.add(v); // COLLECT: v belongs to this component
    visited[v] = true;
    for (int u : g.adj(v)) {
      if (!visited[u]) {
        dfs(g, u, visited, list); // WALK
      }
    }
  }

  public static void main(String[] args) {
    Graph g = new Graph(16);
    g.addEdge(1, 10);
    g.addEdge(1, 5);
    g.addEdge(5, 10);
    g.addEdge(2, 9);
    g.addEdge(9, 15);
    g.addEdge(2, 15);
    g.addEdge(2, 12);
    g.addEdge(12, 15);
    g.addEdge(13, 8);
    g.addEdge(6, 14);
    g.addEdge(14, 3);
    g.addEdge(3, 7);
    g.addEdge(7, 11);
    g.addEdge(11, 6);

    // the triangle 1-5-10 and the loop of five 3-6-7-11-14; the piece with 2, 9, 12, 15 has a
    // vertex of degree 3 and the pair 8-13 has two of degree 1
    System.out.println(count(g)); // 2
  }
}
