package com.svetanis.datastructures.array.pairs;

import com.svetanis.java.base.Pair;

// given an unsorted array
// find the largest pair sum
// the largest and second largest elements

public final class PairLargestSum {
  	
  public static Pair<Integer, Integer> pair(int[] a) {
    // Time complexity: O(n)
    
    int first;
    int second;

    if (a[0] > a[1]) {
      first = a[0];
      second = a[1];
    } else {
      first = a[1];
      second = a[0];
    }

    for (int i = 2; i < a.length; i++) {
      if (a[i] > first) {
        second = first;
        first = a[i];
      } else if (a[i] > second && a[i] < first) {
        second = a[i];
      }
    }
    return Pair.build(first, second); 
  }

  public static void main(String[] args) {
    int[] a = { 12, 34, 10, 6, 40 };
    System.out.println(pair(a));
  }
}