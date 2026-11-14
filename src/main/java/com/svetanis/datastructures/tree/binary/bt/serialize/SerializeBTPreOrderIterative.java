package com.svetanis.datastructures.tree.binary.bt.serialize;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static java.lang.Integer.parseInt;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Serialize a binary tree to a string and back, without recursion. (LeetCode twin: 297. Serialize
// and Deserialize Binary Tree, whose values are -1000 <= val <= 1000.)
//
// Input: serialize takes the root of a binary tree of ints; deserialize takes a string made by
// serialize. Returns: serialize gives the pre-order text, with "#" standing for every missing
// child, values and markers separated by one space; deserialize gives back an equal tree.
//
// The one idea: pre-order (node, left subtree, right subtree) alone does not fix a tree's shape,
// but pre-order with a marker written for every missing child does: reading the text left to right,
// a value is a node whose left subtree is read next and then its right subtree, and a marker ends
// the subtree being read. The marker must be text that no value can spell, so it is "#" and not a
// number.
// serialize keeps the nodes still to be written on a stack, with a stand-in MISSING node for each
// missing child, so a node pops, writes its value, and pushes its right then its left child.
// deserialize keeps two things: the node whose slot is filled next (parent) and whether that slot
// is its left or its right, plus a stack of nodes whose left slot is filled and whose right slot is
// still waiting. A value goes into the slot and becomes the new parent with its left slot next; a
// marker fills the slot with nothing, and the next slot to fill is the right slot of the most
// recent node still waiting.
//
// Time: O(n) -- one token written and one token read per node and per missing child (about 2n).
// Space: O(n) for the text, plus a stack of at most the height of the tree.
//
// Siblings: SerializeBTPreOrder is the same method with recursion; it overflows the default stack
// on a chain of about 3,000 nodes in serialize, this file does not.

public final class SerializeBTPreOrderIterative {

  private static final String MARKER = "#";
  private static final TreeNode MISSING = new TreeNode(0); // stand-in for a missing child, compared by identity

  public static String serialize(TreeNode root) {
    StringBuilder sb = new StringBuilder();
    Deque<TreeNode> stack = new ArrayDeque<>();
    stack.push(orMissing(root));
    while (!stack.isEmpty()) { // WALK: pre-order, one entry per pop
      TreeNode node = stack.pop();
      if (node == MISSING) { // MARKER: a missing child
        sb.append(MARKER).append(" ");
      } else {
        sb.append(node.val).append(" "); // VALUE: the node, before its subtrees
        stack.push(orMissing(node.right)); // RIGHT: pushed first, written last
        stack.push(orMissing(node.left)); // LEFT: on top, written next
      }
    }
    return sb.toString();
  }

  private static TreeNode orMissing(TreeNode node) {
    return node == null ? MISSING : node;
  }

  public static TreeNode deserialize(String str) {
    if (str == null || str.isBlank()) {
      return null;
    }
    StringTokenizer st = new StringTokenizer(str, " ");
    Deque<TreeNode> waiting = new ArrayDeque<>(); // nodes whose right slot is still empty
    TreeNode root = null;
    TreeNode parent = null; // node whose slot is filled next
    boolean fillLeft = true;
    while (st.hasMoreTokens()) { // WALK: one token per round
      String value = st.nextToken();
      TreeNode child = value.equals(MARKER) ? null : newNode(parseInt(value));
      if (parent == null) { // ROOT: the very first token
        root = child;
      } else if (fillLeft) { // LEFT SLOT
        parent.left = child;
        waiting.push(parent); // its right slot comes after the left subtree
      } else { // RIGHT SLOT
        parent.right = child;
      }
      if (child != null) { // DOWN: the new node's left slot is next
        parent = child;
        fillLeft = true;
      } else if (waiting.isEmpty()) { // DONE: every slot is filled
        break;
      } else { // BACK: right slot of the latest node still waiting
        parent = waiting.pop();
        fillLeft = false;
      }
    }
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
