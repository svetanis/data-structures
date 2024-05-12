package com.svetanis.datastructures.array.pairs;

import static java.util.Arrays.sort;

import com.svetanis.java.base.Pair;

// given unsorted array and a number n, 
// find if there exists a pair of elements
// in the array whose difference is n

public final class PairGivenDiffSorted {

//  If arr[j] – arr[i] is smaller than n, we need to look for greater arr[j], so increment j. 
//  If arr[j] – arr[i] is greater than n, we need to look for greater arr[i], so increment i. 

  public static Pair<Integer, Integer> pair(int[] a, int k) {
    // Time Complexity: O(n log n)

    int n = a.length;
    int left = 0;
    int right = 1;

    sort(a);

    while (left < n && right < n) {
      int diff = a[right] - a[left];
      if (left != right && diff == k) {
        return Pair.build(a[left], a[right]);
      } else if (diff < k) {
        right++;
      } else {
        left++;
      }
    }
    return Pair.build(-1, -1);
  }

  public static void main(String[] args) {
    int[] a = { 40, 30, 1, 8, 100 };
    System.out.println(pair(a, 60));
  }
}