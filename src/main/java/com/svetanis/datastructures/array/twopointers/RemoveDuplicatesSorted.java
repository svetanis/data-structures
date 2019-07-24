package com.svetanis.datastructures.array.twopointers;

import static com.svetanis.java.base.utils.Print.print;
import static java.util.Arrays.sort;
import static org.apache.commons.lang3.ArrayUtils.toObject;

public final class RemoveDuplicatesSorted {

  public static int remove(int[] a) {
    // Time Complexity: O(n log n)
    // Space Complexity: O(1)

    int n = a.length;
    sort(a);
    int i = 0;
    for (int j = 1; j < n; j++) {
      if (a[j] != a[i]) {
        i++;
        a[i] = a[j];
      }
    }
    return i + 1;
  }

  public static void main(String[] args) {
    int[] a = { 6, 7, 7, 3, 1, 5, 5, 9, 9, 3 };
    int k = remove(a);
    print(toObject(a), k - 1);
  }
}