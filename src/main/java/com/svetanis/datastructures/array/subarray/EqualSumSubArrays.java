package com.svetanis.datastructures.array.subarray;

import static com.svetanis.java.base.utils.Arrays.sum;
import static com.svetanis.java.base.utils.Print.print;

// Given an array of integers greater than zero, 
// find if it is possible to split it in two subarrays 
// (without reordering the elements), 
// such that the sum of the two subarrays is the same.

public final class EqualSumSubArrays {

  public static int split(int[] a) {
    // Time Complexity: O(n)
    
    int n = a.length;
    int left = sum(a);
    int right = 0;
    for(int i = n - 1; i >= 0; i--) {
      right += a[i];
      left -= a[i];
      if (left == right) {
        return i;
      }
    }
    return -1;
  }
  
  public static void main(String[] args) {
    int[] a = {1, 2, 3, 4, 5, 5};
    int index = split(a);
    print(a, index);
    print(a, index, a.length - 1);
  }
}
