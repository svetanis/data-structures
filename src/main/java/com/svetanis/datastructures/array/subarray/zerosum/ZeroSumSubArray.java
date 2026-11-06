package com.svetanis.datastructures.array.subarray.zerosum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// return true if any contiguous subarray adds up to zero.
//
// Input: an integer array a. Returns true if some non-empty contiguous subarray sums to 0. Not a
// LeetCode problem; 525. Contiguous Array and 325. Maximum Size Subarray Sum Equals k (k = 0) use the
// same prefix-sum idea. Time and space are O(n) for both versions: one pass, one map of at most n
// running totals. Siblings: ZeroSumLongestSubArray returns the longest span, ZeroSumSubArrayIndices
// one span, ZeroSumSubArrays every span.

// sum of a[left ... right] = prefix[right] - prefix[left - 1],
// so a subarray sums to zero exactly when the same prefix
// value occurs twice. the map holds the prefix values seen
// so far, and a repeat is the answer.

// this file carries the SAME algorithm twice. the only
// difference is how each version handles a subarray that
// starts at index 0, and it is worth reading both, because
// the second one is shorter for a reason rather than by luck.

// a third condition used to sit in front of both versions:
//
//     if (a[i] == 0 || sum == 0 || map.get(sum) != null)
//
// the a[i] == 0 check is REDUNDANT and has been dropped.
// a zero element leaves the running sum unchanged, so that
// same sum was placed in the map by the previous iteration
// and the map lookup already catches it. the one case that
// argument skips is i == 0, where there is no previous
// iteration -- and there sum is a[0], which is 0, so the
// sum == 0 check catches it instead. no input reaches the
// first condition without one of the other two firing.

public final class ZeroSumSubArray {

  // VERSION 1 -- no seed, two conditions.
  //
  // nothing is in the map before index 0, so a subarray that
  // starts there has no earlier prefix to match against. the
  // sum == 0 check exists to carry exactly that case, and it
  // is NOT redundant: on {3, -3} it is the only condition
  // that fires.

  public static boolean zeroSum(int[] a) {
    // Time complexity: O(n)
    // Space complexity: O(n)

    Map<Integer, Integer> map = new HashMap<>(); // SEEN: running totals so far
    int sum = 0; // RUNNING TOTAL: of a[0 .. i]
    for (int i = 0; i < a.length; i++) { // WALK: left to right
      sum += a[i];
      if (sum == 0 || map.containsKey(sum)) { // FOUND: a[0 .. i] sums to 0, or a total repeated
        return true;
      }
      map.put(sum, i); // RECORD: this running total
    }
    return false;
  }

  // VERSION 2 -- seeded, one condition.
  //
  // the seed says the prefix before any element is 0, sitting
  // at index -1. a subarray starting at index 0 now HAS an
  // earlier prefix to match, so the sum == 0 branch is no
  // longer carrying anything and disappears.
  //
  // one condition instead of three, and the loop is the
  // counting loop stopped at its first hit.

  public static boolean zeroSumSeeded(int[] a) {
    // Time complexity: O(n)
    // Space complexity: O(n)

    Map<Integer, Integer> map = new HashMap<>(); // SEEN: running totals so far
    map.put(0, -1); // the empty prefix, covering nothing
    int sum = 0;
    for (int i = 0; i < a.length; i++) {
      sum += a[i];
      if (map.containsKey(sum)) { // FOUND: a total repeated (the seed covers the start)
        return true;
      }
      map.put(sum, i); // RECORD: this running total
    }
    return false;
  }

  public static void main(String[] args) {
    // the two versions agree on every input; the comment on
    // each line says which condition is doing the work in
    // version 1, because that is what the seed replaces

    check(new int[] {4, 2, -3, 1, 6}, true); // the map: prefix 4 repeats
    check(new int[] {4, 2, 0, 1, 6}, true); // the map: a zero repeats prefix 6
    check(new int[] {-3, 2, 3, 1, 6}, false); // no zero-sum subarray at all
    check(new int[] {3, -3}, true); // sum == 0 ONLY -- starts at index 0
    check(new int[] {5, 3, -3, 2}, true); // the map ONLY -- no zero element, sum never 0
    check(new int[] {0}, true); // the dropped a[i] == 0 case, still found
    check(new int[] {}, false); // empty input
  }

  private static void check(int[] a, boolean expected) {
    boolean plain = zeroSum(a);
    boolean seeded = zeroSumSeeded(a);
    System.out.println(REPORT.formatted(Arrays.toString(a), plain, seeded, expected));
  }

  private static final String REPORT = "%-18s  two conditions: %-5s  one condition: %-5s  expected: %s";
}
