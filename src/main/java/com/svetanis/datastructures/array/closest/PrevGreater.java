package com.svetanis.datastructures.array.closest;

import static com.svetanis.java.base.utils.Print.print;
import static java.util.Arrays.fill;

import java.util.Stack;

import com.svetanis.java.base.Pair;

public final class PrevGreater {

  public static int[] prevGreater(int[] a) {
    // Time complexity: O(n)

    int n = a.length;
    int[] greater = new int[n];
    fill(greater, -1);

    Stack<Pair<Integer, Integer>> stack = new Stack<>();
    for (int i = n - 1; i >= 0; i--) {
      while (!stack.isEmpty() && a[i] > stack.peek().getLeft()) {
        Pair<Integer, Integer> top = stack.pop();
        greater[top.getRight()] = a[i];
      }
      stack.push(Pair.build(a[i], i));
    }
    return greater;
  }

  public static void main(String[] args) {
    int[] a1 = { 10, 4, 2, 20, 40, 12, 30 };
    print(prevGreater(a1));

    int[] a2 = { 10, 20, 30, 40 };
    print(prevGreater(a2));

    int[] a3 = { 40, 30, 20, 10 };
    print(prevGreater(a3));
  }
}

//-1 10 4 -1 -1 40 40 
//-1 -1 -1 -1 
//-1 40 30 20 
