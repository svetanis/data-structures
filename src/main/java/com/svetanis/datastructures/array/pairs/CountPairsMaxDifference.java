package com.svetanis.datastructures.array.pairs;

import static com.svetanis.java.base.utils.Arrays.max;
import static com.svetanis.java.base.utils.Arrays.min;

// given an array of n integers,
// find the number of ways of
// choosing pairs with max difference

public final class CountPairsMaxDifference {
  // Time Complexity: O(n)
	
  public static int countPairs(int[] a) {

	int n = a.length;
    int min = min(a);
    int max = max(a);
    int minCount = 0;
    int maxCount = 0;
    
    for (int i = 0; i < n; i++) {
      if (a[i] == min) {
        minCount++;
      }
      if (a[i] == max) {
        maxCount++;
      }
    }
    if (min == max) {
      return n * (n - 1) / 2;
    } else {
      return minCount * maxCount;
    }
  }

  public static void main(String[] args) {
    int[] a1 = { 3, 2, 1, 1, 3 };
    System.out.println(countPairs(a1));
    int[] a2 = { 2, 4, 1, 1 };
    System.out.println(countPairs(a2));
  }
}
