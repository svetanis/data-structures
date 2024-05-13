package com.svetanis.datastructures.array.pairs;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.array.pairs.PairGivenSumRotated.pivot;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;

// given an array of distinct integers that
// is sorted and then rotated around an unknown point
// find all pairs with a given sum k

public final class PairsGivenSumRotated {

  public static ImmutableList<Pair<Integer, Integer>> pairs(int[] a, int k) {
    // time complexity: O(n)

    int n = a.length;
    int left = 0;
    int right = n - 1;

    int p = pivot(a);

    if (p != -1) {
      // left is now index of min element	
      left = (p + 1) % n; 
      // right is now index of max element
      right = p; 
    }

    List<Pair<Integer, Integer>> list = newArrayList();
    while (left != right) {
      int sum = a[left] + a[right];
      if (sum == k) {
        list.add(Pair.build(a[left], a[right]));
        left = (left + 1) % n;
        right = (n - 1 + right) % n;
      } else if (sum < k) {
        left = (left + 1) % n;
      } else {// sum > k
        right = (n - 1 + right) % n;
      }
    }
    return newList(list);
  }

  public static void main(String[] args) {
    int[] a1 = { 11, 15, 6, 8, 9, 10 };
    System.out.println(pairs(a1, 16));

    int[] a2 = { 11, 15, 26, 38, 9, 10 };
    System.out.println(pairs(a2, 35));

    int[] a3 = { 11, 15, 26, 38, 9, 10 };
    System.out.println(pairs(a3, 45));

    int[] a4 = { 11, 15, 6, 7, 8, 9, 10 };
    System.out.println(pairs(a4, 16));
  }
}