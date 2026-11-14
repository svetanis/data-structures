package com.svetanis.datastructures.tree.binary.bt.vertical;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.Map;
import java.util.TreeMap;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Vertical Sum of a Binary Tree
//
// Input: the root of a binary tree. Give the root column 0; a left child is one column left of
// its parent and a right child one column right. Returns a map from column number to the sum of
// the node values in that column, columns sorted from left to right.
//
// The one idea: carry the column number down the recursion and add each node's value to its
// column's running sum in a TreeMap, which keeps the columns sorted by their number. The order
// the nodes are visited in does not matter for a sum.
//
// Time: O(n log c) -- each node is visited once and updates a TreeMap of c columns (c <= n).
// Space: O(c) -- the map, plus the recursion stack of the height of the tree.
//
// Known limit: the recursion is as deep as the tree is tall, so a chain of tens of thousands of
// nodes overflows the default stack.

public final class VerticalSumHashing {

  public static Map<Integer, Integer> verticalSum(TreeNode root) {
    Map<Integer, Integer> map = new TreeMap<>();
    verticalSum(root, 0, map);
    return map;
  }

  private static void verticalSum(TreeNode node, int hDist, Map<Integer, Integer> map) {
    if (isNull(node)) {
      return; // BASE: nothing to add
    }

    // store the values in the map for the left subtree
    verticalSum(node.left, hDist - 1, map); // WALK: one column left

    // update vertical sum for horizontal distance of this node
    int prev = map.getOrDefault(hDist, 0);
    map.put(hDist, prev + node.val); // ADD to this column's sum

    // store the values in the map for the right subtree
    verticalSum(node.right, hDist + 1, map); // WALK: one column right
  }

  public static void main(String[] args) {
    // 4, 2, 12, 3 and 7

    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    System.out.println(verticalSum(root)); // {-2=4, -1=2, 0=12, 1=3, 2=7}
    System.out.println(verticalSum(null)); // {}
  }
}
