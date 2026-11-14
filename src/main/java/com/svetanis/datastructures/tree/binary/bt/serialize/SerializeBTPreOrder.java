package com.svetanis.datastructures.tree.binary.bt.serialize;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static java.lang.Integer.parseInt;

import java.util.StringTokenizer;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Serialize a binary tree to a string and back. (LeetCode twin: 297. Serialize and Deserialize
// Binary Tree, whose values are -1000 <= val <= 1000.)
//
// Input: serialize takes the root of a binary tree of ints; deserialize takes a string made by
// serialize. Returns: serialize gives the pre-order text, with MARKER standing for every missing
// child, values and markers separated by one space; deserialize gives back an equal tree.
//
// The one idea: pre-order (node, left subtree, right subtree) alone does not fix a tree's shape,
// but pre-order with a marker written for every missing child does: reading the text left to right,
// a value is a node whose left subtree is read next and then its right subtree, and a marker ends
// the subtree being read. The marker must be text that no value can spell, so it is "#" and not a
// number; a number marker would be read as a real node wherever the tree holds that number.
//
// Time: O(n) -- one token written and one token read per node and per missing child (about 2n).
// Space: O(n) for the text, plus the recursion (as deep as the tree is tall, up to n).
//
// Known limit: the recursion is as deep as the tree is tall. A chain of about 3,000 nodes
// overflows the default stack in serialize; this only happens on very large input.
//
// Siblings: SerializeBTPreOrderIterative is the same method with an explicit stack;
// SerializeBTPreOrderSimple and SerializeDeserializeBTPreOrder are the same idea with other
// separators.

public final class SerializeBTPreOrder {

  private static final String MARKER = "#";

  public static String serialize(TreeNode root) {
    StringBuilder sb = new StringBuilder();
    serialize(root, sb);
    return sb.toString();
  }

  private static void serialize(TreeNode root, StringBuilder sb) {
    if (root == null) { // MARKER: a missing child
      sb.append(MARKER + " ");
      return;
    }
    sb.append(root.val + " "); // VALUE: the node, before its subtrees
    serialize(root.left, sb); // LEFT: whole left subtree
    serialize(root.right, sb); // RIGHT: whole right subtree
  }

  public static TreeNode deserialize(String str) {
    if (str == null || str.isBlank()) {
      return null;
    }
    StringTokenizer st = new StringTokenizer(str, " ");
    return deserialize(st);
  }

  private static TreeNode deserialize(StringTokenizer st) {
    if (!st.hasMoreTokens()) { // BASE: text used up
      return null;
    }
    String value = st.nextToken();
    if (value.equals(MARKER)) { // BASE: marker, a missing child
      return null;
    }
    TreeNode root = newNode(parseInt(value)); // VALUE: the next token is this node
    root.left = deserialize(st); // LEFT: read next
    root.right = deserialize(st); // RIGHT: read after the left subtree is complete
    return root;
  }

  public static void main(String[] args) {
    TreeNode root = newNode(20);
    root.left = newNode(8);
    root.left.left = newNode(4);
    root.left.right = newNode(12);
    root.left.right.left = newNode(10);
    root.left.right.right = newNode(14);
    inOrder(root); // 4 8 10 12 14 20
    System.out.println();
    String serialized = serialize(root);
    System.out.println(serialized); // 20 8 4 # # 12 10 # # 14 # # #
    TreeNode deserialized = deserialize(serialized);
    inOrder(deserialized); // 4 8 10 12 14 20
    System.out.println();
  }
}
