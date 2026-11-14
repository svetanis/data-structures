package com.svetanis.datastructures.tree.binary.bt.cousins;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Print All Cousins of a Given Node (no LeetCode number)
//
// Input: the root of a binary tree and a node of that tree. The cousins of the node are the nodes
// on its level that do not share its parent; its sibling and itself are not cousins. Returns the
// values of the cousins from left to right, as a list that cannot be changed. The root, and a
// node that is not in the tree, have no cousins.
//
// The one idea: go level by level, and stop on the level whose nodes include the given node's
// parent. On that level each node is taken off the queue and its children are queued -- except
// for the parent, whose children are left out. What is then in the queue is the whole next level
// minus the parent's children, the node and its sibling: exactly the cousins, already in left-to-
// right order. The nodes are compared by reference, so equal values elsewhere in the tree do not
// matter.
//
// Time: O(n) -- each node is queued and taken off at most once.
// Space: O(w) -- the queue holds one level or two; w is the widest level, up to n / 2.
//
// Siblings: PrintCousinsRecursive finds the level first and then collects it with a recursion; it
// finds the level by value, so it needs unique values.

public final class PrintCousinsIterative {

  public static List<Integer> cousins(TreeNode root, TreeNode node) {
    List<Integer> list = new ArrayList<>();
    if (root == node) {
      return Collections.unmodifiableList(list); // BASE: the root has no cousins
    }

    boolean found = false;
    Queue<TreeNode> queue = new ArrayDeque<>();
    queue.offer(root);

    while (!queue.isEmpty() && !found) {
      int size = queue.size(); // LEVEL: the nodes now in the queue are one level
      for (int i = 0; i < size; i++) {
        TreeNode front = queue.poll();
        if (node == front.left || node == front.right) {
          found = true; // PARENT: leave its children out of the queue
        } else {
          if (isNotNull(front.left)) {
            queue.offer(front.left); // QUEUE: the next level
          }
          if (isNotNull(front.right)) {
            queue.offer(front.right);
          }
        }
      }

      if (found) {
        while (!queue.isEmpty()) { // COLLECT: everything left in the queue, however many there are
          list.add(queue.poll().val);
        }
      }
    }
    return Collections.unmodifiableList(list); // RETURN: the cousins, left to right
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.left.right.right = newNode(15);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    root.right.left.right = newNode(8);
    System.out.println(cousins(root, root.left.right)); // [6, 7]
  }
}
