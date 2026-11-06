package com.svetanis.datastructures.array;

import java.util.Arrays;

// Given an array arr[] of n integers, construct a Product Array prod[] (of same size)
// such that prod[i] is equal to the product of all the elements of arr[] except arr[i].
//
// Same problem as LeetCode 238. Product of Array Except Self (2 <= n <= 10^5, -30 <= a[i] <= 30,
// every product fits in 32 bits). No division is used, so zeros in the array are fine. n must be
// at least 1: the first lines write left[0] and right[n - 1].
//
// The one idea: out[i] is the product of everything left of i times the product of everything right
// of i. left[i] holds the product of a[0 .. i - 1] (left[0] is 1, the empty product) and right[i]
// the product of a[i + 1 .. n - 1] (right[n - 1] is 1). Each is built from its neighbour in one
// pass, and the answer is left[i] * right[i].
//
// Time: O(n) -- three passes.
// Space: O(n) -- the left and right arrays, besides the answer.
//
// Siblings: ProductArraySpaceEfficient carries the left product in one variable and multiplies the
// right product into the answer on a second pass -- same answer, O(1) extra space.

public final class ProductArrayTimeEfficient {

  public static int[] product(int[] a) {
    int n = a.length;
    int out[] = new int[n];

    int left[] = new int[n]; // LEFT: left[i] is the product of a[0 .. i - 1]
    left[0] = 1; // BASE: nothing to the left
    for (int i = 1; i < n; i++) {
      left[i] = left[i - 1] * a[i - 1];
    }

    int right[] = new int[n]; // RIGHT: right[i] is the product of a[i + 1 .. n - 1]
    right[n - 1] = 1; // BASE: nothing to the right
    for (int i = n - 2; i >= 0; i--) {
      right[i] = right[i + 1] * a[i + 1];
    }

    for (int i = 0; i < n; i++) {
      out[i] = right[i] * left[i]; // COMBINE: everything except a[i]
    }

    return out;
  }

  public static void main(String[] args) {
    int[] a = { 1, 7, 3, 4 };
    System.out.println(Arrays.toString(product(a))); // [84, 12, 28, 21]
    int[] a1 = { 1, 2, 3, 4 };
    System.out.println(Arrays.toString(product(a1))); // [24, 12, 8, 6]
    int[] a2 = { -1, 1, 0, -3, 3 };
    System.out.println(Arrays.toString(product(a2))); // [0, 0, 9, 0, 0]
  }
}