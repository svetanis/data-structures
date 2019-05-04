package com.svetanis.datastructures.graph.bfs;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Lists.sort;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.Queue;

import com.google.common.collect.ImmutableList;

public final class SteppingNumbers {

  public static ImmutableList<Integer> stepNums(int start, int end) {
    List<Integer> list = newArrayList();
    for (int i = 0; i <= 9; i++) {
      list.addAll(bfs(i, start, end));
    }
    return sort(list);
  }

  private static ImmutableList<Integer> bfs(int num, int start, int end) {
    List<Integer> list = newArrayList();
    Queue<Integer> queue = newLinkedList();
    queue.offer(num);
    while (!queue.isEmpty()) {
      int front = queue.poll();
      if (in(front, start, end)) {
        list.add(front);
      }

      if (front == 0 || front > end) {
        continue;
      }

      int last = front % 10;
      int left = front * 10 + (last - 1);
      int right = front * 10 + (last + 1);

      if (last == 0) {
        queue.offer(right);
      } else if (last == 9) {
        queue.offer(left);
      } else {
        queue.offer(left);
        queue.offer(right);
      }
    }
    return newList(list);
  }

  private static boolean in(int num, int start, int end) {
    return num <= end && num >= start;
  }

  public static void main(String[] args) {
    print(stepNums(10, 20));
  }
}
