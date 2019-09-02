package com.svetanis.datastructures.tree.binary.bt;

import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// given binary tree, determine if two given
// nodes are cousins of each other or not.
// two nodes of binary tree are cousins of 
// each other only if they have different 
// parents but they have same level

public final class CousinsHashing {

  public static boolean cousins(Node root, int a, int b) {
    // Time Complexity: O(n)

    if (isNull(root)) {
      return true;
    }

    Map<Integer, Integer> depth = newHashMap();
    Map<Integer, Node> parent = newHashMap();

    preorder(root, null, depth, parent);
    boolean one = depth.get(a) == depth.get(b);
    boolean two = parent.get(a) != parent.get(b);
    return one && two;
  }

  private static void preorder(Node root, Node parent, 
      Map<Integer, Integer> depth, Map<Integer, Node> map) {

    if (isNull(root)) {
      return;
    }

    int height = isNull(parent) ? 0 : 1 + depth.get(parent.data);

    depth.put(root.data, height);
    map.put(root.data, parent);
    preorder(root.left, root, depth, map);
    preorder(root.right, root, depth, map);
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.left.right.right = newNode(15);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    root.right.left.right = newNode(8);
    System.out.println(cousins(root, 4, 7));

    Node root1 = newNode(1);
    root1.left = newNode(2);
    root1.right = newNode(3);
    root1.left.left = newNode(4);
    root1.left.right = newNode(5);
    root1.right.left = newNode(6);
    root1.right.right = newNode(7);
    System.out.println(cousins(root1, 5, 6));
  }
}
