package com.svetanis.datastructures.array.pairs;

import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.lang3.ArrayUtils.toObject;

import java.util.Set;

import com.svetanis.java.base.Pair;

// given an array of distinct and positive elements,
// find pair whose sum already exists in array

public final class PairExistingSumHashing {

  public static Pair<Integer, Integer> pair(int[] a) {
	// Time Complexity: O(n^2)
	  
    int n = a.length;
    Set<Integer> set = newHashSet(toObject(a));
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        int sum = a[i] + a[j];
        if (set.contains(sum)) {
          return Pair.build(a[i], a[j]);
        }
      }
    }
    return Pair.build(-1, -1);
  }

  public static void main(String[] args) {
    int[] a = { 10, 4, 8, 13, 5 };
    System.out.println(pair(a));
  }
}
