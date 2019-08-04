package com.svetanis.datastructures.tree.binary.bt.iterator;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.java.base.collect.Lists.transform;
import static com.svetanis.java.base.utils.Print.print;
import static java.lang.Math.abs;

import java.util.ArrayDeque;
import java.util.Queue;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

//Given BST and a target value. 
//Find k nodes that have values closest to target.

public final class KClosestToGivenValueInOrder {

  public static ImmutableList<Integer> kClosest(Node root, int k, double target) {
    Queue<Node> queue = new ArrayDeque<>();
    kClosest(root, k, target, queue);
    return transform(queue, n -> n.data);
  }

  private static void kClosest(Node root, int k, double target, Queue<Node> q) {
    if (root == null) {
      return;
    }
    kClosest(root.left, k, target, q);
    if (q.size() < k) {
      q.offer(root);
    } else if (abs(q.peek().data - target) > abs(root.data - target)) {
      q.poll();
      q.offer(root);
    }
    kClosest(root.right, k, target, q);
  }

  public static void main(String[] args) {
    Node root = newNode(20);
    root.left = newNode(8);
    root.right = newNode(22);
    root.left.left = newNode(4);
    root.left.right = newNode(12);
    root.left.right.left = newNode(10);
    root.left.right.right = newNode(14);
    print(kClosest(root, 3, 11.5));
  }
}
