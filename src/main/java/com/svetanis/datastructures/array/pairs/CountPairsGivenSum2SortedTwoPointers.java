package com.svetanis.datastructures.array.pairs;

public final class CountPairsGivenSum2SortedTwoPointers {

  public static int count(int[] a1, int[] a2, int k) {
    // Time Complexity: O(n + m)

    int n2 = a2.length;
    int left = 0;
    int right = n2 - 1;
    int count = 0;
    while (left < n2 && right >= 0) {
      int sum = a1[left] + a2[right];
      if (sum == k) {
        left++;
        right--;
        count++;
      } else if (sum < k) {
        left++;
      } else {
        right--;
      }
    }
    return count;
  }

  public static void main(String[] args) {
    int[] a1 = { 1, 3, 5, 7 };
    int[] a2 = { 2, 3, 5, 8 };
    System.out.println(count(a1, a2, 10));

    int[] a3 = { 1, 2, 3, 4, 5, 7, 11 };
    int[] a4 = { 2, 3, 4, 5, 6, 8, 12 };
    System.out.println(count(a3, a4, 9));

  }
}
