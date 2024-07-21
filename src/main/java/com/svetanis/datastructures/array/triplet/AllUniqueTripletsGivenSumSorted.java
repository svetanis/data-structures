package com.svetanis.datastructures.array.triplet;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Lists.sort;
import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;

// Given array of unsorted numbers, find all unique triplets in it that add up to zero.

// fix the first element one by one and
// find the other two elements
// to find the other two elements, start two index variables
// from two corners of the array and move them toward each other

public final class AllUniqueTripletsGivenSumSorted {

  public static ImmutableList<ImmutableList<Integer>> triplets(int[] a, int target) {
    // Time Complexity: O(n^2)

    int n = a.length;
    Arrays.sort(a);
    List<ImmutableList<Integer>> lists = newArrayList();
    for (int i = 0; i < n - 2; i++) {
      // skip same element to avoid duplicate triplets	
      if (i > 0 && a[i - 1] == a[i]) {
        continue; 
      }
      int left = i + 1;
      int right = n - 1;
      
      while (left < right) {
        int sum = a[i] + a[left] + a[right];
        if (sum == target) {
          lists.add(sort(asList(a[left], a[i], a[right])));
          left++;
          right--;
          // skip same element to avoid duplicate triplets
          while (left < right && a[left] == a[left - 1]) {
            left++; 
          }
          while (left < right && a[right] == a[right + 1]) {
            right--; 
          }
        } else if (sum < target) {
          left++;
        } else {
          right--;
        }
      }
    }
    return newList(lists);
  }

  public static void main(String[] args) {
    int[] a1 = {-2, 2, 0, -1, 1};
    System.out.println(triplets(a1, 0));

    int[] a2 = {10, 3, -4, 1, -6, 9};
    System.out.println(triplets(a2, 0));

    int[] a3 = {-31013930, -31013930, 9784175, 21229755};
    System.out.println(triplets(a3, 0));
  }
}
