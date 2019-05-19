package com.svetanis.datastructures.array.closest;

import static com.svetanis.java.base.utils.Print.print;
import static java.util.Arrays.fill;

import java.util.Stack;

import com.svetanis.java.base.Pair;

public final class NextSmaller {

  public static int[] nextSmaller(int[] a) {
    // time complexity: O(n);
    // Space complexity: O(n)

    int n = a.length;
    int[] smaller = new int[n];
    fill(smaller, -1);

    Stack<Pair<Integer, Integer>> stack = new Stack<Pair<Integer, Integer>>();
    for (int i = 0; i < n; i++) {
      while (!stack.isEmpty() && a[i] < stack.peek().getLeft()) {
        Pair<Integer, Integer> top = stack.pop();
        smaller[top.getRight()] = a[i];
      }
      stack.push(Pair.build(a[i], i));
    }
    return smaller;
  }

  public static void main(String[] args) {
    int[] a1 = { 5, 1, 3, 4, 6, 2 };
    print(nextSmaller(a1));

    int[] a2 = { 1, 3, 3, 2, 5 };
    print(nextSmaller(a2));

    int[] a3 = { 4, 2, 1, 5, 3 };
    print(nextSmaller(a3));

    int[] a4 = { 1, 2, 3, 4, 5 };
    print(nextSmaller(a4));

    int[] a5 = { 5, 4, 3, 2, 1 };
    print(nextSmaller(a5));

    int[] a6 = { 1, 3, 5, 4, 2 };
    print(nextSmaller(a6));

    int[] a7 = { 6, 4, 2 };
    print(nextSmaller(a7));
  }
}
