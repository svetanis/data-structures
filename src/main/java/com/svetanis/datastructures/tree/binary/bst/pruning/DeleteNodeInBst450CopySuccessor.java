package com.svetanis.datastructures.tree.binary.bst.pruning;

import static com.svetanis.datastructures.tree.binary.bt.traversal.lot.LotLineByLineQueue.lot;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.height;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.insert;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 450. Delete Node in a BST

public final class DeleteNodeInBst450CopySuccessor {
  // Time complexity O(h) - balanced
  // Space complexity: O(h)

  // the sibling DeleteNodeInBst450 re-links instead of copying:
  // it hangs the left subtree off the successor and returns the
  // right child. same O(h), same answer, but the left subtree
  // sinks by the depth of the successor, so the tree it hands
  // back is taller than the one it was given

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
   * at n = 63 the same delete gives height 6 -> 10 for re-linking
   * and 6 -> 6 here. it compounds: every later delete pays it
   */

  // level order, so it builds a perfect bst of 15 nodes
  private static final List<Integer> KEYS = //
      ImmutableList.of(8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15);

  private static final String RESULT = "%s height %d";

  public static Node delete(Node root, int key) {
    if (root == null) {
      return root;
    }

    // if the key is smaller than root's value
    // delete in the left subtree
    if (root.data > key) {
      root.left = delete(root.left, key);
      return root;
    }
    // if the key is greater than root's value
    // delete in the right subtree
    if (root.data < key) {
      root.right = delete(root.right, key);
      return root;
    }

    // if root itself is the node to be deleted
    // if the root has no left child
    // return the right child directly
    if (root.left == null) {
      return root.right;
    }
    // if the root has no right child
    // return the left child directly
    if (root.right == null) {
      return root.left;
    }

    // if the root has both left and right children
    // overwrite the root with the successor's value: the
    // smallest in the right subtree, so it stays a bst
    root.data = min(root.right);
    // then delete that value from the right subtree. it is the
    // leftmost node there, so it has no left child and lands in
    // one of the two single-child cases above - the recursion
    // goes down one path and stops
    root.right = delete(root.right, root.data);
    return root;
  }

  private static int min(Node node) {
    Node curr = node;
    while (curr.left != null) {
      curr = curr.left;
    }
    return curr.data;
  }

  private static void print(Node root) {
    System.out.println(RESULT.formatted(lot(root), height(root)));
  }

  public static void main(String[] args) {
    Node root = newNode(5);
    root.left = newNode(3);
    root.right = newNode(6);
    root.left.left = newNode(2);
    root.left.right = newNode(4);
    root.right.right = newNode(7);
    print(delete(root, 3)); // [[5], [4, 6], [2, 7]] height 3

    Node root2 = newNode(5);
    root2.left = newNode(3);
    root2.right = newNode(6);
    root2.left.left = newNode(2);
    root2.left.right = newNode(4);
    root2.right.right = newNode(7);
    print(delete(root2, 0)); // [[5], [3, 6], [2, 4, 7]] height 3

    // deleting the root of a perfect tree is the worst case for
    // re-linking: the successor is as deep as the tree is tall.
    // the sibling returns height 6 here, this returns height 4
    Node root3 = insert(KEYS);
    print(delete(root3, 8)); // ... height 4
  }
}
