package com.svetanis.datastructures.array.pairs;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.utils.Print.print;
import static java.util.Arrays.sort;

import java.util.List;

import com.svetanis.java.base.Pair;

public final class PairsGivenSumSorted {

  public static List<Pair<Integer, Integer>> pairs(int[] a, int k) {
    int n = a.length;
    int left = 0;
    int right = n - 1;
    sort(a);
    List<Pair<Integer, Integer>> list = newArrayList();

    while (left < right) {
      int sum = a[left] + a[right];
      if (sum == k) {
        list.add(Pair.build(a[left], a[right]));
        left++;
        right--;
      } else if (sum < k) {
        left++;
      } else { // sum > k
        right--;
      }
    }
    return list;
  }

  public static void main(String[] args) {
    int[] a = { 9, 3, 6, 5, 7, -1, 13, 14, -2, 12, 0 };
    print(pairs(a, 12));
  }
}