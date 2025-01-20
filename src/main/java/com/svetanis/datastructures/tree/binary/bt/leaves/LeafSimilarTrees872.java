package com.svetanis.datastructures.tree.binary.bt.leaves;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 872. Leaf-Similar Trees

public final class LeafSimilarTrees872 {
  // Time Complexity: O(n + m)
  // Space Complexity: O(n + m)

  public static boolean leafSimilar(Node root1, Node root2) {
    if (root1 == null && root2 == null) {
      return true;
    }
    if (root1 == null || root2 == null) {
      return false;
    }
    List<Integer> list1 = leaves(root1);
    List<Integer> list2 = leaves(root2);
    return list1.equals(list2);
  }

  private static List<Integer> leaves(Node root) {
    if (root == null) {
      return new ArrayList<>();
    }
    List<Integer> list = leaves(root.left);
    list.addAll(leaves(root.right));
    if (list.isEmpty()) {
      list.add(root.data);
    }
    return list;
  }

  private static void leaves(Node root, List<Integer> list) {
    if (root == null) {
      return;
    }
    if (root.left == null && root.right == null) {
      list.add(root.data);
    }
    leaves(root.left, list);
    leaves(root.right, list);
  }

  public static void main(String[] args) {
    Node root1 = newNode(3);
    root1.left = newNode(5);
    root1.right = newNode(1);
    root1.left.left = newNode(6);
    root1.left.right = newNode(2);
    root1.right.left = newNode(9);
    root1.right.right = newNode(8);
    root1.left.right.left = newNode(7);
    root1.left.right.right = newNode(4);
    Node root2 = newNode(3);
    root2.left = newNode(5);
    root2.right = newNode(1);
    root2.left.left = newNode(6);
    root2.left.right = newNode(7);
    root2.right.left = newNode(4);
    root2.right.right = newNode(2);
    root2.right.right.left = newNode(9);
    root2.right.right.right = newNode(8);
    System.out.println(leafSimilar(root1, root2)); // true

    Node root3 = newNode(1);
    root3.left = newNode(2);
    root3.right = newNode(3);
    Node root4 = newNode(1);
    root4.left = newNode(3);
    root4.right = newNode(2);
    System.out.println(leafSimilar(root3, root4)); // false
  }
}
