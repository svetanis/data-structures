package com.svetanis.datastructures.array;

import java.util.Arrays;

// Given an array arr[] of n integers, construct a Product Array prod[] (of same size)
// such that prod[i] is equal to the product of all the elements of arr[] except arr[i].
//
// Same problem as LeetCode 238. Product of Array Except Self (2 <= n <= 10^5, -30 <= a[i] <= 30,
// every product fits in 32 bits). No division is used, so zeros in the array are fine.
//
// The one idea: out[i] is the product of everything left of i times the product of everything right
// of i. Two passes build it in the answer array itself. The left-to-right pass writes into out[i]
// the product of a[0 .. i - 1], carried in one running variable. The right-to-left pass multiplies
// out[i] by the product of a[i + 1 .. n - 1], carried the same way. No second array is needed.
//
// Time: O(n) -- two passes.
// Space: O(1) -- one running variable, apart from the answer array itself.
//
// Siblings: ProductArrayTimeEfficient keeps the left and right products in two full arrays first
// and multiplies them at the end -- same answer, O(n) extra space.

public final class ProductArraySpaceEfficient {

  private static int[] product(int[] a) {
    int n = a.length;
    int[] out = new int[n];

    int product = 1; // LEFT: product of the elements to the left of i
    for (int i = 0; i < n; i++) {
      out[i] = product; // RECORD: everything left of i
      product *= a[i];
    }

    product = 1; // RIGHT: product of the elements to the right of i
    for (int i = n - 1; i >= 0; i--) {
      out[i] *= product; // COMBINE: left part times everything right of i
      product *= a[i];
    }

    return out; // RETURN: all but a[i], at every i
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