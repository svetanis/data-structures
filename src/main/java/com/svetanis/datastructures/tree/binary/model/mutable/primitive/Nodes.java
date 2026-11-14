package com.svetanis.datastructures.tree.binary.model.mutable.primitive;

import static java.lang.Math.max;

import java.util.List;

// Nodes -- static helpers for the mutable TreeNode (a node holds an int val and a left and a right
// child; a missing child is null). Four groups:
//   counting and shape -- size and height, each O(n): they visit every node of the subtree.
//   tests -- isNull, isLeaf, isHalfNode (exactly one child), isFull (two children), and
//            isPresent / isAbsent, which search EVERY node, so they work on any binary tree and
//            take O(n); they do not use the search-tree order.
//   printing -- inOrder, preOrder, postOrder and leaves print each node's val followed by a
//            space; printDLL and printLL print a tree used as a linked list (right = next,
//            left = previous).
//   building a search tree -- buildBST and insert(root, value) add values one at a time and send
//            a value equal to a node's to the right (duplicates are kept). insert(int[]) and
//            insert(List) build a new tree with insertUtil, which ignores a value already
//            present; insert(int[]) adds the array from its LAST element to its first, insert(List)
//            from first to last.
//
// insert(root, value) and buildBST need a non-null root; insert(int[]) and insert(List) create
// it. Time: O(h) per inserted value, h the tree's height.
//
// Known limit: size, height, isPresent, the traversals and the inserts recurse as deep as the
// tree is tall, so a chain of a few tens of thousands of nodes overflows the default stack (size
// and height run on a 10^4-node chain).

public final class Nodes {

  public static int size(TreeNode root) {
    if (root == null) {
      return 0; // BASE: an empty tree has no nodes
    }
    return 1 + size(root.left) + size(root.right); // COUNT: this node plus both subtrees
  }

  public static int height(TreeNode node) {
    if (node == null) {
      return 0;
    } else {
      int left = height(node.left);
      int right = height(node.right);
      return 1 + max(left, right); // COUNT: levels on the taller side, plus this one
    }
  }

  public static boolean isNull(TreeNode node) {
    return node == null;
  }

  public static boolean isNotNull(TreeNode node) {
    return !isNull(node);
  }

  public static boolean isLeaf(TreeNode node) {
    if (node == null) {
      return false;
    }
    if (node.left == null && node.right == null) {
      return true;
    }
    return false;
  }

  public static boolean isHalfNode(TreeNode node) {
    boolean one = node.left != null && node.right == null;
    boolean two = node.left == null && node.right != null;
    return one || two;
  }

  public static boolean isFull(TreeNode node) {
    return node.left != null && node.right != null;
  }

  public static boolean isAbsent(TreeNode root, int k) {
    return !isPresent(root, k);
  }

  public static boolean isPresent(TreeNode root, int k) {
    if (root == null) {
      return false;
    }
    if (root.val == k || isPresent(root.left, k) || isPresent(root.right, k)) { // SEARCH: every node, any order
      return true;
    }
    return false;
  }

  public static void inOrder(TreeNode node) {
    if (node == null) {
      return;
    }
    inOrder(node.left);
    System.out.print(node + " ");
    inOrder(node.right);
  }

  public static void preOrder(TreeNode node) {
    if (node == null) {
      return;
    }
    System.out.print(node + " ");
    preOrder(node.left);
    preOrder(node.right);
  }

  public static void postOrder(TreeNode node) {
    if (node == null) {
      return;
    }
    postOrder(node.left);
    postOrder(node.right);
    System.out.print(node + " ");
  }

  // BST
  public static TreeNode buildBST(TreeNode root, int[] keys) {
    for (int i = 0; i < keys.length; i++) {
      TreeNode node = new TreeNode(keys[i]);
      root = insert(root, node);
    }
    return root;
  }

  public static TreeNode insert(TreeNode root, TreeNode node) {
    insert(root, node.val);
    return root;
  }

  // bst insert, dups allowed: a value equal to a node's goes to the right; root must not be null
  public static void insert(TreeNode root, int value) {
    if (value < root.val) {
      if (root.left == null) {
        root.left = new TreeNode(value);
      } else {
        insert(root.left, value);
      }
    } else {
      if (root.right == null) {
        root.right = new TreeNode(value);
      } else {
        insert(root.right, value);
      }
    }
  }

  // new tree from the array, adding its elements from the last to the first
  public static TreeNode insert(int[] a) {
    TreeNode root = null;
    int n = a.length;
    for (int i = n - 1; i >= 0; i--) {
      root = insertUtil(root, a[i]);
    }
    return root;
  }
  
  // new tree from the list, adding its elements from the first to the last
  public static TreeNode insert(List<Integer> list) {
    TreeNode root = null;
    for(int val : list) {
      root = insertUtil(root, val);
    }
    return root;
  }

  // adds value below root and returns the root; a value already in the tree is skipped
  private static TreeNode insertUtil(TreeNode root, int value) {
    if (root == null) {
      return new TreeNode(value);
    } else if (value < root.val) {
      if (root.left == null) {
        root.left = new TreeNode(value);
      } else {
        root.left = insertUtil(root.left, value);
      }
    } else if (value > root.val) {
      if (root.right == null) {
        root.right = new TreeNode(value);
      } else {
        root.right = insertUtil(root.right, value);
      }
    }
    return root;
  }

  public static void leaves(TreeNode node) {
    if (node == null) {
      return;
    }
    if (node.isLeaf()) {
      System.out.print(node + " ");
    }
    leaves(node.left);
    leaves(node.right);
  }

  public static void printDLL(TreeNode head) {
    TreeNode node = head;
    System.out.println("Forward DLL traversal: ");
    while (head != null) {
      System.out.print(head.val + "->");
      node = head;
      head = head.right;
    }
    System.out.println();
    System.out.println("Backward DLL traversal: ");
    while (node != null) {
      System.out.print(node.val + "<-");
      node = node.left;
    }
    System.out.println();
  }

  public static void printLL(TreeNode node) {
    while (node != null) {
      System.out.print(node + " ");
      node = node.right;
    }
    System.out.println();
  }

}
