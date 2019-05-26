package com.svetanis.datastructures.stack;

import static java.lang.Math.max;

import java.util.Stack;

public final class LargestAreaInHistogram {

  public static int maxArea(int[] hist) {
    // time complexity: O(n)
    int n = hist.length;
    int i = 0;
    int max = 0;
    Stack<Integer> stack = new Stack<>();
    while (i < n) {
      if (stack.isEmpty() || hist[stack.peek()] <= hist[i]) {
        stack.push(i++);
      } else {
        max = max(max, areaWithTop(stack, hist, i));
      }
    }

    while (!stack.isEmpty()) {
      max = max(max, areaWithTop(stack, hist, i));
    }
    return max;
  }

  private static int areaWithTop(Stack<Integer> stack, int[] hist, int i) {
    int top = stack.pop();
    if (stack.isEmpty()) {
      return hist[top] * i;
    } else {
      return hist[top] * (i - stack.peek() - 1);
    }
  }

  public static void main(String[] args) {
    int[] hist = { 6, 2, 5, 4, 5, 1, 6 };
    System.out.println(maxArea(hist));
  }
}