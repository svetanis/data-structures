package com.svetanis.datastructures.graph.dfs;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

public final class ZombieClusters {

  public static int zombieCluster(List<String> zombies) {
    int[][] g = buildGrid(zombies);
    return dfs(g);
  }

  private static int dfs(int[][] g) {
    int n = g.length;
    int scc = 0;
    boolean[] visited = new boolean[n];
    for (int v = 0; v < n; v++) {
      if (!visited[v]) {
        scc++;
        explore(g, v, visited);
      }
    }
    return scc;
  }

  private static void explore(int[][] g, int v, boolean[] visited) {
    visited[v] = true;
    for (int u = 0; u < g[0].length; u++) {
      if (g[v][u] == 1 && !visited[u]) {
        explore(g, u, visited);
      }
    }
  }

  private static int[][] buildGrid(List<String> zombies) {
    int n = zombies.size();
    int m = zombies.get(0).length();
    int[][] g = new int[n][m];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        g[i][j] = zombies.get(i).charAt(j) - '0';
      }
    }
    return g;
  }

  public static void main(String[] args) {
    List<String> a = newArrayList("1000001000", "0100010001", //
        "0010100000", "0001000000", "0010100000", "0100010000", //
        "1000001000", "0000000100", "0000000010", "0100000001");
    System.out.println(zombieCluster(a));
  }
}
