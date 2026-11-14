package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Level order traversal, as one flat list of nodes
//
// Input: root of a binary tree (the nodes, not the values; the per-level lists of problem 102 are
// in LotLineByLineQueue). Returns the nodes in level order: top level first, each level left to
// right. A null root gives an empty list.
//
// The one idea: one queue entry is one node waiting to be listed. Take the node at the front, list
// it, and queue its left then right child at the back. The queue hands nodes back in the order they
// went in, so every node of a level is listed before any of the next level, and inside a level
// left to right -- the children of an earlier node were queued before those of a later one.
//
// Time: O(n) -- each node is queued and taken off once.
// Space: O(n) -- the output; the queue holds one level at a time, up to n / 2 nodes.

public final class LotQueue {

  public static List<TreeNode> traverse(TreeNode root) {
    if (isNull(root)) {
      return new ArrayList<>();
    }

    List<TreeNode> list = new ArrayList<>();
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      TreeNode node = queue.poll(); // TAKE: the front of the queue
      list.add(node); // LIST: it
      if (isNotNull(node.left)) {
        queue.offer(node.left); // QUEUE: its children at the back, left then right
      }
      if (isNotNull(node.right)) {
        queue.offer(node.right);
      }
    }
    return list;
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);

    System.out.println(traverse(root)); // [1, 2, 3, 4, 5, 6]
  }
}
