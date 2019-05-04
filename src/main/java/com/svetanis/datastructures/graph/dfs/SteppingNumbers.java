package com.svetanis.datastructures.graph.dfs;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.sort;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;

import com.google.common.collect.ImmutableList;

public final class SteppingNumbers {

  public static ImmutableList<Integer> stepNums(int start, int end) {
    List<Integer> concat = newArrayList();
    for (int i = 0; i <= 9; i++) {
      List<Integer> list = newArrayList();
      dfs(i, start, end, list);
      concat.addAll(list);
    }
    return sort(concat);
  }

  private static void dfs(int num, int start, int end, List<Integer> list) {

    if (in(num, start, end)) {
      list.add(num);
    }

    if (num == 0 || num > end) {
      return;
    }

    int last = num % 10;
    int left = num * 10 + (last - 1);
    int right = num * 10 + (last + 1);

    if (last == 0) {
      dfs(right, start, end, list);
    } else if (last == 9) {
      dfs(left, start, end, list);
    } else {
      dfs(left, start, end, list);
      dfs(right, start, end, list);
    }
  }

  private static boolean in(int num, int start, int end) {
    return num <= end && num >= start;
  }

  public static void main(String[] args) {
    print(stepNums(10, 20));
  }
}
