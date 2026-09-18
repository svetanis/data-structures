package com.svetanis.datastructures.tree.binary.bst.candidate;

// 510. Inorder Successor in BST II

// Step. 1 : if right subtree of node is not null,
// then succ lies in right subtree
// Go to right subtree and return
// node with min key value in right subtree

// Step. 2 : if right subtree of node is null,
// then succ is one of the ancestors
// Travel up using the parent pointer
// until you see a node which is
// left child of its parent.
// The parent of such a node is the succ.
// If no such node exists, the node was
// the largest in the tree: return null.

// No line reads a value: `parent.left != node`
// compares references (is it the same object),
// which is what LC 510's follow-up asks for.

public final class InorderSuccessorParent {
  // Time complexity: O(h), h is height of tree

  // the only node type in this package with a parent pointer
  private static final class Node {
    private final int data;
    private Node left;
    private Node right;
    private Node parent;

    private Node(int data) {
      this.data = data;
    }

    @Override
    public String toString() {
      return Integer.toString(data);
    }
  }

  public static Node inOrderSuccessor(Node node) {
    if (node == null) {
      return null;
    }
    if (node.right != null) {
      return min(node.right);
    }
    Node parent = node.parent;
    // came up from a right child: the parent is smaller, keep climbing
    while (parent != null && parent.left != node) {
      node = parent;           // node first, so it takes parent's old place
      parent = parent.parent;  // then parent, one level above it
    }
    return parent;             // null when the climb passed the root
  }

  private static Node min(Node root) {
    Node current = root;
    // the leftmost node, which may still have a right child
    while (current.left != null) {
      current = current.left;
    }
    return current;
  }

  private static Node insert(Node node, int data) {
    if (node == null) {
      return new Node(data);
    }
    if (data <= node.data) {
      node.left = insert(node.left, data);
      node.left.parent = node;
    } else {
      node.right = insert(node.right, data);
      node.right.parent = node;
    }
    return node;
  }

  private static void inOrder(Node node) {
    if (node == null) {
      return;
    }
    inOrder(node.left);
    System.out.print(node + " ");
    inOrder(node.right);
  }

  public static void main(String[] args) {
    //         20
    //       /    \
    //      8      22
    //     / \
    //    4   12
    //       /  \
    //      10   14

    Node root = null;
    root = insert(root, 20);
    root = insert(root, 8);
    root = insert(root, 22);
    root = insert(root, 4);
    root = insert(root, 12);
    root = insert(root, 10);
    root = insert(root, 14);

    inOrder(root);
    System.out.println();
    System.out.println(inOrderSuccessor(root.left)); // 10 -- right subtree, then leftmost
    System.out.println(inOrderSuccessor(root.left.right.left)); // 12 -- up from a left child
    System.out.println(inOrderSuccessor(root.left.right.right)); // 20 -- up through two right children
    System.out.println(inOrderSuccessor(root.right)); // null -- the largest
  }
}
