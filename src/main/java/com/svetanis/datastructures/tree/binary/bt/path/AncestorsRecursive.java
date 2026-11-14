package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import java.util.ArrayList;
import java.util.List;

// Ancestors of a Node in a Binary Tree -- not a LeetCode problem
//
// Input: the root of a binary tree whose values are all different, and a value key. Returns the
// values of the ancestors of the node holding key, from its parent up to the root; the root has
// none, and a key that is not in the tree gives an empty list.
//
// The one idea: ask each node "is key somewhere below me, or is it me?". A node that holds key
// answers yes without looking further. Any other node asks both children; if either says yes, this
// node is an ancestor, so it writes its value down and says yes to its own parent. The answers
// come back from the bottom, so the list is written parent first and the root last.
//
// Time: O(n) -- each node is asked once.
// Space: O(h) -- the recursion stack (h = height, n on a chain); the list is extra.
//
// Siblings: AncestorsIterative builds a map of value -> parent value and follows it up; it throws
// on a key that is absent.
public final class AncestorsRecursive {

  public static List<Integer> ancestors(TreeNode root, int key) {
    List<Integer> list = new ArrayList<>();
    ancestors(root, key, list);
    return list;
  }

  public static boolean ancestors(TreeNode root, int key, List<Integer> list) {
    if (isNull(root)) { // BASE: key is not below an empty spot
      return false;
    }
    if (root.val == key) { // FOUND: this node is key; its ancestors are written by its callers
      return true;
    }
    boolean isLeft = ancestors(root.left, key, list); // ASK: left subtree
    boolean isRight = ancestors(root.right, key, list); // ASK: right subtree
    if (isLeft || isRight) { // RECORD: key is below, so this node is an ancestor
      list.add(root.val);
      return true;
    }
    return false;
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    root.left.left.left = newNode(8);
    root.left.right.right = newNode(9);
    root.right.right.left = newNode(10);
    // [], [1], [1], [2, 1], [2, 1], [3, 1], [3, 1], [4, 2, 1], [5, 2, 1], [7, 3, 1]
    for (int key = 1; key <= 10; key++) {
      System.out.println(ancestors(root, key));
    }
  }
}
