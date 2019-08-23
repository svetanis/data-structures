package com.svetanis.datastructures.array.triplet;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Lists.sort;
import static java.util.Arrays.asList;

import java.util.List;

import com.google.common.collect.ImmutableList;

// Given array of unsorted numbers, find all unique triplets in it that add up to zero.

// fix the first element one by one and
// find the other two elements
// to find the other two elements, start two index variables
// from two corners of the array and move them toward each other

public final class AllUniqueTripletsGivenSumSorted {

  public static ImmutableList<ImmutableList<Integer>> triplets(List<Integer> a, int k) {
    // Time Complexity: O(n^2)

    int n = a.size();
    List<ImmutableList<Integer>> lists = newArrayList();
    for (int i = 0; i < n - 2; i++) {
      if (i > 0 && a.get(i - 1).equals(a.get(i))) {
        continue; // skip same element
      }
      int left = i + 1;
      int right = n - 1;
      
      while (left < right) {
        int sum = a.get(i) + a.get(left) + a.get(right);
        if (sum == k) {
          List<Integer> triplet = newArrayList(a.get(left), a.get(i), a.get(right));
          lists.add(sort(triplet));
          left++;
          right--;
          
          while (left < right && a.get(left) == a.get(left - 1)) {
            left++; // skip same element
          }
          while (left < right && a.get(right) == a.get(right + 1)) {
            right--; // skip same element
          }
        } else if (sum < k) {
          left++;
        } else {
          right--;
        }
      }
    }
    return newList(lists);
  }

  public static void main(String[] args) {
    List<Integer> list1 = asList(-2, 2, 0, -1, 1);
    System.out.println(triplets(sort(list1), 0));

    List<Integer> list2 = asList(10, 3, -4, 1, -6, 9);
    System.out.println(triplets(sort(list2), 0));

    List<Integer> list3 = asList(-31013930, -31013930, 9784175, 21229755);
    System.out.println(triplets(sort(list3), 0));
  }
}
