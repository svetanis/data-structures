package com.svetanis.datastructures.array.subarray;

import java.util.PriorityQueue;
import java.util.Queue;

// Kth largest sum of a contiguous subarray (not a LeetCode problem).
//
// Input: an integer array a with n >= 1 elements, and k with 1 <= k <= n * (n + 1) / 2, the number
// of non-empty contiguous subarrays. Returns the kth largest of the sums of all those subarrays,
// counting equal sums separately (for a = { 10, -10, 20, -40 } the ten sums are 10, 0, 20, -20,
// -10, 10, -30, 20, -20, -40, and the 6th largest is -10).
//
// The one idea: prefix[j] - prefix[i - 1] is the sum of a[i - 1 .. j - 1] (1-based prefix, with
// prefix[0] = 0), so every subarray sum costs one subtraction. The heap is a min-heap of the k
// largest sums so far: its top is the smallest of those k, which is the current kth largest. A new
// sum is added while the heap holds fewer than k sums; after that it replaces the top only if it
// is larger. When all sums have been offered the top is the kth largest.
//
// Time: O(n^2 log k) -- n * (n + 1) / 2 sums, each offered to a heap of at most k entries.
// Space: O(n + k) -- the prefix array and the heap.

public final class KthLargestSumContiguousSubArr {

  public static int kthLargestSum(int[] a, int k) {
    int n = a.length;
    int[] prefix = prefixSum(a);
    Queue<Integer> queue = new PriorityQueue<Integer>(); // MIN-HEAP: the k largest sums so far

    for (int i = 1; i <= n; i++) {
      for (int j = i; j <= n; j++) { // WALK: every subarray a[i - 1 .. j - 1]
        int sum = prefix[j] - prefix[i - 1];
        if (queue.size() < k) {
          queue.offer(sum); // FILL: the heap is not full yet
        } else {
          if (queue.peek() < sum) { // BEAT: larger than the smallest of the k kept
            queue.poll();
            queue.offer(sum);
          }
        }
      }
    }
    return queue.poll(); // RETURN: the smallest of the k largest
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
    System.out.println(kthLargestSum(a, 6)); // -10
    System.out.println(kthLargestSum(a, 1)); // 20
    System.out.println(kthLargestSum(a, 10)); // -40
  }
}
