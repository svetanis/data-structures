package com.svetanis.datastructures.array.majority;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.svetanis.datastructures.array.majority.MajorityElementGuava.isMajority;

import com.google.common.base.Optional;

// A majority element in an array A[] of size n is an element that appears more than n/2 times 

public final class MajorityElementMooreVotingAlgo {

  public static Optional<Integer> majorityElement(int[] a) {
    // Time Complexity: O(n)
    // Space Complexity: O(1)

    // 1. find candidate for majority
    int candidate = findCandidate(a);

    // 2. check if candidate is majority
    if (isMajority(a, candidate)) {
      return of(candidate);
    }

    return absent();
  }

  private static int findCandidate(int[] a) {
    int index = 0;
    int count = 1;
    int n = a.length;

    for (int i = 1; i < n; ++i) {
      if (a[i] == a[index]) {
        ++count;
      } else {
        --count;
      }

      if (count == 0) {
        index = i;
        count = 1;
      }
    }
    return a[index];
  }

  public static void main(String[] args) {
    int[] a = { 1, 3, 3, 3, 3, 1, 2 };
    System.out.println(majorityElement(a));
  }
}