package com.svetanis.datastructures.array.majority;

import static com.svetanis.java.base.utils.Maps.freqMap;

import java.util.Map;

public final class MajorityElementHashing {

  public static boolean isMajority(int[] a) {
    int n = a.length;
    Map<Integer, Integer> map = freqMap(a);
    for (int val : map.values()) {
      if (val >= n / 2) {
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args) {
    int[] a = { 2, 3, 9, 2, 2 };
    System.out.println(isMajority(a));
  }
}
