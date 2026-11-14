package com.svetanis.datastructures.tree.binary.bt.special;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayDeque;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Full Binary Tree
//
// Input: the root of a binary tree; it may be empty. A full binary tree is one in which every node
// has either zero or two children, never exactly one. Returns true when the tree is full (the empty
// tree is). This is not a LeetCode problem, so it has no stated constraints.
//
// The one idea: read the nodes level by level and look at each one's two child slots. A node with
// no children is fine; a node with two is fine and its children are read next; a node with exactly
// one breaks the rule, so the answer is false. Every node is looked at once, so a tree with no such
// node is full.
//
// Time: O(n) -- each node enters the queue and leaves it once.
// Space: O(n) -- the queue holds about one level of nodes, which is O(n) for a wide tree.

public final class FullBinaryTree {

  public static boolean fullBt(TreeNode root) {

    if (isNull(root)) {
      return true; // BASE: the empty tree is full
    }
    Queue<TreeNode> queue = new ArrayDeque<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      TreeNode node = queue.poll();
      if (isLeaf(node)) {
        continue; // SKIP: no children, nothing to check
      }
      if (isNull(node.left) || isNull(node.right)) {
        return false; // MISMATCH: exactly one child
      }

      queue.offer(node.left); // WALK: both children are read next
      queue.offer(node.right);
    }
    return true;
  }

  public static void main(String[] args) {
    TreeNode root = newNode(10);
    root.left = newNode(20);
    root.right = newNode(30);
    root.left.left = newNode(50);
    root.left.right = newNode(40);
    root.right.left = newNode(60);
    root.right.right = newNode(70);
    root.left.left.left = newNode(80);
    root.left.left.right = newNode(90);
    root.left.right.left = newNode(80);
    root.left.right.right = newNode(90);
    root.right.left.left = newNode(80);
    root.right.left.right = newNode(90);
    root.right.right.left = newNode(80);
    root.right.right.right = newNode(90);
    System.out.println(fullBt(root)); // true

    TreeNode root1 = newNode(1);
    root1.left = newNode(2);
    System.out.println(fullBt(root1)); // false
  }
}
