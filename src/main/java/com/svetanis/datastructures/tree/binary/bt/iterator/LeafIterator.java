package com.svetanis.datastructures.tree.binary.bt.iterator;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// An iterator over the leaves of a binary tree, left to right
//
// Input: the root of a binary tree; it may be empty. next() returns the next leaf, a node with no
// children, in left-to-right order; hasNext() says whether there is one. A call to next() with no
// leaf left throws IllegalArgumentException.
//
// The one idea: the stack holds the subtrees not looked at yet, the one that comes first in
// left-to-right order on top. hasNext() pops until it pops a leaf; a node that is not a leaf is
// replaced by its two children, the right pushed first so that the left comes off first. The leaf
// found is kept in a field until next() hands it out, so asking hasNext() twice does not skip a
// leaf.
//
// Time: hasNext() and next() are O(1) amortized -- each node is pushed once and popped once across
// the whole iteration, so the whole walk is O(n); a single call can take O(n) when it has to
// pass a large subtree with no leaf before the next one.
// Space: O(h) -- the stack holds at most one pending right sibling per level of the tree.
//
// Siblings: LeafSimilarTrees872.equal uses two of these to compare the leaves of two trees; BtIterator walks
// every node in inorder instead of only the leaves.

public final class LeafIterator {

  private TreeNode leaf;
  private Deque<TreeNode> stack;

  public LeafIterator(TreeNode root) {
    this.stack = new ArrayDeque<>();
    if (isNotNull(root)) { // an ArrayDeque refuses null, and an empty tree has no leaves
      stack.push(root);
    }
  }

  public boolean hasNext() {
    if (isNotNull(leaf)) {
      return true; // CACHED: a leaf is already waiting
    }
    while (!stack.isEmpty()) {
      TreeNode node = stack.pop(); // TAKE: the subtree that comes first, left to right
      if (isLeaf(node)) {
        leaf = node; // FOUND: keep it for next()
        return true;
      }
      if (isNotNull(node.right)) {
        stack.push(node.right); // PUSH: right first, so the left comes off first
      }
      if (isNotNull(node.left)) {
        stack.push(node.left);
      }
    }
    return false; // RETURN: no leaf left
  }

  public TreeNode next() {
    if (!hasNext()) {
      throw new IllegalArgumentException("stack underflow");
    }
    TreeNode node = leaf;
    leaf = null; // HAND OVER: the next hasNext() looks for a new leaf
    return node;
  }

  public static void main(String[] args) {
    TreeNode root = newNode(10);
    root.left = newNode(1);
    root.right = newNode(11);
    root.left.right = newNode(6);
    root.right.right = newNode(12);
    LeafIterator iter = new LeafIterator(root);
    while (iter.hasNext()) {
      System.out.print(iter.next() + " ");
    }
    System.out.println(); // 6 12
  }
}
