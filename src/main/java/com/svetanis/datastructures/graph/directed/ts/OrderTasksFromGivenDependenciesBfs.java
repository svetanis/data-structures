package com.svetanis.datastructures.graph.directed.ts;

import static com.svetanis.datastructures.graph.directed.ts.TopologicalSortingKahn.asQueue;
import static com.svetanis.datastructures.graph.directed.ts.TopologicalSortingKahn.inDegree;

import com.svetanis.datastructures.graph.directed.Graph;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

// Order of Tasks (the same task as 210. Course Schedule II)
//
// Input: a directed Graph on tasks 0..n-1; an edge u -> v means u must be done before v.
// Returns one order that respects every edge, or an empty list when the graph has a loop. More
// than one order is usually correct; this returns the one the queue produces.
//
// The one idea: a task is READY when no edge still points at it (its in-degree is 0). Take a
// ready task, append it to the answer, and cross off the edges leaving it; any task whose count
// just reached 0 is now ready. A task is appended only after everything that must precede it, so
// the answer is valid. If the queue is empty before n tasks are appended, the rest wait on each
// other: a loop.
//
// Time: O(V + E) -- each task enters the queue once and each edge is crossed off once.
// Space: O(V) -- the in-degree array, the queue and the answer.
//
// Siblings: OrderTasksFromGivenDependenciesDfs builds the same kind of order backwards with a
// depth-first walk.

public final class OrderTasksFromGivenDependenciesBfs {

  public static List<Integer> ordering(Graph g) {
    int[] indegree = inDegree(g); // CARDS: indegree[v] -- edges into v not yet crossed off
    Queue<Integer> queue = asQueue(indegree); // READY: every task with in-degree 0
    return tc(g, queue, indegree);
  }

  private static List<Integer> tc(Graph g, Queue<Integer> queue, int[] indegree) {
    int n = g.size();
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      if (queue.isEmpty()) {
        return new ArrayList<>(); // RETURN: nothing ready but tasks left -- a loop
      }
      int u = queue.poll();
      list.add(u); // TAKE
      for (int v : g.adj(u)) {
        if (--indegree[v] == 0) { // CROSS OFF: one edge into v is used up
          queue.offer(v); // READY
        }
      }
    }
    return list;
  }

  public static void main(String[] args) {
    // { [1,0], [2,1], [3,2] }
    Graph g = new Graph(4);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    System.out.println(ordering(g)); // [0, 1, 2, 3]

    // 0 -> 1 -> 0: a loop, so no order
    Graph loop = new Graph(2);
    loop.addEdge(0, 1);
    loop.addEdge(1, 0);
    System.out.println(ordering(loop)); // []
  }
}
