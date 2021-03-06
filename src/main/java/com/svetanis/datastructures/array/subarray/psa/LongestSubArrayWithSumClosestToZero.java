package com.svetanis.datastructures.array.subarray.psa;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.sort;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Comparator.comparing;

import java.util.List;

import com.svetanis.java.base.Pair;
import com.svetanis.java.base.utils.Triplet;

// Given an array of both positive and negative numbers, 
// find the subarray whose sum is closest to 0.

// prefix[i] = a[0] + a[1] + a[2] + … + a[i-1] + a[i]
// prefix[i] = prefix[i-1] + a[i]
// a[i] = prefix[i] - prefix[i-1]

// sum of a[left ... k] =
// a[left] + a[left + 1] + ... + a[k - 1] + a[k] 
// prefix[left] - prefix[left - 1] +
// prefix[left + 1] - prefix[left] + 
// …
// prefix[k - 1] - prefix[k - 2] + 
// prefix[k] - prefix[k - 1]
// =
// prefix[k] - prefix[left - 1]

// for the sum of a[left ... k] to be equal to zero :
// prefix[k] = prefix[left - 1]
// therefore, the sum closest to zero can be found  
// by locating the two closest elements in prefix

// ALGORITHM:
//  1. Compute prefix array with index of original array as well, so it is a collection of pair (value, index). O(n)
//  2. Sort the above prefix array by value. O(n log n)
//  3. Compute pair-wise diff by value. Prepare absolute values to get a measure of how far/close these are to zero. O(n)
//  4. The closest pair is that with min value found above. O(n)
//  5. Report the indices found above in the original array. This is the sub-array with sum being closest to zero. 

public final class LongestSubArrayWithSumClosestToZero {

  public static Triplet<Integer, Integer, Integer> subArraySum(List<Integer> list) {
    // Time complexity: O(n + n log n);
    // Space complexity: O(n)

    int left;
    int right;
    int min = MAX_VALUE;

    List<Pair<Integer, Integer>> pairs = build(list);
    Pair<Integer, Integer> prev = pairs.get(0);
    int start = prev.getRight();
    int end = prev.getRight();
    for (int i = 1; i < pairs.size(); i++) {
      Pair<Integer, Integer> pair = pairs.get(i);
      int diff = pair.getLeft() - prev.getLeft();
      if (diff <= min) {
        min = diff;
        start = prev.getRight();
        end = pair.getRight();
      }
      prev = pair;
    }
    left = min(start, end) + 1;
    right = max(start, end);
    return Triplet.build(left, right, min);
  }

  private static List<Pair<Integer, Integer>> build(List<Integer> a) {
    int n = a.size();
    List<Pair<Integer, Integer>> list = newArrayList();
    list.add(Pair.build(0, -1));
    list.add(Pair.build(a.get(0), 0));
    for (int i = 1; i < n; ++i) {
      Pair<Integer, Integer> prev = list.get(i);
      int val = prev.getLeft() + a.get(i);
      list.add(Pair.build(val, i));
    }
    return sort(list, comparing(p -> p.getLeft()));
  }

  public static void main(String[] args) {
    List<Integer> a = newArrayList(8, -3, 2, 1, -4, 10, -5);
    System.out.println(subArraySum(a)); // [1, 3] = 0

    List<Integer> a1 = newArrayList(-3, 2, 4, -6, -8, 10, 11);
    System.out.println(subArraySum(a1)); // [2, 5] = 0

    List<Integer> a2 = newArrayList(10, -2, -7);
    System.out.println(subArraySum(a2)); // [0, 2] = 1

    List<Integer> a3 = newArrayList(2, 3, -4, -1, 6);
    System.out.println(subArraySum(a3)); // [0, 3] = 0

    List<Integer> a4 = newArrayList(-1, 3, 2, -5, 4);
    System.out.println(subArraySum(a4)); // [1, 3] = 0

    List<Integer> a5 = newArrayList(2, -5, 4, -6, 3);
    System.out.println(subArraySum(a5)); // [0, 2] = 1

    List<Integer> a6 = newArrayList(2, 3, -4, -1, 6);
    System.out.println(subArraySum(a6)); // [0, 3] = 0

  }
}