package com.svetanis.datastructures.graph.unionfind;

import java.util.Arrays;

// 1168. Optimize Water Distribution in a Village

public final class WaterDistribution1168 {
  // Time Complexity: O(E * log E + N)
  // Space Complexity: O(E + N)

  private int[] parent;

  public int minCost(int n, int[] wells, int[][] pipes) {
    initParent(n);
    int[][] connections = connections(n, wells, pipes);
    // sort connections by cost
    Arrays.sort(connections, (a, b) -> a[2] - b[2]);
    return totalCost(n, connections);
  }

  private int totalCost(int n, int[][] connections) {
    int totalCost = 0;
    for (int[] connection : connections) {
      int pa = find(connection[0]);
      int pb = find(connection[1]);
      // if both have the same root
      // they are already connected
      if (pa == pb) {
        continue;
      }
      // otherwise, connect them
      parent[pa] = pb;
      // and add the cost
      totalCost += connection[2];
      if (--n == 0) {
        break;
      }
    }
    return totalCost;
  }

  private void initParent(int n) {
    this.parent = new int[n + 1];
    for (int i = 1; i <= n; i++) {
      parent[i] = i;
    }
  }

  private int[][] connections(int n, int[] wells, int[][] pipes) {
    int index = 0;
    int[][] grid = new int[n + pipes.length][3];
    for (int[] pipe : pipes) {
      grid[index++] = pipe;
    }
    for (int i = 0; i < n; i++) {
      grid[index++] = new int[] { 0, i + 1, wells[i] };
    }
    return grid;
  }

  private int find(int x) {
    if (parent[x] != x) {
      parent[x] = find(parent[x]);
    }
    return parent[x];
  }

  public static void main(String[] args) {
    int[] wells = { 1, 2, 2 };
    int[][] pipes = { { 1, 2, 1 }, { 2, 3, 1 } };
    WaterDistribution1168 wd = new WaterDistribution1168();
    System.out.println(wd.minCost(3, wells, pipes)); // 2
  }
}