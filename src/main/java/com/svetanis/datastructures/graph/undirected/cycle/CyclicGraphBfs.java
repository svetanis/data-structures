package com.svetanis.datastructures.graph.undirected.cycle;

import com.svetanis.datastructures.graph.undirected.Graph;
import java.util.ArrayDeque;
import java.util.Queue;

// Does an undirected graph contain a cycle?
//
// Input: a Graph g, simple (no parallel edges, no self-loops). No statement fixes the sizes; this
// is not a LeetCode problem and has not been run against a judge. Returns true when some
// sequence of three or more distinct vertices is joined in a loop. Outside that input: a
// parallel edge is reported as a cycle (as in CyclicGraphDfs), and so is a self-loop, except one
// on vertex 0, which looks like the edge to its parent (see parent below) and is missed.
//
// The one idea: a graph without a cycle is a forest, and in a forest a breadth-first walk
// reaches each vertex by exactly one edge, the edge from its parent. So while walking, a
// neighbour that has already been reached and is not the vertex's own parent was reached some
// other way, and the two ways together make a cycle. parent[v] holds the vertex v was reached
// from. Every component is walked from its first vertex, which has no parent: its entry stays 0.
// That is harmless on a simple graph, because the first vertex of a component is the smallest
// in it, so vertex 0 is never one of its neighbours unless the first vertex is 0 itself.
//
// Time: O(V * C + E) for C components -- every adjacency entry is read once, but the parent
// array of V entries is allocated again for each component walked, so a graph of V isolated
// vertices allocates V arrays of V entries, O(V^2).
// Space: O(V) -- the visited flags, one parent array at a time, and the queue.
//
// Siblings: CyclicGraphDfs -- the same test, depth first, carrying the parent in the call.

public final class CyclicGraphBfs {

  public static boolean isCyclic(Graph g) {
    int size = g.size();
    boolean[] visited = new boolean[size];
    for (int v = 0; v < size; v++) {
      if (!visited[v] && isCyclic(g, v, visited)) { // NEW COMPONENT, then walk it
        return true;
      }
    }
    return false;
  }

  private static boolean isCyclic(Graph g, int src, boolean[] visited) {

    int n = g.size();
    visited[src] = true; // BASE: src has no parent
    int[] parent = new int[n];
    Queue<Integer> queue = new ArrayDeque<>();
    queue.offer(src);

    while (!queue.isEmpty()) {
      int u = queue.poll();
      for (int v : g.adj(u)) {
        if (!visited[v]) {
          visited[v] = true;
          queue.offer(v);
          parent[v] = u; // RECORD: v was reached from u
        } else if (parent[u] != v) {
          // if an adjacent is visited and
          // not parent of current vertex
          return true; // then there is a cycle
        }
      }
    }
    return false;
  }

  public static void main(String[] args) {
    Graph g = new Graph(5);
    g.addEdge(1, 0);
    g.addEdge(0, 2);
    g.addEdge(2, 0);
    g.addEdge(0, 3);
    g.addEdge(3, 4);
    // 0-2 is added twice, a parallel edge, which counts as a cycle
    System.out.println(isCyclic(g)); // true

    Graph g2 = new Graph(3);
    g2.addEdge(0, 1);
    g2.addEdge(1, 2);
    System.out.println(isCyclic(g2)); // false

    Graph triangle = new Graph(3);
    triangle.addEdge(0, 1);
    triangle.addEdge(1, 2);
    triangle.addEdge(2, 0);
    System.out.println(isCyclic(triangle)); // true

  }
}
