package com.svetanis.datastructures.graph.dfs;

import static java.util.Arrays.asList;
import static java.util.Arrays.fill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// Basins -- not a LeetCode problem.
//
// Input: a rectangular grid of heights, at least one cell. Water on a cell runs to its lowest
// neighbour (up, down, left, right) while that neighbour is lower than the cell, and stops in a
// cell with no lower neighbour. Returns the size of every basin -- the cells whose water stops
// in the same place -- biggest first.
//
// The one idea: grid[x][y] holds the number of the basin that cell belongs to, or -1 while it
// has not been given one. Take the cells from the lowest height to the highest. A cell still
// at -1 when its turn comes has no lower neighbour that claimed it, so it is the bottom of a new
// basin and gets the next number. Then it hands its number to every neighbour still at -1. A
// neighbour that is still unclaimed is at least as high as this cell, and any lower neighbour
// would have been taken earlier and would have claimed it first, so this cell is its lowest
// neighbour and the water does run here. Counting the cells per number gives the sizes. When
// two neighbours have the same height the one taken first claims the cell; the grid does not
// say which is meant.
//
// Time: O(n * m * log(n * m)) -- the TreeMap keeps the heights in order, each cell is handled
// once with four constant-time looks, and the sizes are sorted at the end.
// Space: O(n * m) -- the map of height to cells, and the grid of basin numbers.
//
// Siblings: BasinsIK, in the ik package, holds the same method.

public final class Basins {

  public static List<Integer> basins(List<List<Integer>> lists) {
    int n = lists.size();
    int m = lists.get(0).size();
    return basins(asMap(lists), n, m);
  }

  private static List<Integer> basins(Map<Integer, List<Node>> map, int n, int m) {
    int count = 0;
    int[][] grid = init(n, m);
    for (int key : map.keySet()) { // SORT: lowest height first (the map is ordered by height)
      for (Node node : map.get(key)) {
        int x = node.x;
        int y = node.y;

        if (grid[x][y] == -1) {
          grid[x][y] = count++; // TAKE: nobody lower claimed it, a new basin starts here
        }

        if (x > 0 && grid[x - 1][y] == -1) {
          grid[x - 1][y] = grid[x][y]; // CLAIM: the neighbour above joins this basin
        }

        if (x < n - 1 && grid[x + 1][y] == -1) {
          grid[x + 1][y] = grid[x][y]; // CLAIM: below
        }

        if (y > 0 && grid[x][y - 1] == -1) {
          grid[x][y - 1] = grid[x][y]; // CLAIM: left
        }

        if (y < m - 1 && grid[x][y + 1] == -1) {
          grid[x][y + 1] = grid[x][y]; // CLAIM: right
        }

      }
    }

    Integer[] sizes = new Integer[count];
    fill(sizes, 0);
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        sizes[grid[i][j]]++; // COUNT: one more cell in this basin
      }
    }
    List<Integer> result = new ArrayList<>(asList(sizes));
    result.sort(Collections.reverseOrder()); // SORT: biggest basin first
    return result; // RETURN: the sizes
  }

  private static int[][] init(int n, int m) {
    int[][] grid = new int[n][m];
    for (int i = 0; i < n; i++) {
      fill(grid[i], -1);
    }
    return grid;
  }

  private static Map<Integer, List<Node>> asMap(List<List<Integer>> lists) {
    int n = lists.size();
    int m = lists.get(0).size();
    Map<Integer, List<Node>> map = new TreeMap<>();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        int key = lists.get(i).get(j);
        List<Node> list = map.getOrDefault(key, new ArrayList<>());
        list.add(new Node(i, j)); // GROUP: the cells of each height
        map.put(key, list);
      }
    }
    return map;
  }

  public static void main(String[] agrs) {
    List<List<Integer>> lists = new ArrayList<>();
    lists.add(asList(1, 5, 2));
    lists.add(asList(2, 4, 7));
    lists.add(asList(3, 6, 9));
    System.out.println(basins(lists)); // [7, 2]

    List<List<Integer>> flat = new ArrayList<>();
    flat.add(asList(5));
    System.out.println(basins(flat)); // [1]

    List<List<Integer>> ridge = new ArrayList<>();
    ridge.add(asList(1, 9, 2));
    System.out.println(basins(ridge)); // [2, 1]
  }

  private static class Node {
    private int x;
    private int y;

    public Node(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
}
