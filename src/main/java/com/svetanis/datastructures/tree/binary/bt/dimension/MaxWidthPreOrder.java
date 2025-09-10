package com.svetanis.datastructures.tree.binary.bt.dimension;

import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.utils.Arrays.max;

import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree, write a function to get the maximum width of the given tree. 
// Width of a tree is maximum of widths of all levels. 

// excluding null nodes between the end nodes

public final class MaxWidthPreOrder {

  // 2. Using PreOrder Traversal
  public static int maxWidth(Node root) {
    Map<Integer, Integer> map = newHashMap();
    preorder(root, 1, map);
    return max(map.values());
  }

  private static void preorder(Node root, int level, Map<Integer, Integer> map) {
    if (isNull(root)) {
      return;
    }
    map.putIfAbsent(level, 0);
    map.put(level, map.get(level) + 1);
    preorder(root.left, level + 1, map);
    preorder(root.right, level + 1, map);
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(8);
    root.right.right.left = newNode(6);
    root.right.right.right = newNode(7);
    System.out.println(maxWidth(root));
  }
}