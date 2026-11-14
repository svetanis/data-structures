package com.svetanis.datastructures.graph.directed.scc;

import static com.svetanis.datastructures.graph.directed.scc.StronglyConnected.isVisited;
import static com.svetanis.datastructures.graph.directed.scc.TransposeGraph.transpose;
import static java.util.Arrays.fill;

import java.util.ArrayDeque;
import java.util.Queue;

import com.svetanis.datastructures.graph.directed.Graph;

// Is a directed graph strongly connected? (two breadth-first walks)
//
// Input: g -- a directed graph on the vertices 0 .. n - 1, n >= 1. Returns: true if every vertex
// can be reached from every other vertex by following edges.
//
// The one idea: if vertex 0 reaches every vertex, and every vertex reaches vertex 0, then any
// vertex a reaches any vertex b by going through 0. And that is also necessary. "Vertex 0 reaches
// every vertex" is one walk from 0. "Every vertex reaches 0" is one walk from 0 in the graph with
// every edge turned around.
//
// Time: O(V + E) -- two walks and one reversal.
// Space: O(V + E) -- the reversed graph, the marks and the queue.
//
// Siblings: StronglyConnected is the same test with recursive walks; SccKosarajuDfs goes on to list
// the components when the graph is not strongly connected.

public final class SccKosarajuBfs {

  public static boolean scc(Graph g) {

    int size = g.size();
    boolean[] visited = new boolean[size];

    bfs(g, 0, visited); // FORWARD: what 0 reaches

    boolean isVisited = isVisited(visited);

    // create a reversed graph
    Graph reversed = transpose(g);

    // clear the marks for the second walk
    fill(visited, false);

    bfs(reversed, 0, visited); // BACKWARD: what reaches 0

    return isVisited && isVisited(visited);
  }

  private static void bfs(Graph g, int src, boolean[] visited) {
    visited[src] = true;
    Queue<Integer> queue = new ArrayDeque<>();
    queue.offer(src);
    while (!queue.isEmpty()) {
      int v = queue.poll();
      for (int u : g.adj(v)) {
        if (!visited[u]) { // MARK on entry, so u is queued once
          visited[u] = true;
          queue.offer(u);
        }
      }
    }
  }

  public static void main(String[] args) {
    Graph g1 = new Graph(5);
    g1.addEdge(0, 1);
    g1.addEdge(1, 2);
    g1.addEdge(2, 3);
    g1.addEdge(3, 0);
    g1.addEdge(2, 4);
    g1.addEdge(4, 2);
    System.out.println(scc(g1)); // true

    Graph g2 = new Graph(4);
    g2.addEdge(0, 1);
    g2.addEdge(1, 2);
    g2.addEdge(2, 3);
    System.out.println(scc(g2)); // false -- 0 is not reachable from 3
  }
}
