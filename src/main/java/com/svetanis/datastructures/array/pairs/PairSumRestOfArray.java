package com.svetanis.datastructures.array.pairs;

import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.java.base.utils.Arrays.sum;
import static com.svetanis.java.base.utils.Nums.isOdd;

import java.util.Set;

// given an array of integers,
// find two such elements in the array
// such that sum of these two elements
// is equal to the sum of rest of elements in array

public final class PairSumRestOfArray {

  public static boolean isPair(int[] a) {
    // Time Complexity: O(n)
    
	// find sum of whole array  
    int sum = sum(a);

    // a pair can exist only if 
    // the sum of whole array is even
    if (isOdd(sum)) {
      return false;
    }
    return isPair(a, sum/2);
  }

  private static boolean isPair(int[] a, int k) {
    int n = a.length;
    Set<Integer> set = newHashSet();
    for (int i = 0; i < n; i++) {
      int diff = k - a[i];
      if (set.contains(diff)) {
        return true;
      }
      set.add(a[i]);
    }
    return false;
  }

  public static void main(String[] args) {
    int[] a = {2, 11, 5, 1, 4, 7};
    System.out.println(isPair(a));
  }
}
