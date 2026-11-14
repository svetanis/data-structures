package com.svetanis.datastructures.graph.directed.traverse;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.graph.directed.Graph;

// Breadth-first traversal of a directed graph from one vertex.
//
// Input: g -- a directed graph on the vertices 0 .. n - 1; src -- the start. Returns: the vertices
// reachable from src, each once, nearest first: src, then everything one edge away, then everything
// two edges away, and so on (inside a layer, in the order the edges were added).
//
// The one idea: a queue holds the vertices that have been met but whose outgoing edges are not yet
// followed. Take the oldest one, record it, and queue every vertex it points at that has not been
// met. A vertex is marked the moment it is queued, so it is queued once however many edges point at
// it. First in, first out means a vertex one edge nearer is always taken before one farther away.
//
// Time: O(V + E) -- each vertex is queued once and each edge is followed once.
// Space: O(V) -- the marks, the queue and the result.
//
// Siblings: BFSDisconnected repeats this from every vertex not yet reached; DFS and DFSIterative
// are the depth-first traversals.

public final class BFS {

  public static List<Integer> bfs(Graph g, int src) {
    int size = g.size();
    boolean[] visited = new boolean[size];
    visited[src] = true; // MARK on entry
    Queue<Integer> queue = new ArrayDeque<>();
    queue.offer(src);
    List<Integer> list = new ArrayList<>();
    while (!queue.isEmpty()) {
      int v = queue.poll();
      list.add(v); // RECORD in order of distance
      for (int u : g.adj(v)) {
        if (!visited[u]) { // SKIP a vertex already met
          visited[u] = true;
          queue.offer(u);
        }
      }
    }
    return list;
  }

  private static void print(List<Integer> list) {
    for (int v : list) {
      System.out.printf("%d ", v);
    }
    System.out.println();
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
    print(bfs(g, 4)); // 4 1 3 0 2 -- 4; then 1 and 3; then 0 (from 1); then 2

    Graph graph = new Graph(4);
    graph.addEdge(0, 1);
    graph.addEdge(0, 2);
    graph.addEdge(1, 2);
    graph.addEdge(2, 0);
    graph.addEdge(2, 3);
    graph.addEdge(3, 3);
    print(bfs(graph, 2)); // 2 0 3 1 -- 2; then 0 and 3; then 1
  }
}
