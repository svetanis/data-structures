package com.svetanis.datastructures.array.pairs;

// given sorted array of integers with duplicates
// find the number of distinct absolute values

public final class CountPairsAbsoluteDistinct {

  public static int count(int[] a) {
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    
    int n = a.length;
    int count = n;
    int left = 0;
    int right = n - 1;
    
    while (left < right) {
      // eliminate duplicates on the left
      while (left != right && a[left] == a[left + 1]) {
        count--;
        left++;
      }
      // eliminate duplicates on the right
      while (left != right && a[right] == a[right - 1]) {
        count--;
        right--;
      }

      // only one element left
      if (left == right) {
        return count;
      }
      
      int sum = a[left] + a[right];

      if (sum == 0) {
        count--;
        left++;
        right--;
      } else if (sum < 0) {
        left++;
      } else {
        right--;
      } 
    }
    return count;
  }

  public static void main(String[] args) {
    int[] a1 = { -3, -2, 0, 3, 4, 5 };
    System.out.println(count(a1));

    int[] a2 = { -1, -1, -1, -1, 0, 1, 1, 1, 1 };
    System.out.println(count(a2));

    int[] a3 = { -1, -1, -1, -1, 0 };
    System.out.println(count(a3));

    int[] a4 = { 0, 0, 0 };
    System.out.println(count(a4));
    
    int[] a5 = { -2, -1, 0, 1, 1 };
    System.out.println(count(a5));
    
  }
}