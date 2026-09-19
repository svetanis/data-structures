package com.svetanis.datastructures.graph.shortestpath;

import static java.util.Comparator.comparingInt;

import java.util.Arrays;
import java.util.PriorityQueue;

// 1066. Campus Bikes II
// Dijkstra over a graph that is not in the input: a node is the SET of bikes
// already handed out, and the worker being served is that set's size, because
// bikes go out in worker order. CampusBikesBacktracking.java is the same
// problem by exhaustive search.

public final class CampusBikesDijkstra {
  // Time Complexity: O(2^m * m * log(2^m * m))
  // Space Complexity: O(2^m)
  //
  // every edge sets one more bit, so the state graph is ACYCLIC and the heap
  // is not earning its place -- a sweep over masks in increasing bit count
  // settles each one on arrival. it is written with a heap because the shape
  // is the lesson: a shortest path over states that the statement never names.
  //
  // m is the bike count and 1 << m is the table, so this is only viable while
  // m stays small. LC 1066 caps it at 10.

  private static final int INF = 1 << 30;

  public int assignBikes(int[][] workers, int[][] bikes) {
    int n = workers.length;
    int m = bikes.length;
    int[] dist = new int[1 << m];
    Arrays.fill(dist, INF);
    dist[0] = 0;

    // entry is { mask, cost so far }, ordered on the cost
    PriorityQueue<int[]> pq = new PriorityQueue<>(comparingInt(a -> a[1]));
    pq.offer(new int[] { 0, 0 });

    while (!pq.isEmpty()) {
      int[] curr = pq.poll();
      int mask = curr[0];
      int cost = curr[1];
      // the worker is not stored: it is how many bikes have gone out
      int worker = Integer.bitCount(mask);
      if (worker == n) {
        return cost;
      }
      if (cost > dist[mask]) {
        continue;
      }
      handOutOneBike(workers[worker], bikes, mask, cost, dist, pq);
    }
    return -1;
  }

  private static void handOutOneBike(int[] worker, int[][] bikes, int mask, //
      int cost, int[] dist, PriorityQueue<int[]> pq) {
    for (int bike = 0; bike < bikes.length; bike++) {
      if ((mask & (1 << bike)) != 0) {
        continue;
      }
      int next = mask | (1 << bike);
      int step = cost + mdist(worker, bikes[bike]);
      if (step < dist[next]) {
        dist[next] = step;
        pq.offer(new int[] { next, step });
      }
    }
  }

  private static int mdist(int[] worker, int[] bike) {
    int dx = Math.abs(worker[0] - bike[0]);
    int dy = Math.abs(worker[1] - bike[1]);
    return dx + dy;
  }

  public static void main(String[] args) {
    CampusBikesDijkstra cb = new CampusBikesDijkstra();
    int[][] w1 = { { 0, 0 }, { 2, 1 } };
    int[][] b1 = { { 1, 2 }, { 3, 3 } };
    System.out.println(cb.assignBikes(w1, b1)); // 6

    int[][] w2 = { { 0, 0 }, { 1, 1 }, { 2, 0 } };
    int[][] b2 = { { 1, 0 }, { 2, 2 }, { 2, 1 } };
    System.out.println(cb.assignBikes(w2, b2)); // 4

    int[][] w3 = { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 }, { 4, 0 } };
    int[][] b3 = { { 0, 999 }, { 1, 999 }, { 2, 999 }, { 3, 999 }, { 4, 999 } };
    System.out.println(cb.assignBikes(w3, b3)); // 4995

    // eleven bikes. LC 1066 promises at most ten, and the backtracking file's
    // visited board was once sized at a literal 10, so this threw there
    int[][] w4 = { { 0, 0 } };
    int[][] b4 = { { 0, 1 }, { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 }, //
        { 6, 1 }, { 7, 1 }, { 8, 1 }, { 9, 1 }, { 10, 1 } };
    System.out.println(cb.assignBikes(w4, b4)); // 1

    // fewer workers than bikes: two bikes are never handed out at all
    int[][] w5 = { { 0, 0 }, { 5, 5 } };
    int[][] b5 = { { 0, 1 }, { 9, 9 }, { 5, 4 }, { 8, 0 } };
    System.out.println(cb.assignBikes(w5, b5)); // 2
  }
}
