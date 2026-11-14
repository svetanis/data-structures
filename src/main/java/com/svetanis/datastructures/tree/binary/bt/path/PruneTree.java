package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Prune Nodes Whose Root-to-Leaf Paths All Sum Below k -- not a LeetCode problem; the same
// problem is 1080. Insufficient Nodes in Root to Leaf Paths (there k is called limit)
//
// Input: the root of a binary tree and a number k. A complete path runs from the root to a leaf.
// Removes, in place, every node that lies on no complete path with sum >= k, and returns the
// root of what is left (null if nothing is). Values may be negative. Assumes k minus a path sum
// does not overflow an int (LeetCode 1080: 1 <= n <= 5000, -10^5 <= val <= 10^5).
//
// The one idea: carry down what the rest of the path must still add up to (k minus the values
// above). A leaf is kept exactly when its own value reaches that target. An inner node is kept
// exactly when at least one child survives after pruning, because then some complete path through
// it is good. So prune both subtrees first, then decide: a node that was a leaf is removed if its
// value is below the target; a node that was NOT a leaf is removed if both its children were
// removed, whatever its own value. (Judging an inner node that lost both children by its own
// value alone is wrong: it is no longer a leaf of the original tree, and no path ends at it.)
//
// Time: O(n) -- each node is visited once.
// Space: O(h) -- the recursion stack (h = height, n on a chain).
//
// Siblings: RemoveShortPathNodes prunes by the number of nodes on a path instead of the sum.
public final class PruneTree {

  public static TreeNode prune(TreeNode root, int k) {
    if (isNull(root)) { // BASE: nothing to prune
      return null;
    }
    boolean wasLeaf = isLeaf(root); // LEAF: judged before the children are pruned
    root.left = prune(root.left, k - root.val); // WALK: left subtree, smaller target
    root.right = prune(root.right, k - root.val); // WALK: right subtree, smaller target
    boolean remove = wasLeaf ? k > root.val : isLeaf(root); // REMOVE: too small, or lost every path
    if (remove) {
      root = null;
    }
    return root;
  }

  public static void main(String[] args) {
    TreeNode root = newNode(6);
    root.left = newNode(3);
    root.right = newNode(8);
    root.right.left = newNode(4);
    root.right.right = newNode(2);
    root.right.left.left = newNode(1);
    root.right.left.right = newNode(7);
    root.right.right.right = newNode(3);
    System.out.println("tree before truncation");
    inOrder(root); // prints 3 6 1 4 7 8 2 3
    System.out.println();
    System.out.println("tree after truncation");
    TreeNode pruned = prune(root, 20);
    inOrder(pruned); // prints 6 4 7 8
    System.out.println();

    // a negative value: 10 -> -20 sums to -10 < 5, so the root goes too
    TreeNode root2 = newNode(10);
    root2.left = newNode(-20);
    System.out.println(prune(root2, 5)); // null
  }
}
