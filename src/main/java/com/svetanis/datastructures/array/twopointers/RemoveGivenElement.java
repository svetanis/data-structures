package com.svetanis.datastructures.array.twopointers;

import static com.svetanis.java.base.utils.Print.print;

// Given an array and a value, remove all the instances of that value in the array. 
// Also return the number of elements left in the array after the operation.
// It does not matter what is left beyond the expected length.

public final class RemoveGivenElement {

  public static int remove(int[] a, int target) {
    // Time Complexity: O(n)
    // Space Complexity: O(1)

    int next = 0; // index of the next element which is not target
    for (int i = 0; i < a.length; i++) {
      if (a[i] != target) {
        a[next] = a[i];
        next++;
      }
    }
    return next;
  }

  public static void main(String[] args) {
    int[] a = {4, 1, 1, 2, 1, 3};
    System.out.println(remove(a, 1));
    print(a);
  }
}