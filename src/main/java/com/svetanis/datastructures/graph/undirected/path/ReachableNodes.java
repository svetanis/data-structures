package com.svetanis.datastructures.graph.undirected.path;

import com.svetanis.datastructures.graph.undirected.Graph;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

// All nodes reachable from each node of a given set.
//
// Input: an undirected Graph g on the vertices 0 .. g.size() - 1, and an array a of query
// vertices, each a vertex of g. No statement fixes the sizes; this is not a LeetCode problem and
// has not been run against a judge. For each query vertex in order, prints "u--> " and the list
// of every vertex reachable from u (u included), in the order a breadth-first walk from the
// first query vertex of its component reached them.
//
// The one idea: two vertices reach the same vertices exactly when they are in the same
// connected component, so one walk per component is enough. visited[v] holds the number of the
// component v was found in, 0 while v has not been reached, and members maps a component number
// to its vertex list. A query whose component number is already set reuses the stored list.
//
// Time: O(V + E + q * V) -- each component is walked once for O(V + E) in all, and each of the q
// queries prints a list of up to V vertices.
// Space: O(V) -- the component numbers and the lists, which together hold each vertex once.

public final class ReachableNodes {

  public static void reachable(Graph g, int[] a) {
    int n = g.size();
    int scc = 0;
    int[] visited = new int[n + 1];
    Map<Integer, List<Integer>> members = new HashMap<>(); // component number -> its vertices
    for (int u : a) {
      if (visited[u] == 0) { // NEW COMPONENT: u has not been reached by an earlier walk
        scc++;
        members.put(scc, bfs(g, u, scc, visited));
      }
      System.out.print(u + "--> ");
      System.out.println(members.get(visited[u])); // RETURN: the stored list of u's component
    }
  }

  private static List<Integer> bfs(Graph g, int src, int scc, int[] visited) {
    visited[src] = scc;
    Queue<Integer> queue = new ArrayDeque<>();
    queue.offer(src);
    List<Integer> list = new ArrayList<>();
    while (!queue.isEmpty()) {
      int v = queue.poll();
      list.add(v); // COLLECT: v is in the component
      for (int u : g.adj(v)) {
        if (visited[u] == 0) {
          visited[u] = scc;
          queue.offer(u);
        }
      }
    }
    return list;
  }

  public static void main(String[] args) {
    int[] a = { 2, 4, 5 };
    Graph g = new Graph(8);
    g.addEdge(1, 2);
    g.addEdge(2, 3);
    g.addEdge(3, 4);
    g.addEdge(3, 1);
    g.addEdge(5, 6);
    g.addEdge(5, 7);
    // prints "2--> [2, 1, 3, 4]", "4--> [2, 1, 3, 4]" (same component, list reused),
    // "5--> [5, 6, 7]"
    reachable(g, a);
  }
}
