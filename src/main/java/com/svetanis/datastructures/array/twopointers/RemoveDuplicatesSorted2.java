package com.svetanis.datastructures.array.twopointers;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.utils.Print.print;
import static java.util.Collections.sort;

import java.util.List;

// Remove Duplicates from Sorted Array
// Given a sorted array, remove the duplicates in place 
// such that each element can appear atmost twice and return the new length.
// Do not allocate extra space for another array, you must do this in place with constant memory.
// Note that even though we want you to return the new length, 
// make sure to change the original array as well in place

public final class RemoveDuplicatesSorted2 {

  public static int remove(List<Integer> a) {
    // Time Complexity: O(n log n)
    // Space Complexity: O(1)

    sort(a);

    int n = a.size();
    if (n <= 2) {
      return n;
    }
    int i = 1;
    int j = 2;
    
    while (j < n) {
      if (a.get(j).equals(a.get(i)) && a.get(j).equals(a.get(i - 1))) {
        j++;
      } else {
        i++;
        a.set(i, a.get(j));
        j++;
      }
    }

    for (int k = i + 1; k < n; k++) {
      a.set(k, null);
    }
    return i + 1;
  }

  public static void main(String[] args) {
    List<Integer> a = newArrayList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
        3, 3, 3, 3, 3);
    System.out.println(remove(a));
    print(a);

    List<Integer> a2 = newArrayList(0);
    System.out.println(remove(a2));
    print(a2);
  }
}
