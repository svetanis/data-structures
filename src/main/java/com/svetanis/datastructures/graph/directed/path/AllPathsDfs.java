package com.svetanis.datastructures.graph.directed.path;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.graph.directed.Graph;

// Print every path from one vertex to another in a directed graph.
//
// Input: g -- a directed graph on the vertices 0 .. n - 1 (it may contain cycles); src and dst --
// the start and the end. A path may not use any vertex twice. Returns: nothing; prints each path
// on its own line as its vertices separated by spaces. A path from src to src is the single
// vertex src.
//
// The one idea: walk from src, keeping the path so far in a list and a visited mark on each vertex
// of that path. At a vertex, if it is dst print the list; otherwise try every edge leaving it to a
// vertex not on the path. When a vertex has been tried in every way, take it off the list and
// clear its mark, so that a different path may pass through it. Clearing the mark is what makes
// this find ALL the paths: a mark that stayed would hide every path that needs the vertex later.
//
// Time: exponential in V -- one step for every simple path that starts at src (and every prefix of
// one), O(P * V) with P such paths.
// Space: O(V) -- the list, the marks and the recursion, none longer than V.
//
// Siblings: AllPathsBfs prints the same set of paths shortest first, with a queue of whole paths;
// CountPathsBetweenTwoNodes counts them with this same walk.

public final class AllPathsDfs {

  public static void paths(Graph g, int src, int dst) {
    int size = g.size();
    boolean[] visited = new boolean[size];
    List<Integer> list = new ArrayList<>();
    paths(g, src, dst, visited, list);
  }

  // visited[] marks the vertices on the current path
  // list holds the current path
  private static void paths(Graph g, int src, int dst, boolean[] visited, List<Integer> list) {
    visited[src] = true; // MARK: src is on the path
    list.add(src);
    if (src == dst) { // REPORT: the path has arrived
      printPath(list);
    } else {
      for (int v : g.adj(src)) { // EXTEND by every edge leaving src
        if (!visited[v]) { // SKIP a vertex the path already uses
          paths(g, v, dst, visited, list);
        }
      }
    }
    // BACKTRACK: take src off the path and clear its mark
    list.remove(list.size() - 1);
    visited[src] = false;
  }

  private static void printPath(List<Integer> path) {
    for (int v : path) {
      System.out.printf("%d ", v);
    }
    System.out.println();
  }

  public static void main(String[] args) {
    Graph g = new Graph(4);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(0, 3);
    g.addEdge(2, 0);
    g.addEdge(2, 1);
    g.addEdge(1, 3);

    int src = 2;
    int dst = 3;

    System.out.printf("%d-->%d%n", src, dst); // 2-->3
    // then, in the order the walk finds them:
    // 2 0 1 3
    // 2 0 3
    // 2 1 3
    paths(g, src, dst);
  }
}
