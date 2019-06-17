package com.svetanis.datastructures.array.triplet;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Set;

import com.svetanis.java.base.utils.Triplet;

public final class TripletGivenSumHashing {

  public static Triplet<Integer, Integer, Integer> triplet(int[] a, int k) {
    int n = a.length;

    for (int i = 0; i < n - 2; ++i) {
      int pairSum = k - a[i];
      Set<Integer> set = newHashSet();
      for (int j = i + 1; j < n; j++) {
        int diff = pairSum - a[j];
        if (set.contains(diff)) {
          return Triplet.build(a[i], a[j], diff);
        }
        set.add(a[j]);
      }
    }
    return Triplet.build(-1, -1, -1);
  }

  public static void main(String[] args) {
    int[] a = { 1, 4, 45, 6, 10, 8 };
    System.out.println(triplet(a, 22));
  }
}