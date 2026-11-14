package com.svetanis.datastructures.graph.directed.ts;

import static com.svetanis.datastructures.graph.directed.cycle.CyclicGraphDfs.isCyclic;

import com.svetanis.datastructures.graph.directed.Graph;

// Can All Tasks Be Finished? (the same question as 207. Course Schedule)
//
// Input: a directed Graph on tasks 0..n-1; an edge u -> v means u must be done before v.
// Returns true when some order does every task after the tasks that must precede it, which is
// exactly when the graph has no loop. Recursion goes as deep as the longest chain of edges, so
// this assumes chains of a few thousand tasks at most (LeetCode's 207 allows 2000).
//
// The one idea: walk depth first from each task not yet visited. `path[v]` is true while v is on
// the walk that is still open. If an edge leads to a task that is on the open walk, the walk has
// come back to itself: a loop, so the tasks cannot all be finished. A walk that finishes without
// meeting its own path proves nothing on it is part of a loop.
//
// Time: O(V + E) -- each task is entered once and each edge is looked at once.
// Space: O(V) -- the two flag arrays and the recursion.
//
// Siblings: FinishAllTasksFromGivenDependenciesBfs answers the same question by repeatedly
// finishing a task nothing holds back (O(V^2 + E) as written).

public final class FinishAllTasksFromGivenDependenciesDfs {

  public static boolean isComplete(Graph g) {
    int n = g.size();
    boolean[] path = new boolean[n]; // ON PATH: the walk that is still open
    boolean[] visited = new boolean[n]; // VISITED: entered at least once
    for (int i = 0; i < n; i++) {
      if (!visited[i] && isCyclic(g, i, visited, path)) {
        return false; // RETURN: a walk met its own path -- a loop
      }
    }
    return true;
  }

  public static void main(String[] args) {
    // { [1,0], [2,1], [3,2] }
    Graph g = new Graph(4);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    System.out.println(isComplete(g)); // true

    // 0 -> 1 -> 2 -> 0
    Graph loop = new Graph(3);
    loop.addEdge(0, 1);
    loop.addEdge(1, 2);
    loop.addEdge(2, 0);
    System.out.println(isComplete(loop)); // false
  }
}
