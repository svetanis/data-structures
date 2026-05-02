package com.svetanis.datastructures.graph.advanced;

import java.util.Arrays;
import java.util.PriorityQueue;

// 3341. Find Minimum Time to Reach Last Room I

public final class MinTimeToReachLastRoom {
  // Time Complexity: O(n * m * log(n*m))
  // Space Complexity: O(n * m)

  private static final int[] dx = { -1, 0, 1, 0 };
  private static final int[] dy = { 0, -1, 0, 1 };

  public static int minTime(int[][] grid) {
    int n = grid.length;
    int m = grid[0].length;
    int[][] minTime = new int[n][m];
    for (int[] row : minTime) {
      Arrays.fill(row, Integer.MAX_VALUE);
    }
    minTime[0][0] = 0;
    PriorityQueue<int[]> pq = new PriorityQueue<>(
        (a, b) -> a[2] - b[2]);
    pq.offer(new int[] { 0, 0, 0 });
    while (!pq.isEmpty()) {
      int[] node = pq.poll();
      int row = node[0];
      int col = node[1];
      int time = node[2];
      if (row == n - 1 && col == m - 1) {
        return time;
      }
      if (time > minTime[row][col]) {
        continue;
      }
      for (int k = 0; k < 4; k++) {
        int x = row + dx[k];
        int y = col + dy[k];
        if (valid(grid, x, y)) {
          int nextTime = Math.max(minTime[row][col], grid[x][y]) + 1;
          if (minTime[x][y] > nextTime) {
            minTime[x][y] = nextTime;
            pq.offer(new int[] { x, y, nextTime });
          }
        }
      }
    }
    return -1;
  }

  private static boolean valid(int[][] grid, int x, int y) {
    int n = grid.length;
    int m = grid[0].length;
    return x >= 0 && x < n && y >= 0 && y < m;
  }

  public static void main(String[] args) {
    int[][] m1 = { { 0, 4 }, { 4, 4 } };
    System.out.println(minTime(m1)); // 6

    int[][] m2 = { { 0, 0, 0 }, { 0, 0, 0 } };
    System.out.println(minTime(m2)); // 3

    int[][] m3 = { { 0, 1 }, { 1, 2 } };
    System.out.println(minTime(m3)); // 3

  }
}
