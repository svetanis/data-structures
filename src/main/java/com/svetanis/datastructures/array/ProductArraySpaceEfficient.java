package com.svetanis.datastructures.array;

import static com.svetanis.java.base.utils.Print.print;

// Given an array arr[] of n integers, construct a Product Array prod[] (of same size) 
// such that prod[i] is equal to the product of all the elements of arr[] except arr[i]. 

public final class ProductArraySpaceEfficient {

  private static int[] product(int[] a) {
    // Time Complexity: O(n)

    int n = a.length;
    int out[] = new int[n];

    // product is product
    // of elements on left side
    int product = 1;
    for (int i = 0; i < n; i++) {
      out[i] = product;
      product *= a[i];
    }

    // product is product
    // of elements on right side
    product = 1;
    for (int i = n - 1; i >= 0; i--) {
      out[i] *= product;
      product *= a[i];
    }

    return out;
  }

  public static void main(String[] args) {
    int[] a = { 1, 7, 3, 4 };
    print(product(a));
  }
}