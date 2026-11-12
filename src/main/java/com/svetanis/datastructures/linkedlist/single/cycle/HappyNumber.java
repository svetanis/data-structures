package com.svetanis.datastructures.linkedlist.single.cycle;

// 202. Happy Number
//
// Input: a positive integer n. On LeetCode: 1 <= n <= 2^31 - 1. A number is happy if replacing
// it with the sum of the squares of its digits, again and again, reaches 1. Returns true if n is
// happy and false if it is not (the numbers then go round a cycle that does not contain 1).
//
// The one idea: there is no list here, but each number has exactly one next number, the sum of
// the squares of its digits, so numbers behave like nodes joined by next pointers. Every number
// from 1000 up has a smaller next number, so the numbers cannot grow forever: they must repeat,
// and from then on they go round a cycle. 1 is its own next number, so for a happy n that cycle
// is just 1. slow moves one number per turn and fast two, as in Linked List Cycle (LC 141), so
// they meet inside the cycle, and n is happy exactly when they meet on 1.
//
// Time: O(log n) -- the first step reads the digits of n; every later number is below 1000, so
//   the rest is a fixed amount of work.
// Space: O(1) -- two numbers.

public final class HappyNumber {

  public static boolean happyNum(int n) {
    int slow = squareSum(n); // STEP: one number ahead of n, the same step for both
    int fast = squareSum(slow); // one step ahead of slow: equal values would end the loop at once
    while (slow != fast) {
      slow = squareSum(slow); // STEP: one number
      fast = squareSum(squareSum(fast)); // STEP: two numbers
    }
    return slow == 1; // MEET: they meet inside the cycle, and the cycle is just 1 only for a happy n
  }

  private static int squareSum(int n) {
    int sum = 0;
    while (n > 0) {
      int digit = n % 10; // TAKE the last digit
      sum += digit * digit; // ADD its square
      n = n / 10; // DROP the last digit
    }
    return sum;
  }

  public static void main(String[] args) {
    System.out.println(happyNum(23)); // true
    System.out.println(happyNum(12)); // false
  }
}
