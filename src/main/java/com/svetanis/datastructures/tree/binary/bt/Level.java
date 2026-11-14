package com.svetanis.datastructures.tree.binary.bt;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Level of a node, and the nodes of one level
//
// Input: root of a binary tree (not a LeetCode problem), and either a value or a node to find, or
// a level number to print. The root is on level 1. level(...) returns the level of the value, or
// 0 when it is not in the tree; the values are assumed distinct (with a repeated value the answer
// is the level of the first one met going down the left side first, not the shallowest).
// givenLevel(...) prints the values of one level, left to right or right to left.
//
// The one idea: a node's level is one more than its parent's, so carry the level down the
// recursion: the root is level 1, each child is the parent's level + 1. level() stops at the first
// node holding the value. givenLevel() carries the level down by counting it backwards: asking for
// level k of a tree is asking for level k - 1 of each child, until k is 1 and the node itself is
// the answer. The direction flag only decides which child is asked first.
//
// Time: O(n) -- each call visits at most every node once.
// Space: O(h) -- the recursion stack, h = the height of the tree (up to n for a chain).

public final class Level {

  public static int level(TreeNode root, TreeNode node) {
    return level(root, node.val);
  }

  public static int level(TreeNode root, int data) {
    return level(root, data, 1);
  }

  public static int level(TreeNode root, int data, int level) {

    if (isNull(root)) { // BASE: fell off the tree, not found on this path
      return 0;
    }

    if (root.val == data) { // FOUND: this node's level
      return level;
    }
    // return level if node is present in left subtree
    int downLevel = level(root.left, data, level + 1); // WALK: left first
    if (downLevel != 0) {
      return downLevel;
    }
    // else search in right subtree
    return level(root.right, data, level + 1); // WALK: then right
  }

  public static void givenLevel(TreeNode node, int level) {
    givenLevel(node, level, true);
  }

  public static void givenLevel(TreeNode node, int level, boolean ltr) {
    if (isNull(node)) {
      return;
    }
    if (level == 1) { // PRINT: the node is on the wanted level
      System.out.print(node + " ");
    } else if (level > 1) { // WALK: one level closer, children in the wanted order
      if (ltr) {
        givenLevel(node.left, level - 1, ltr);
        givenLevel(node.right, level - 1, ltr);
      } else {
        givenLevel(node.right, level - 1, ltr);
        givenLevel(node.left, level - 1, ltr);
      }
    }
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    System.out.println(level(root, 4)); // 3
  }
}