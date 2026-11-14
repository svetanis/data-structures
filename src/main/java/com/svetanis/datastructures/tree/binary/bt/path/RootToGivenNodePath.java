package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import java.util.ArrayList;
import java.util.List;

// Root-to-Node Path in a Binary Tree -- not a LeetCode problem
//
// Input: the root of a binary tree whose values are all different, and a value x. Returns the
// nodes on the path from the root down to the node holding x, root first. If x is not in the
// tree the result is empty. (With a repeated value the search adds the path to every copy into
// the same list, so the result is wrong; the values must be different.)
//
// The one idea: ask each node "is x me, or somewhere below me?". A node answers yes when it holds
// x or when either child says yes. Every node that answers yes is on the path, and the answers
// come back from the bottom, so each yes-node is put at the FRONT of the list: the root ends up
// first.
//
// Time: O(n + h^2) -- every node is asked once; each of the h nodes on the path is inserted at
//   the front of the list, which moves up to h entries (h = height).
// Space: O(h) -- the recursion stack (h = height, n on a chain); the list is extra.
//
// Siblings: PathBetweenAnyTwoNodes uses this twice to find the path between two nodes.
public final class RootToGivenNodePath {

  public static List<TreeNode> path(TreeNode root, int x) {
    List<TreeNode> list = new ArrayList<>();
    path(root, x, list);
    return list;
  }

  public static boolean path(TreeNode root, int x, List<TreeNode> list) {
    if (isNull(root)) { // BASE: x is not below an empty spot
      return false;
    }
    boolean equal = root.val == x; // FOUND: this node holds x
    boolean left = path(root.left, x, list); // ASK: left subtree
    boolean right = path(root.right, x, list); // ASK: right subtree
    if (equal || left || right) { // RECORD: x is here or below, so this node is on the path
      list.add(0, root);
      return true;
    }
    return false;
  }

  public static void main(String[] args) {
    TreeNode root = newNode(5);
    root.left = newNode(10);
    root.right = newNode(15);
    root.left.left = newNode(20);
    root.left.right = newNode(25);
    root.left.right.right = newNode(45);
    root.right.left = newNode(30);
    root.right.right = newNode(35);
    System.out.println(path(root, 45)); // [5, 10, 25, 45]
  }
}
