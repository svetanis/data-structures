package com.svetanis.datastructures.tree.binary.bt.leaves;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.bt.iterator.LeafIterator;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 872. Leaf-Similar Trees
//
// Input: the roots of two binary trees; either may be empty. The leaf value sequence of a tree is
// the values of its leaves -- nodes with no children -- read from left to right. Returns true
// when the two trees have the same leaf value sequence -- the same number of leaves, and the same
// value at each place in the sequence. On LeetCode: 1 <= nodes <= 200 in each tree,
// 0 <= Node.val <= 200.
//
// The one idea of leafSimilar: build the leaf value sequence of each tree, then compare the two
// lists. leaves(root) returns the sequence of a subtree: the leaves of the left subtree followed
// by the leaves of the right subtree. A node that finds both lists empty has no children, so it is
// a leaf and its own value is the sequence; that is how a leaf is recognised without asking about
// its children.
//
// The one idea of equal: do not build the two leaf lists. A LeafIterator hands out the leaves of
// one tree one at a time, left to right, and the two iterators are stepped together: the first
// pair of leaves that differ answers false, and if one tree runs out of leaves before the other
// the counts differ and the answer is false too.
//
// Time: leafSimilar is O(n * h) in the worst case -- the lists of the right subtrees are copied
// into the lists of the left subtrees, so a leaf is copied once for every ancestor that reaches it
// from the right; on a tree with a leaf at every level on the left and one long chain on the right
// that is a copy per leaf per level, about L^2 / 2 for L leaves. A balanced tree is O(n log n);
// n = n1 + n2. equal is O(n + m) -- each node of each tree is visited at most once; the walk stops
// early at the first leaf that differs.
// Space: leafSimilar is O(n) -- the two lists, plus O(h) recursion stack. equal is O(h1 + h2) --
// each iterator keeps a stack of pending nodes, one per level of its tree.
//
// Siblings: LeafIterator is the iterator that equal steps; BtIterator is the other iterator in
// the iterator package.

public final class LeafSimilarTrees872 {

  public static boolean leafSimilar(TreeNode root1, TreeNode root2) {
    if (root1 == null && root2 == null) {
      return true; // BASE: both empty
    }
    if (root1 == null || root2 == null) {
      return false; // BASE: one empty, one not
    }
    List<Integer> list1 = leaves(root1); // BUILD: leaf sequence of the first tree
    List<Integer> list2 = leaves(root2); // BUILD: leaf sequence of the second tree
    return list1.equals(list2); // RETURN: same values in the same order
  }

  private static List<Integer> leaves(TreeNode root) {
    if (root == null) {
      return new ArrayList<>(); // BASE: no leaves
    }
    List<Integer> list = leaves(root.left); // WALK: left leaves first
    list.addAll(leaves(root.right)); // WALK: then the right leaves
    if (list.isEmpty()) {
      list.add(root.val); // LEAF: no leaves below, so this node is one
    }
    return list;
  }

  // not called: leaves(TreeNode) above builds the list by returning it instead of filling a parameter
  private static void leaves(TreeNode root, List<Integer> list) {
    if (root == null) {
      return;
    }
    if (root.left == null && root.right == null) {
      list.add(root.val);
    }
    leaves(root.left, list);
    leaves(root.right, list);
  }

  public static boolean equal(TreeNode root1, TreeNode root2) {
    LeafIterator iter1 = new LeafIterator(root1);
    LeafIterator iter2 = new LeafIterator(root2);
    while (iter1.hasNext() && iter2.hasNext()) {
      TreeNode leaf1 = iter1.next(); // TAKE: the next leaf of the first tree
      TreeNode leaf2 = iter2.next(); // TAKE: the next leaf of the second tree
      if (leaf1.val != leaf2.val) {
        return false; // COMPARE: the leaves at this place differ
      }
    }
    if (iter1.hasNext() || iter2.hasNext()) {
      return false; // COUNT: one tree has leaves left over
    }
    return true; // RETURN: same leaves, same order
  }

  public static void main(String[] args) {
    TreeNode root1 = newNode(3);
    root1.left = newNode(5);
    root1.right = newNode(1);
    root1.left.left = newNode(6);
    root1.left.right = newNode(2);
    root1.right.left = newNode(9);
    root1.right.right = newNode(8);
    root1.left.right.left = newNode(7);
    root1.left.right.right = newNode(4);
    TreeNode root2 = newNode(3);
    root2.left = newNode(5);
    root2.right = newNode(1);
    root2.left.left = newNode(6);
    root2.left.right = newNode(7);
    root2.right.left = newNode(4);
    root2.right.right = newNode(2);
    root2.right.right.left = newNode(9);
    root2.right.right.right = newNode(8);
    System.out.println(leafSimilar(root1, root2)); // true

    TreeNode root3 = newNode(1);
    root3.left = newNode(2);
    root3.right = newNode(3);
    TreeNode root4 = newNode(1);
    root4.left = newNode(3);
    root4.right = newNode(2);
    System.out.println(leafSimilar(root3, root4)); // false

    TreeNode root5 = newNode(1);
    root5.left = newNode(2);
    root5.right = newNode(3);
    root5.left.left = newNode(4);
    root5.left.right = newNode(5);
    root5.right.right = newNode(6);

    TreeNode root6 = newNode(1);
    root6.left = newNode(2);
    root6.left.left = newNode(4);
    root6.left.right = newNode(3);
    root6.left.right.left = newNode(5);
    root6.left.right.right = newNode(6);

    System.out.println(equal(root5, root6)); // true
  }
}
