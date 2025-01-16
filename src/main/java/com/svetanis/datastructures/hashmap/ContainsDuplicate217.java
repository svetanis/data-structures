package com.svetanis.datastructures.hashmap;

import java.util.HashSet;
import java.util.Set;

// 217. Contains Duplicate

public final class ContainsDuplicate217 {
  // Time Complexity: O(n)
  // Space Complexity: O(n)

  public static boolean duplicates(int[] a) {
    Set<Integer> set = new HashSet<>();
    for (int element : a) {
      if (!set.add(element)) {
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args) {
    int[] a = { 1, 2, 3, 1 };
    System.out.println(duplicates(a)); // true
    int[] a1 = { 1, 2, 3, 4 };
    System.out.println(duplicates(a1)); // false
    int[] a2 = { 1, 1, 1, 3, 3, 4, 3, 2, 4, 2 };
    System.out.println(duplicates(a2)); // true
  }
}
