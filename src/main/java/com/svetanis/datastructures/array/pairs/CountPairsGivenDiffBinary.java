package com.svetanis.datastructures.array.pairs;

import static java.util.Arrays.sort;

// given an array of distinct integers 
// and a positive integer k
// count all distinct pairs with difference = k

public final class CountPairsGivenDiffBinary {

  public static int count(int[] a, int k) {
    // time complexity: O(n log n)
    
	// initialize count as 0  
    int count = 0;
    int n = a.length;

    // sort all numbers in increasing order
    sort(a);

    // for each element a[i] 
    // binary search for a[i] + k
    // in subarray from i + 1 to n - 1
    // if a[i] + k found, increment count
    for (int i = 0; i < n - 1; i++) {
      int target = a[i] + k;
      if (binary(a, i + 1, n - 1, target) != -1) {
        count++;
      }
    }
    return count;
  }

  private static int binary(int[] a, int low, int high, int x) {
    if (high >= low) {
      int mid = low + (high - low) / 2;
      if (x == a[mid]) {
        return mid;
      }
      if (x > a[mid]) {
        return binary(a, mid + 1, high, x);
      }else {
        return binary(a, low, mid - 1, x);
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    int[] a1 = { 1, 5, 3, 4, 2 };     
    System.out.println(count(a1, 3)); // 2
    
    int[] a2 = { 8, 12, 16, 4, 0, 20 };  
    System.out.println(count(a2, 4)); // 5
  }
}