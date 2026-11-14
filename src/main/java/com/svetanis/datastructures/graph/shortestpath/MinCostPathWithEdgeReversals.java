package com.svetanis.datastructures.graph.shortestpath;

import static java.util.Comparator.comparingInt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

// 3650. Minimum Cost Path with Edge Reversals
//
// Input: n -- the number of nodes, labelled 0 to n - 1; edges[i] = { u, v, w } -- a one-way edge
// from u to v that costs w. At a node you may use its one-time switch to turn an edge that points
// into the node around and walk it backwards, which costs 2 * w. Returns the least cost to get
// from node 0 to node n - 1, or -1 if it cannot be done. On LeetCode: 2 <= n <= 5 * 10^4,
// 1 <= edges.length <= 10^5, 1 <= w <= 1000.
//
// The one idea: walking an edge forward costs w, and walking it backwards costs 2 * w, so every
// edge becomes two ordinary edges -- u to v at w and v to u at 2 * w -- and the question is a
// plain shortest path from 0 to n - 1. One heap entry is a node with the cost of the cheapest
// walk known to it, cheapest first. Costs only grow along a walk, so the first time a node comes
// off the heap its cost is final, and later entries for it are skipped.
//
// Time: O(n + m log m) -- the heap holds at most one entry per improvement of a node, and every
// improvement comes from one of the 2 * m directed edges.
// Space: O(n + m) -- the adjacency lists, dist, visited, and the heap.

public final class MinCostPathWithEdgeReversals {
  private List<int[]>[] g;

  public int minCost(int n, int[][] edges) {
    ginit(n, edges);
    int[] dist = new int[n];
    boolean[] visited = new boolean[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[0] = 0;
    // entry is { node, distance }, ordered on the distance -- the same layout as every
    // other Dijkstra in this package. the comparator index and the field order must agree
    PriorityQueue<int[]> pq = new PriorityQueue<>//
    (comparingInt(a -> a[1]));
    pq.offer(new int[] { 0, 0 });
    while (!pq.isEmpty()) {
      int[] curr = pq.poll(); // PICK: the cheapest node not yet finished
      int x = curr[0];
      int d = curr[1];
      if (x == n - 1) {
        return d; // RETURN: the first time n - 1 comes off the heap its cost is final
      }
      if (visited[x]) {
        continue; // SKIP: a stale entry; x was finished at a lower cost
      }
      visited[x] = true;
      for (int[] neighbor : g[x]) {
        int y = neighbor[0];
        int w = neighbor[1];
        if (d + w < dist[y]) {
          dist[y] = d + w; // RELAX: a cheaper walk to y through x
          pq.offer(new int[] { y, dist[y] });
        }
      }
    }
    return -1;
  }

  private void ginit(int n, int[][] edges) {
    this.g = new ArrayList[n];
    for (int i = 0; i < n; i++) {
      g[i] = new ArrayList<>();
    }
    for (int[] e : edges) {
      int x = e[0], y = e[1], w = e[2];
      g[x].add(new int[] { y, w }); // BUILD: forward at its own cost
      g[y].add(new int[] { x, 2 * w }); // BUILD: reversed at twice the cost
    }
  }

  public static void main(String[] args) {
    MinCostPathWithEdgeReversals mcp = new MinCostPathWithEdgeReversals();
    int[][] e1 = { { 0, 1, 3 }, { 3, 1, 1 }, { 2, 3, 4 }, { 0, 2, 2 } };
    System.out.println(mcp.minCost(4, e1)); // 5

    int[][] e2 = { { 0, 2, 1 }, { 2, 1, 1 }, { 1, 3, 1 }, { 2, 3, 3 } };
    System.out.println(mcp.minCost(4, e2)); // 3
  }
}
