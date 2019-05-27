package com.svetanis.datastructures.tree.interval;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.interval.Interval.newInterval;
import static com.svetanis.java.base.collect.Lists.newList;
import static java.lang.Math.max;

import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public final class IntervalTree {

  public static Node construct(List<Interval> list) {
    Node root = null;
    for (Interval interval : list) {
      root = insert(root, interval);
    }
    return root;
  }

  public static Node insert(Node root, Interval interval) {
    // base case: tree is empty, new node becomes root
    if (root == null) {
      return new Node(interval);
    }

    // get low value of interval at root
    int low = root.interval.low;

    // if root's low value is smaller, then
    // new interval goes to left subtree
    if (interval.low < low) {
      root.left = insert(root.left, interval);
    } else { // new node goes to right subtree
      // max value of root is also updated
      root.right = insert(root.right, interval);
      root.max = max(root.max, interval.high);
    }
    return root;
  }

  public static void inOrder(Node root) {
    if (root == null) {
      return;
    }
    inOrder(root.left);
    System.out.println(root);
    inOrder(root.right);
  }

  public static Optional<Interval> search(Node root, Interval x) {
    if (root == null) {
      return absent() ;
    }
    // if given interval overlaps with root
    if (isOverlap(root.interval, x)) {
      return of(root.interval);
    }

    // if left child of root is present and left.max >= x,
    // it may overlap with interval in left subtree
    if (root.left != null && root.left.max >= x.low) {
      return search(root.left, x);
    }
    // else interval can only overlap with right subtree
    return search(root.right, x);
  }

  private static boolean isOverlap(Interval x, Interval y) {
    return x.low <= y.high && y.low <= x.high;
  }

  public static void main(String[] args) {
    List<Interval> list = intervals();
    Node root = construct(list);
    inOrder(root);

    Interval x = newInterval(6, 7);
    Optional<Interval> overlaped = search(root, x);

    if (overlaped.isPresent()) {
      System.out.println("Overlaps with " + overlaped.get());
    } else {
      System.out.println("No overlaped interval");
    }
  }

  private static ImmutableList<Interval> intervals() {
    List<Interval> list = newArrayList();
    list.add(newInterval(15, 20));
    list.add(newInterval(10, 30));
    list.add(newInterval(17, 19));
    list.add(newInterval(5, 20));
    list.add(newInterval(12, 15));
    list.add(newInterval(30, 40));
    return newList(list);
  }

}