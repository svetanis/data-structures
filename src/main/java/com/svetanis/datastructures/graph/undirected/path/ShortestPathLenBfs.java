package com.svetanis.datastructures.graph.undirected.path;

import static java.util.Arrays.fill;

import com.svetanis.datastructures.graph.undirected.Graph;
import java.util.ArrayDeque;
import java.util.Queue;

// Shortest path length between two vertices of an unweighted undirected graph.
//
// Input: a Graph g on the vertices 0 .. g.size() - 1, a source vertex src and a destination
// vertex dst. No statement fixes the sizes; this is not a LeetCode problem and has not been run
// against a judge. Returns the fewest edges on a path from src to dst, 0 when they are the same
// vertex, and -1 when dst cannot be reached from src.
//
// The one idea: a breadth-first walk reaches the vertices in rings -- first the vertices one
// edge from src, then those two edges away, and so on -- so the first time it reaches a vertex
// is by a shortest route. dist[v] holds that number of edges, -1 until v is reached; reaching u
// from v gives dist[u] = dist[v] + 1.
//
// Time: O(V + E) -- each vertex is queued once and each adjacency entry is read once.
// Space: O(V) -- the visited flags, the distances and the queue.

public final class ShortestPathLenBfs {

  private static final int V = 9;

  public static int sp(Graph g, int src, int dst) {
    int n = g.size();
    boolean[] visited = new boolean[n];
    visited[src] = true;
    int[] dist = new int[n];
    fill(dist, -1); // -1: not reached (an unreached dst must not read as distance 0)
    dist[src] = 0; // BASE: src is 0 edges from itself

    Queue<Integer> queue = new ArrayDeque<>();
    queue.offer(src);

    while (!queue.isEmpty()) {
      int v = queue.poll();
      for (int u : g.adj(v)) {
        if (!visited[u]) {
          visited[u] = true;
          dist[u] = dist[v] + 1; // RECORD: one edge farther than v
          queue.offer(u);
        }
      }
    }
    return dist[dst]; // RETURN: -1 when dst was never reached
  }

  public static void main(String[] args) {
    Graph g = new Graph(V);
    g.addEdge(0, 1);
    g.addEdge(0, 7);
    g.addEdge(1, 7);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    g.addEdge(2, 5);
    g.addEdge(2, 8);
    g.addEdge(3, 4);
    g.addEdge(3, 5);
    g.addEdge(4, 5);
    g.addEdge(5, 6);
    g.addEdge(6, 7);
    g.addEdge(7, 8);

    int src = 0;
    int dst = 5;
    System.out.println(sp(g, src, dst)); // 3, by 0-1-2-5

    Graph apart = new Graph(3);
    apart.addEdge(0, 1);
    System.out.println(sp(apart, 0, 2)); // -1, vertex 2 has no edge
  }

}
