package com.svetanis.datastructures.array.pairs;

import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.java.base.utils.Arrays.sum;
import static com.svetanis.java.base.utils.Nums.isOdd;
import static org.apache.commons.lang3.ArrayUtils.toObject;

import java.util.Set;
import com.svetanis.java.base.Pair;

// given two arrays of integers,
// find a pair of values (one value from each array)
// that can be swapped so that both arrays have the same sum

// sum1 - a[i] + b[j] = sum2 - b[j] + a[i]
// 2 * b[j] = sum2 - sum1 + 2 * a[i]
// b[j] = a[i] + (sum2 - sum1)/2
// where difference (sum2 - sum1) must be an even integer

public final class SwapPairsHashing {
	// Time complexity: O(n + m)

  public static Pair<Integer, Integer> swap(int[] a, int[] b) {

    int diff = diff(a, b);
    if (diff == 0) {
      return Pair.build(-1, -1);
    }

    Set<Integer> set1 = newHashSet(toObject(a));
    Set<Integer> set2 = newHashSet(toObject(b));

    for(int s1 : set1) {
    	int s2 = s1 + diff;
    	if(set2.contains(s2)) {
    		return Pair.build(s1, s2);
    	}
    }
    
    return Pair.build(-1, -1);
  }

  private static int diff(int[] a, int[] b) {
    int sum1 = sum(a);
    int sum2 = sum(b);
    if (isOdd(sum1 - sum2)) {
      return 0;
    } else {
      return (sum2 - sum1) / 2;
    }
  }

  public static void main(String[] args) {
    int[] a = { 4, 1, 2, 1, 1, 2 };
    int[] b = { 3, 6, 3, 3 };
    System.out.println(swap(a, b));

    int[] c = { 5, 7, 4, 6 };
    int[] d = { 1, 2, 3, 8 };
    System.out.println(swap(c, d));
    
    int[] e = { 4, 1, 2, 1, 1, 2 };
    int[] f = { 1, 6, 3, 3 };
    System.out.println(swap(e, f));
  }
}
