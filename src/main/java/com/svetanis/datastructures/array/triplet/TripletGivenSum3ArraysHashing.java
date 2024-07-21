package com.svetanis.datastructures.array.triplet;

import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.lang3.ArrayUtils.toObject;

import java.util.Set;

import com.svetanis.java.base.utils.Triplet;

// given 3 integer arrays and a target, 
// find triplet such that a + b + c == target
// and a, b, c belong to three different arrays

public final class TripletGivenSum3ArraysHashing {

  public static Triplet<Integer, Integer, Integer> triplet(int[] a1, int[] a2, int[] a3, int k) {
    // time complexity: O(n^2)

    int n2 = a2.length;
    int n3 = a3.length;

    Set<Integer> set = newHashSet(toObject(a1));
    for (int i = 0; i < n2; ++i) {
      for (int j = 0; j < n3; ++j) {
        int sum = a2[i] + a3[j];
        int diff = k - sum;
        if (set.contains(diff)) {
          return Triplet.build(diff, a2[i], a3[j]);
        }
      }
    }
    return Triplet.build(-1, -1, -1);
  }

  public static void main(String[] args) {
    int[] a1 = { 1, 2, 3, 4, 5 };
    int[] a2 = { 2, 3, 6, 1, 2 };
    int[] a3 = { 3, 2, 4, 5, 6 };

    System.out.println(triplet(a1, a2, a3, 9));
  }
}