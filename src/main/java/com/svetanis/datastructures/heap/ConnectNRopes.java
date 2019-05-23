package com.svetanis.datastructures.heap;

import java.util.PriorityQueue;
import java.util.Queue;

public final class ConnectNRopes {

  public static int minCost(int[] a) {
    int cost = 0;
    Queue<Integer> queue = build(a);
    
    while (queue.size() > 1) {
      // extract shortest two ropes from priority queue
      int first = queue.poll();
      int second = queue.poll();
      // connect the ropes: update result and
      // insert the new rope to priority queue
      cost += (first + second);
      queue.offer(first + second);
    }
    return cost;
  }

  private static Queue<Integer> build(int[] a) {
    int n = a.length;
    Queue<Integer> queue = new PriorityQueue<>();
    for (int i = 0; i < n; i++) {
      queue.offer(a[i]);
    }
    return queue;
  }

  public static void main(String[] args) {
    int[] a = { 4, 3, 2, 6 };
    System.out.println(minCost(a));
  }
}

