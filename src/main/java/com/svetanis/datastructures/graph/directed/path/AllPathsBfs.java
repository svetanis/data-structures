package com.svetanis.datastructures.graph.directed.path;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.graph.directed.Graph;

// Print every path from one vertex to another in a directed graph, shortest first.
//
// Input: g -- a directed graph on the vertices 0 .. n - 1 (it may contain cycles); src and dst --
// the start and the end. A path may not use any vertex twice. Returns: nothing; prints each path
// on its own line as its vertices separated by spaces. A path from src to src is the single
// vertex src.
//
// The one idea: a queue of unfinished paths, each a list of vertices that starts at src. Take the
// oldest path off the queue; if it ends at dst, print it. Then make one longer path for every edge
// leaving its last vertex, except an edge to a vertex the path already uses (that would make a
// loop), and queue them. Because the queue is first in, first out, every path of k vertices is
// taken before any of k + 1, so the paths come out shortest first.
//
// Time: exponential in V, O(P * V * deg) for the P simple paths that start at src -- each is queued
// once, and each of its extensions is copied and checked for repeats in O(V).
// Space: O(P * V) -- the queue may hold a path for every vertex sequence not yet extended.
//
// Siblings: AllPathsDfs prints the same set of paths, one at a time, in depth-first order and with
// O(V) space; CountPathsBetweenTwoNodes counts them.

public final class AllPathsBfs {

  public static void paths(Graph g, int src, int dst) {
    List<Integer> path = new ArrayList<>();
    path.add(src); // START: the path of one vertex
    Queue<List<Integer>> queue = new ArrayDeque<>();
    queue.offer(path);
    while (!queue.isEmpty()) {
      path = queue.poll();
      int last = path.get(path.size() - 1);
      if (last == dst) { // REPORT: this path has arrived
        printPath(path);
      }
      List<Integer> adj = g.adj(last);
      for (int i = 0; i < adj.size(); i++) { // EXTEND by every edge leaving the last vertex
        int next = adj.get(i);
        if (isNotVisited(path, next)) { // SKIP a vertex the path already uses
          List<Integer> list = new ArrayList<>(path);
          list.add(next);
          queue.offer(list);
        }
      }
    }
  }

  private static void printPath(List<Integer> path) {
    for (int v : path) {
      System.out.printf("%d ", v);
    }
    System.out.println();
  }

  private static boolean isNotVisited(List<Integer> path, int x) {
    return !isVisited(path, x);
  }

  private static boolean isVisited(List<Integer> path, int x) {
    int n = path.size();
    for (int i = 0; i < n; i++) {
      if (path.get(i) == x) {
        return true;
      }
    }
    return false;
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
    // then, shortest first:
    // 2 0 3
    // 2 1 3
    // 2 0 1 3
    paths(g, src, dst);
  }
}
