package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.height;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 103. Binary Tree Zigzag Level Order Traversal
//
// Input: root of a binary tree. Returns the values level by level as a list of lists: the first
// level left to right, the second right to left, and so on, alternating. On LeetCode:
// 0 <= number of nodes <= 2000, -100 <= node value <= 100.
//
// The one idea: the values of level k are what you get by walking down k - 1 steps from the root
// and collecting the nodes where the walk runs out of steps. Which child is asked first decides
// the order inside the level: left child first gives left to right, right child first gives right
// to left. So find the height, and for each level 1 .. height make one such walk, asking the
// children in the order that level wants; a flag, flipped after each level, says which.
//
// Time: O(n * h) -- the walk for level k visits every node above level k; for a chain
//   (h = n) that is O(n^2), for a balanced tree O(n).
// Space: O(n) -- the output; the recursion is h deep.
//
// Siblings: ZigZagQueue gets the same lists in O(n) with a queue; ZigZagTwoStack in O(n) with two
// stacks; ZigZagStackQueue returns one flat list.

public final class ZigZagRecursive {

  public static List<List<Integer>> traverse(TreeNode root) {
    List<List<Integer>> lists = new ArrayList<>();

    int height = height(root); // HEIGHT: how many levels to ask for
    boolean leftToRight = true; // DIRECTION: of the level being read
    for (int i = 1; i <= height; ++i) { // WALK: once per level
      List<Integer> list = new ArrayList<>();
      givenLevel(root, i, leftToRight, list);
      lists.add(list);
      // revert ltr to traverse
      // next level in opposite order
      leftToRight = !leftToRight; // FLIP: the next level goes the other way
    }
    return lists;
  }

  private static void givenLevel(TreeNode node, int k, boolean leftToRight, List<Integer> list) {
    if (isNull(node)) { // BASE: the walk ran off the tree
      return;
    }
    if (k == 1) { // RECORD: the walk ran out of steps on this node
      list.add(node.val);
    } else if (k > 1) { // WALK: one step down, the children in this level's order
      if (leftToRight) {
        givenLevel(node.left, k - 1, leftToRight, list);
        givenLevel(node.right, k - 1, leftToRight, list);
      } else {
        givenLevel(node.right, k - 1, leftToRight, list);
        givenLevel(node.left, k - 1, leftToRight, list);
      }
    }
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);

    System.out.println(traverse(root)); // [[1], [3, 2], [4, 5, 6]]
  }
}
