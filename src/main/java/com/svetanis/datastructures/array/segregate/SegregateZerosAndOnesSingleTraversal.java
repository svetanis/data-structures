package com.svetanis.datastructures.array.segregate;

import static com.svetanis.java.base.utils.Print.print;
import static com.svetanis.java.base.utils.Swap.swap;

// You are given an array of 0s and 1s in random order. 
// Segregate 0s on left side and 1s on right side of the array. 
// Traverse array only once.

// Maintain two indexes. Initialize first index left as 0 and second index right as n-1.
// Do following while left < right
// a) Keep incrementing index left while there are 0s at it
// b) Keep decrementing index right while there are 1s at it
// c) If left < right then exchange arr[left] and arr[right]

public final class SegregateZerosAndOnesSingleTraversal {

  public static void segregate(Integer[] a) {
    // O(n)

    int n = a.length;
    int left = 0;
    int right = n - 1;

    while (left < right) {
      while (a[left] == 0 && left < right) {
        left++;
      }
      while (a[right] == 1 && left < right) {
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
    Integer[] a = { 0, 1, 0, 1, 1, 1 };
    segregate(a);
    print(a);
  }
}