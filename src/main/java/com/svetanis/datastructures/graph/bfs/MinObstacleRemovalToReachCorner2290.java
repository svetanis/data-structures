package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.Deque;

// 2290. Minimum Obstacle Removal to Reach Corner

public final class MinObstacleRemovalToReachCorner2290 {
  // Time Complexity: O(n * m)
  // Space Complexity: O(n * m)

  private static final int[] dx = { 1, 0, 0, -1 };
  private static final int[] dy = { 0, 1, -1, 0 };

  public static int minObstacles(int[][] grid) {
    int n = grid.length;
    int m = grid[0].length;
    Deque<Node> queue = new ArrayDeque<>();
    queue.offer(new Node(0, 0, 0));
    boolean[][] visited = new boolean[n][m];
    while (!queue.isEmpty()) {
      Node node = queue.poll();
      int row = node.x;
      int col = node.y;
      int obstacles = node.obstacles;
      if (row == n - 1 && col == m - 1) {
        return obstacles;
      }
      if (visited[row][col]) {
        continue;
      }
      visited[row][col] = true;
      for (int k = 0; k < dx.length; k++) {
        int x = row + dx[k];
        int y = col + dy[k];
        if (valid(grid, x, y)) {
          if (grid[x][y] == 0) {
            queue.offerFirst(new Node(x, y, obstacles));
          } else {
            queue.offerLast(new Node(x, y, obstacles + 1));
          }
        }
      }
    }
    return -1;
  }

  private static boolean valid(int[][] grid, int x, int y) {
    boolean rows = x >= 0 && x < grid.length;
    boolean cols = y >= 0 && y < grid[0].length;
    return rows && cols;
  }

  public static void main(String[] args) {
    int[][] grid1 = { { 0, 1, 1 }, { 1, 1, 0 }, { 1, 1, 0 } };
    System.out.println(minObstacles(grid1)); // 2

    int[][] grid2 = { { 0, 1, 0, 0, 0 }, { 0, 1, 0, 1, 0 }, { 0, 0, 0, 1, 0 } };
    System.out.println(minObstacles(grid2)); // 0
  }

  private static class Node {
    private int x;
    private int y;
    private int obstacles;

    public Node(int x, int y, int obstacles) {
      this.x = x;
      this.y = y;
      this.obstacles = obstacles;
    }
  }
}
