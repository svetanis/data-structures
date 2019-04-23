package com.svetanis.datastructures.tree.binary.model.mutable.primitive;

import static java.lang.Math.max;

public final class Nodes {

  public static int size(Node root) {
    if (root == null) {
      return 0;
    }
    return 1 + size(root.left) + size(root.right);
  }

  public static int depth(Node node) {
    if (node == null) {
      return 0;
    } else {
      int left = depth(node.left);
      int right = depth(node.right);
      return 1 + max(left, right);
    }
  }

  public static boolean isNull(Node node) {
    return node == null;
  }

  public static boolean isNotNull(Node node) {
    return !isNull(node);
  }

  public static boolean isLeaf(Node node) {
    if (node == null) {
      return false;
    }
    if (node.left == null && node.right == null) {
      return true;
    }
    return false;
  }

  public static boolean isHalfNode(Node node) {
    boolean one = node.left != null && node.right == null;
    boolean two = node.left == null && node.right != null;
    return one || two;
  }

  public static boolean isFull(Node node) {
    return node.left != null && node.right != null;
  }

  public static boolean isAbsent(Node root, int k) {
    return !isPresent(root, k);
  }

  public static boolean isPresent(Node root, int k) {
    if (root == null) {
      return false;
    }
    if (root.data == k || isPresent(root.left, k) || isPresent(root.right, k)) {
      return true;
    }
    return false;
  }

  public static void inOrder(Node node) {
    if (node == null) {
      return;
    }
    inOrder(node.left);
    System.out.print(node + " ");
    inOrder(node.right);
  }

  public static void preOrder(Node node) {
    if (node == null) {
      return;
    }
    System.out.print(node + " ");
    preOrder(node.left);
    preOrder(node.right);
  }

  public static void postOrder(Node node) {
    if (node == null) {
      return;
    }
    postOrder(node.left);
    postOrder(node.right);
    System.out.print(node + " ");
  }

  // BST
  public static Node buildBST(Node root, int[] keys) {
    for (int i = 0; i < keys.length; i++) {
      Node node = new Node(keys[i]);
      root = insert(root, node);
    }
    return root;
  }

  public static Node insert(Node root, Node node) {
    insert(root, node.data);
    return root;
  }

  // bst insert, dups allowed
  public static void insert(Node root, int value) {
    if (value < root.data) {
      if (root.left == null) {
        root.left = new Node(value);
      } else {
        insert(root.left, value);
      }
    } else {
      if (root.right == null) {
        root.right = new Node(value);
      } else {
        insert(root.right, value);
      }
    }
  }

  public static Node insert(int[] a) {
    Node root = null;
    int n = a.length;
    for (int i = n - 1; i >= 0; i--) {
      root = insertUtil(root, a[i]);
    }
    return root;
  }

  private static Node insertUtil(Node root, int value) {
    if (root == null) {
      return new Node(value);
    } else if (value < root.data) {
      if (root.left == null) {
        root.left = new Node(value);
      } else {
        root.left = insertUtil(root.left, value);
      }
    } else if (value > root.data) {
      if (root.right == null) {
        root.right = new Node(value);
      } else {
        root.right = insertUtil(root.right, value);
      }
    }
    return root;
  }

  public static void leaves(Node node) {
    if (node == null) {
      return;
    }
    if (node.isLeaf()) {
      System.out.print(node + " ");
    }
    leaves(node.left);
    leaves(node.right);
  }

  public static void printDLL(Node head) {
    Node node = head;
    System.out.println("Forward DLL traversal: ");
    while (head != null) {
      System.out.print(head.data + "->");
      node = head;
      head = head.right;
    }
    System.out.println();
    System.out.println("Backward DLL traversal: ");
    while (node != null) {
      System.out.print(node.data + "<-");
      node = node.left;
    }
    System.out.println();
  }

  public static void printLL(Node node) {
    while (node != null) {
      System.out.print(node + " ");
      node = node.right;
    }
    System.out.println();
  }

}
