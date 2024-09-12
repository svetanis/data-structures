package com.svetanis.datastructures.graph.bfs;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Queue;

public final class KnightShortestPath {

  // Time Complexity: O(n*m)
  // Space Complexity: O(n*m)

  private static final int N = 8;
  private static final int[] dx = { 2, 1, -1, -2, -2, -1, 1, 2 };
  private static final int[] dy = { 1, 2, 2, 1, -1, -2, -2, -1 };

  public static int knight(int n, int m, int srcX, int srcY, int dstX, int dstY) {

    Node src = new Node(srcX, srcY, 0);
    boolean[][] visited = new boolean[n][m];
    Queue<Node> queue = newLinkedList();
    queue.offer(src);
    visited[srcX][srcY] = true;

    while (!queue.isEmpty()) {
      Node node = queue.poll();
      if (node.x == dstX && node.y == dstY) {
        return node.dist;
      }

      for (int dir = 0; dir < N; ++dir) {
        int x = node.x + dx[dir];
        int y = node.y + dy[dir];
        if (isSafe(x, y, n, m) && !visited[x][y]) {
          visited[x][y] = true;
          queue.offer(new Node(x, y, node.dist + 1));
        }
      }
    }
    return -1;
  }

  private static boolean isSafe(int x, int y, int n, int m) {
    return x >= 0 && x < n && y >= 0 && y < m;
  }

  public static void main(String[] args) {
    System.out.println(knight(5, 5, 0, 0, 4, 1));
  }
}
