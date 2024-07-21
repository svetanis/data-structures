package com.svetanis.datastructures.array.triplet;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static java.util.Arrays.sort;

import com.google.common.base.Optional;
import com.svetanis.java.base.utils.Triplet;

// given an array of integers
// find 3 nums such that
// sum of 2 elements equals 
// the third element

public final class SumOfTwoEqualThird {
	// Time Complexity: O(n^2)

  public static Optional<Triplet<Integer, Integer, Integer>> triplet(int[] a) {
    int n = a.length;
    sort(a);
    
    for (int i = n - 1; i >= 0; i--) {
      int start = 0;
      int end = i - 1;
      while (start < end) {
    	int sum = a[start] + a[end];  
        if (a[i] == sum) {
          return of(Triplet.build(a[i], a[start], a[end]));
        } else if (a[i] > sum) {
          start++;
        } else {
          end--;
        }
      }
    }
    return absent();
  }

  public static void main(String[] args) {
    int[] a1 = { 5, 32, 1, 7, 10, 50, 19, 21, 2 };
    System.out.println(triplet(a1));

    int[] a2 = { 5, 32, 1, 7, 10, 50, 19, 21, 0 };
    System.out.println(triplet(a2));
  }
}
