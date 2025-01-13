package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.Deque;

// 1730. Shortest Path to Get Food

public final class ShortestPathToGetFood1730 {

  private static final int[] dx = { 0, 1, 0, -1 };
  private static final int[] dy = { 1, 0, -1, 0 };
  private static final int[] dir = {-1, 0, 1, 0, -1};

  public static int shortestPath(char[][] grid) {
    Deque<Point> queue = init(grid);
    int steps = 0;
    while (!queue.isEmpty()) {
      ++steps;
      for (int size = queue.size(); size >= 0; size--) {
        Point p = queue.poll();
        for (int k = 0; k < 4; k++) {
          int x = p.x + dir[k];
          int y = p.y + dir[k + 1];
          if (valid(grid, x, y)) {
            if (grid[x][y] == '#') {
              return steps;
            }
            if (grid[x][y] == 'O') {
              grid[x][y] = 'X';
              queue.offer(new Point(x, y));
            }
          }
        }
      }
    }
    return -1;
  }

  private static boolean valid(char[][] grid, int x, int y) {
    boolean row = x >= 0 && x < grid.length;
    boolean col = y >= 0 && y < grid[0].length;
    return row && col;
  }

  private static Deque<Point> init(char[][] grid) {
    int n = grid.length;
    int m = grid[0].length;
    Deque<Point> queue = new ArrayDeque<>();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (grid[i][j] == '*') {
          queue.offer(new Point(i, j));
          break;
        }
      }
    }
    return queue;
  }

  public static void main(String[] args) {
    char[][] grid = { //
        { '*', 'O', 'O', 'X' }, //
        { 'O', 'X', 'O', '#' }, //
        { 'O', 'O', 'X', 'O' } };//
    System.out.println(shortestPath(grid)); // 3
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