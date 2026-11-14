package com.svetanis.datastructures.graph.shortestpath;

import static java.util.Comparator.comparingInt;

import java.util.Arrays;
import java.util.PriorityQueue;

// 3341. Find Minimum Time to Reach Last Room I
//
// Input: moveTime -- an n x m grid of rooms; moveTime[i][j] is the earliest second at which you may
// start moving into room (i, j). You start in room (0, 0) at second 0, and a move to an adjacent
// room (up, down, left or right) takes exactly one second; you may wait. Returns the least time at
// which you can be in room (n - 1, m - 1). On LeetCode: 2 <= n, m <= 50, 0 <= moveTime[i][j] <= 10^9.
//
// The one idea: minTime[r][c] is the earliest second found so far at which you can be standing in
// room (r, c), and one heap entry is a room with such a second, earliest first. Leaving room A for
// its neighbour B can begin at the later of "now" and B's own time, and then takes one second, so
// the second you arrive at B is max(minTime[A], moveTime[B]) + 1. That never goes back in time, so
// the first time a room comes off the heap its second is final.
//
// Time: O(n * m * log(n * m)) -- a room is pushed only when its time improves, at most once per
// neighbour, so at most 4 * n * m heap entries.
// Space: O(n * m) -- the table of times and the heap.
//
// Siblings: MinTimeToVisitCell is the harder grid variant (LC 2577), where you cannot wait.

public final class MinTimeToReachLastRoom {
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
        comparingInt(a -> a[2]));
    pq.offer(new int[] { 0, 0, 0 });
    while (!pq.isEmpty()) {
      int[] node = pq.poll(); // PICK: the room with the earliest time so far
      int row = node[0];
      int col = node[1];
      int time = node[2];
      if (row == n - 1 && col == m - 1) {
        return time; // RETURN: final the first time the last room comes off the heap
      }
      if (time > minTime[row][col]) {
        continue; // SKIP: a stale entry; this room was reached earlier
      }
      for (int k = 0; k < 4; k++) {
        int x = row + dx[k];
        int y = col + dy[k];
        if (valid(grid, x, y)) {
          int nextTime = Math.max(minTime[row][col], grid[x][y]) + 1; // WAIT if needed, then move
          if (minTime[x][y] > nextTime) {
            minTime[x][y] = nextTime; // RECORD: an earlier arrival at (x, y)
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
