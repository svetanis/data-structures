package com.svetanis.datastructures.tree.binary.bt.path;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class AncestorsRecursive {

  public static ImmutableList<Integer> ancestors(Node root, int key) {
    List<Integer> list = newArrayList();
    ancestors(root, key, list);
    return newList(list);
  }

  public static boolean ancestors(Node root, int key, List<Integer> list) {
    // base case
    if (isNull(root)) {
      return false;
    }

    if (root.data == key) {
      return true;
    }

    // if target key is present in either
    // left or right subtree of this node,
    // then print this node
    boolean isLeft = ancestors(root.left, key, list);
    boolean isRight = ancestors(root.right, key, list);
    if (isLeft || isRight) {
      list.add(root.data);
      return true;
    }

    // else return false
    return false;
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    root.left.left.left = newNode(8);
    root.left.right.right = newNode(9);
    root.right.right.left = newNode(10);

    for (int key = 1; key <= 10; key++) {
      print(ancestors(root, key));
    }
  }
}
