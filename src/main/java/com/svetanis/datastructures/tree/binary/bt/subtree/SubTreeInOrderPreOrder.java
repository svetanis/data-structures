package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Is tree S a subtree of tree T? (LeetCode 572, Subtree of Another Tree, asks the same question
// with integer values; SubTreeOfAnotherTree holds that version.)
//
// Input: the roots of two binary trees T and S; either may be empty. Each node value is read as one
// character, (char) node.val, so values must fit in 0..65535, and '$' is kept for "no child" so no
// node may hold it. Returns true when S matches some node of T together with everything below that
// node, shape and values alike; an empty S always matches.
//
// The one idea: write each tree down as a string of characters, then ask whether S's string occurs
// inside T's. Walking a tree and writing each node's character, and a '$' for every missing child,
// gives a string in which every node's whole subtree is one unbroken stretch. The '$' marks are what
// make the stretch exact: without them, a leaf "a" would be found inside a node "a" that has
// children, since the characters of its children come after it. The strings are built twice, once
// left-node-right (in-order) and once node-left-right (pre-order); the pre-order string alone
// decides the answer; the in-order test is a condition any match must also meet, so it can only
// return false early.
//
// Time: O(n + m) to build the four strings, then O(n * m) in the worst case for each String.contains
// -- n and m are the node counts of T and S.
// Space: O(n + m) -- the four strings; the recursion is as deep as the taller tree.

public final class SubTreeInOrderPreOrder {

  private static final char NO_CHILD = '$';

  public static boolean isSubTree(TreeNode rootT, TreeNode rootS) {
    // base case
    if (isNull(rootS)) {
      return true; // BASE: an empty S matches anywhere
    }

    if (isNull(rootT)) {
      return false; // BASE: a non-empty S cannot sit inside an empty T
    }

    // store inorder traversal of T and S
    StringBuilder inT = new StringBuilder();
    StringBuilder inS = new StringBuilder();

    inorder(rootT, inT); // WALK: write T left-node-right
    inorder(rootS, inS); // WALK: write S left-node-right

    if (!inT.toString().contains(inS)) {
      return false; // FILTER: S's in-order string is not inside T's
    }

    // store preorder traversal of T and S
    StringBuilder preT = new StringBuilder();
    StringBuilder preS = new StringBuilder();

    preorder(rootT, preT); // WALK: write T node-left-right
    preorder(rootS, preS); // WALK: write S node-left-right

    return preT.toString().contains(preS); // RETURN: S's pre-order string is inside T's
  }

  private static void inorder(TreeNode node, StringBuilder sb) {
    if (isNull(node)) {
      sb.append(NO_CHILD); // MARK: a missing child
      return;
    }
    inorder(node.left, sb);
    sb.append((char) node.val); // WRITE: the node, between its two sides
    inorder(node.right, sb);
  }

  private static void preorder(TreeNode node, StringBuilder sb) {
    if (isNull(node)) {
      sb.append(NO_CHILD); // MARK: a missing child
      return;
    }
    sb.append((char) node.val); // WRITE: the node, before its two sides
    preorder(node.left, sb);
    preorder(node.right, sb);
  }

  public static void main(String[] args) {
    TreeNode tree1 = newNode('z');
    tree1.left = newNode('x');
    tree1.right = newNode('e');
    tree1.left.left = newNode('a');
    tree1.left.right = newNode('b');
    tree1.right.right = newNode('k');
    tree1.left.left.right = newNode('c');

    TreeNode tree2 = newNode('x');
    tree2.left = newNode('a');
    tree2.right = newNode('b');
    tree2.left.right = newNode('c');

    System.out.println(isSubTree(tree1, tree2)); // true

    TreeNode root1 = newNode('a');
    root1.left = newNode('b');
    root1.right = newNode('d');
    root1.left.left = newNode('c');
    root1.right.right = newNode('e');

    TreeNode root2 = newNode('a');
    root2.left = newNode('b');
    root2.right = newNode('d');
    root2.left.left = newNode('c');

    System.out.println(isSubTree(root2, root1)); // false
  }
}
