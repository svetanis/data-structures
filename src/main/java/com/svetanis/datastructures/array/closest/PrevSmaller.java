package com.svetanis.datastructures.array.closest;

import static com.svetanis.java.base.utils.Print.print;
import static java.util.Arrays.fill;

import java.util.Stack;

import com.svetanis.java.base.Pair;

public final class PrevSmaller {

  public static int[] prevSmaller(int[] a) {
    // time complexity: O(n);
    // Space complexity: O(n)

    int n = a.length;
    int[] smaller = new int[n];
    fill(smaller, -1);

    Stack<Pair<Integer, Integer>> stack = new Stack<Pair<Integer, Integer>>();
    for (int i = n - 1; i >= 0; i--) {
      while (!stack.isEmpty() && a[i] < stack.peek().getLeft()) {
        Pair<Integer, Integer> top = stack.pop();
        smaller[top.getRight()] = a[i];
      }
      stack.push(Pair.build(a[i], i));
    }
    return smaller;
  }

  public static void main(String[] args) {
    int[] a1 = { 1, 6, 4, 10, 2, 5 };
    print(prevSmaller(a1));

    int[] a2 = { 1, 3, 0, 2, 5 };
    print(prevSmaller(a2));
  }
}
