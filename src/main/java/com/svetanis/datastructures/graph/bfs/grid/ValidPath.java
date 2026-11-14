package com.svetanis.datastructures.graph.bfs.grid;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// Reach the Far Corner Without Touching a Circle (not a LeetCode problem)
//
// Input: a rectangle with its bottom-left corner at (0, 0) and its top-right corner at
// (dstX, dstY); n circles of the same radius r, whose centres (xi[i], yi[i]) are whole-number
// points inside the rectangle. You move between whole-number points, one step to any of the 8
// neighbours (sides and corners), and a point is blocked if it lies inside a circle or on its
// edge, that is, its distance to a centre is at most r. The parameter n is not used; the number
// of circles is xi.size().
// Returns true if you can go from (0, 0) to (dstX, dstY) without touching any circle. If (0, 0)
// itself touches a circle the answer is false.
//
// The one idea: one queue entry is a point you can stand on. dp[x][y] says what is known about
// the point (x, y): -1 not looked at yet, 1 reachable and clear, 2 blocked by a circle. A point
// is judged once, the first time a neighbour reaches it: if a circle covers it, it gets 2;
// otherwise it gets 1 and joins the queue so its own neighbours are tried. The search stops as
// soon as the destination has been judged, and the destination is reachable exactly when it
// got a 1.
//
// Time: O(dstX * dstY * N) -- every point is judged once, against all N circles, and joins the
// queue at most once with 8 neighbours looked at.
// Space: O(dstX * dstY) -- the dp table and the queue.
//
// Siblings: none -- this is the only file for this problem.

public final class ValidPath {

  private static final int N = 8;
  private static final int[] dx = { 1, 1, 1, 0, -1, -1, -1, 0 };
  private static final int[] dy = { -1, 0, 1, 1, 1, 0, -1, -1 };

  public static boolean validPath(int dstX, int dstY, int n, int r, List<Integer> xi, List<Integer> yi) {
    if (!isValidCell(0, 0, r, xi, yi)) {
      return false; // RETURN: the start itself touches a circle
    }
    int[][] dp = init(dstX, dstY);
    dp[0][0] = 1; // src = (0, 0)

    LinkedList<Cell> queue = new LinkedList<>();
    queue.add(new Cell(0, 0));

    while (queue.size() != 0) {
      Cell cell = queue.poll(); // TAKE: a clear point
      for (int i = 0; i < N; i++) {
        int x = cell.x + dx[i];
        int y = cell.y + dy[i];

        if (isSafe(x, y, dstX, dstY)) {
          if (dp[x][y] == -1) { // JUDGE: first visit to this point
            if (!isValidCell(x, y, r, xi, yi)) {
              dp[x][y] = 2; // BLOCKED: covered by a circle
            } else {
              dp[x][y] = 1; // CLEAR
              queue.add(new Cell(x, y));
            }
          }
        }
      }

      if (dp[dstX][dstY] != -1) {
        break; // STOP: the destination has been judged
      }
    }

    return dp[dstX][dstY] == 1; // RETURN
  }

  private static int[][] init(int dstX, int dstY) {
    int[][] dp = new int[dstX + 1][dstY + 1];
    for (int i = 0; i <= dstX; i++) {
      for (int j = 0; j <= dstY; j++) {
        dp[i][j] = -1; // BASE: not looked at yet
      }
    }
    return dp;
  }

  private static boolean isSafe(int x, int y, int n, int m) {
    return x >= 0 && x <= n && y >= 0 && y <= m; // SKIP: outside the rectangle
  }

  private static boolean isValidCell(int x, int y, int r, List<Integer> xi, List<Integer> yi) {
    for (int i = 0; i < xi.size(); i++) {
      int x1 = xi.get(i);
      int y1 = yi.get(i);
      if (x == x1 && y == y1) {
        return false; // the point is a centre
      }
      int dist = (x1 - x) * (x1 - x) + (y1 - y) * (y1 - y); // squared distance to the centre
      if (dist <= r * r) {
        return false; // inside or on the edge of this circle
      }
    }
    return true;
  }

  public static void main(String[] args) {
    System.out.println(validPath(2, 3, 1, 1, Arrays.asList(2), Arrays.asList(3))); // false

    List<Integer> none = Collections.emptyList();
    System.out.println(validPath(3, 3, 0, 0, none, none)); // true

    System.out.println(validPath(3, 3, 1, 1, Arrays.asList(0), Arrays.asList(0))); // false

    System.out.println(validPath(3, 3, 3, 0, Arrays.asList(1, 1, 1), Arrays.asList(0, 1, 2))); // true
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