package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 103. Binary Tree Zigzag Level Order Traversal, as one flat list
//
// Input: root of a binary tree. Returns the values as ONE list (problem 103 asks for a list per
// level): the first level left to right, the second right to left, and so on, alternating. A null
// root gives an empty list. On LeetCode: 0 <= number of nodes <= 2000, -100 <= node value <= 100.
//
// The one idea: one queue entry is one node waiting to be read. At the start of each round the
// queue holds exactly one whole level, left to right, so its size is that level's node count.
// Take that many nodes off; on a left-to-right level append each value to the output as it comes;
// on a right-to-left level push each value on a stack instead, and when the level is done pop the
// stack into the output -- a stack hands the values back last-in first, which reverses the level.
// A flag, flipped after each level, says which kind the level is, and it starts as left to right.
//
// Time: O(n) -- each node is queued, and pushed and popped at most once.
// Space: O(n) -- the output; the queue and the stack each hold at most one level.
//
// Siblings: ZigZagQueue and ZigZagTwoStack return one list per level; ZigZagRecursive walks the
// tree once per level.

public final class ZigZagStackQueue {

  public static List<Integer> traverse(TreeNode root) {
    List<Integer> list = new ArrayList<>();
    if (isNull(root)) {
      return list;
    }

    boolean reverse = false; // DIRECTION: true when the level being read goes right to left
    Deque<Integer> stack = new ArrayDeque<>();
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      int size = queue.size(); // SIZE: the queue holds exactly one level
      while (size > 0) {
        TreeNode front = queue.poll(); // TAKE: one node of this level
        size--;

        if (reverse) {
          stack.push(front.val); // HOLD: to be popped in reverse
        } else {
          list.add(front.val); // APPEND: left-to-right level
        }

        if (isNotNull(front.left)) {
          queue.offer(front.left); // QUEUE: the next level, always left to right
        }

        if (isNotNull(front.right)) {
          queue.offer(front.right);
        }
      }

      if (reverse) {
        while (!stack.isEmpty()) {
          list.add(stack.pop()); // FLUSH: the level, last-in first
        }
      }
      reverse = !reverse; // FLIP: the next level goes the other way
    }

    return list;
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(7);
    root.left.right = newNode(6);
    root.right.left = newNode(5);
    root.right.right = newNode(4);
    System.out.println(traverse(root)); // [1, 3, 2, 7, 6, 5, 4]
  }
}
