package com.svetanis.datastructures.array.pairs;

import static java.util.Arrays.sort;

import com.svetanis.java.base.Pair;

// given an array of distinct integers
// and a number k, find whether or not
// there exist two elements in array
// whose sum is exactly k

public final class PairGivenSumSorted {

  public static Pair<Integer, Integer> pair(int[] a, int target) {
	// Time Complexity: O(n)
	// Space Complexity: O(1) 
	  
    int left = 0;
    int right = a.length - 1;
    while (left < right) {
      if (a[left] + a[right] == target) {
        return Pair.build(a[left], a[right]);
      } else if (a[left] + a[right] < target) {
        left++;
      } else { 
        right--;
      }
    }
    return Pair.build(-1, -1);
  }

  public static void main(String[] args) {
    int[] a = { 1, 4, 45, 6, 10, -8 };
    sort(a);
    System.out.println(pair(a, 16));
  }
}