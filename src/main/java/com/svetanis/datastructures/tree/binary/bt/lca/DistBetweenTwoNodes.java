package com.svetanis.datastructures.tree.binary.bt.lca;

import static com.svetanis.datastructures.tree.binary.bt.Level.level;
import static com.svetanis.datastructures.tree.binary.bt.lca.LcaBtNoParentBottomUp.lca;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1740. Find Distance in a Binary Tree
//
// Input: root -- the root of a binary tree; p, q -- two values in that tree. Returns the number of
// edges on the path between the node holding p and the node holding q; 0 when p == q. On LeetCode:
// 1 <= nodes <= 10^4, 0 <= Node.val <= 10^9, all values distinct, p and q both in the tree.
//
// The one idea: the path from p to q goes up from p to their lowest common ancestor and down to q,
// so its length is (depth of p - depth of the LCA) + (depth of q - depth of the LCA). Measured from
// the root, that is distP + distQ - 2 * distLCA: the part of both root paths above the LCA is
// counted twice, once in each, and is taken out. Each depth is found by a search from the root.
//
// Time: O(n) -- the LCA and the three depth lookups are each one pass over the tree.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: DistBetweenTwoNodesEfficient finds the LCA first and measures down from it, so the two
// depth lookups search only the LCA's subtree.

public final class DistBetweenTwoNodes {

  public static int dist(TreeNode root, int p, int q) {

    // dist from root to node p
    int distP = level(root, p); // DEPTH: levels from the root, the root being level 1

    // dist from root to node q
    int distQ = level(root, q);

    TreeNode lca = lca(root, new TreeNode(p), new TreeNode(q)); // MEET: the LCA search compares values
    // dist from root to node lca of p and q
    int distLCA = level(root, lca.val);

    // dist between p and q
    return distP + distQ - 2 * distLCA; // RETURN: the shared top part counted twice, taken out
  }

  public static void main(String[] args) {
    TreeNode root = new TreeNode(3); // [3,5,1,6,2,0,8,null,null,7,4], the LeetCode examples' tree
    root.left = new TreeNode(5);
    root.right = new TreeNode(1);
    root.left.left = new TreeNode(6);
    root.left.right = new TreeNode(2);
    root.right.left = new TreeNode(0);
    root.right.right = new TreeNode(8);
    root.left.right.left = new TreeNode(7);
    root.left.right.right = new TreeNode(4);

    System.out.println(dist(root, 5, 0)); // 3: 5-3-1-0
    System.out.println(dist(root, 5, 7)); // 2: 5-2-7
    System.out.println(dist(root, 5, 5)); // 0: a node to itself
    System.out.println(dist(root, 6, 4)); // 3: 6-5-2-4
    System.out.println(dist(root, 7, 8)); // 5: 7-2-5-3-1-8
  }
}
