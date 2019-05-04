package com.svetanis.datastructures.graph.dfs;

public final class NumberOfIslands {

  private static int ROW = 5;
  private static int COL = 5;
  private static int N = 8;
  private static int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
  private static int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };

  public static int count(int[][] g) {
    // Time complexity: O(ROW x COL)

    int count = 0;
    int row = g.length;
    int col = g[0].length;

    boolean[][] visited = new boolean[row][col];

    // traverse through all cells of given matrix
    // if a cell with value 1 is not visited yet,
    // then new island found.
    // visit all cells in this island
    // and increment island count
    for (int i = 0; i < row; ++i) {
      for (int j = 0; j < col; ++j) {
        if (g[i][j] != 0 && !visited[i][j]) {
          dfs(g, i, j, visited);
          ++count;
        }
      }
    }
    return count;
  }

  private static void dfs(int[][] g, int row, int col, boolean[][] visited) {
    // considers only the 8 neighbors as adjacent vertices
    // mark this cell as visited
    visited[row][col] = true;
    // recur for all connected neighbours
    for (int k = 0; k < N; ++k) {
      int x = row + dx[k];
      int y = col + dy[k];
      if (isSafe(g, x, y, visited)) {
        dfs(g, x, y, visited);
      }
    }
  }

  private static boolean isSafe(int[][] g, int row, int col, boolean[][] visited) {
    boolean one = row >= 0 && row < ROW; // row number is in range
    boolean two = col >= 0 && col < COL; // col number is in range
    return one && two && g[row][col] != 0 && !visited[row][col];
  }

  public static void main(String[] args) {
    int[][] g = { //
        { 1, 1, 0, 0, 0 }, //
        { 0, 1, 0, 0, 1 }, //
        { 1, 0, 0, 1, 1 }, //
        { 0, 0, 0, 0, 0 }, //
        { 1, 0, 1, 0, 1 } };//
    System.out.println(count(g));
  }
}
