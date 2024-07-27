package com.svetanis.datastructures.array.triplet;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;

import com.google.common.base.Optional;
import com.svetanis.java.base.utils.Triplet;

// Pythagorean Triplet is a set of natural numbers
// such that a < b < c, for which a^2 + b^2 == c^2

// given a number n, 
// find a Pythagorean Triplet
// with sum as given n

public final class PythagoreanTripletGivenSum {
  // Time Complexity: O(n^2)
	
  public static Optional<Triplet<Integer, Integer, Integer>> triplet(int n) {
    
    for (int i = 1; i <= n / 3; i++) {
      for (int j = i + 1; j <= n / 2; j++) {
        int k = n - i - j;
        if (i * i + j * j == k * k) {
          return of(Triplet.build(i, j, k));
        }
      }
    }
    return absent();
  }

  public static void main(String[] args) {
    int n = 12;
    System.out.println(triplet(n));
  }
}
