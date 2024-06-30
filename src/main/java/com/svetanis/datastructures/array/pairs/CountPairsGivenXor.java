package com.svetanis.datastructures.array.pairs;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Set;

// given an array of positive integers and a number k,
// count pairs whose xor is equal to k

// a[i]^a[j] == k, if and only if a[i]^k == a[j]

public final class CountPairsGivenXor {

  public static int count(int[] a, int k) {
    // Time Complexity: O(n)

    int n = a.length;
    int count = 0;
    Set<Integer> set = newHashSet();
    for (int i = 0; i < n; i++) {
      int xor = k ^ a[i];
      if (set.contains(xor)) {
        count++;
      }
      set.add(a[i]);
    }
    return count;
  }

  public static void main(String[] args) {
    int[] a1 = { 5, 4, 10, 15, 7, 6 };
    System.out.println(count(a1, 5));

    int[] a2 = { 3, 6, 8, 10, 15, 50 };
    System.out.println(count(a2, 5));
  }
}
