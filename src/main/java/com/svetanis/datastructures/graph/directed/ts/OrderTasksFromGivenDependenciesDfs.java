package com.svetanis.datastructures.graph.directed.ts;

import com.svetanis.datastructures.graph.directed.Graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Order of Tasks (the same task as 210. Course Schedule II)
//
// Input: a directed Graph on tasks 0..n-1; an edge u -> v means u must be done before v.
// Returns one order that respects every edge, or an empty list when the graph has a loop. Recursion
// goes as deep as the longest chain of edges, so this assumes chains of a few thousand tasks at
// most (LeetCode's 210 allows 2000).
//
// The one idea: walk depth first. A task is written down when its walk FINISHES, which is after
// every task that depends on it was written. So the list comes out backwards -- dependants before
// the tasks they wait on -- and reversing it gives a valid order. `path[v]` is true while v is on
// the walk that is still open; an edge into such a task closes a loop, and then there is no order.
//
// Time: O(V + E) -- each task is entered once and each edge looked at once.
// Space: O(V) -- the flag arrays, the answer and the recursion.
//
// Siblings: OrderTasksFromGivenDependenciesBfs builds the order front to back from a queue of
// ready tasks, with no recursion.

public final class OrderTasksFromGivenDependenciesDfs {

  public static List<Integer> ordering(Graph g) {
    int n = g.size();
    boolean[] path = new boolean[n]; // ON PATH: the walk that is still open
    boolean[] visited = new boolean[n]; // VISITED: entered at least once
    List<Integer> list = new ArrayList<>(); // FINISHED: tasks in the order their walks ended
    for (int i = 0; i < n; i++) {
      if (!visited[i] && isCyclic(g, i, visited, path, list)) {
        return new ArrayList<>(); // RETURN: a walk met its own path -- a loop
      }
    }
    Collections.reverse(list); // REVERSE: last to finish goes first
    return list;
  }

  private static boolean isCyclic(Graph g, int v, boolean[] visited, boolean[] path, List<Integer> list) {
    if (!visited[v]) {
      visited[v] = true;
      path[v] = true;
      for (int u : g.adj(v)) {
        if (!visited[u] && isCyclic(g, u, visited, path, list)) {
          return true;
        } else if (path[u]) {
          return true; // LOOP: u is on the open walk
        }
      }
    }
    list.add(v); // FINISH: every task v points at is already written
    path[v] = false; // remove the vertex from recursion stack
    return false;
  }

  public static void main(String[] args) {
    // { [1,0], [2,1], [3,2] }
    Graph g = new Graph(4);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    System.out.println(ordering(g)); // [0, 1, 2, 3] -- same answer, built back to front

    // 0 -> 1 -> 0: a loop, so no order
    Graph loop = new Graph(2);
    loop.addEdge(0, 1);
    loop.addEdge(1, 0);
    System.out.println(ordering(loop)); // []
  }
}
