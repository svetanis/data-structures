package com.svetanis.datastructures.hashmap;

import java.util.HashSet;
import java.util.Set;

// 217. Contains Duplicate
//
// duplicates(a) returns true if some value appears at least twice in a, false if every
// value is distinct. On LeetCode: 1 <= a.length <= 10^5, -10^9 <= a[i] <= 10^9.
//
// Walk the array once, keeping every value seen so far in a set. A value already in the set
// is the second copy, and the answer is known; reaching the end means no value repeated.
// Set.add returns false exactly when the value was already there, so one call both checks
// and records.
//
// Siblings, same problem: none in this package. DuplicatesKDistAway and DuplicatesKDistAwaySW
// solve 219, where the two copies must also be at most k positions apart.

public final class ContainsDuplicate217 {
  // Time complexity: O(n), expected -- one set add per element, stopping at the first repeat
  // Auxiliary Space: O(n) -- the set holds every distinct value seen

  public static boolean duplicates(int[] a) {
    Set<Integer> set = new HashSet<>(); // SEEN: values met so far
    for (int element : a) {
      if (!set.add(element)) { // REPEAT?: add refuses a value already seen
        return true;
      }
    }
    return false; // DISTINCT: no add was refused
  }

  public static void main(String[] args) {
    int[] a = { 1, 2, 3, 1 };
    System.out.println(duplicates(a)); // true
    int[] a1 = { 1, 2, 3, 4 };
    System.out.println(duplicates(a1)); // false
    int[] a2 = { 1, 1, 1, 3, 3, 4, 3, 2, 4, 2 };
    System.out.println(duplicates(a2)); // true
  }
}
