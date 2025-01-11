package com.svetanis.datastructures.array.twopointers;

import static java.util.Arrays.sort;

// Given an unsorted array of positive integers. 
// Find the number of triangles that can be formed 
// with three different array elements as three sides of triangles. 

// For a triangle to be possible from 3 values, 
// the sum of any two values (or sides) must be greater 
// than the third value (or third side).

// this is only partially correct
public final class CountTrianglesToDo {

  public static int triangles(int[] a) {
    // Time Complexity: O(n log n)
    int n = a.length;
    if(n < 3) {
      return 0;
    }
    
    sort(a);
    
    int count = 0;
    for (int i = n - 1; i >= 1; i--) {
      int left = 0;
      int right = i - 1;
      while (left < right) {
        if (a[left] + a[right] > a[i]) {
          count += (right - left);
          right--;
        } else {
          left++;
        }
      }
    }
    int mod = new Double(Math.pow(10,9)).intValue();
    return count%(mod + 7);
  }

  public static void main(String[] args) {
    int[] a1 = { 4, 3, 5, 7, 6 };
    System.out.println(triangles(a1));

    int[] a2 = { 1, 1, 1, 2, 2 };
    System.out.println(triangles(a2));
    
    int[] a3 = { 2345678, 23456, 23459, 2343, 5463789, 2345678, 23456, 23459, 2343, 5463789};
    System.out.println(triangles(a3));
  }
}
