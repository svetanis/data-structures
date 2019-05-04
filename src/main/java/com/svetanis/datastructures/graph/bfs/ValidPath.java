package com.svetanis.datastructures.graph.bfs;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;

import java.util.LinkedList;
import java.util.List;

// There is a rectangle with left bottom as (0, 0) and right up as (x, y). 
// There are N circles such that their centers are inside the rectangle.
// Radius of each circle is R. Now we need to find out if it is possible 
// that we can move from (0, 0) to (x, y) without touching any circle.

public final class ValidPath {

  private static final int N = 8;
  private static final int dx[] = { 1, 1, 1, 0, -1, -1, -1, 0 };
  private static final int dy[] = { -1, 0, 1, 1, 1, 0, -1, -1 };

  public static boolean validPath(int dstX, int dstY, int n, int r, List<Integer> xi, List<Integer> yi) {
    int dp[][] = init(dstX, dstY);
    dp[0][0] = 1; // src = (0, 0)

    LinkedList<Cell> queue = newLinkedList();
    queue.add(new Cell(0, 0));

    while (queue.size() != 0) {
      Cell cell = queue.poll();
      for (int i = 0; i < N; i++) {
        int x = cell.x + dx[i];
        int y = cell.y + dy[i];

        if (isSafe(x, y, dstX, dstY)) {
          if (dp[x][y] == -1) {
            if (!isValidCell(x, y, r, xi, yi)) {
              dp[x][y] = 2;
            } else {
              dp[x][y] = 1;
              queue.add(new Cell(x, y));
            }
          }
        }
      }

      if (dp[dstX][dstY] != -1) {
        break;
      }
    }

    return dp[dstX][dstY] == 1;
  }

  private static int[][] init(int dstX, int dstY) {
    int dp[][] = new int[dstX + 1][dstY + 1];
    for (int i = 0; i <= dstX; i++) {
      for (int j = 0; j <= dstY; j++) {
        dp[i][j] = -1;
      }
    }
    return dp;
  }

  private static boolean isSafe(int x, int y, int n, int m) {
    return x >= 0 && x <= n && y >= 0 && y <= m;
  }

  private static boolean isValidCell(int x, int y, int r, List<Integer> xi, List<Integer> yi) {
    for (int i = 0; i < xi.size(); i++) {
      int x1 = xi.get(i);
      int y1 = yi.get(i);
      if (x == x1 && y == y1) {
        return false;
      }
      int dist = (x1 - x) * (x1 - x) + (y1 - y) * (y1 - y);
      if (dist <= r * r) {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    System.out.println(validPath(2, 3, 1, 1, newArrayList(2), newArrayList(3)));
  }

  private static class Cell {
    private int x;
    private int y;

    public Cell(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
}