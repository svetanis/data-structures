package com.svetanis.datastructures.graph.advanced;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

// Minimum Cost Path with Edge Reversals

public final class MinCostPathWithEdgeReversals {
  // Time Complexity: O(n + m * log(m))
  // Space Complexity: O(n + m)

  private List<int[]>[] g;

  public int minCost(int n, int[][] edges) {
    ginit(n, edges);
    int[] dist = new int[n];
    boolean[] visited = new boolean[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[0] = 0;
    PriorityQueue<int[]> pq = new PriorityQueue<>//
    ((a, b) -> a[0] - b[0]);
    pq.offer(new int[] { 0, 0 });
    while (!pq.isEmpty()) {
      int[] curr = pq.poll();
      int d = curr[0];
      int x = curr[1];
      if (x == n - 1) {
        return d;
      }
      if (visited[x]) {
        continue;
      }
      visited[x] = true;
      for (int[] neighbor : g[x]) {
        int y = neighbor[0];
        int w = neighbor[1];
        if (d + w < dist[y]) {
          dist[y] = d + w;
          pq.offer(new int[] { dist[y], y });
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
      g[x].add(new int[] { y, w });
      g[y].add(new int[] { x, 2 * w });
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
