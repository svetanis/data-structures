package com.svetanis.datastructures.tree.binary.bt.view;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Top View of a Binary Tree (no LeetCode number)
//
// Input: the root of a binary tree. Returns the values of the top view, from the leftmost
// column to the rightmost: for each column the value of the highest node in it. Two nodes in
// the same column and on the same row: the left one counts.
//
// The one idea: give every node a column number (root 0, left child one less than its parent,
// right child one more) and a row number (root 0, each child one more than its parent). A
// depth-first walk does not visit rows in order, so each map entry keeps two numbers for its
// column: the value of the highest node seen so far and that node's row. A node replaces the
// entry only when its row is strictly smaller; the walk goes left before right, so on a tie
// the left-hand node came first and stays.
//
// Known limit: the walk recurses once per level of the tree, so a chain of 10^4 nodes (one
// child per node, ten thousand levels) overflows the default stack; TopViewIterative
// has no such limit.
//
// Time: O(n log n) -- n nodes, each one looks up and maybe puts in a TreeMap, O(log n) each.
// Space: O(n) -- the map, plus recursion depth up to the tree height.
//
// Siblings: TopViewIterative walks row by row, so the first node met in a column is already the
// highest and no row number is kept.

public final class TopViewRecursive {

  public static List<Integer> topView(TreeNode root) {
    if (root == null) {
      return new ArrayList<>(); // BASE: empty tree, empty view
    }

    Map<Integer, int[]> map = new TreeMap<>(); // MAP: column -> { value, row } of the highest node
    topView(root, 0, 0, map);
    return values(map);
  }

  private static List<Integer> values(Map<Integer, int[]> map) {
    List<Integer> list = new ArrayList<>();
    for (int[] entry : map.values()) { // COLLECT: columns in order, value only
      list.add(entry[0]);
    }
    return list;
  }

  private static void topView(TreeNode root, int dist, int level, Map<Integer, int[]> map) {
    if (root == null) {
      return; // BASE: past a leaf
    }

    if (!map.containsKey(dist) || level < map.get(dist)[1]) {
      map.put(dist, new int[] { root.val, level }); // RECORD: strictly higher row replaces
    }
    topView(root.left, dist - 1, level + 1, map); // WALK: left child, one column left
    topView(root.right, dist + 1, level + 1, map); // WALK: right child, one column right
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    System.out.println(topView(root)); // [4, 2, 1, 3, 7]

    TreeNode root2 = newNode(1);
    root2.left = newNode(2);
    root2.right = newNode(3);
    root2.left.right = newNode(4);
    root2.left.right.right = newNode(5);
    root2.left.right.right.right = newNode(6);
    System.out.println(topView(root2)); // [2, 1, 3, 6]
  }
}