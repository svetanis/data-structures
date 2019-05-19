package com.svetanis.datastructures.array;

import static com.svetanis.java.base.utils.Print.print;

// Given an array arr[] of n integers, construct a Product Array prod[] (of same size) 
// such that prod[i] is equal to the product of all the elements of arr[] except arr[i]. 

public final class ProductArrayTimeEfficient {

  public static int[] product(int[] a) {
    // Time Complexity: O(n)

    int n = a.length;
    int out[] = new int[n];

    // 1. construct left product array
    int left[] = new int[n];
    left[0] = 1;
    for (int i = 1; i < n; i++) {
      left[i] = left[i - 1] * a[i - 1];
    }

    // 2. construct right product array
    int right[] = new int[n];
    right[n - 1] = 1;
    for (int i = n - 2; i >= 0; i--) {
      right[i] = right[i + 1] * a[i + 1];
    }

    // construct product array
    for (int i = 0; i < n; i++) {
      out[i] = right[i] * left[i];
    }

    return out;
  }

  public static void main(String[] args) {
    int[] a = { 1, 7, 3, 4 };
    print(product(a));
  }
}