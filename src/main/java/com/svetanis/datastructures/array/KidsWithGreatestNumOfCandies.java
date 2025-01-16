package com.svetanis.datastructures.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 1431. Kids With the Greatest Number of Candies

public final class KidsWithGreatestNumOfCandies {
  // Time Complexity: O(n)
  // Space Complexity: O(n)

  public static List<Boolean> kidsWithCandies(int[] candies, int extra) {
    int max = Arrays.stream(candies).max().getAsInt();
    List<Boolean> list = new ArrayList<>();
    for (int candy : candies) {
      list.add(candy + extra >= max);
    }
    return list;
  }

  public static void main(String[] args) {
    int[] a = { 2, 3, 5, 1, 3 };
    System.out.println(kidsWithCandies(a, 3)); // true,true,true,false,true
    int[] a1 = { 4, 2, 1, 1, 2 };
    System.out.println(kidsWithCandies(a1, 1)); // true,false,false,false,false
    int[] a2 = { 12, 1, 12 };
    System.out.println(kidsWithCandies(a2, 10)); // true,false,true

  }
}
