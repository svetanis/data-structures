package com.svetanis.datastructures.array.closest;

import static com.svetanis.java.base.utils.Print.print;
import static java.lang.Math.max;
import static java.util.Arrays.fill;

import java.util.Stack;

public final class MaxOfMinsStack {

  public static int[] maxOfMins(int[] a) {
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    
    int n = a.length;
    int[] result = new int[n + 1];

    // arrays of indexes
    int[] left = prevSmaller(a);
    int[] right = nextSmaller(a);

    // fill result array by comparing
    // mins of all lengths
    // computed using left[] and right[]
    for (int i = 0; i < n; i++) {
      // length of interval
      int len = right[i] - left[i] - 1;

      // a[i] is possible answer for this interval
      // check if a[i] > max for len
      result[len] = max(result[len], a[i]);
    }

    // fill the remaining by taking
    // values from right side
    for (int i = n - 1; i >= 0; i--) {
      result[i] = max(result[i], result[i + 1]);
    }
    return result;
  }

  public static int[] prevSmaller(int[] a) {
    // Time Complexity: O(n)

    int n = a.length;
    int[] left = new int[n + 1];
    fill(left, -1);
    Stack<Integer> stack = new Stack<Integer>();
    for (int i = 0; i < n; i++) {
      while (!stack.empty() && a[stack.peek()] >= a[i]) {
        stack.pop();
      }
      
      if (!stack.isEmpty()) {
        left[i] = stack.peek();
      }
      stack.push(i);
    }
    return left;
  }


  private static int[] nextSmaller(int[] a) {
    int n = a.length;
    int[] right = new int[n + 1];
    fill(right, n);
    Stack<Integer> stack = new Stack<Integer>();
    for (int i = n - 1; i >= 0; i--) {
      while (!stack.empty() && a[stack.peek()] >= a[i]) {
        stack.pop();
      }
      
      if (!stack.empty()) {
        right[i] = stack.peek();
      }
      stack.push(i);
    }
    return right;
  }

  public static void main(String[] args) {
    int[] a = { 10, 20, 30, 50, 10, 70, 30 };
    print(maxOfMins(a));
  }
}
