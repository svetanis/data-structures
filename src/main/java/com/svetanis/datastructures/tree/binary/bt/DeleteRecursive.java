package com.svetanis.datastructures.tree.binary.bt;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Delete a binary tree, children first
//
// Input: root of a binary tree (not a LeetCode problem). Returns null, to be assigned to the
// caller's root variable: root = delete(root).
//
// The one idea: take the tree apart from the bottom. For a node, first delete its left subtree and
// its right subtree, then set the node's own left and right links to null. Every node ends with no
// links at all, so even a caller that kept a reference to the old root sees a single childless
// node, and nothing keeps the rest of the tree alive. The method returns null for the caller to
// store in place of the root.
//
// Time: O(n) -- every node is visited once.
// Space: O(h) -- the recursion stack, h = the height of the tree (up to n for a chain).
//
// Siblings: DeleteIterative walks level by level and clears no link; this one clears every link.

public final class DeleteRecursive {

  // RECURSIVE: POST-ORDER TRAVERSAL
  public static TreeNode delete(TreeNode root) {
    if (isNotNull(root)) {
      delete(root.left); // WALK: children first
      delete(root.right);
      root.left = null; // UNLINK: the node lets go of both subtrees
      root.right = null;
      root = null;
    }
    return root; // RETURN: null
  }

  public static void main(String[] args) {

    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);

    inOrder(root); // 4 2 5 1 3
    System.out.println();
    root = delete(root);
    inOrder(root); // prints nothing: the tree is gone
    System.out.println();

    TreeNode tree = newNode(15);
    tree.left = newNode(10);
    tree.right = newNode(20);
    tree.left.left = newNode(8);
    tree.left.right = newNode(12);
    tree.right.left = newNode(16);
    tree.right.right = newNode(25);

    inOrder(tree); // 8 10 12 15 16 20 25
    System.out.println();
    tree = delete(tree);
    inOrder(tree); // prints nothing: the tree is gone
    System.out.println();
  }
}