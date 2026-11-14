package com.svetanis.datastructures.tree.binary.bst.candidate;

// 510. Inorder Successor in BST II
//
// Input: node, a node of a BST in which every node also points to its parent (the root's parent is
// null). The root of the tree is not given. On LeetCode: 1 <= number of nodes <= 10^4,
// -10^5 <= node.val <= 10^5, all values distinct. Returns the node that comes right after node in an
// in-order walk -- the node with the smallest value larger than node's -- or null when node holds the
// largest value.
//
// The one idea: the next node in order is found from where the node sits, in one of two ways.
// If the node has a right subtree, the successor is the smallest value in it: take the right child
// and go left as far as possible. If it has none, everything under it is already visited, and the
// successor is the nearest ancestor that sits to the right of the node's path: climb with the
// parent pointer while the current node is a right child (its parent is smaller, so it is behind
// us); the first time the current node is a left child, its parent is the successor. If the climb
// passes the root, the node was the largest and the answer is null.
// No line reads a value: `parent.left != node` compares references (is it the same object), which
// is what the LC 510 follow-up asks for.
//
// Time: O(h) -- at most one climb to the root or one descent to a leaf, h <= n.
// Space: O(1) -- a few variables.
//
// Siblings: InorderSuccessor solves LC 285, where the root is given and there are no parent
// pointers, so it searches down from the root instead of climbing.

public final class InorderSuccessorParent {

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
      return min(node.right); // DESCEND: the smallest value in the right subtree
    }
    Node parent = node.parent;
    // came up from a right child: the parent is smaller, keep climbing
    while (parent != null && parent.left != node) {
      node = parent;           // CLIMB: node first, so it takes parent's old place
      parent = parent.parent;  // then parent, one level above it
    }
    return parent;             // RETURN: null when the climb passed the root
  }

  private static Node min(Node root) {
    Node current = root;
    // the leftmost node, which may still have a right child
    while (current.left != null) {
      current = current.left; // WALK: left until there is no left child
    }
    return current;
  }

  private static Node insert(Node node, int data) {
    if (node == null) {
      return new Node(data); // BASE: the empty place for the new node
    }
    if (data <= node.data) {
      node.left = insert(node.left, data); // WALK: smaller or equal goes left
      node.left.parent = node;
    } else {
      node.right = insert(node.right, data); // WALK: larger goes right
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

    inOrder(root); // prints 4 8 10 12 14 20 22
    System.out.println();
    System.out.println(inOrderSuccessor(root.left)); // 10 -- right subtree, then leftmost
    System.out.println(inOrderSuccessor(root.left.right.left)); // 12 -- up from a left child
    System.out.println(inOrderSuccessor(root.left.right.right)); // 20 -- up through two right children
    System.out.println(inOrderSuccessor(root.right)); // null -- the largest
  }
}
