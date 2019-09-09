package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a Binary Tree and a number k, remove all nodes 
// that lie only on root to leaf path(s) of length smaller than k. 
// If a node X lies on multiple root-to-leaf paths and if any of 
// the paths has path length >= k, then X is not deleted from Binary Tree. 
// In other words a node is deleted if all paths going through it 
// have lengths smaller than k.

public final class RemoveShortPathNodes {

  public static Node prune(Node root, int k) {
    return prune(root, k, 1);
  }

  private static Node prune(Node root, int k, int len) {

    if (isNull(root)) {
      return null;
    }

    root.left = prune(root.left, k, len + 1);
    root.right = prune(root.right, k, len + 1);

    if (isLeaf(root) && len < k) {
      root = null;
    }
    return root;
  }

  public static void main(String[] args) {
    // there are 3 paths:
    // 1. 1->2->4->7 path length = 4
    // 2. 1->2->5 path length =3
    // 3. 1->3->6->8 path length = 4

    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);
    root.left.left.left = newNode(7);
    root.right.right.left = newNode(8);

    System.out.println("tree before truncation");
    inOrder(root);
    System.out.println();

    System.out.println("tree after truncation");
    Node pruned = prune(root, 4);
    inOrder(pruned);
    System.out.println();
  }
}
