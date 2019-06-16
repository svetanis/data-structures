package com.svetanis.datastructures.graph.bfs;

import static com.google.common.collect.Lists.newLinkedList;

import java.util.Queue;

// returns min num of dice throws required to reach
// last cell from 0'th cell in a snake and ladder game.
// move[] is an array of size N where N is no. of cells
// if there is no snake or ladder from cell i, then move is -1
// otherwise move[i] contains cell to which snake or ladder
// at i takes to

public final class SnakeLadder {

  public static int shortestPath(int n, int[] moves) {
    // Time Complexity: O(n)
    // Space Complexity: O(n)

    boolean[] visited = new boolean[n];
    visited[0] = true;

    Queue<Node> queue = newLinkedList();
    queue.offer(new Node(0, 0));
    while (!queue.isEmpty()) {
      Node front = queue.poll();
      int v = front.v;
      if (v == n - 1) {
        return front.dist;
      }
      for (int j = v + 1; j <= (v + 6) && j < n; j++) {
        if (!visited[j]) {
          visited[j] = true;

          Node node = new Node();
          node.dist = front.dist + 1;

          // check if there a snake or ladder at j
          // then tail of snake or top of ladder
          // become the adjacent of i
          if (moves[j] != -1) {
            node.v = moves[j];
          } else {
            node.v = j;
          }
          queue.offer(node);
        }
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    int n = 30;
    int[] moves = new int[n];
    createMoves(n, moves);
    System.out.println(shortestPath(n, moves));
  }

  private static void createMoves(int n, int[] moves) {
    for (int i = 0; i < n; i++) {
      moves[i] = -1;
    }

    // LADDERS
    moves[2] = 21;
    moves[4] = 7;
    moves[10] = 25;
    moves[19] = 28;

    // SNAKES
    moves[26] = 0;
    moves[20] = 8;
    moves[16] = 3;
    moves[18] = 6;
  }

  private static class Node {
    private int v; // vertex num
    private int dist; // distance of this vertex from src

    public Node() {
    }

    public Node(int v, int dist) {
      this.v = v;
      this.dist = dist;
    }
  }
}
