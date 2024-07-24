package com.svetanis.datastructures.array.triplet;

import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.lang3.ArrayUtils.toObject;

import java.util.Set;

// given array of integers with no duplicates,
// count number of unique triplets such that
// their XOR is 0.

public final class CountTripletsXorZero {

  public static int count(int[] a) {
    // Time Complexity: O(n^2)
    
    int n = a.length;
    Set<Integer> set = newHashSet(toObject(a));
    int count = 0;
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        int xor = a[i] ^ a[j];
        if (set.contains(xor) && xor != a[i] && xor != a[j]) {
          count++;
        }
      }
    }
    return count / 3;
  }

  public static void main(String[] args) {
    int[] a = { 1, 3, 5, 10, 14, 15 };
    System.out.println(count(a));
  }
}
