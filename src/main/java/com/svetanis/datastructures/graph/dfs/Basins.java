package com.svetanis.datastructures.graph.dfs;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newTreeMap;
import static com.google.common.collect.Ordering.natural;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Maps.newMap;
import static java.util.Arrays.asList;
import static java.util.Arrays.fill;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public final class Basins {

  public static ImmutableList<Integer> basins(List<ImmutableList<Integer>> lists) {
    int n = lists.size();
    int m = lists.get(0).size();
    return basins(asMap(lists), n, m);
  }

  private static ImmutableList<Integer> basins(Map<Integer, List<Node>> map, int n, int m) {
    int count = 0;
    int[][] grid = init(n, m);
    for (int key : map.keySet()) {
      for (Node node : map.get(key)) {
        int x = node.x;
        int y = node.y;

        if (grid[x][y] == -1) {
          grid[x][y] = count++;
        }

        if (x > 0 && grid[x - 1][y] == -1) {
          grid[x - 1][y] = grid[x][y];
        }

        if (x < n - 1 && grid[x + 1][y] == -1) {
          grid[x + 1][y] = grid[x][y];
        }

        if (y > 0 && grid[x][y - 1] == -1) {
          grid[x][y - 1] = grid[x][y];
        }

        if (y < m - 1 && grid[x][y + 1] == -1) {
          grid[x][y + 1] = grid[x][y];
        }

      }
    }

    Integer[] sizes = new Integer[count];
    fill(sizes, 0);
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        sizes[grid[i][j]]++;
      }
    }
    return natural().reverse().immutableSortedCopy(asList(sizes));
  }

  private static int[][] init(int n, int m) {
    int[][] grid = new int[n][m];
    for (int i = 0; i < n; i++) {
      fill(grid[i], -1);
    }
    return grid;
  }

  private static ImmutableMap<Integer, List<Node>> asMap(List<ImmutableList<Integer>> lists) {
    int n = lists.size();
    int m = lists.get(0).size();
    Map<Integer, List<Node>> map = newTreeMap();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        int key = lists.get(i).get(j);
        List<Node> list = map.getOrDefault(key, newArrayList());
        list.add(new Node(i, j));
        map.put(key, list);
      }
    }
    return newMap(map);
  }

  public static void main(String[] agrs) {
    List<ImmutableList<Integer>> lists = newArrayList();
    lists.add(newList(asList(1, 5, 2)));
    lists.add(newList(asList(2, 4, 7)));
    lists.add(newList(asList(3, 6, 9)));
    System.out.println(basins(lists));
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
