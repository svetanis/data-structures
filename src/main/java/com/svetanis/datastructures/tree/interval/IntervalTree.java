package com.svetanis.datastructures.tree.interval;

import static com.svetanis.datastructures.tree.interval.Interval.newInterval;
import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Interval tree: stores intervals and finds a stored one that overlaps a given interval. Not a
// LeetCode problem; LeetCode 729 (My Calendar I) asks the same overlap question of a booking.
//
// Input: construct and insert add intervals; search(root, x) returns some stored interval that
// overlaps x, or empty when none does. Intervals are closed, so two that only touch at an
// endpoint overlap: [1, 5] and [5, 6] share the point 5. Every interval must have low <= high.
// Which overlapping interval search returns depends on the shape of the tree; it need not be
// the first one inserted or the one with the smallest low.
//
// The one idea: the tree is a binary search tree keyed on low: an interval with a smaller low
// than a node's goes to its left, an equal or larger low goes to its right. Each node also
// keeps max, the largest high among all the intervals in its subtree, raised on the way down
// by every insert that passes through the node, whichever side the new interval goes to.
// search uses max to choose one side and skip the other for good. If the left subtree's max is
// below x.low, every interval there ends before x starts, so only the right side can hold an
// answer. If it is at least x.low, some interval L on the left ends at or after x.low; if L
// still misses x, L must start after x.high, and every interval on the right starts no earlier
// than L, so they all miss x too. Either way one side is enough.
//
// Time: insert, search O(h) -- one path from the root, where h is the tree's height: about
// log n when the lows arrive in random order, n when they arrive sorted (no rebalancing).
// construct O(n * h) -- one insert per interval. inOrder O(n) -- every node once.
// Space: O(n) nodes, plus O(h) recursion stack for each call.
//
// Known limit: inserts and searches are recursive, one frame per level. Tens of thousands of
// intervals inserted in sorted order of low make a chain as tall as it is long, and the
// recursion then overflows the stack. Inserted in random order, the tree stays shallow.
//
// Siblings: ConflictingAppointments uses this tree to report the appointments that conflict
// with an earlier one.

public final class IntervalTree {

  public static Node construct(List<Interval> list) {
    Node root = null;
    for (Interval interval : list) { // WALK: in the order given
      root = insert(root, interval);
    }
    return root;
  }

  public static Node insert(Node root, Interval interval) {
    // base case: tree is empty, new node becomes root
    if (root == null) { // BASE: a new node holds the interval
      return new Node(interval);
    }

    // get low value of interval at root
    int low = root.interval.low;

    // a smaller low goes left; an equal or larger low goes right
    if (interval.low < low) { // SPLIT: by low
      root.left = insert(root.left, interval);
    } else {
      root.right = insert(root.right, interval);
    }
    // on either side, root's subtree now also holds interval.high
    root.max = max(root.max, interval.high); // RAISE: max for the subtree
    return root;
  }

  public static void inOrder(Node root) {
    if (root == null) {
      return;
    }
    inOrder(root.left);
    System.out.println(root); // PRINT: interval, then max, in order of low
    inOrder(root.right);
  }

  public static Optional<Interval> search(Node root, Interval x) {
    if (root == null) { // BASE: fell off the tree, nothing overlaps
      return Optional.empty();
    }
    // if given interval overlaps with root
    if (isOverlap(root.interval, x)) { // FOUND: this one overlaps
      return Optional.of(root.interval);
    }

    // some interval on the left ends at or after x.low: if none of the left
    // overlaps x, nothing on the right does either (see the class comment)
    if (root.left != null && root.left.max >= x.low) { // CHOOSE: left, using its max
      return search(root.left, x);
    }
    // every interval on the left ends before x starts
    return search(root.right, x); // CHOOSE: right
  }

  private static boolean isOverlap(Interval x, Interval y) {
    return x.low <= y.high && y.low <= x.high; // <=: touching endpoints overlap
  }

  public static void main(String[] args) {
    List<Interval> list = intervals();
    Node root = construct(list);
    // the intervals in order of low, each with its subtree's max:
    // [5, 20] 20, [10, 30] 30, [12, 15] 15, [15, 20] 40, [17, 19] 40, [30, 40] 40
    inOrder(root);

    Interval x = newInterval(6, 7);
    Optional<Interval> overlaped = search(root, x);

    if (overlaped.isPresent()) {
      System.out.println("Overlaps with " + overlaped.get()); // Overlaps with [5, 20]
    } else {
      System.out.println("No overlaped interval");
    }

    Optional<Interval> none = search(root, newInterval(41, 50));
    System.out.println(none.isPresent()); // false
  }

  private static List<Interval> intervals() {
    List<Interval> list = new ArrayList<>();
    list.add(newInterval(15, 20));
    list.add(newInterval(10, 30));
    list.add(newInterval(17, 19));
    list.add(newInterval(5, 20));
    list.add(newInterval(12, 15));
    list.add(newInterval(30, 40));
    return list;
  }

}