package com.svetanis.datastructures.array.pairs;

// given a sorted array of integers and a number k
// count pairs whose sum is less than k

public final class PairsLessGivenSum {

  public static int count(int[] a, int k) {
    int left = 0;
    int right = a.length - 1;
    int count = 0;
    while(left < right) {
      if (a[left] + a[right] < k) {
        count += right - left;
        left++;
      } else {
        right--;
      }
    }
    return count;
  }
  
  public static void main(String[] args) {
    int[] a1 = {1, 3, 7, 9, 10, 11};
    System.out.println(count(a1, 7));
    
    int[] a2 = {1, 2, 3, 4, 5, 6, 7, 8};
    System.out.println(count(a2, 7));
  }
}
