package com.svetanis.datastructures.stack;

import static com.svetanis.java.base.utils.Print.print;

import java.util.Stack;

import com.svetanis.java.base.utils.Arrays;
import com.svetanis.java.base.utils.Print;

public final class StackSortDescendingRecursive {

  public static void sort(Stack<Integer> stack) {
    if (!stack.isEmpty()) {
      int temp = stack.pop();
      System.out.println(temp);
      sort(stack);
      Print.print(Arrays.toList(stack));
      sortedInsert(stack, temp);
      
    }
  }

  private static void sortedInsert(Stack<Integer> stack, int x) {
    if (stack.isEmpty() || stack.peek() > x) {
      stack.push(x);
      return;
    }
    int temp = stack.pop();
    sortedInsert(stack, x);
    stack.push(temp);
  }

  public static void main(String[] args) {
    Stack<Integer> stack = new Stack<Integer>();
    stack.push(9);
    stack.push(1);
    stack.push(5);
    stack.push(12);
    print(stack);
    sort(stack);
    print(stack);
  }
}