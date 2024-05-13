package com.svetanis.datastructures.array.pairs;

import com.svetanis.java.base.Pair;

// given an array of distinct integers that
// is sorted and then rotated around an unknown point
// find if array has a pair with given sum k

public final class PairGivenSumRotated {
  
  public static Pair<Integer, Integer> pair(int[] a, int k) {
    // time complexity: O(n)
	  
    int n = a.length;
    int left = 0;
    int right = n - 1;

    int p = pivot(a);
    if (p != -1) {
      // left is now index of min element	
      left = (p + 1) % n; 
      // right is now index of max element
      right = p; 
    } 

    while (left != right) {
      int sum = a[left] + a[right]; 
      if (sum == k) {
        return Pair.build(a[left], a[right]);      
      }
      if (sum < k) {
        left = (left + 1) % n;
      } else {// sum > k
        right = (n - 1 + right) % n;
      }
    }
    return Pair.build(-1, -1);
  }

  public static int pivot(int[] a) {
    int n = a.length;
    for (int i = 0; i < n - 1; i++) {
      if (a[i] > a[i + 1]) {
        return i;
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    int[] a1 = { 11, 15, 6, 8, 9, 10 };
    System.out.println(pair(a1, 16));

    int[] a2 = { 11, 15, 26, 38, 9, 10 };
    System.out.println(pair(a2, 35));

    int[] a3 = { 11, 15, 26, 38, 9, 10 };
    System.out.println(pair(a3, 45));

    int[] a4 = { 11, 15, 6, 7, 8, 9, 10 };
    System.out.println(pair(a4, 16));
  }
}