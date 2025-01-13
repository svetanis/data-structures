package com.svetanis.datastructures.graph.bfs.multisource;

import java.util.ArrayDeque;
import java.util.Deque;

// 317. Shortest Distance from all buildings

public final class ShortestDistFromAllBuildings317 {
  // Time Complexity: O(n * m)
  // Space Complexity: O(n * m)

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
        if (grid[row][col] == 1) {
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
        if (grid[row][col] == 0 && count[row][col] == buildings) {
          min = Math.min(min, dist[row][col]);
        }
      }
    }
    return min == Integer.MAX_VALUE ? -1 : min;
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
      depth++;
      for (int size = queue.size(); size > 0; size--) {
        Point p = queue.poll();
        for (int k = 0; k < dx.length; k++) {
          int x = p.x + dx[k];
          int y = p.y + dy[k];
          if (valid(grid, x, y) && !visited[x][y]) {
            count[x][y]++;
            dist[x][y] += depth;
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
    System.out.println(shortestDist(grid));
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