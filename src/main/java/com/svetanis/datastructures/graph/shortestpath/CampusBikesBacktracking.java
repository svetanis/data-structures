package com.svetanis.datastructures.graph.shortestpath;

// 1066. Campus Bikes II
// backtracking

public final class CampusBikesBacktracking {

  private int minDistSum;
  private int[][] workers;
  private int[][] bikes;

  public int assignBikes(int[][] workers, int[][] bikes) {
    this.workers = workers;
    this.bikes = bikes;
    this.minDistSum = Integer.MAX_VALUE;
    // one flag per bike. a literal 10 is LC 1066's cap on bikes written
    // as a number, and it throws on the first input with an eleventh bike
    boolean[] visited = new boolean[bikes.length];
    dfs(0, 0, visited);
    return minDistSum;
  }

  private void dfs(int windex, int sum, boolean[] visited) {
    if (windex >= workers.length) {
      minDistSum = Math.min(minDistSum, sum);
      return;
    }
    if (sum >= minDistSum) {
      return;
    }

    for (int i = 0; i < bikes.length; i++) {
      if (!visited[i]) {
        visited[i] = true;
        dfs(windex + 1, sum + mdist(workers[windex], bikes[i]), visited);
        visited[i] = false;
      }
    }
  }

  private int mdist(int[] worker, int[] bike) {
    int dx = Math.abs(worker[0] - bike[0]);
    int dy = Math.abs(worker[1] - bike[1]);
    return dx + dy;
  }

  public static void main(String[] args) {
    CampusBikesBacktracking cb = new CampusBikesBacktracking();
    int[][] w1 = { { 0, 0 }, { 2, 1 } };
    int[][] b1 = { { 1, 2 }, { 3, 3 } };
    System.out.println(cb.assignBikes(w1, b1)); // 6

    int[][] w2 = { { 0, 0 }, { 1, 1 }, { 2, 0 } };
    int[][] b2 = { { 1, 0 }, { 2, 2 }, { 2, 1 } };
    System.out.println(cb.assignBikes(w2, b2)); // 4

    int[][] w3 = { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 }, { 4, 0 } };
    int[][] b3 = { { 0, 999 }, { 1, 999 }, { 2, 999 }, { 3, 999 }, { 4, 999 } };
    System.out.println(cb.assignBikes(w3, b3)); // 4995

    // eleven bikes. LC 1066 promises at most ten, and the visited board
    // was sized at a literal 10, so this threw ArrayIndexOutOfBounds
    int[][] w4 = { { 0, 0 } };
    int[][] b4 = { { 0, 1 }, { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 }, //
        { 6, 1 }, { 7, 1 }, { 8, 1 }, { 9, 1 }, { 10, 1 } };
    System.out.println(cb.assignBikes(w4, b4)); // 1
  }
}
