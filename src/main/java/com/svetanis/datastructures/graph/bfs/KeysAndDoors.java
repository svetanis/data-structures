package com.svetanis.datastructures.graph.bfs;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Lists.transform;
import static com.svetanis.java.base.utils.Print.printLines;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;

public final class KeysAndDoors {

  // Time & Space Complexity: O(n*m*2^10).

  private static final char SRC = '@';
  private static final char DST = '+';

  private static final int N = 4;
  private static final int[] dx = { -1, 0, 1, 0 };
  private static final int[] dy = { 0, -1, 0, 1 };

  public static ImmutableList<Pair<Integer, Integer>> find_shortest_path(String[] grid) {
    Node src = getSrc(grid, SRC);
    List<Node> nodes = shortestPath(src, grid);
    return transform(nodes, n -> Pair.build(n.x, n.y));
  }

  private static ImmutableList<Node> shortestPath(Node src, String[] grid) {
    boolean[][][] visited = new boolean[grid.length][grid[0].length()][1024];
    visited[src.x][src.y][src.key] = true;
    Queue<Node> queue = new LinkedList<>();
    queue.offer(src);
    
    while (!queue.isEmpty()) {
      Node node = queue.poll();
      int key = node.key;
      if (isDst(node.x, node.y, grid)) {
        List<Node> list = new ArrayList<>();
        while (node != null) {
          list.add(0, node);
          node = node.parent;
        }
        return newList(list);
      }

      for (Node n : neighbors(node, grid)) {
        if (!visited[n.x][n.y][n.key]) {
          visited[n.x][n.y][key] = true;
          queue.offer(n);
        }
      }
    }
    return newList();
  }

  private static ImmutableList<Node> neighbors(Node node, String[] grid) {
    List<Node> list = newArrayList();
    for (int i = 0; i < N; i++) {
      int x = node.x + dx[i];
      int y = node.y + dy[i];
      if (!isSafe(x, y, grid.length, grid[0].length())) {
        continue;
      }
      boolean water = isWater(x, y, grid);
      boolean hasKey = hasKey(node.key, grid[x].charAt(y));
      boolean lockedDoor = isDoor(x, y, grid) && !hasKey;
      if (water || lockedDoor) {
        continue;
      }
      int key = node.key;
      if (isKey(x, y, grid)) {
        key = addKey(key, grid[x].charAt(y));
      }
      list.add(new Node(x, y, key, node));
    }
    return newList(list);
  }

  private static Node getSrc(String[] grid, char c) {
    for (int x = 0; x < grid.length; x++) {
      for (int y = 0; y < grid[0].length(); y++) {
        if (grid[x].charAt(y) == c)
          return new Node(x, y, 0, null);// no keys
      }
    }
    return null;
  }

  private static boolean hasKey(int keys, char door) {
    return (keys & (1 << (door - 'A'))) > 0;
  }

  private static int addKey(int keys, char key) {
    return keys | (1 << (key - 'a'));
  }

  private static boolean isSafe(int r, int c, int n, int m) {
    return r >= 0 && r < n && c >= 0 && c < m;
  }

  private static boolean isWater(int r, int c, String[] grid) {
    return grid[r].charAt(c) == '#';
  }

  private static boolean isDoor(int r, int c, String[] grid) {
    return grid[r].charAt(c) >= 'A' && grid[r].charAt(c) <= 'J';
  }

  private static boolean isKey(int r, int c, String[] grid) {
    return grid[r].charAt(c) >= 'a' && grid[r].charAt(c) <= 'j';
  }

  private static boolean isDst(int r, int c, String[] grid) {
    return grid[r].charAt(c) == DST;
  }

  private static class Node {
    private int x;
    private int y;
    private int key;
    private Node parent;

    public Node(int x, int y, int key, Node parent) {
      this.x = x;
      this.y = y;
      this.key = key;
      this.parent = parent;
    }
  }

  // [“...B”, “.b#.”, “@#+.”]
  // n = 3, m = 4

  public static void main(String[] args) {
    String[] a = { "...B", ".b#.", "@#+." };
    printLines(find_shortest_path(a));
  }
  // 2 0
  // 1 0
  // 1 1
  // 0 1
  // 0 2
  // 0 3
  // 1 3
  // 2 3
  // 2 2
}
