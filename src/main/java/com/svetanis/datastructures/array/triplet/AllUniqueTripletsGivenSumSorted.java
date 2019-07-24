package com.svetanis.datastructures.array.triplet;

import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.java.base.collect.Lists.newList;
import static java.util.Arrays.sort;

import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.utils.Triplet;

// fix the first element one by one and
// find the other two elements
// to find the other two elements, start two index variables
// from two corners of the array and move them toward each other

public final class AllUniqueTripletsGivenSumSorted {

  public static ImmutableList<Triplet<Integer, Integer, Integer>> triplets(int[] a, int k) {
    // time complexity: O(n^2)
    int n = a.length;
    sort(a);
    Set<Triplet<Integer, Integer, Integer>> set = newHashSet();
    for (int i = 0; i < n - 2; ++i) {
      int left = i + 1;
      int right = n - 1;
      while (left < right) {
        int sum = a[i] + a[left] + a[right];
        if (sum == k) {
          set.add(Triplet.build(a[left], a[i], a[right]));
          left++;
          right--;
          while (left < right && a[left] == a[left - 1]) {
            left++;
          }
          while (left < right && a[right] == a[right + 1]) {
            right--;
          }
        } else if (sum < k) {
          left++;
        } else {
          right--;
        }
      }
    }
    return newList(set);
  }

  public static void main(String[] args) {
    int[] a = { -2, 2, 0, -1, 1 };
    System.out.println(triplets(a, 0));

    int[] a1 = { 10, 3, -4, 1, -6, 9 };
    System.out.println(triplets(a1, 0));
  }
}