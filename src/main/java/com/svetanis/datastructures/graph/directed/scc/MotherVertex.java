package com.svetanis.datastructures.graph.directed.scc;

import static com.svetanis.datastructures.graph.directed.traverse.DFS.dfs;
import static java.util.Arrays.fill;

import com.svetanis.datastructures.graph.directed.Graph;

// Find a mother vertex of a directed graph.
//
// Input: g -- a directed graph on the vertices 0 .. n - 1, n >= 1. A mother vertex is a vertex from
// which every other vertex can be reached by following edges. Returns: a mother vertex, or -1 if
// there is none. (If there are several, any one of them may come back.)
//
// The one idea: sweep the vertices in order 0, 1, 2, ..., and from each one not yet reached start a
// fresh walk that reaches everything it can. Remember the vertex of the LAST walk started. If a
// mother vertex exists, that last vertex is one: consider the walk that first reached any mother
// vertex m; it started at some u that reaches m, and m reaches everything, so u reaches everything
// too -- u is a mother vertex, and since that walk reached all vertices no later walk was started,
// making u the last. So there is one candidate, and a single second walk from it settles whether it
// really reaches every vertex.
//
// Time: O(V + E) -- two sweeps, each vertex and edge handled once per sweep.
// Space: O(V) -- the marks and the recursion, up to V deep.
//
// Siblings: StronglyConnected asks a stronger question (every vertex reaches every other); a graph
// that passes it has every vertex as a mother vertex.

public final class MotherVertex {

  public static int motherVertex(Graph g) {
    int size = g.size();
    boolean[] visited = new boolean[size];

    // the only candidate: the vertex of the last walk started
    int vertex = motherVertex(g, visited);

    // check whether the candidate really reaches every vertex
    fill(visited, false);
    dfs(g, vertex, visited);

    for (int i = 0; i < size; i++) {
      if (!visited[i]) {
        return -1; // RETURN: the candidate misses a vertex, so no mother vertex exists
      }
    }
    return vertex;
  }

  private static int motherVertex(Graph g, boolean[] visited) {
    int size = g.size();
    int vertex = 0;
    for (int v = 0; v < size; ++v) {
      if (!visited[v]) { // START a fresh walk from a vertex nothing so far reaches
        dfs(g, v, visited);
        vertex = v; // CANDIDATE: remember the latest start
      }
    }
    return vertex;
  }

  public static void main(String[] args) {
    Graph g = new Graph(7);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 3);
    g.addEdge(4, 1);
    g.addEdge(6, 4);
    g.addEdge(5, 6);
    g.addEdge(5, 2);
    g.addEdge(6, 0);
    System.out.println(motherVertex(g)); // 5 -- 5 -> 6 -> 4 -> 1 -> 3, 6 -> 0 -> 2

    Graph none = new Graph(3);
    none.addEdge(0, 1);
    none.addEdge(2, 1);
    System.out.println(motherVertex(none)); // -1 -- neither 0 nor 2 reaches the other
  }
}
