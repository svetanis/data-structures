package com.svetanis.datastructures.graph.directed.path;

import static java.lang.Integer.MAX_VALUE;
import static java.util.Arrays.fill;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.graph.directed.Graph;

// Print a shortest path between two vertices of a directed graph, counting each edge as 1.
//
// Input: g -- a directed graph on the vertices 0 .. n - 1; src and dst -- two vertices. Returns:
// nothing; if dst can be reached from src, prints the line "Shortest path len: <edges>" and then
// one shortest path as its vertices separated by spaces (for src == dst, length 0 and the single
// vertex); prints nothing if dst cannot be reached.
//
// The one idea: a breadth-first walk from src reaches every vertex by a route with the fewest
// edges, because it takes the vertices in order of distance. When it first reaches a vertex u from
// a vertex v, it records dist[u] = dist[v] + 1 and parent[u] = v. The route to dst is read off
// backwards: dst, its parent, that vertex's parent, and so on until src (the only vertex without
// a parent), then reversed.
//
// Time: O(V + E) -- the walk stops early but may need all vertices and edges; the route has at
// most V vertices.
// Space: O(V) -- dist, parent, visited and the queue.
//
// Siblings: CountShortestPathsBfs counts the shortest paths instead of listing one.

public final class ShortestPathBfs {

  private static final int V = 8;

  public static void print(Graph g, int src, int dst) {
    int n = g.size();
    int[] dist = new int[n]; // DIST: edges from src; MAX_VALUE = not reached
    fill(dist, MAX_VALUE);
    int[] parents = new int[n]; // PARENT: the vertex this one was first reached from; -1 = none
    fill(parents, -1);

    boolean sp = sp(g, src, dst, parents, dist);
    if (!sp) {
      return;
    }

    List<Integer> path = path(src, dst, parents);
    System.out.printf("Shortest path len: %d%n", dist[dst]);
    for (int v : path) {
      System.out.printf("%d ", v);
    }
    System.out.println();
  }

  private static boolean sp(Graph g, int src, int dst, int[] parent, int[] dist) {
    int n = g.size();
    boolean[] visited = new boolean[n];
    visited[src] = true;
    dist[src] = 0;
    if (src == dst) { // BASE: the path is src alone
      return true;
    }
    Queue<Integer> queue = new ArrayDeque<>();
    queue.offer(src);
    while (!queue.isEmpty()) {
      int v = queue.poll();
      for (int u : g.adj(v)) {
        if (!visited[u]) { // FIRST sight of u: this is a shortest route to it
          visited[u] = true;
          dist[u] = dist[v] + 1;
          parent[u] = v;
          queue.offer(u);
          if (u == dst) { // FOUND
            return true;
          }
        }
      }
    }
    return false;
  }

  // WALK the parents from dst back to src, then reverse
  private static List<Integer> path(int src, int dst, int[] parents) {
    List<Integer> path = new ArrayList<>();
    for (int at = dst; at != -1; at = parents[at]) {
      path.add(at);
    }
    Collections.reverse(path);
    return path;
  }

  public static void main(String[] args) {
    Graph g = new Graph(V);
    g.addEdge(0, 1);
    g.addEdge(0, 3);
    g.addEdge(1, 2);
    g.addEdge(3, 4);
    g.addEdge(3, 7);
    g.addEdge(4, 5);
    g.addEdge(4, 6);
    g.addEdge(4, 7);
    g.addEdge(5, 6);
    g.addEdge(6, 7);

    int src = 0;
    int dst = 7;
    print(g, src, dst); // Shortest path len: 2 / 0 3 7
    print(g, 0, 6); // Shortest path len: 3 / 0 3 4 6
    print(g, 0, 0); // Shortest path len: 0 / 0
    print(g, 7, 0); // prints nothing -- 7 has no edges out
  }
}
