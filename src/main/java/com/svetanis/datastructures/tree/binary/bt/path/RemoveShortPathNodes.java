package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Remove Nodes on Short Root-to-Leaf Paths -- not a LeetCode problem
//
// Input: the root of a binary tree and a number k. The length of a root-to-leaf path is the
// number of nodes on it. Removes, in place, every node that lies only on root-to-leaf paths
// shorter than k (a node on at least one path of length k or more stays), and returns the root
// of what is left (null if nothing is). The tree has no stated size limit.
//
// The one idea: carry down the depth of the current node (the root is 1). A leaf at depth len
// ends a path of exactly len nodes, so it is removed when len < k. Prune both subtrees first,
// then look at the node: if both children were removed, the node is now a leaf, and every path
// through it was shorter than k (its own children were at depth len + 1, and they were shorter
// than k), so len < k holds and it goes too. If one child lives, the node stays.
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion stack (h = height, n on a chain).
//
// Siblings: PruneTree prunes by the sum of a path instead of its length.
public final class RemoveShortPathNodes {

  public static TreeNode prune(TreeNode root, int k) {
    return prune(root, k, 1);
  }

  private static TreeNode prune(TreeNode root, int k, int len) {
    if (isNull(root)) { // BASE: nothing to prune
      return null;
    }
    root.left = prune(root.left, k, len + 1); // WALK: left subtree, one deeper
    root.right = prune(root.right, k, len + 1); // WALK: right subtree, one deeper
    if (isLeaf(root) && len < k) { // REMOVE: a leaf, or a node that lost both children, too short
      root = null;
    }
    return root;
  }

  public static void main(String[] args) {
    // there are 3 paths:
    // 1. 1->2->4->7 path length = 4
    // 2. 1->2->5 path length =3
    // 3. 1->3->6->8 path length = 4
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);
    root.left.left.left = newNode(7);
    root.right.right.left = newNode(8);
    System.out.println("tree before truncation");
    inOrder(root); // prints 7 4 2 5 1 3 8 6
    System.out.println();
    System.out.println("tree after truncation");
    TreeNode pruned = prune(root, 4);
    inOrder(pruned); // prints 7 4 2 1 3 8 6
    System.out.println();
  }
}
