package com.svetanis.datastructures.array.closest;

import static com.svetanis.java.base.utils.Print.print;
import static java.util.Arrays.fill;

import java.util.Stack;

import com.svetanis.java.base.Pair;

public final class NextGreater {

  public static int[] nextGreater(int[] a) {
    // Time complexity: O(n)

    int n = a.length;
    int[] greater = new int[n];
    fill(greater, -1);

    Stack<Pair<Integer, Integer>> stack = new Stack<>();
    for (int i = 0; i < n; i++) {
      while (!stack.isEmpty() && a[i] > stack.peek().getLeft()) {
        Pair<Integer, Integer> top = stack.pop();
        greater[top.getRight()] = a[i];
      }
      stack.push(Pair.build(a[i], i));
    }
    return greater;
  }

  public static void main(String[] args) {
    int[] a1 = { 11, 13, 21, 3 };
    print(nextGreater(a1));

    int[] a2 = { 4, 5, 2, 25 };
    print(nextGreater(a2));

    int[] a3 = { 13, 7, 6, 12 };
    print(nextGreater(a3));

    int[] a4 = { 4, 5, 2, 10 };
    print(nextGreater(a4));

    int[] a5 = { 3, 2, 1 };
    print(nextGreater(a5));
  }
}