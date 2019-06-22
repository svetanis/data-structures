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