package com.svetanis.datastructures.graph.directed.ts;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.graph.directed.Graph;

// Topological order of a directed acyclic graph, by repeatedly removing vertices nobody points at
// (Kahn's algorithm).
//
// Input: g -- a directed graph on the vertices 0 .. n - 1. Returns: all the vertices in a line so
// that every edge u -> v has u before v. Throws IllegalArgumentException if the graph has a cycle,
// since no such line exists then. (Several lines may be valid; this returns the one the queue
// produces.)
//
// The one idea: the in-degree of a vertex is how many edges point at it. A vertex with in-degree 0
// has nothing that must come before it, so it can go next. Place it, and take its outgoing edges
// away: each vertex it pointed at has one fewer edge pointing at it, and any that reach 0 are ready
// to place. If the line takes in every vertex it is a valid order; if it stops early, the vertices
// left all have an edge from another vertex that is left, which is a cycle.
//
// Time: O(V + E) -- every vertex is queued once and every edge is used up once.
// Space: O(V) -- the in-degree array, the queue and the result.
//
// Siblings: TopologicalSorting builds an order by depth-first finishing times instead, and also
// throws on a cycle; the schedule files in the sibling package reuse inDegree and asQueue.

public final class TopologicalSortingKahn {

  public static List<Integer> tc(Graph g) {
    int[] indegree = inDegree(g);
    Queue<Integer> queue = asQueue(indegree);
    List<Integer> list = tc(g, queue, indegree);
    if (list.size() != g.size()) { // STUCK: the vertices left have edges from each other
      throw new IllegalArgumentException("cyclic graph");
    }
    return list;
  }

  private static List<Integer> tc(Graph g, Queue<Integer> queue, int[] indegree) {
    List<Integer> list = new ArrayList<>();
    while (!queue.isEmpty()) {
      int u = queue.poll();
      list.add(u); // PLACE u next in the line
      for (int v : g.adj(u)) {
        indegree[v]--; // USE UP the edge u -> v
        if (indegree[v] == 0) {
          queue.offer(v);
        }
      }
    }
    return list;
  }

  public static int[] inDegree(Graph g) {
    int size = g.size();
    int[] a = new int[size];
    for (int v = 0; v < size; v++) {
      for (int u : g.adj(v)) {
        a[u]++; // COUNT the edge v -> u at its head
      }
    }
    return a;
  }

  public static Queue<Integer> asQueue(int[] indegree) {
    Queue<Integer> queue = new ArrayDeque<>();
    for (int i = 0; i < indegree.length; i++) {
      if (indegree[i] == 0) {
        queue.offer(i); // SOURCE: nobody points at it
      }
    }
    return queue;
  }

  public static void main(String[] args) {
    Graph g = new Graph(6);
    g.addEdge(5, 2);
    g.addEdge(5, 0);
    g.addEdge(4, 0);
    g.addEdge(4, 1);
    g.addEdge(2, 3);
    g.addEdge(3, 1);
    System.out.println(tc(g)); // [4, 5, 2, 0, 3, 1] -- a DIFFERENT valid order to the DFS sibling's
  }
}
