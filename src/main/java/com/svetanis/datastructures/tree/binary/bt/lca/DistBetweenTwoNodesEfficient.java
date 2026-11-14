package com.svetanis.datastructures.tree.binary.bt.lca;

import static com.svetanis.datastructures.tree.binary.bt.Level.level;
import static com.svetanis.datastructures.tree.binary.bt.lca.LcaBtNoParentBottomUp.lca;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 1740. Find Distance in a Binary Tree
//
// Input: root -- the root of a binary tree; p, q -- two values in that tree. Returns the number of
// edges on the path between the node holding p and the node holding q; 0 when p == q. On LeetCode:
// 1 <= nodes <= 10^4, 0 <= Node.val <= 10^9, all values distinct, p and q both in the tree.
//
// The one idea: the path from p to q goes up from p to their lowest common ancestor and down to q,
// so its length is the number of edges from the LCA down to p plus the number from the LCA down to
// q. Find the LCA first, then search for each node only inside the LCA's subtree, starting the count
// at 0 for the LCA itself so that the count is edges.
//
// Time: O(n) -- the LCA is one pass; the two searches below it together read at most the LCA's
// subtree again.
// Space: O(h) -- the recursion stack. h is lg n only on a balanced tree; a skewed one makes it n.
//
// Siblings: DistBetweenTwoNodes measures all three depths from the root and subtracts.

public final class DistBetweenTwoNodesEfficient {

  public static int dist(TreeNode root, int p, int q) {
    TreeNode lca = lca(root, newNode(p), newNode(q)); // MEET: the LCA search compares values
    // dist from lca to node p
    int distP = level(lca, p, 0); // DOWN: edges from the LCA to p; 0 when the LCA is p
    // dist from lca to node q
    int distQ = level(lca, q, 0); // DOWN: edges from the LCA to q
    // dist between p and q
    return distP + distQ; // RETURN: up from p to the LCA, then down to q
  }

  public static void main(String[] args) {
    TreeNode root = newNode(3); // [3,5,1,6,2,0,8,null,null,7,4], the LeetCode examples' tree
    root.left = newNode(5);
    root.right = newNode(1);
    root.left.left = newNode(6);
    root.left.right = newNode(2);
    root.right.left = newNode(0);
    root.right.right = newNode(8);
    root.left.right.left = newNode(7);
    root.left.right.right = newNode(4);

    System.out.println(dist(root, 5, 0)); // 3: 5-3-1-0
    System.out.println(dist(root, 5, 7)); // 2: 5-2-7
    System.out.println(dist(root, 5, 5)); // 0: a node to itself
    System.out.println(dist(root, 6, 4)); // 3: 6-5-2-4
    System.out.println(dist(root, 7, 8)); // 5: 7-2-5-3-1-8
  }
}
