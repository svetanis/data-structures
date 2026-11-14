package com.svetanis.datastructures.tree.binary.bt.convert;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Convert a binary tree to a binary search tree, keeping its shape. (No LeetCode problem; it is a
// classic exercise.)
//
// Input: the root of a binary tree of ints; values may repeat. The tree is changed in place.
// Returns: nothing -- afterwards every node stands where it stood, only the values have moved, and
// the in-order traversal is sorted (non-decreasing).
//
// The one idea: a search tree is a tree whose in-order walk (left subtree, node, right subtree)
// visits the values from smallest to largest. So keep every node where it is, collect all the
// values, sort them, and walk the same tree in-order, giving each node the next value from the
// sorted list. The shape is untouched and the in-order walk is now sorted, which is exactly the
// search-tree rule. The values go in a list, not a set, so a value that occurs twice is written
// back twice and the walk never runs out of values.
//
// Time: O(n log n) -- collect n values, sort them, write them back.
// Space: O(n) -- the list of values, plus the recursion (as deep as the tree is tall).

public final class ConvertBtToBst {

  public static void btToBst(TreeNode root) {
    List<Integer> values = new ArrayList<>();
    collect(root, values); // COLLECT: every value, once per node
    Collections.sort(values); // SORT: smallest first
    toBst(root, values.iterator());
  }

  private static void collect(TreeNode root, List<Integer> values) {
    if (isNull(root)) { // BASE: empty subtree
      return;
    }
    collect(root.left, values);
    values.add(root.val); // RECORD: this node's value
    collect(root.right, values);
  }

  private static void toBst(TreeNode root, Iterator<Integer> iter) {
    if (isNull(root)) { // BASE: empty subtree
      return;
    }
    toBst(root.left, iter);
    root.val = iter.next(); // WRITE: the next smallest value, in in-order position
    toBst(root.right, iter);
  }

  public static void main(String[] args) {
    TreeNode root = newNode(8);
    root.left = newNode(3);
    root.right = newNode(5);
    root.left.left = newNode(10);
    root.left.right = newNode(2);
    root.right.left = newNode(4);
    root.right.right = newNode(6);

    inOrder(root); // 10 3 2 8 4 5 6
    System.out.println();
    btToBst(root);
    inOrder(root); // 2 3 4 5 6 8 10
    System.out.println();

    TreeNode dup = newNode(2);
    dup.left = newNode(2);
    dup.right = newNode(1);
    inOrder(dup); // 2 2 1
    System.out.println();
    btToBst(dup);
    inOrder(dup); // 1 2 2
    System.out.println();
  }
}
