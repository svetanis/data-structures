package com.svetanis.datastructures.array.segregate;

import static com.svetanis.java.base.utils.Nums.isEven;
import static com.svetanis.java.base.utils.Nums.isOdd;
import static com.svetanis.java.base.utils.Print.print;
import static com.svetanis.java.base.utils.Swap.swap;

// given an unsorted array 
// segregate even and odd numbers

public final class SegregateOddAndEvenInPlace {

  public static void segregate(int[] a) {
    // Time Complexity: O(n)

    int left = 0;
    int right = a.length - 1;

    while (left < right) {

      while (isEven(a[left]) && left < right) {
        left++;
      }

      while (isOdd(a[right]) && left < right) {
        right--;
      }

      if (left < right) {
        swap(a, left, right);
        left++;
        right--;
      }
    }
  }

  public static void main(String[] args) {
    //int[] a = { 12, 34, 45, 9, 8, 90, 3 };
    int[] a = {3, 4};
    segregate(a);
    print(a);
    
    int[] a1 = {4, 9, 5, 2, 9, 5, 7, 10};
    segregate(a1);
    print(a1);
  }
}
