package com.svetanis.datastructures.tree.binary.bst.pruning;

import static com.svetanis.datastructures.tree.binary.bt.traversal.lot.LotLineByLineQueue.lot;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 450. Delete Node in a BST
//
// Input: root -- the root of a binary search tree with distinct values; key -- the value to
// delete. Returns the root of the tree after the node holding key is removed, or the tree
// unchanged when no node holds key. On LeetCode: 0 <= number of nodes <= 10^4,
// -10^5 <= Node.val <= 10^5, all values distinct.
//
// The one idea: one call looks at one node, which holds one value, and returns the root of that
// node's subtree after the delete. If key is smaller than the value, the node to remove is in the
// left subtree, so the left child becomes whatever the recursive call on it returns; larger,
// the same on the right. When the node holds key, there are three cases: no left child -- the
// right child takes its place; no right child -- the left child takes its place; both children --
// the smallest value in the right subtree (the successor) has no left child, so the whole left
// subtree can hang off it, and the right child then takes the deleted node's place. Every value
// in the left subtree is smaller than every value in the right one, so the order is kept.
//
// Time: O(h) -- one path down to the node, then one path down to the successor; h is the tree's
// height, up to n on a chain.
// Space: O(h) -- the call stack.
//
// Depth: the recursion is as deep as the tree is tall; the default stack holds LeetCode's largest
// chain (10^4 nodes) and overflows near 12,000.
//
// Siblings: DeleteNodeInBst450CopySuccessor copies the successor's value into the node and deletes
// the successor from the right subtree instead; same O(h), but it moves no subtrees, so the tree
// does not get taller.

public final class DeleteNodeInBst450 {

  public static TreeNode delete(TreeNode root, int key) {
    if (root == null) {
      return root; // BASE: key is not in the tree
    }

    // if the key is smaller than root's value
    // delete in the left subtree
    if (root.val > key) {
      root.left = delete(root.left, key); // WALK left
      return root;
    }
    // if the key is greater than root's value
    // delete in the right subtree
    if (root.val < key) {
      root.right = delete(root.right, key); // WALK right
      return root;
    }

    // if root itself is the node to be deleted
    // if the root has no left child
    // return the right child directly
    if (root.left == null) {
      return root.right; // TAKE: the right child takes its place
    }
    // if the root has no right child
    // return the left child directly
    if (root.right == null) {
      return root.left; // TAKE: the left child takes its place
    }

    // if the root has both left and right children
    // find successor: smallest in the right subtree
    TreeNode successor = successor(root.right);
    // move the left subtree of the root
    // to the left of the successor
    successor.left = root.left; // RELINK: the successor has no left child, so the slot is free
    // the new root should be the right
    // child of the deleted node
    root = root.right;
    return root;
  }

  private static TreeNode successor(TreeNode node) {
    TreeNode curr = node;
    while (curr.left != null) {
      curr = curr.left; // WALK: leftmost is the smallest
    }
    return curr;
  }

  public static void main(String[] args) {
    TreeNode root = newNode(5);
    root.left = newNode(3);
    root.right = newNode(6);
    root.left.left = newNode(2);
    root.left.right = newNode(4);
    root.right.right = newNode(7);
    System.out.println(lot(delete(root, 3))); // [[5], [4, 6], [2, 7]]

    TreeNode root2 = newNode(5);
    root2.left = newNode(3);
    root2.right = newNode(6);
    root2.left.left = newNode(2);
    root2.left.right = newNode(4);
    root2.right.right = newNode(7);
    System.out.println(lot(delete(root2, 0))); // [[5], [3, 6], [2, 4, 7]]
  }
}
