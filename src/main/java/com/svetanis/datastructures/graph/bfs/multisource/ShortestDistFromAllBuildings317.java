package com.svetanis.datastructures.graph.bfs.multisource;

import java.util.ArrayDeque;
import java.util.Deque;

// 317. Shortest Distance from All Buildings
//
// Input: a grid where 0 is empty land, 1 is a building and 2 is an obstacle. Moves are one step
// up, down, left or right, only over empty land (a building or obstacle cannot be walked
// through). Build a house on one empty cell so that the total walking distance to all the
// buildings is smallest. Returns that total, or -1 if no empty cell can reach every building.
// On LeetCode: 1 <= m, n <= 50, grid[i][j] is 0, 1 or 2, at least one building.
//
// The one idea: do one breadth-first walk from each building. For an empty cell, dist[row][col]
// holds the sum, over the buildings walked so far, of the steps from that building to the cell,
// and count[row][col] holds how many of those walks reached it. One queue entry is a cell on the
// edge of the current walk; the walk goes one ring at a time, and depth is the number of steps
// of the ring being filled. A cell is a candidate only when count equals the number of
// buildings, which means every building reached it; the answer is the smallest dist among the
// candidates.
//
// Time: O(b * m * n) -- b is the number of buildings; each building starts a walk over up to
// m * n cells, so in the worst case O((m * n)^2).
// Space: O(m * n) -- dist, count, and one walk's visited grid and queue.

public final class ShortestDistFromAllBuildings317 {

  private static final int[] dx = { 1, 0, 0, -1 };
  private static final int[] dy = { 0, 1, -1, 0 };

  public static int shortestDist(int[][] grid) {
    int n = grid.length;
    int m = grid[0].length;
    int buildings = 0;
    int[][] dist = new int[n][m];
    int[][] count = new int[n][m];
    for (int row = 0; row < n; row++) {
      for (int col = 0; col < m; col++) {
        if (grid[row][col] == 1) { // WALK: one search per building
          buildings++;
          Point src = new Point(row, col);
          bfs(src, grid, count, dist);
        }
      }
    }
    return minDist(grid, count, dist, buildings);
  }

  private static int minDist(int[][] grid, int[][] count, int[][] dist, int buildings) {
    int min = Integer.MAX_VALUE;
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[0].length; col++) {
        if (grid[row][col] == 0 && count[row][col] == buildings) { // TAKE: empty and reached by every building
          min = Math.min(min, dist[row][col]);
        }
      }
    }
    return min == Integer.MAX_VALUE ? -1 : min; // RETURN: -1 when no cell reaches all
  }

  private static void bfs(Point src, int[][] grid, int[][] count, int[][] dist) {
    int depth = 0;
    int n = grid.length;
    int m = grid[0].length;
    boolean[][] visited = new boolean[n][m];
    Deque<Point> queue = new ArrayDeque<>();
    queue.offer(src);
    visited[src.x][src.y] = true;
    while (!queue.isEmpty()) {
      depth++; // the ring being filled is depth steps from src
      for (int size = queue.size(); size > 0; size--) { // WALK: one ring at a time
        Point p = queue.poll();
        for (int k = 0; k < dx.length; k++) {
          int x = p.x + dx[k];
          int y = p.y + dy[k];
          if (valid(grid, x, y) && !visited[x][y]) { // SKIP: off the grid, not empty land, or seen
            count[x][y]++; // RECORD: this building reaches the cell
            dist[x][y] += depth; // RECORD: and is depth steps away
            visited[x][y] = true;
            queue.offer(new Point(x, y));
          }
        }
      }
    }
  }

  private static boolean valid(int[][] grid, int x, int y) {
    boolean row = x >= 0 && x < grid.length;
    boolean col = y >= 0 && y < grid[0].length;
    return row && col && grid[x][y] == 0;
  }

  public static void main(String[] args) {
    int[][] grid = { { 0, 1, 0 }, { 0, 2, 0 }, { 0, 0, 0 } };
    System.out.println(shortestDist(grid)); // 1

    int[][] grid1 = { { 1, 0, 2, 0, 1 }, { 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0 } };
    System.out.println(shortestDist(grid1)); // 7

    System.out.println(shortestDist(new int[][] { { 1, 0 } })); // 1

    System.out.println(shortestDist(new int[][] { { 1 } })); // -1
  }

  private static class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
}