package com.svetanis.datastructures.graph.directed.ts;

import static com.svetanis.datastructures.graph.directed.ts.TopologicalSortingKahn.inDegree;

import com.svetanis.datastructures.graph.directed.Graph;

// Can All Tasks Be Finished? (the same question as 207. Course Schedule)
//
// Input: a directed Graph on tasks 0..n-1; an edge u -> v means u must be done before v.
// Returns true when some order does every task after the tasks that must precede it, which is
// exactly when the graph has no loop.
//
// The one idea: a task is READY when no unfinished task must precede it (its in-degree is 0).
// Finish a ready task and cross off its edges; that may make others ready. If at some step no
// task is ready while some are unfinished, the unfinished ones all wait on each other: a loop.
// This version finds the next ready task by scanning the count array from the left each time;
// a finished task is marked -1 so the scan skips it.
//
// Time: O(V^2 + E) -- V rounds, each scanning up to V counts, plus each edge crossed off once.
// Space: O(V) -- the in-degree array.
//
// Siblings: FinishAllTasksFromGivenDependenciesDfs answers the same question by looking for a loop
// with a depth-first walk; IsCourseScheduleSubmit keeps a queue of ready tasks, which makes it
// O(V + E).

public final class FinishAllTasksFromGivenDependenciesBfs {

  public static boolean isComplete(Graph g) {
    int n = g.size();
    int[] indegree = inDegree(g); // CARDS: indegree[v] -- unfinished tasks that must precede v

    for (int i = 0; i < n; i++) {
      int index = getIndex(indegree); // PICK: a ready task
      if (index == -1) {
        return false; // RETURN: none ready but some unfinished -- a loop
      }
      indegree[index] = -1; // FINISH: mark it so the scan skips it
      for (int u : g.adj(index)) {
        indegree[u]--; // CROSS OFF: one fewer task holds u back
      }
    }
    return true;
  }

  private static int getIndex(int[] a) {
    int n = a.length;
    for (int i = 0; i < n; i++) {
      if (a[i] == 0) {
        return i;
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    // { [1,0], [2,1], [3,2] }
    Graph g = new Graph(4);
    g.addEdge(0, 1);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    System.out.println(isComplete(g)); // true

    // 0 -> 1 -> 2 -> 0: every task waits on another
    Graph loop = new Graph(3);
    loop.addEdge(0, 1);
    loop.addEdge(1, 2);
    loop.addEdge(2, 0);
    System.out.println(isComplete(loop)); // false
  }
}
