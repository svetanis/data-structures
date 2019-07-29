package com.svetanis.datastructures.tree.interval;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.interval.Interval.newInterval;
import static com.svetanis.datastructures.tree.interval.IntervalTree.inOrder;
import static com.svetanis.datastructures.tree.interval.IntervalTree.insert;
import static com.svetanis.datastructures.tree.interval.IntervalTree.search;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public final class ConflictingAppointments {

  public static Node conflicts(List<Interval> list) {
    // create an empty Interval Search Tree,
    // add first appointment
    Node root = insert(null, list.get(0));
    // process rest of the intervals
    for (int i = 1; i < list.size(); i++) {
      // if current appointment conflicts with
      // any of the existing intervals, print it
      Optional<Interval> overlaped = search(root, list.get(i));
      if (overlaped.isPresent()) {
        System.out.println(list.get(i) + " conflicts with " + overlaped.get());
      }
      // insert this appointment
      root = insert(root, list.get(i));
    }
    return root;
  }

  public static void main(String[] args) {
    List<Interval> list = intervals();
    Node root = conflicts(list);
    inOrder(root);
  }

  private static ImmutableList<Interval> intervals() {
    List<Interval> list = newArrayList();
    list.add(newInterval(1, 5));
    list.add(newInterval(3, 7));
    list.add(newInterval(2, 6));
    list.add(newInterval(10, 15));
    list.add(newInterval(5, 6));
    list.add(newInterval(4, 100));
    return newList(list);
  }

}