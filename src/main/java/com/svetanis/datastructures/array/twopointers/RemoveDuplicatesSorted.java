package com.svetanis.datastructures.array.twopointers;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.utils.Print.print;
import static java.util.Collections.sort;

import java.util.List;

// Remove duplicates from Sorted Array
// Given a sorted array, remove the duplicates in place 
// such that each element appears only once and return the new length.

public final class RemoveDuplicatesSorted {

  public static int remove(List<Integer> a) {
    // Time Complexity: O(n log n)
    // Space Complexity: O(1)

    sort(a);
    int i = 0;
    int n = a.size();
    for (int j = 1; j < n; j++) {
      if (!a.get(j).equals(a.get(i))) {
        i++;
        a.set(i, a.get(j));
      }
    }
    for (int k = i + 1; k < n; k++) {
      a.set(k, null);
    }
    return i + 1;
  }

  public static void main(String[] args) {
    List<Integer> a = newArrayList(6, 7, 7, 3, 1, 5, 5, 9, 9, 3);
    System.out.println(remove(a));
    print(a);

    List<Integer> a2 = newArrayList(5000, 5000, 5000);
    System.out.println(remove(a2));
    print(a2);
  }
}