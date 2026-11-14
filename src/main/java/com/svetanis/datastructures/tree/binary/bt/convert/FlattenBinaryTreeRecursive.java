package com.svetanis.datastructures.tree.binary.bt.convert;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes;

// 114. Flatten Binary Tree to Linked List
//
// Input: the root of a binary tree. On LeetCode: 0 to 2000 nodes, -100 <= val <= 100.
// Returns: nothing -- the tree is rewired in place so that, following only right pointers from the
// root, the nodes appear in pre-order (node, left subtree, right subtree), and every left pointer
// is null.
//
// The one idea: trust the method on the two subtrees. After flatten(left) the left subtree is a
// right-pointer list in pre-order, and after flatten(right) so is the right subtree. The result for
// this node is: the node, then the left list, then the right list. So point the node's right
// pointer at the left list, walk to the tail of that list, and attach the right list there. If the
// left list is empty the node's right pointer is just set to null first and the right list is
// attached directly to the node.
//
// Time: O(n^2) -- after flattening the left subtree it walks that whole flattened list again to
// find its tail, once per node. On a left-leaning tree that walk is n-1 steps at the root, n-2
// below it, and so on: n(n-1)/2 in total.
// Space: O(h) -- the recursion stack, as deep as the tree is tall (up to n).
//
// Siblings: FlattenBinaryTreeIterative finds each tail while rewiring and needs no stack (O(n)
// time, O(1) space); FlattenBinaryTreeStack walks the nodes in pre-order with a stack (O(n) time,
// O(h) space).

public final class FlattenBinaryTreeRecursive {

  public static void flatten(TreeNode root) {
    if (root == null) { // BASE: empty subtree
      return;
    }
    TreeNode left = root.left;
    TreeNode right = root.right;
    root.left = null; // CLEAR: no left pointers in the result

    flatten(left); // LEFT LIST: left subtree as a right-pointer list
    flatten(right); // RIGHT LIST: right subtree as a right-pointer list

    root.right = left; // ATTACH: left list directly after this node
    TreeNode node = root;
    while (node.right != null) { // FIND: tail of the list built so far
      node = node.right;
    }
    node.right = right; // ATTACH: right list after the tail
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(5);
    root.left.left = newNode(3);
    root.left.right = newNode(4);
    root.right.right = newNode(6);
    flatten(root);
    Nodes.preOrder(root); // 1 2 3 4 5 6
    System.out.println();
    TreeNode root1 = newNode(0);
    flatten(root1);
    Nodes.preOrder(root1); // 0
    System.out.println();
  }
}
