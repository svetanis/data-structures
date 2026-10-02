package com.svetanis.datastructures.linkedlist.single.cycle;

// 202. Happy Number
//
// A number is called happy if it leads to 1 after a sequence of steps
// where in each step number is replaced by sum of squares of its digit
// that is if we start with Happy Number and
// keep replacing it with digits square sum, we reach 1.
// Given a positive n, return true if n is happy and false if it is not.
//
// There is no list here, but each number has exactly one next number, the sum of the
// squares of its digits, so the numbers behave like nodes joined by next pointers. Every
// number from 1000 up has a smaller next number, so the numbers cannot grow forever: they
// must repeat, and from then on they go round a cycle. 1 is its own next number, so for a
// happy n that cycle is just 1. slow moves one number per turn and fast two, as in Linked
// List Cycle (LC 141), so they meet inside the cycle, and n is happy exactly when they meet on 1.

public final class HappyNumber {
	// Time Complexity: O(log n), the first step reads the digits of n; every later number is below 1000
	// Space Complexity: O(1), two numbers

  public static boolean happyNum(int n) {
    int slow = squareSum(n);
    int fast = squareSum(slow); // one step ahead of slow: equal values would end the loop at once
    while (slow != fast) {
      slow = squareSum(slow);
      fast = squareSum(squareSum(fast));
    }
    return slow == 1; // they meet inside the cycle, and the cycle is just 1 only for a happy n
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
    System.out.println(happyNum(23)); // true
    System.out.println(happyNum(12)); // false
  }
}
