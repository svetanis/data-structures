package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.bt.compare.IdenticalBinaryTreesRecursive.identical;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 572. Subtree of Another Tree
//
// Input: root1 -- the main tree; root2 -- the tree to look for. Returns true when root2 matches some
// node of root1 together with everything below that node, shape and values alike. On LeetCode:
// 1 <= nodes in root1 <= 2000, 1 <= nodes in root2 <= 1000, -10^4 <= Node.val <= 10^4. An empty
// root2 returns true here (LeetCode never passes one).
//
// The one idea: S sits inside T exactly when S is the same tree as T itself, or as the tree under
// T's left child, or as the tree under T's right child -- and "the same tree" is one question the
// repo already answers (IdenticalBinaryTreesRecursive). So try the match at the current node, and
// if it fails, ask the same question of each child.
//
// Time: O(n * m) -- at most one match attempt per node of root1, each costing at most m, the size
// of root2, since an attempt stops at the first difference.
// Space: O(h) -- the recursion stack; h is the height of root1, n for a chain.
//
// Siblings: SubTreeOfAnotherTree -- the same method with the match written out in the file (and
// false for two empty trees, where this one returns true); SubTreeInOrderPreOrder -- compares
// strings of the two trees' node characters instead of comparing nodes.

public final class SubTreeNodeComparison {

  public static boolean isSubTree(TreeNode root1, TreeNode root2) {
    // base case
    if (root2 == null) {
      return true; // BASE: an empty tree is inside any tree
    }

    if (root1 == null) {
      return false; // BASE: a non-empty tree cannot be inside an empty one
    }

    // check the tree with
    // root as current node
    if (identical(root1, root2)) {
      return true; // MATCH: root2 is exactly the tree at this node
    }

    // if the tree with root as current node doesn't match
    // then try left and right subtrees one by one
    boolean left = isSubTree(root1.left, root2); // WALK: look under the left child
    boolean right = isSubTree(root1.right, root2); // WALK: look under the right child
    return left || right;
  }

  public static void main(String[] args) {
    TreeNode root1 = newNode(26);
    root1.right = newNode(3);
    root1.right.right = newNode(3);
    root1.left = newNode(10);
    root1.left.left = newNode(4);
    root1.left.left.right = newNode(30);
    root1.left.right = newNode(6);

    TreeNode root2 = newNode(10);
    root2.left = newNode(4);
    root2.right = newNode(6);
    root2.left.right = newNode(30);

    System.out.println(isSubTree(root1, root2)); // true
  }
}
