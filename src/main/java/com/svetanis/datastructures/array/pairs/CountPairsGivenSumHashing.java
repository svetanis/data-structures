package com.svetanis.datastructures.array.pairs;

import static com.svetanis.java.base.utils.Maps.freqMap;
import java.util.Map;

// given an array of integers and a number k
// count pairs whose sum is equal to k

public final class CountPairsGivenSumHashing {

  public static int count(int[] a, int k) {
    // Time Complexity: O(n)
    
    int count = 0;
    Map<Integer, Integer> map = freqMap(a);
    for (int i = 0; i < a.length; i++) {
      int diff = k - a[i];	
      count += map.getOrDefault(diff, 0);
      if (diff == a[i]) {
        count--;
      }
    }
    return count / 2;
  }

  public static void main(String[] args) {
	int[] a1 = { 1, 5, 7, -1 };
	System.out.println(count(a1, 6));

	int[] a2 = { 1, 5, 7, -1, 5 };
    System.out.println(count(a2, 6));

    int[] a3 = { 1, 1, 1, 1 };
    System.out.println(count(a3, 2));

    int[] a4 = {10, 12, 10, 15, -1, 7, 6, 5, 4, 2, 1, 1, 1 };
    System.out.println(count(a4, 11));
  }
}