package com.svetanis.datastructures.array.segregate;

import static com.svetanis.java.base.utils.Nums.isEven;
import static com.svetanis.java.base.utils.Print.print;

// given an unsorted array 
// segregate even and odd numbers

public final class SegregateOddAndEvenExtraSpace {

  public static int[] segregate(int[] a) {
    // Time Complexity: O(n)
	// Auxiliary Space: O(n)  

    int n = a.length;
    int left = 0;
    int right = n - 1;
    int[] seg = new int[n];

    for (int i = 0, j = n - 1; i < j; i++, j--) {
      if (isEven(a[i])) {
        seg[left] = a[i];
        left++;
      } else {
        seg[right] = a[i];
        right--;
      }

      if (isEven(a[j])) {
        seg[left] = a[j];
        left++;
      } else {
        seg[right] = a[j];
        right--;
      }
    }
    return seg;
  }

  public static void main(String[] args) {
    int[] a = { 12, 34, 45, 9, 8, 90, 3 };
    print(segregate(a));
  }
}
