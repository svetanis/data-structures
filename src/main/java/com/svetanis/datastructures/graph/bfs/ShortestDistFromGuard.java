package  com.svetanis.datastructures.graph.bfs;

import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.java.base.utils.Print.print;
import static java.util.Arrays.fill;

import java.util.Queue;

public final class ShortestDistFromGuard {

  private static int dx[] = { -1, 0, 1, 0 };
  private static int dy[] = { 0, 1, 0, -1 };

  public static int[][] shortestDist(char[][] matrix) {
    int n = matrix.length;
    int m = matrix[0].length;
    int[][] grid = initGrid(matrix);
    Queue<Node> queue = initQueue(matrix);
    while (!queue.isEmpty()) {
      Node node = queue.poll();

      for (int i = 0; i < dx.length; i++) {
        int x = node.x + dx[i];
        int y = node.y + dy[i];
        if (isValid(x, y, n, m) && isSafe(matrix, grid, x, y)) {
          int dist = node.dist + 1;
          grid[x][y] = dist;
          queue.offer(new Node(x, y, dist));
        }
      }
    }
    return grid;
  }

  private static boolean isSafe(char[][] matrix, int[][] grid, int r, int c) {
    if (matrix[r][c] != 'O' || grid[r][c] != -1) {
      return false;
    }
    return true;
  }

  private static boolean isValid(int r, int c, int n, int m) {
    return r >= 0 && r < n && c >= 0 && c < m;
  }

  private static Queue<Node> initQueue(char[][] matrix) {
    int n = matrix.length;
    int m = matrix[0].length;
    Queue<Node> queue = newLinkedList();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (matrix[i][j] == 'G') {
          queue.offer(new Node(i, j, 0));
        }
      }
    }
    return queue;
  }

  private static int[][] initGrid(char[][] matrix) {
    int n = matrix.length;
    int m = matrix[0].length;
    int[][] grid = new int[n][m];
    for (int i = 0; i < n; i++) {
      fill(grid[i], -1);
    }
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (matrix[i][j] == 'G') {
          grid[i][j] = 0;
        }
      }
    }
    return grid;
  }

  public static void main(String[] agrs) {
    char matrix[][] = { //
        { 'O', 'O', 'O', 'O', 'G' }, //
        { 'O', 'W', 'W', 'O', 'O' }, //
        { 'O', 'O', 'O', 'W', 'O' }, //
        { 'G', 'W', 'W', 'W', 'O' }, //
        { 'O', 'O', 'O', 'O', 'G' } };//
    int[][] grid = shortestDist(matrix);
    print(grid);
  }

  private static class Node {
    private int x;
    private int y;
    private int dist;

    public Node(int x, int y, int dist) {
      this.x = x;
      this.y = y;
      this.dist = dist;
    }
  }
}
