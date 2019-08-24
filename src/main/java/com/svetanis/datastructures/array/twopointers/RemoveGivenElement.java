package com.svetanis.datastructures.array.twopointers;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;

// Given an array and a value, remove all the instances of that value in the array. 
// Also return the number of elements left in the array after the operation.
// It does not matter what is left beyond the expected length.

public final class RemoveGivenElement {

  public static int remove(List<Integer> a, int target) {
    // Time Complexity: O(n)
    // Space Complexity: O(1)

    int i = 0;
    int n = a.size();
    for (int j = 0; j < n; j++) {
      if (!a.get(j).equals(target)) {
        a.set(i, a.get(j));
        i++;
      }
    }
    for (int k = i; k < n; k++) {
      a.set(k, null);
    }
    return i;
  }

  public static void main(String[] args) {
    List<Integer> a = newArrayList(4, 1, 1, 2, 1, 3);
    System.out.println(remove(a, 1));
    print(a);
  }
}