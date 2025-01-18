package com.svetanis.datastructures.linkedlist.single.cycle;

// 202. Happy Number

// A number is called happy if it leads to 1 after a sequence of steps 
// where in each step number is replaced by sum of squares of its digit 
// that is if we start with Happy Number and 
// keep replacing it with digits square sum, we reach 1.

public final class HappyNumber {
	// Time Complexity: O(log n)

  public static boolean happyNum(int n) {
    int slow = squareSum(n);
    int fast = squareSum(slow);
    while (slow != fast) {
      slow = squareSum(slow);
      fast = squareSum(squareSum(fast));
    }
    return slow == 1;
  }

  private static int squareSum(int n) {
    int sum = 0;
    while (n > 0) {
      int digit = n % 10;
      sum += digit * digit;
      n = n / 10;
    }
    return sum;
  }

  public static void main(String[] args) {
    System.out.println(happyNum(23));
    System.out.println(happyNum(12));
  }
}
