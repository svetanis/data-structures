package com.svetanis.datastructures.graph.directed.path;

import static java.lang.Integer.MAX_VALUE;
import static java.util.Arrays.fill;

import java.util.ArrayDeque;
import java.util.Queue;

import com.svetanis.datastructures.graph.directed.Graph;

// Count the shortest paths from one vertex to every vertex of a directed graph.
//
// Input: g -- a directed graph on the vertices 0 .. n - 1, each edge counting 1; src -- the start.
// Returns: nothing; prints, for each vertex in order, the number of different shortest paths from
// src to it, separated by spaces (0 for a vertex src cannot reach; 1 for src itself). Counts are
// ints, so they are assumed to fit.
//
// The one idea: dist[x] is the length of a shortest path from src to x and paths[x] is how many
// such paths there are. A shortest path to x ends with an edge front -> x where front is one step
// nearer, so paths[x] is the sum of paths[front] over all such front. A breadth-first walk takes
// the vertices in order of distance, so by the time front is taken off the queue paths[front] is
// final. Looking at the edge front -> x: if x is farther than dist[front] + 1 it has just been
// found by a shorter route, so its count restarts from paths[front]; if it is exactly
// dist[front] + 1, this is one more shortest route and paths[front] is added; otherwise the edge
// is no use.
//
// Time: O(V + E) -- each vertex is queued once and each edge is looked at once.
// Space: O(V) -- dist, paths, visited and the queue.
//
// Siblings: ShortestPathBfs finds one shortest path and its length rather than counting them all.

public final class CountShortestPathsBfs {

  public static void paths(Graph g, int src) {
    int n = g.size();
    int[] dist = new int[n]; // DIST: edges on a shortest path from src; MAX_VALUE = not reached
    int[] paths = new int[n]; // PATHS: how many shortest paths; 0 = not reached
    fill(dist, MAX_VALUE);
    paths(g, src, dist, paths);
    for (int count : paths) {
      System.out.printf("%d ", count);
    }
    System.out.println();
  }

  private static void paths(Graph g, int src, int[] dist, int[] paths) {
    int n = g.size();
    dist[src] = 0; // BASE: src is at distance 0, by one (empty) path
    paths[src] = 1;
    boolean[] visited = new boolean[n];
    visited[src] = true;
    Queue<Integer> queue = new ArrayDeque<>();
    queue.offer(src);
    while (!queue.isEmpty()) {
      int front = queue.poll();
      for (int x : g.adj(front)) {
        if (!visited[x]) { // FIRST sight of x: queue it once
          queue.offer(x);
          visited[x] = true;
        }
        if (dist[x] > dist[front] + 1) { // SHORTER route: restart the count
          dist[x] = dist[front] + 1;
          paths[x] = paths[front];
        } else if (dist[x] == dist[front] + 1) { // ANOTHER shortest route: add its count
          paths[x] += paths[front];
        }
      }
    }
  }

  public static void main(String[] args) {
    Graph g = new Graph(7);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(1, 2);
    g.addEdge(1, 3);
    g.addEdge(2, 3);
    g.addEdge(3, 4);
    g.addEdge(3, 5);
    g.addEdge(4, 6);
    g.addEdge(5, 6);
    int src = 0;
    // 0 is reached 1 way; 1 and 2 one way each (0 -> 2 directly is shorter than 0 -> 1 -> 2);
    // 3 two ways (via 1 or via 2); 4 and 5 two ways each; 6 four ways
    paths(g, src); // 1 1 1 2 2 2 4
  }
}
