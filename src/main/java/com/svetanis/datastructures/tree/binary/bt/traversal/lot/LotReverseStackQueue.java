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

// Reverse level order traversal, as one flat list of values
//
// Input: root of a binary tree (not the per-level lists of problem 107; see
// LotLineByLineReverseQueue for those). Returns the values with the deepest level first and the
// root last; inside a level left to right. A null root gives an empty list.
//
// The one idea: one queue entry is one node waiting to be read. Read the tree level by level but
// each level RIGHT to left (queue the right child before the left), pushing every value on a
// stack. That reading order is exactly the reverse of what is wanted -- the root first, the deepest
// level last, each level right to left -- and popping a stack reverses it: the last value pushed,
// the leftmost node of the deepest level, comes out first, and the root comes out last.
//
// Time: O(n) -- each node is queued, pushed and popped once.
// Space: O(n) -- the stack holds every value; the queue holds one level at a time.

public final class LotReverseStackQueue {

  public static List<Integer> traverse(TreeNode root) {
    if (isNull(root)) {
      return new ArrayList<>();
    }

    Queue<TreeNode> queue = new LinkedList<>();
    Deque<Integer> stack = new ArrayDeque<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      TreeNode node = queue.poll(); // TAKE: the next node, level by level
      stack.push(node.val); // PUSH: its value
      if (isNotNull(node.right)) {
        queue.offer(node.right); // QUEUE: right child first, so each level is read right to left
      }
      if (isNotNull(node.left)) {
        queue.offer(node.left);
      }
    }
    return new ArrayList<>(stack); // RETURN: ArrayDeque iterates from the top, the pop order
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);

    System.out.println(traverse(root)); // [4, 5, 6, 2, 3, 1]
  }
}
