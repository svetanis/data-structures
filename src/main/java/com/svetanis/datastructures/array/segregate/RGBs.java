package com.svetanis.datastructures.array.segregate;

import static com.svetanis.java.base.utils.Print.print;

// given n balls, randomly arranged in a line. 
// each of these balls are of one of 3 colors: red, green and blue.
// rearrange them such that all balls of the same color grouped
// together in this order: Red, Green, Blue

public final class RGBs {
  // Time Complexity: O(n)	

  public static void sort(char[] a) {
    int low = 0;
    int mid = 0;
    int high = a.length - 1;

    while (mid <= high) {
      if (a[mid] == 'R') {
        swap(a, low, mid);
        low++;
        mid++;
      } else if (a[mid] == 'G') {
        mid++;
      } else {
        swap(a, mid, high);
        high--;
      }
    }
  }

  public static void swap(char[] chars, int i, int j) {
    char temp = chars[i];
    chars[i] = chars[j];
    chars[j] = temp;
  }

  public static void main(String[] args) {
    char[] a = { 'G', 'G', 'B',  'B', 'R', 'R' };
    sort(a);
    print(a);
    
    char[] a1 = { 'R', 'G', 'B' };
    sort(a1);
    print(a1);

    char[] a2 = { 'B', 'G', 'R' };
    sort(a2);
    print(a2);
  }
}
