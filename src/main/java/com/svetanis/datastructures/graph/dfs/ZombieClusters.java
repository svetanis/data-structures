package com.svetanis.datastructures.graph.dfs;

import java.util.Arrays;
import java.util.List;

// Zombie Clusters (no LeetCode number)
//
// Input: zombies -- n strings of n characters each; character j of string i is '1' if zombie i and
// zombie j know each other directly and '0' if not. A cluster is a group of zombies connected to
// each other directly or through other zombies. Returns the number of clusters. Assumed, as for
// the matrix of LeetCode 547: n >= 1, the matrix is square and symmetric, and every zombie knows
// itself.
//
// The one idea: the same count as 547 Number of Provinces, with the matrix given as strings. One
// entry of visited says whether that zombie has been reached by a walk. Go through the zombies in
// order; one not yet reached belongs to a cluster no walk has touched, so count a cluster and walk
// to every zombie reachable from it, marking each. The whole cluster is then marked, and none of
// its zombies starts another count.
//
// Time: O(n^2) -- the strings are turned into a grid of n * n numbers, and every zombie is walked
// once, reading its whole row of n cells.
// Space: O(n^2) -- the grid; the walk is at most n calls deep.
//
// Siblings: NumberOfProvinces in this package takes the matrix as numbers.

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
      if (!visited[v]) { // a zombie no walk has reached: a new cluster
        scc++; // COUNT
        explore(g, v, visited); // WALK: mark the whole cluster
      }
    }
    return scc; // RETURN
  }

  private static void explore(int[][] g, int v, boolean[] visited) {
    visited[v] = true; // MARK
    for (int u = 0; u < g[0].length; u++) { // SCAN: the whole row of v
      if (g[v][u] == 1 && !visited[u]) { // SKIP: not connected, or reached already
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
        g[i][j] = zombies.get(i).charAt(j) - '0'; // BUILD: character '0' or '1' to the number
      }
    }
    return g;
  }

  public static void main(String[] args) {
    List<String> a = Arrays.asList("1000001000", "0100010001", //
        "0010100000", "0001000000", "0010100000", "0100010000", //
        "1000001000", "0000000100", "0000000010", "0100000001");
    System.out.println(zombieCluster(a)); // 6
  }
}
