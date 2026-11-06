package com.svetanis.datastructures.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 1431. Kids With the Greatest Number of Candies
//
// Input: candies -- candies[i] is how many candies kid i has; extra -- the number of extra candies
// that can all be given to ONE kid. Returns, for each kid, true if that kid, given all the extras,
// would have at least as many candies as any other kid has now. On LeetCode: n == candies.length,
// 2 <= n <= 100, 1 <= candies[i] <= 100, 1 <= extra <= 50.
//
// The one idea: the others stay as they are, so the test for kid i is candies[i] + extra >= the
// largest count anyone has now. (If kid i is the largest already, adding candies keeps it so;
// comparing with the largest including kid i is the same test.) Find the largest once, then test
// each kid against it.
//
// Time: O(n) -- one pass for the maximum, one for the answers.
// Space: O(n) -- the answer list.

public final class KidsWithGreatestNumOfCandies {

  public static List<Boolean> kidsWithCandies(int[] candies, int extra) {
    int max = Arrays.stream(candies).max().getAsInt(); // MAX: the largest count anyone has now
    List<Boolean> list = new ArrayList<>();
    for (int candy : candies) {
      list.add(candy + extra >= max); // TEST: this kid, given the extras, reaches the maximum
    }
    return list;
  }

  public static void main(String[] args) {
    int[] a = { 2, 3, 5, 1, 3 };
    System.out.println(kidsWithCandies(a, 3)); // [true, true, true, false, true]
    int[] a1 = { 4, 2, 1, 1, 2 };
    System.out.println(kidsWithCandies(a1, 1)); // [true, false, false, false, false]
    int[] a2 = { 12, 1, 12 };
    System.out.println(kidsWithCandies(a2, 10)); // [true, false, true]
  }
}
