package com.svetanis.datastructures.array.pairs;

import static com.svetanis.java.base.utils.Maps.freqMap;
import java.util.Map;

//given an array of size n
//find the number of pairs (i,j) s.t.
//a[i] xor a[j] == 0, 1<=i<j<=n

//if frequency of some element is k then, it
//will contribute k * (k - 1)/2 to the answer

public final class CountPairsXorZeroHashing {

  public static int countPairs(int[] a) {
    // Time Complexity: O(n)

    Map<Integer, Integer> map = freqMap(a);
    int sum = 0;
    for (int v : map.values()) {
      sum += (v * (v - 1)) / 2;
    }
    return sum;
  }

  public static void main(String[] args) {
    int[] a = { 1, 2, 1, 2, 4 };
    System.out.println(countPairs(a));
  }
}
