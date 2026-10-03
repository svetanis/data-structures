package com.svetanis.datastructures.stack.parentheses;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLines;

import java.util.List;

import com.google.common.collect.ImmutableList;

// 22. Generate Parentheses
//
// Every balanced string of n pairs of parentheses.
//
// Given n, returns every string of n '(' and n ')' in which each ')' closes an earlier
// unmatched '('.
//
// The string is built one character at a time. left and right count the '(' and ')' still
// to place. A '(' may be added while any remain; a ')' only while more ')' than '(' remain,
// which means some '(' already placed is still unmatched. So the recursion never builds a
// prefix that cannot be finished, and every string it finishes is an answer.

public final class GenerateBalancedParentheses {
  // Time Complexity: O(4^n / sqrt(n)), about 4^n / n^1.5 strings (the Catalan number), each O(n)
  // Space Complexity: O(4^n / sqrt(n)) for the output; the recursion is 2n calls deep

  public static ImmutableList<String> generate(int n) {
    List<String> list = newArrayList();
    add("", n, n, list);
    return newList(list);
  }

  private static void add(String s, int left, int right, List<String> list) {
    // the two ifs below never let left go negative or right fall below left
    if (left == 0 && right == 0) {
      list.add(s);
      return;
    }

    if (left > 0) {
      add(s + '(', left - 1, right, list);
    }

    if (right > left) { // a ')' needs a '(' before it that is still unmatched
      add(s + ')', left, right - 1, list);
    }

  }

  public static void main(String[] args) {
    printLines(generate(3));
  }
}