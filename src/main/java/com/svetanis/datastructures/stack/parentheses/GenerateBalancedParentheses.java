package com.svetanis.datastructures.stack.parentheses;

import java.util.ArrayList;
import java.util.List;

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
//
// Siblings -- the same problem in the algorithms repo, backtracking/additionalstates:
//   GenerateBalancedParenthesesGuarded      -- the same search: counts still to place, tests at
//                                              the call sites
//   GenerateBalancedParenthesesRecursive    -- counts placed brackets upward; both calls are made
//                                              and a guard at the top rejects a bad state
//   GenerateBalancedParenthesesBacktracking -- one shared StringBuilder, each append undone on
//                                              the way out
//   GenerateBalancedParenthesesIterative    -- the backtracking search with a queue in place of
//                                              the call stack
//   GenerateBalancedParenthesesBruteForce   -- builds all 2^(2n) strings, tests each when complete

public final class GenerateBalancedParentheses {
  // Time Complexity: O(4^n / sqrt(n)), about 4^n / n^1.5 strings (the Catalan number), each O(n)
  // Space Complexity: O(4^n / sqrt(n)) for the output; the recursion is 2n calls deep

  public static List<String> generate(int n) {
    List<String> list = new ArrayList<>(); // ANSWER: every finished string
    add("", n, n, list); // START: nothing placed, n of each left to place
    return list; // RETURN: every balanced string of n pairs
  }

  private static void add(String s, int left, int right, List<String> list) {
    // the two ifs below never let left go negative or right fall below left
    if (left == 0 && right == 0) {
      list.add(s); // RECORD: every bracket placed, and every prefix was legal
      return;
    }

    if (left > 0) {
      add(s + '(', left - 1, right, list); // OPEN: a '(' is always allowed while any remain
    }

    if (right > left) { // CLOSE: a ')' needs a '(' before it that is still unmatched
      add(s + ')', left, right - 1, list);
    }

  }

  public static void main(String[] args) {
    generate(3).forEach(System.out::println);
  }
}