package com.svetanis.datastructures.tree.binary.bt.view;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class BoundaryTraversalRecursive {

  public static ImmutableList<Node> boundary(Node root) {
    // time complexity: O(n)

    List<Node> list = newArrayList();
    if (root != null) {
      list.add(root);
      // left boundary in top-down manner
      boundaryLeft(root.left, list);

      // all leaf nodes
      leaves(root.left, list);
      leaves(root.right, list);

      // right boundary in bottom-up manner
      boundaryRight(root.right, list);
    }
    return newList(list);
  }

  private static void boundaryLeft(Node node, List<Node> list) {
    if (node != null) {
      if (node.left != null) {
        // to ensure top down order, print the node
        // before calling itself for left subtree
        list.add(node);
        boundaryLeft(node.left, list);
      } else if (node.right != null) {
        list.add(node);
        boundaryLeft(node.right, list);
      }
      // do nothing if it is a leaf node,
      // this way we avoid duplicates in output
    }
  }

  private static void boundaryRight(Node node, List<Node> list) {
    if (node != null) {
      if (node.right != null) {
        // to ensure bottom up order, first call for
        // right subtree, then print this node
        boundaryRight(node.right, list);
        list.add(node);
      } else if (node.left != null) {
        boundaryRight(node.left, list);
        list.add(node);
      }
      // do nothing if it is a leaf node,
      // this way we avoid duplicates in output
    }
  }

  private static void leaves(Node node, List<Node> list) {
    if (node == null) {
      return;
    }
    if (isLeaf(node)) {
      list.add(node);
    }
    leaves(node.left, list);
    leaves(node.right, list);
  }

  public static void main(String[] args) {
    Node tree = newNode(1);
    tree.left = newNode(2);
    tree.right = newNode(3);
    tree.left.left = newNode(4);
    tree.left.right = newNode(5);
    tree.right.right = newNode(6);

    print(boundary(tree));
    System.out.println();

    Node root = newNode(20);
    root.left = newNode(8);
    root.right = newNode(22);
    root.left.left = newNode(4);
    root.left.right = newNode(12);
    root.left.right.left = newNode(10);
    root.left.right.right = newNode(14);
    root.right.right = newNode(25);

    inOrder(root);
    System.out.println();

    print(boundary(root));
  }
}
