package com.svetanis.datastructures.graph.directed.traverse;

import java.util.ArrayDeque;
import java.util.Queue;

import com.svetanis.datastructures.graph.directed.Graph;

// Breadth-first traversal of a whole directed graph, including the parts no single start reaches.
//
// Input: g -- a directed graph on the vertices 0 .. n - 1. Returns: nothing; prints every vertex
// once, one line for each fresh start: the start vertex, then what it newly reaches nearest first.
//
// The one idea: one breadth-first walk (see BFS) only covers what its start can reach. To cover the
// whole graph, try the vertices in order and start a new walk from each vertex no earlier walk has
// met. The marks are shared by all the walks, so a vertex met by an earlier walk is not printed
// again.
//
// Time: O(V + E) -- each vertex is queued once over all the walks and each edge is followed once.
// Space: O(V) -- the marks and the queue.
//
// Siblings: BFS is a single walk and returns the list; DFS.dfs is the same whole-graph sweep done
// depth first.

public final class BFSDisconnected {

  public static void bfs(Graph g) {
    int size = g.size();
    boolean[] visited = new boolean[size];
    for (int v = 0; v < size; v++) {
      if (!visited[v]) { // START a new walk from a vertex no earlier walk met
        explore(g, v, visited);
        System.out.println();
      }
    }
  }

  // one walk, sharing the marks of all the walks
  private static void explore(Graph g, int src, boolean[] visited) {
    visited[src] = true; // MARK on entry
    Queue<Integer> queue = new ArrayDeque<>();
    queue.offer(src);
    while (!queue.isEmpty()) {
      int v = queue.poll();
      System.out.printf("%d ", v); // PRINT in order of distance
      for (int u : g.adj(v)) {
        if (!visited[u]) { // SKIP a vertex already met
          visited[u] = true;
          queue.offer(u);
        }
      }
    }
  }

  public static void main(String[] args) {
    // two pieces: 0 - 1 - 4 - 3 - 0 with 2 hanging off 0, and the triangle 5 - 6 - 7;
    // every edge is in both directions
    Graph g = new Graph(8);
    g.addEdge(0, 1);
    g.addEdge(1, 0);
    g.addEdge(0, 2);
    g.addEdge(2, 0);
    g.addEdge(0, 3);
    g.addEdge(3, 0);
    g.addEdge(1, 4);
    g.addEdge(4, 1);
    g.addEdge(3, 4);
    g.addEdge(4, 3);
    g.addEdge(5, 6);
    g.addEdge(6, 5);
    g.addEdge(5, 7);
    g.addEdge(7, 5);
    g.addEdge(6, 7);
    g.addEdge(7, 6);
    // one line per start:
    // 0 1 2 3 4
    // 5 6 7
    bfs(g);

    Graph graph = new Graph(4);
    graph.addEdge(0, 1);
    graph.addEdge(0, 2);
    graph.addEdge(1, 2);
    graph.addEdge(2, 0);
    graph.addEdge(2, 3);
    graph.addEdge(3, 3);
    // a single start reaches everything:
    // 0 1 2 3
    bfs(graph);
  }
}
