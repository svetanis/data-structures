package com.svetanis.datastructures.graph.bfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

// 1215. Stepping Numbers
//
// Input: start and end -- the two ends of a range. A stepping number is one in which every two
// neighbouring digits differ by exactly 1 (321 and 98 are, 421 is not; every one-digit number is).
// Returns every stepping number in [start, end], smallest first. On LeetCode (where the ends are
// called low and high): 0 <= low <= high <= 2 * 10^9.
//
// The one idea: do not test every number in the range, build only the stepping numbers. One queue
// entry (or, in the depth-first walk, one call of dfs) is a stepping number; its next numbers are
// the ones made by adding one digit on the right, and the new digit must differ from the old last
// digit by 1, so there are at most two: last - 1 and last + 1 (only one at 0 and at 9). Start from
// the one-digit numbers 0 to 9, one search from each digit. The numbers on each branch only get
// longer and so only get bigger, so a number above end can be dropped together with everything
// built from it. The final sort puts the results from the ten searches into one increasing list.
//
// Time: O(k log k) -- k is the number of stepping numbers up to end (3502 of them up to
// 2 * 10^9), each built once, then sorted.
// Space: O(k) -- the answer, plus the queue in stepNums; the recursion in stepNumsDfs is at most
// 10 calls deep, one per digit.
//
// Siblings: stepNums builds the numbers breadth first and stepNumsDfs builds the same numbers
// depth first; both are in this class.

public final class SteppingNumbers {

  public static List<Integer> stepNums(int start, int end) {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i <= 9; i++) {
      list.addAll(bfs(i, start, end)); // WALK: all stepping numbers that begin with digit i
    }
    Collections.sort(list); // SORT: ten searches, each in its own order
    return list;
  }

  public static List<Integer> stepNumsDfs(int start, int end) {
    List<Integer> concat = new ArrayList<>();
    for (int i = 0; i <= 9; i++) {
      List<Integer> list = new ArrayList<>();
      dfs(i, start, end, list); // WALK: all stepping numbers that begin with digit i
      concat.addAll(list);
    }
    Collections.sort(concat); // SORT: ten searches, each in its own order
    return concat;
  }

  private static List<Integer> bfs(int num, int start, int end) {
    List<Integer> list = new ArrayList<>();
    Queue<Integer> queue = new ArrayDeque<>();
    queue.offer(num);
    while (!queue.isEmpty()) {
      int front = queue.poll(); // TAKE
      if (in(front, start, end)) {
        list.add(front); // RECORD: inside the range
      }

      // SKIP: 0 is not extended (00 is not a number), and every number built from front would be
      // above end; front * 10 is tested as front > end / 10 so that it never overflows an int
      if (front == 0 || front > end / 10) {
        continue;
      }

      int last = front % 10;
      int left = front * 10 + (last - 1); // EXTEND: a digit one smaller than the last digit
      int right = front * 10 + (last + 1); // EXTEND: a digit one bigger than the last digit

      if (last == 0) {
        queue.offer(right); // no digit below 0
      } else if (last == 9) {
        queue.offer(left); // no digit above 9
      } else {
        queue.offer(left);
        queue.offer(right);
      }
    }
    return list;
  }

  private static void dfs(int num, int start, int end, List<Integer> list) {

    if (in(num, start, end)) {
      list.add(num); // RECORD: inside the range
    }

    // SKIP: 0 is not extended (00 is not a number), and every number built from num would be
    // above end; num * 10 is tested as num > end / 10 so that it never overflows an int
    if (num == 0 || num > end / 10) {
      return;
    }

    int last = num % 10;
    int left = num * 10 + (last - 1); // EXTEND: a digit one smaller than the last digit
    int right = num * 10 + (last + 1); // EXTEND: a digit one bigger than the last digit

    if (last == 0) {
      dfs(right, start, end, list); // no digit below 0
    } else if (last == 9) {
      dfs(left, start, end, list); // no digit above 9
    } else {
      dfs(left, start, end, list);
      dfs(right, start, end, list);
    }
  }

  private static boolean in(int num, int start, int end) {
    return num <= end && num >= start;
  }

  public static void main(String[] args) {
    System.out.println(stepNums(10, 20)); // [10, 12]
    System.out.println(stepNums(0, 21)); // [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 21]
    System.out.println(stepNums(0, 2000000000).size()); // 3502
    System.out.println(stepNumsDfs(10, 20)); // [10, 12]
    System.out.println(stepNumsDfs(0, 21)); // [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 21]
    System.out.println(stepNumsDfs(0, 2000000000).size()); // 3502
  }
}
