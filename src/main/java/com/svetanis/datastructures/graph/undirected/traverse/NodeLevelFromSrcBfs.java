package com.svetanis.datastructures.graph.undirected.traverse;

import com.svetanis.datastructures.graph.undirected.Graph;
import java.util.ArrayDeque;
import java.util.Queue;

// Level of each node in a tree from a source node, using BFS.
//
// Input: a connected Graph g on the vertices 0 .. g.size() - 1 (the title says tree; any
// connected graph works), and a source vertex src. No statement fixes the sizes;
// this is not a LeetCode problem and has not been run against a judge. Prints one line
// "<vertex>--> <level>" for every vertex, in vertex order, where the level of a vertex is the
// number of edges on a shortest path from src to it. A vertex src cannot reach is printed with
// level 0, as if it were src. main adds every edge twice (parallel entries, which a walk with
// visited flags ignores).
//
// The one idea: a breadth-first walk reaches the vertices in rings -- first those one edge from
// src, then two edges away -- so the first time it reaches a vertex is by the shortest route,
// and reaching u from v gives level[u] = level[v] + 1.
//
// Time: O(V + E) -- each vertex is queued once and each adjacency entry is read once.
// Space: O(V) -- the visited flags, the levels and the queue.
//
// Siblings: BFS -- the same walk, returning the order instead of the levels. ShortestPathLenBfs
// -- the same distances, asked for one destination, with -1 for a vertex that is not reached.

public final class NodeLevelFromSrcBfs {

  public static void print(Graph g, int src) {
    int[] level = bfs(g, src);
    for (int i = 0; i < g.size(); i++) {
      System.out.println(i + "--> " + level[i]);
    }
  }

  private static int[] bfs(Graph g, int src) {
    int size = g.size();

    boolean[] visited = new boolean[size];
    visited[src] = true;

    int[] level = new int[size];
    level[src] = 0; // BASE: src is level 0

    Queue<Integer> queue = new ArrayDeque<>();
    queue.offer(src);

    while (!queue.isEmpty()) {
      int v = queue.poll();
      for (int u : g.adj(v)) {
        if (!visited[u]) {
          queue.offer(u);
          visited[u] = true;
          level[u] = level[v] + 1; // RECORD: one edge farther than v
        }
      }
    }
    return level;
  }

  public static void main(String[] args) {
    Graph g = new Graph(8);
    g.addEdge(0, 1);
    g.addEdge(1, 0);
    g.addEdge(0, 2);
    g.addEdge(2, 0);
    g.addEdge(1, 3);
    g.addEdge(3, 1);
    g.addEdge(1, 4);
    g.addEdge(4, 1);
    g.addEdge(1, 5);
    g.addEdge(5, 1);
    g.addEdge(2, 5);
    g.addEdge(5, 2);
    g.addEdge(2, 6);
    g.addEdge(6, 2);
    g.addEdge(6, 7);
    g.addEdge(7, 6);
    // prints 0--> 0, 1--> 1, 2--> 1, 3--> 2, 4--> 2, 5--> 2, 6--> 2, 7--> 3
    print(g, 0);
  }
}
