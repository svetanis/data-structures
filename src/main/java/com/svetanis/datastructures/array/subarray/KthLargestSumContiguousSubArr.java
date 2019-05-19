package com.svetanis.datastructures.array.subarray;

import java.util.PriorityQueue;
import java.util.Queue;

public final class KthLargestSumContiguousSubArr {

  public static int kthLargestSum(int[] a, int k) {
    int n = a.length;
    int[] prefix = prefixSum(a);
    Queue<Integer> queue = new PriorityQueue<Integer>();

    for (int i = 1; i <= n; i++) {
      for (int j = i; j <= n; j++) {
        int sum = prefix[j] - prefix[i - 1];
        if (queue.size() < k) {
          queue.offer(sum);
        } else {
          if (queue.peek() < sum) {
            queue.poll();
            queue.offer(sum);
          }
        }
      }
    }
    return queue.poll();
  }

  private static int[] prefixSum(int[] a) {
    int n = a.length;
    int[] sum = new int[n + 1];
    sum[0] = 0;
    sum[1] = a[0];
    for (int i = 2; i <= n; i++) {
      sum[i] = sum[i - 1] + a[i - 1];
    }
    return sum;
  }

  public static void main(String[] args) {
    int[] a = { 10, -10, 20, -40 };
    System.out.println(kthLargestSum(a, 6));
  }
}
