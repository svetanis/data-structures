package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Deque;

// 2290. Minimum Obstacle Removal to Reach Corner
//
// Input: a grid where 0 is an empty cell and 1 is an obstacle that may be removed. You start at
// the top-left cell and want the bottom-right cell, moving up, down, left or right. On LeetCode:
// 1 <= rows, cols <= 10^5, 2 <= rows * cols <= 10^5, and both corner cells are 0.
// Returns the fewest obstacles that must be removed to get from the top-left to the bottom-right
// cell.
//
// The one idea: a move costs the value of the cell stepped onto -- 1 for an obstacle, 0 for an
// empty cell -- and cells must come out smallest total first, as in Dijkstra. Every move adds only
// 0 or 1, so a deque keeps that order with no heap: a free step goes to the FRONT and a costly one
// to the BACK. One deque entry is {row, col, total}: a cell and the obstacles removed on the way to
// it. Every move is placed; because the smallest total always comes out first, the first time a
// cell comes out its total is final, so it is marked then and its later copies are skipped; the
// first time the bottom-right cell comes out, its total is the answer.
//
// Time: O(m * n) -- each cell is expanded once (4 moves); at most 4 deque entries per cell.
// Space: O(m * n) -- the visited table and the deque.
//
// Siblings: MinObstacleRemovalToReachCorner2290Dist is the same deque with the best total per cell
// kept in a table, placing a cell again only when its total improves. MinCost is the same
// free-front / paid-back deque with the price on leaving a cell against its arrow instead of on
// entering an obstacle.

public final class MinObstacleRemovalToReachCorner2290 {

  public static int minObstacles(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
    boolean[][] visited = new boolean[m][n]; // TAKEN: true once a cell has come out
    Deque<int[]> deque = new ArrayDeque<>(); // ENTRY: {row, col, total removed on the way}
    deque.offerFirst(new int[] { 0, 0, grid[0][0] }); // START: the top-left cell, its own value: 0
    while (!deque.isEmpty()) {
      int[] cell = deque.pollFirst(); // NEXT: the smallest total waiting
      int x = cell[0], y = cell[1], total = cell[2];
      if (x == m - 1 && y == n - 1) {
        return total; // RETURN: the first time the corner comes out, its total is final
      }
      if (visited[x][y]) {
        continue; // SKIP: this cell already came out with a total no larger
      }
      visited[x][y] = true; // MARK: when it comes out, not when it is placed
      for (int[] d : dirs) {
        int r = x + d[0], c = y + d[1];
        if (r < 0 || r >= m || c < 0 || c >= n) {
          continue; // INSIDE: skip a move off the grid
        }
        if (grid[r][c] == 0) {
          deque.offerFirst(new int[] { r, c, total }); // FREE: same total, front
        } else {
          deque.offerLast(new int[] { r, c, total + 1 }); // OBSTACLE: total + 1, back
        }
      }
    }
    return -1; // RETURN: not reached while the corners are empty -- every cell can be entered
  }

  public static void main(String[] args) {
    int[][] grid1 = { { 0, 1, 1 }, { 1, 1, 0 }, { 1, 1, 0 } };
    System.out.println(minObstacles(grid1)); // 2

    int[][] grid2 = { { 0, 1, 0, 0, 0 }, { 0, 1, 0, 1, 0 }, { 0, 0, 0, 1, 0 } };
    System.out.println(minObstacles(grid2)); // 0
  }
}
