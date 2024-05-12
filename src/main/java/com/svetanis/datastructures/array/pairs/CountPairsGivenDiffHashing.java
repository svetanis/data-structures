package com.svetanis.datastructures.array.pairs;

import static com.google.common.collect.Sets.newHashSet;

import java.util.Set;

//given an array of distinct integers 
//and a positive integer k
//count all distinct pairs with difference = k

public final class CountPairsGivenDiffHashing {

  public static int count(Integer[] a, int k) {
    // time complexity: O(n)

	// initialize count as 0  
    int count = 0;
    // insert all distinct elements in hash set
    Set<Integer> set = newHashSet(a);

    // for each element
    // look for a[i] + k in the hash set,
    // look for a[i] - k in the hash set,
    // if found then increment count
    for (int i = 0; i < a.length; i++) {
      int x = a[i];
      if (set.contains(x - k)) {
        count++;
      }
      if (set.contains(x + k)) {
        count++;
      }
      // remove a[i] from hash set
      set.remove(x);
    }
    return count;
  }

  public static void main(String[] args) {
    Integer[] a1 = { 1, 5, 3, 4, 2 };
    System.out.println(count(a1, 3)); // 2

    Integer[] a2 = { 8, 12, 16, 4, 0, 20 };
    System.out.println(count(a2, 4)); // 5
  }
}