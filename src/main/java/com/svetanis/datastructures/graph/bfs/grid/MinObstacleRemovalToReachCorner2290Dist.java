package com.svetanis.datastructures.graph.bfs.grid;

import java.util.ArrayDeque;
import java.util.Arrays;
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
// 0 or 1, so a deque keeps that order with no heap: a free neighbour goes to the FRONT and an
// obstacle to the BACK. dist[r][c] holds the fewest obstacles found so far on the way to a cell,
// and a cell is placed again only when a move gives it a smaller total -- without that check two
// free cells hand each other back for ever. One deque entry is {row, col}; its total is in dist.
//
// Time: O(m * n) -- one deque pop per cell on a 0/1 grid.
// Space: O(m * n) -- the dist table and the deque.
// Known limit: with costs above 1 the deque is no longer sorted; the check still repairs the
// answer, but cells come out many times -- use a heap then.
//
// Siblings: MinObstacleRemovalToReachCorner2290 places every move and marks a cell the first time
// it comes out, returning as soon as the corner does.

public final class MinObstacleRemovalToReachCorner2290Dist {

  public static int minObstacles(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int[][] dist = new int[m][n]; // DIST: fewest obstacles removed to reach (r, c), found so far
    for (int[] row : dist) {
      Arrays.fill(row, Integer.MAX_VALUE);
    }
    int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
    Deque<int[]> deque = new ArrayDeque<>(); // ENTRY: {row, col}
    deque.offerFirst(new int[] { 0, 0 }); // START: the top-left cell
    dist[0][0] = grid[0][0]; // START: its own value, 0 -- set before the loop: MAX_VALUE + 1 wraps
    while (!deque.isEmpty()) {
      int[] cell = deque.pollFirst(); // NEXT: the smallest total waiting
      int x = cell[0], y = cell[1];
      for (int[] d : dirs) {
        int r = x + d[0], c = y + d[1];
        if (r < 0 || r >= m || c < 0 || c >= n) {
          continue; // INSIDE: skip a move off the grid
        }
        int total = dist[x][y] + grid[r][c]; // TOTAL: the cell stepped onto is the cost
        if (total < dist[r][c]) { // BETTER: only an improvement is placed
          dist[r][c] = total;
          if (grid[r][c] == 0) {
            deque.offerFirst(new int[] { r, c }); // FREE: same total, front
          } else {
            deque.offerLast(new int[] { r, c }); // OBSTACLE: total + 1, back
          }
        }
      }
    }
    return dist[m - 1][n - 1];
  }

  public static void main(String[] args) {
    int[][] grid1 = { { 0, 1, 1 }, { 1, 1, 0 }, { 1, 1, 0 } };
    System.out.println(minObstacles(grid1)); // 2

    int[][] grid2 = { { 0, 1, 0, 0, 0 }, { 0, 1, 0, 1, 0 }, { 0, 0, 0, 1, 0 } };
    System.out.println(minObstacles(grid2)); // 0
  }
}
