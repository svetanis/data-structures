package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.height;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayList;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 102. Binary Tree Level Order Traversal
//
// Input: root of a binary tree. Returns the values level by level, top level first, each level
// left to right, as a list of lists. On LeetCode: 0 <= number of nodes <= 2000,
// -1000 <= node value <= 1000.
//
// The one idea: the values of level k are what you get by walking down k - 1 steps from the root,
// left child before right child, and collecting the nodes where the walk runs out of steps. So
// find the height, and for each level 1 .. height make one such walk: givenLevel(node, k) asks the
// two children for level k - 1, and at k == 1 the node itself is the answer. No queue is needed,
// at the price of one walk per level.
//
// Time: O(n * h) -- the walk for level k visits every node above level k; for a chain
//   (h = n) that is O(n^2), for a balanced tree O(n).
// Space: O(n) -- the output; the recursion is h deep.
//
// Siblings: LotLineByLineQueue gets the same lists in O(n) with a queue; LotLineByLineDfs in O(n)
// with one depth-first walk.

public final class LotLineByLineRecursive {

  public static List<List<Integer>> traverse(TreeNode root) {
    int height = height(root); // HEIGHT: how many levels to ask for
    List<List<Integer>> lists = new ArrayList<>();
    for (int i = 1; i <= height; i++) { // WALK: once per level
      List<Integer> list = new ArrayList<>();
      givenLevel(root, i, list);
      lists.add(list);
    }
    return lists;
  }

  private static void givenLevel(TreeNode node, int k, List<Integer> list) {
    if (isNull(node)) { // BASE: the walk ran off the tree
      return;
    }
    if (k == 1) { // RECORD: the walk ran out of steps on this node
      list.add(node.val);
    } else if (k > 1) { // WALK: one step down, left before right
      givenLevel(node.left, k - 1, list);
      givenLevel(node.right, k - 1, list);
    }
  }

  public static void main(String[] args) {
    TreeNode root = newNode(3);
    root.left = newNode(9);
    root.right = newNode(20);
    root.right.left = newNode(15);
    root.right.right = newNode(7);
    System.out.println(traverse(root)); // [[3], [9, 20], [15, 7]]
  }
}
