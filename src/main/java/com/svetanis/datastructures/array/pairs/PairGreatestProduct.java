package com.svetanis.datastructures.array.pairs;

import static com.svetanis.java.base.utils.Maps.freqMap;
import static com.svetanis.java.base.utils.Nums.isDivisible;
import static java.lang.Math.sqrt;
import static java.util.Arrays.sort;
import java.util.Map;

// given array, find the greatest number such that
// it is product of two elements of given array

// if no pair till sqrt than no such pair exists

public final class PairGreatestProduct {

  public static int product(int[] a) {
    // Time Complexity: O(n * log n)

    int n = a.length;
    Map<Integer, Integer> map = freqMap(a);
    sort(a);
    
    for (int i = n - 1; i > 1; i--) {
      for (int j = 0; j < i && a[j] <= sqrt(a[i]); j++) {
        if (isDivisible(a[i], a[j])) {
          int result = a[i] / a[j];
          int freq = map.getOrDefault(result, 0);
          if (result != a[j] && freq > 0) {
            return a[i];
          } else if (result == a[j] && freq > 1) {
            return a[i];
          }

        }
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    int[] a = { 30, 10, 9, 3, 35 };
    System.out.println(product(a));
  }
}
