package com.svetanis.datastructures.tree.interval;

import static com.svetanis.datastructures.tree.interval.Interval.newInterval;
import static com.svetanis.datastructures.tree.interval.IntervalTree.inOrder;
import static com.svetanis.datastructures.tree.interval.IntervalTree.insert;
import static com.svetanis.datastructures.tree.interval.IntervalTree.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Print every appointment that conflicts with an appointment booked before it. Not a LeetCode
// problem; LeetCode 729 (My Calendar I) asks whether one booking conflicts with the earlier ones.
//
// Input: list holds appointments as closed intervals [low, high], in the order they were
// booked. For each appointment after the first, prints it together with one earlier appointment
// it overlaps, if there is one. Appointments that only touch at an endpoint conflict: [1, 5] and
// [5, 6] share the time 5. Returns the root of the interval tree that ends up holding them all,
// or null when there are no appointments. Every interval must have low <= high.
//
// The one idea: the earlier appointments sit in an interval tree. Each new appointment is first
// searched for, which finds one stored appointment overlapping it or none, and only then
// inserted, so the tree always holds exactly the appointments booked before the one being
// checked.
//
// Time: O(n * h) -- one search and one insert per appointment, each one path of height h: about
// n log n when the lows arrive in random order, n^2 when they arrive sorted.
// Space: O(n) -- one tree node per appointment.
//
// Siblings: IntervalTree holds the tree, and has the same Known limit on a very tall tree.

public final class ConflictingAppointments {

  public static Node conflicts(List<Interval> list) {
    if (list.isEmpty()) { // BASE: no appointments, so no tree
      return null;
    }
    // the tree starts with the first appointment, which has nothing before it
    Node root = insert(null, list.get(0));
    // process rest of the intervals
    for (int i = 1; i < list.size(); i++) { // WALK: in booking order
      // if current appointment conflicts with
      // any of the existing intervals, print it
      Optional<Interval> overlaped = search(root, list.get(i)); // SEARCH: before inserting
      if (overlaped.isPresent()) {
        System.out.println(list.get(i) + " conflicts with " + overlaped.get()); // REPORT: the pair
      }
      // insert only after the search, so it is never compared with itself
      root = insert(root, list.get(i)); // INSERT: now it counts as booked
    }
    return root;
  }

  public static void main(String[] args) {
    List<Interval> list = intervals();
    // prints, as the appointments are checked:
    // [3, 7] conflicts with [1, 5]
    // [2, 6] conflicts with [1, 5]
    // [5, 6] conflicts with [1, 5]
    // [4, 100] conflicts with [1, 5]
    Node root = conflicts(list);
    // then the tree, in order of low, each interval with its subtree's max:
    // [1, 5] 100, [2, 6] 6, [3, 7] 100, [4, 100] 100, [5, 6] 100, [10, 15] 100
    inOrder(root);
  }

  private static List<Interval> intervals() {
    List<Interval> list = new ArrayList<>();
    list.add(newInterval(1, 5));
    list.add(newInterval(3, 7));
    list.add(newInterval(2, 6));
    list.add(newInterval(10, 15));
    list.add(newInterval(5, 6));
    list.add(newInterval(4, 100));
    return list;
  }

}