package com.svetanis.datastructures.tree.binary.bst.pruning;

import static com.svetanis.datastructures.tree.binary.bt.traversal.lot.LotLineByLineQueue.lot;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.height;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.insert;

import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 450. Delete Node in a BST
//
// Input: root -- the root of a binary search tree with distinct values; key -- the value to
// delete. Returns the root of the tree after the node holding key is removed, or the tree
// unchanged when no node holds key. On LeetCode: 0 <= number of nodes <= 10^4,
// -10^5 <= Node.val <= 10^5, all values distinct.
//
// The one idea: one call looks at one node, which holds one value, and returns the root of that
// node's subtree after the delete. Smaller key: the left child becomes what the call on it
// returns; larger: the same on the right. When the node holds key: no left child -- the right
// child takes its place; no right child -- the left child takes its place; both children -- copy
// the smallest value of the right subtree (the successor) into this node, which keeps the order,
// then delete that value from the right subtree. The successor is the leftmost node there, so it
// has no left child and that second delete ends in one of the two one-child cases. No subtree
// moves: one value changes and one node goes.
//
// Time: O(h) -- one path down to the node, then one path down to the successor; h is the tree's
// height, up to n on a chain.
// Space: O(h) -- the call stack.
//
// Depth: the recursion is as deep as the tree is tall; the default stack holds LeetCode's largest
// chain (10^4 nodes) and overflows near 12,000.
//
// Siblings: DeleteNodeInBst450 re-links instead of copying: it hangs the left subtree off the
// successor and returns the right child. Same O(h), same answer, but the left subtree sinks by
// the depth of the successor, so the tree it hands back can be taller than the one it was given.

/*-
 * delete(8) from a perfect bst - the worst case for re-linking,
 * because the successor sits as deep as the tree is tall
 *
 *                 8                     before, height 4
 *         +-------+-------+
 *         4              12
 *     +---+---+       +---+---+
 *     2       6      10      14
 *   +-+-+   +-+-+   +-+-+   +-+-+
 *   1   3   5   7   9  11  13  15
 *
 *
 *             12                        re-link, height 6
 *       +-----+-----+                   successor.left = root.left
 *      10          14                   hangs all 7 left nodes off
 *    +--+--+      +-+-+                 node 9, already at depth 3.
 *    9    11     13  15                 everything under it drops
 *    |                                  by 2, and the tree is now
 *    4                                  lopsided as well as taller
 *  +-+-+
 *  2   6
 * +-+ +-+
 * 1 3 5 7
 *
 *
 *                 9                     copy value, height 4
 *         +-------+-------+             one value changed, one leaf
 *         4              12             gone, nothing moved
 *     +---+---+       +---+---+
 *     2       6      10      14
 *   +-+-+   +-+-+       +   +-+-+
 *   1   3   5   7      11  13  15
 *
 * a perfect tree of 63 nodes gives the same delete a height of 10
 * for re-linking and 6 here. it compounds: every later delete pays it
 */

public final class DeleteNodeInBst450CopySuccessor {

  // level order, so it builds a perfect bst of 15 nodes
  private static final List<Integer> KEYS = //
      List.of(8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15);

  private static final String RESULT = "%s height %d";

  public static TreeNode delete(TreeNode root, int key) {
    if (root == null) {
      return root; // BASE: key is not in the tree
    }

    // if the key is smaller than root's value
    // delete in the left subtree
    if (root.val > key) {
      root.left = delete(root.left, key); // WALK left
      return root;
    }
    // if the key is greater than root's value
    // delete in the right subtree
    if (root.val < key) {
      root.right = delete(root.right, key); // WALK right
      return root;
    }

    // if root itself is the node to be deleted
    // if the root has no left child
    // return the right child directly
    if (root.left == null) {
      return root.right; // TAKE: the right child takes its place
    }
    // if the root has no right child
    // return the left child directly
    if (root.right == null) {
      return root.left; // TAKE: the left child takes its place
    }

    // if the root has both left and right children
    // overwrite the root with the successor's value: the
    // smallest in the right subtree, so it stays a bst
    root.val = min(root.right); // COPY: the successor's value moves up
    // then delete that value from the right subtree. it is the
    // leftmost node there, so it has no left child and lands in
    // one of the two single-child cases above - the recursion
    // goes down one path and stops
    root.right = delete(root.right, root.val); // REMOVE: the successor's old node
    return root;
  }

  private static int min(TreeNode node) {
    TreeNode curr = node;
    while (curr.left != null) {
      curr = curr.left; // WALK: leftmost is the smallest
    }
    return curr.val;
  }

  private static void print(TreeNode root) {
    System.out.println(RESULT.formatted(lot(root), height(root)));
  }

  public static void main(String[] args) {
    TreeNode root = newNode(5);
    root.left = newNode(3);
    root.right = newNode(6);
    root.left.left = newNode(2);
    root.left.right = newNode(4);
    root.right.right = newNode(7);
    print(delete(root, 3)); // [[5], [4, 6], [2, 7]] height 3

    TreeNode root2 = newNode(5);
    root2.left = newNode(3);
    root2.right = newNode(6);
    root2.left.left = newNode(2);
    root2.left.right = newNode(4);
    root2.right.right = newNode(7);
    print(delete(root2, 0)); // [[5], [3, 6], [2, 4, 7]] height 3

    // deleting the root of a perfect tree is the worst case for
    // re-linking: the successor is as deep as the tree is tall.
    // DeleteNodeInBst450 returns height 6 here, this returns height 4
    TreeNode root3 = insert(KEYS);
    print(delete(root3, 8)); // [[9], [4, 12], [2, 6, 10, 14], [1, 3, 5, 7, 11, 13, 15]] height 4
  }
}
