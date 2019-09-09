package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree, a complete path is defined as a path from root to a leaf. 
// The sum of all nodes on that path is defined as the sum of that path. 
// Given a number K, you have to remove (prune the tree) all nodes 
// which don’t lie in any path with sum>=k.

public final class PruneTree {

  // remove all nodes which don't
  // lie in any path with sum >= k

  public static Node prune(Node root, int k) {

    if (isNull(root)) {
      return null;
    }

    // recursively prune left and right subtrees
    root.left = prune(root.left, k - root.data);
    root.right = prune(root.right, k - root.data);

    if (isLeaf(root) && k > root.data) {
      root = null;
    }
    return root;
  }

  public static void main(String[] args) {
    Node root = newNode(6);
    root.left = newNode(3);
    root.right = newNode(8);
    root.right.left = newNode(4);
    root.right.right = newNode(2);
    root.right.left.left = newNode(1);
    root.right.left.right = newNode(7);
    root.right.right.right = newNode(3);

    System.out.println("tree before truncation");
    inOrder(root);
    System.out.println();

    System.out.println("tree after truncation");
    Node pruned = prune(root, 20);
    inOrder(pruned);
    System.out.println();
  }
}
