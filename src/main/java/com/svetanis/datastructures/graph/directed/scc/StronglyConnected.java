package com.svetanis.datastructures.graph.directed.scc;

import static com.svetanis.datastructures.graph.directed.scc.TransposeGraph.transpose;
import static com.svetanis.datastructures.graph.directed.traverse.DFS.dfs;
import static java.util.Arrays.fill;

import com.svetanis.datastructures.graph.directed.Graph;

// Is a directed graph strongly connected? (two depth-first walks)
//
// Input: g -- a directed graph on the vertices 0 .. n - 1, n >= 1. A directed graph is strongly
// connected if every vertex can be reached from every other vertex by following edges. Returns:
// true if it is.
//
// The one idea: if vertex 0 reaches every vertex, and every vertex reaches vertex 0, then any
// vertex a reaches any vertex b by going through 0. And that is also necessary. "Vertex 0 reaches
// every vertex" is one walk from 0. "Every vertex reaches 0" is one walk from 0 in the graph with
// every edge turned around (Kosaraju's idea).
//
// Time: O(V + E) -- two walks and one reversal.
// Space: O(V + E) -- the reversed graph, the marks and the recursion, up to V deep.
//
// Siblings: SccKosarajuBfs is the same test with queues instead of recursion; SccKosarajuDfs prints
// the components.

public final class StronglyConnected {

  public static boolean isStronglyConnected(Graph g) {

    int size = g.size();
    boolean[] visited = new boolean[size];

    // FORWARD: what 0 reaches
    dfs(g, 0, visited);

    // if the walk doesn't reach
    // all vertices, then return false
    if (!isVisited(visited)) {
      return false;
    }

    // create a reversed graph
    Graph reversed = transpose(g);

    // clear the marks for the second walk
    fill(visited, false);

    // BACKWARD: what reaches 0 -- the same start vertex as the first walk
    dfs(reversed, 0, visited);

    // if the second walk doesn't reach
    // all vertices, then return false
    return isVisited(visited);
  }

  public static boolean isVisited(boolean[] visited) {
    for (int i = 0; i < visited.length; ++i) {
      if (!visited[i]) {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    Graph g = new Graph(5);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    g.addEdge(3, 0);
    g.addEdge(2, 4);
    g.addEdge(4, 2);
    System.out.println(isStronglyConnected(g)); // true

    Graph g2 = new Graph(4);
    g2.addEdge(0, 1);
    g2.addEdge(1, 2);
    g2.addEdge(2, 3);
    System.out.println(isStronglyConnected(g2)); // false -- 0 is not reachable from 3
  }
}
