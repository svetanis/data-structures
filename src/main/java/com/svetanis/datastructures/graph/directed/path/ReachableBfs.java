package com.svetanis.datastructures.graph.directed.path;

import java.util.ArrayDeque;
import java.util.Queue;

import com.svetanis.datastructures.graph.directed.Graph;

// Is one vertex reachable from another in a directed graph? (breadth-first)
//
// Input: g -- a directed graph on the vertices 0 .. n - 1; src and dst -- two vertices. Returns:
// true if some chain of edges leads from src to dst (always true when src == dst).
//
// The one idea: the vertices src can reach are found layer by layer -- first those one edge away,
// then those two edges away, and so on. A mark on each vertex already queued stops a vertex being
// queued twice, which also stops a loop in the graph from going on forever. If dst turns up
// among the targets of an edge, the answer is yes; if the queue empties first, every vertex src
// can reach has been seen and dst was not among them.
//
// Time: O(V + E) -- each vertex is queued once and each edge is looked at once.
// Space: O(V) -- the marks and the queue.
//
// Siblings: ReachableDfs is the same search by recursion.

public final class ReachableBfs {

  public static boolean isReachable(Graph g, int src, int dst) {
    int size = g.size();
    if (src == dst) { // BASE: a vertex reaches itself
      return true;
    }
    boolean[] visited = new boolean[size];
    visited[src] = true;
    Queue<Integer> queue = new ArrayDeque<>();
    queue.offer(src);
    while (!queue.isEmpty()) {
      int front = queue.poll();
      for (int u : g.adj(front)) {
        if (u == dst) { // FOUND
          return true;
        }
        // else continue to do BFS
        if (!visited[u]) { // MARK on entry, so u is queued once
          visited[u] = true;
          queue.offer(u);
        }
      }
    }
    return false; // RETURN: everything reachable was seen, and dst was not there
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
    System.out.println(isReachable(g, 2, 4)); // true -- 2 -> 0 -> 1 -> 4
    System.out.println(isReachable(g, 0, 7)); // false -- 7 is in the other piece

    Graph graph = new Graph(4);
    graph.addEdge(0, 1);
    graph.addEdge(0, 2);
    graph.addEdge(1, 2);
    graph.addEdge(2, 0);
    graph.addEdge(2, 3);
    graph.addEdge(3, 3);
    System.out.println(isReachable(graph, 1, 3)); // true -- 1 -> 2 -> 3
    System.out.println(isReachable(graph, 3, 1)); // false -- 3 only leads back to 3
  }
}
