package com.svetanis.datastructures.array;

import static java.lang.Math.max;
import static java.lang.Math.min;

import com.svetanis.java.base.Pair;

// Maximum and minimum of an array using minimum number of comparisons

public final class MinMaxPairTournament {
  
  public static Pair<Integer, Integer> pair(int[] a) {
    // Time complexity: O(n)

    int n = a.length;
    int left = 0; 
    int right = n - 1;
    return pair(a, left, right);
  }

  private static Pair<Integer, Integer> pair(int[] a, int low, int high) {

    if (low == high) {
      return Pair.build(a[low], a[low]);
    }

    if (high == low + 1) {
      int max = (a[low] > a[high]) ? a[low] : a[high];
      int min = (a[low] > a[high]) ? a[high] : a[low];
      return Pair.build(min, max);
    }
    
    int mid = (low + high) / 2;
    Pair<Integer, Integer> left = pair(a, low, mid);
    Pair<Integer, Integer> right = pair(a, mid + 1, high);

    int min = min(left.getLeft(), right.getLeft());
    int max = max(left.getRight(), right.getRight());

    return Pair.build(min, max);
  }

  public static void main(String[] args) {
    int[] a = { 1, 30, 40, 50, 60, 70, 23, 20 };
    System.out.println(pair(a));
  }
}