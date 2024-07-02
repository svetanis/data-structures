package com.svetanis.datastructures.array.pairs;

import static com.svetanis.java.base.utils.Arrays.sum;
import static com.svetanis.java.base.utils.Nums.isOdd;
import static java.util.Arrays.sort;

import com.svetanis.java.base.Pair;

// given two arrays of integers,
// find a pair of values (one value from each array)
// that can be swapped so that both arrays have the same sum

public final class SwapPairsSort {
	// Time complexity: O(n * log n + m * log m)

  public static Pair<Integer, Integer> swap(int[] a, int[] b) {
    sort(a);
    sort(b);

    int diff = diff(a, b);
    if (diff == 0) {
      return Pair.build(-1, -1);
    }

    int i = 0;
    int j = 0;
    while (i < a.length && j < b.length) {
      int d = a[i] - b[j];
      if (d == diff) {
        return Pair.build(a[i], b[j]);
      } else if (d < diff) {
        i++;
      } else {
        j++;
      }
    }
    return Pair.build(-1, -1);
  }

  private static int diff(int[] a, int[] b) {
    int sum1 = sum(a);
    int sum2 = sum(b);
    if (isOdd(sum1 - sum2)) {
      return 0;
    } else {
      return (sum1 - sum2) / 2;
    }
  }

  public static void main(String[] args) {
    int[] a = { 4, 1, 2, 1, 1, 2 };
    int[] b = { 3, 6, 3, 3 };
    System.out.println(swap(a, b));

    int[] c = { 5, 7, 4, 6 };
    int[] d = { 1, 2, 3, 8 };
    System.out.println(swap(c, d));
    
    int[] e = { 4, 1, 2, 1, 1, 2 };
    int[] f = { 1, 6, 3, 3 };
    System.out.println(swap(e, f));
  }
}
