package com.svetanis.datastructures.array.pairs;

import static java.util.Arrays.sort;

// given an array of size n
// find the number of pairs (i,j) s.t.
// a[i] xor a[j] == 0, 1<=i<j<=n

// if frequency of some element is k then, it
// will contribute k * (k - 1)/2 to the answer

public final class CountPairsXorZeroSorting {

  public static int countPairs(int[] a) {
    // Time Complexity: O(n log n)

    sort(a);

    int sum = 0;
    int count = 1;
    int n = a.length;

    for (int i = 1; i < n; i++) {
      if (a[i] == a[i - 1]) {
        count++;
      } else {
        sum += sum(count);
        count = 1;
      }
    }
    return sum += sum(count);
  }

  private static int sum(int n) {
    return (n * (n - 1)) / 2;
  }

  public static void main(String[] args) {
    int[] a = { 1, 2, 1, 2, 4 };
    System.out.println(countPairs(a));
  }
}
