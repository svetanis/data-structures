package com.svetanis.datastructures.tree.binary.bt;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

public final class CloneBTRandomNode {

  public static Node copy(Node root) {
    if (root == null) {
      return null;
    }
    Map<Node, Node> map = newHashMap();
    Node copy = copy(root, map);
    copyRandom(root, copy, map);
    return copy;
  }

  private static Node copy(Node root, Map<Node, Node> map) {
    if (root == null) {
      return null;
    }
    Node copy = new Node(root.data);
    map.put(root, copy);
    copy.left = copy(root.left, map);
    copy.right = copy(root.right, map);
    return copy;
  }

  private static void copyRandom(Node node, Node copy, Map<Node, Node> map) {
    if (copy == null) {
      return;
    }
    copy.random = map.get(node.random);
    copyRandom(node.left, copy.left, map);
    copyRandom(node.right, copy.right, map);
  }

  public static void main(String[] args) {
    Node root = new Node(1);
    root.left = new Node(2);
    root.right = new Node(3);
    root.left.left = new Node(4);
    root.left.right = new Node(5);
    root.random = root.left.right;
    root.left.left.random = root;
    root.left.right.random = root.right;

    printInOrder(root);
    System.out.println();

    Node copy = copy(root);
    printInOrder(copy);
  }

  private static void printInOrder(Node node) {
    if (node == null) {
      return;
    }
    printInOrder(node.left);
    System.out.print("[" + node.data + " ");
    if (node.random == null) {
      System.out.print("NULL], ");
    } else {
      System.out.print(node.random.data + "], ");
    }
    printInOrder(node.right);
  }

  private static class Node {
    private int data;
    private Node left;
    private Node right;
    private Node random;

    Node(int data) {
      this.data = data;
      this.left = null;
      this.right = null;
      this.random = null;
    }
  }
}
