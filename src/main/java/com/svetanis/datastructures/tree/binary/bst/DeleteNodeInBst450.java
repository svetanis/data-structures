package com.svetanis.datastructures.tree.binary.bst;

import static com.svetanis.datastructures.tree.binary.bt.traversal.lot.LotLineByLineQueue.lot;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 450. Delete Node in a BST

public final class DeleteNodeInBst450 {
  // Time complexity O(h) - balanced
  // Space complexity: O(h)

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
    // find successor: smallest in the right subtree
    Node successor = successor(root.right);
    // move the left subtree of the root
    // to the left of the successor
    successor.left = root.left;
    // the new root should be the right
    // child of the deleted node
    root = root.right;
    return root;
  }

  private static Node successor(Node node) {
    Node curr = node;
    while (curr.left != null) {
      curr = curr.left;
    }
    return curr;
  }

  public static void main(String[] args) {
    Node root = newNode(5);
    root.left = newNode(3);
    root.right = newNode(6);
    root.left.left = newNode(2);
    root.left.right = newNode(4);
    root.right.right = newNode(7);
    System.out.println(lot(delete(root, 3))); // 5 4 6 2 7

    Node root2 = newNode(5);
    root2.left = newNode(3);
    root2.right = newNode(6);
    root2.left.left = newNode(2);
    root2.left.right = newNode(4);
    root2.right.right = newNode(7);
    System.out.println(lot(delete(root2, 0))); // 5 3 6 2 4 7
  }
}
