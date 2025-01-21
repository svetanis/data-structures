package com.svetanis.datastructures.graph.unionfind;

// 323. Number of Connected Components in an Undirected Graph

public final class NumberOfConnectedComponents323 {
  // Time Complexity: O(N + E)
  // Space Complexity: O(n)

  private int[] parent;

  public int countComponents(int n, int[][] edges) {
    init(n);
    merge(edges);
    return count(n);
  }

  // count components by counting nodes
  // that are their own parents
  private int count(int n) {
    int count = 0;
    for (int i = 0; i < n; i++) {
      if (i == find(i)) {
        count++;
      }
    }
    return count;
  }

  private void merge(int[][] edges) {
    for (int[] edge : edges) {
      int v1 = edge[0];
      int v2 = edge[1];
      union(v1, v2);
    }
  }

  private void init(int n) {
    this.parent = new int[n];
    for (int i = 0; i < n; i++) {
      this.parent[i] = i;
    }
  }

  private int find(int node) {
    if (parent[node] != node) {
      parent[node] = find(parent[node]);
    }
    return parent[node];
  }

  private void union(int node1, int node2) {
    int root1 = find(node1);
    int root2 = find(node2);
    parent[root1] = root2;
  }

  public static void main(String[] args) {
    int[][] edges = { { 0, 1 }, { 1, 2 }, { 3, 4 } };
    NumberOfConnectedComponents323 cc = new NumberOfConnectedComponents323();
    System.out.println(cc.countComponents(5, edges)); // 2
  }
}
