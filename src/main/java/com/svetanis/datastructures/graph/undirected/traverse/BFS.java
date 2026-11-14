package com.svetanis.datastructures.graph.undirected.traverse;

import com.svetanis.datastructures.graph.undirected.Graph;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

// Breadth-first traversal of an undirected graph from a source vertex.
//
// Input: a Graph g on the vertices 0 .. g.size() - 1 and a source vertex src. No statement fixes
// the sizes; this is not a LeetCode problem and has not been run against a judge. Returns the
// vertices reachable from src in the order a breadth-first walk reaches them: src first, then the
// vertices one edge away, then two edges away, and so on. Vertices src cannot reach are not
// listed.
//
// The one idea: a queue holds the vertices that are reached but not yet expanded. Taking from the
// front and adding new neighbours at the back means every vertex one edge from src is expanded
// before any vertex two edges away, so the walk goes in rings. A vertex is marked visited when
// it is queued, not when it is expanded, so it is queued once.
//
// Time: O(V + E) -- each vertex is queued once and each adjacency entry is read once.
// Space: O(V) -- the visited flags, the queue and the result.
//
// Siblings: LexicographicallySmallestBFS -- the same loop with a priority queue in place of the
// queue. NodeLevelFromSrcBfs -- the same walk, recording each vertex's ring number instead of
// the order.

public final class BFS {

  public static List<Integer> bfs(Graph g, int src) {
    int size = g.size();

    boolean[] visited = new boolean[size];
    visited[src] = true; // BASE: src is reached first

    Queue<Integer> queue = new ArrayDeque<>();
    queue.offer(src);

    List<Integer> list = new ArrayList<>();
    while (!queue.isEmpty()) {
      int v = queue.poll();
      list.add(v); // RECORD: v is expanded now
      for (int u : g.adj(v)) {
        if (!visited[u]) {
          visited[u] = true; // MARK when queued, so u is queued once
          queue.offer(u);
        }
      }
    }
    return list;
  }

  private static void print(List<Integer> list) {
    for (int v : list) {
      System.out.print(v);
      System.out.print(' ');
    }
    System.out.println();
  }

  public static void main(String[] args) {
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
    print(bfs(g, 4)); // 4 1 3 0 2

    Graph graph = new Graph(4);
    graph.addEdge(0, 1);
    graph.addEdge(0, 2);
    graph.addEdge(1, 2);
    graph.addEdge(2, 0);
    graph.addEdge(2, 3);
    graph.addEdge(3, 3);
    print(bfs(graph, 2)); // 2 0 1 3
  }
}
