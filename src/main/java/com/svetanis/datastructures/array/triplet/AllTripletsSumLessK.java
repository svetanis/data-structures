package com.svetanis.datastructures.array.triplet;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLines;
import static java.util.Arrays.sort;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.utils.Triplet;

public final class AllTripletsSumLessK {

  public static ImmutableList<Triplet<Integer, Integer, Integer>> triplets(int[] a, int k) {
    // Time complexity: O(n log n)
    int n = a.length;
    sort(a);
    List<Triplet<Integer, Integer, Integer>> list = newArrayList();
    for (int i = 0; i < n; i++) {
      int left = i + 1;
      int right = n - 1;
      while (left < right) {
        int sum = a[i] + a[left] + a[right];
        if (sum >= k) {
          right--;
        } else {
          for (int j = left + 1; j <= right; j++) {
            list.add(Triplet.build(a[i], a[left], a[j]));
          }
          left++;
        }
      }
    }
    return newList(list);
  }


  public static void main(String[] args) {
    int[] a = { 5, 1, 3, 4, 7 };
    printLines(triplets(a, 12));
  }

}
