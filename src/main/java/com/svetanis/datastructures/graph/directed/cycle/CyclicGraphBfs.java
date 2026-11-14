package com.svetanis.datastructures.graph.directed.cycle;

import java.util.ArrayDeque;
import java.util.Queue;

import com.svetanis.datastructures.graph.directed.Graph;

// Does a directed graph contain a cycle? (breadth-first, by in-degree)
//
// Input: g -- a directed graph on the vertices 0 .. n - 1; repeated edges and self-loops allowed.
// Returns: true if some chain of edges leads from a vertex back to itself (a self-loop counts).
//
// The one idea: the in-degree of a vertex is how many edges point at it. Put the vertices in a
// line so that every edge points forward. A vertex nobody points at can go first; once it is placed
// its outgoing edges are used up, so each vertex it pointed at has one fewer edge pointing at it,
// and any that reach zero can go next. If the line takes in every vertex, no cycle exists. If it
// gets stuck, every vertex left has an edge pointing at it from another vertex that is also left;
// following those edges backwards never ends, so it must go round a loop -- a cycle.
//
// Time: O(V + E) -- every vertex is queued at most once and every edge is used up once.
// Space: O(V) -- the in-degree array and the queue.
//
// Siblings: CyclicGraphDfs answers the same question by walking and watching the path it is on;
// TopologicalSortingKahn.tc is this same line-building but returns the order and throws on a cycle;
// IsCyclicDGraphBfs is the same test on an edge array with maps.

public final class CyclicGraphBfs {

  public static boolean isCyclic(Graph g) {
    int size = g.size();
    int[] inDegree = inDegree(g);
    Queue<Integer> queue = new ArrayDeque<>(); // QUEUE: placed-next vertices, nobody left pointing at them
    for (int i = 0; i < size; i++) {
      if (inDegree[i] == 0) {
        queue.offer(i); // SOURCE: nobody points at it
      }
    }

    int count = 0; // COUNT: vertices placed in the line
    while (!queue.isEmpty()) {
      int front = queue.poll();
      for (int u : g.adj(front)) { // USE UP the edges front -> u
        if (--inDegree[u] == 0) {
          queue.offer(u);
        }
      }
      count++;
    }
    return count != size; // RETURN: anything left over is on, or behind, a cycle
  }

  private static int[] inDegree(Graph g) {
    int size = g.size();
    int[] a = new int[size];
    for (int u = 0; u < size; u++) {
      for (int v : g.adj(u)) {
        a[v]++; // COUNT the edge u -> v at its head
      }
    }
    return a;
  }

  public static void main(String[] args) {
    Graph g = new Graph(6);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(2, 0);
    g.addEdge(3, 4);
    g.addEdge(4, 5);
    System.out.println(isCyclic(g)); // true -- 0 -> 1 -> 2 -> 0

    Graph dag = new Graph(3);
    dag.addEdge(0, 1);
    dag.addEdge(1, 2);
    dag.addEdge(0, 2);
    System.out.println(isCyclic(dag)); // false -- every edge points forward in 0, 1, 2
  }
}
