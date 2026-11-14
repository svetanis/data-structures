package com.svetanis.datastructures.tree.binary.bt.view;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Bottom View of a Binary Tree (no LeetCode number)
//
// Input: the root of a binary tree. Returns the values of the bottom view, from the leftmost
// column to the rightmost: for each column the value of the lowest node in it. Two nodes in
// the same column and on the same row: the right one counts.
//
// The one idea: give every node a column number (root 0, left child one less than its parent,
// right child one more) and a row number (root 0, each child one more than its parent). A
// depth-first walk does not visit rows in order, so each map entry keeps two numbers for its
// column: the value of the lowest node seen so far and that node's row. A node replaces the
// entry when its row is at least as large; the walk goes left before right, so on a tie the
// right-hand node comes later and wins.
//
// Known limit: the walk recurses once per level of the tree, so a chain of 10^4 nodes (one
// child per node, ten thousand levels) overflows the default stack; BottomViewIterative
// has no such limit.
//
// Time: O(n log n) -- n nodes, each one looks up and maybe puts in a TreeMap, O(log n) each.
// Space: O(n) -- the map, plus recursion depth up to the tree height.
//
// Siblings: BottomViewIterative walks row by row and simply overwrites, so it needs no row
// number.

public final class BottomViewRecursive {

  public static List<Integer> bottomView(TreeNode root) {
    if (root == null) {
      return new ArrayList<>(); // BASE: empty tree, empty view
    }

    Map<Integer, int[]> map = new TreeMap<>(); // MAP: column -> { value, row } of the lowest node
    bottomView(root, 0, 0, map);
    return values(map);
  }

  private static List<Integer> values(Map<Integer, int[]> map) {
    List<Integer> list = new ArrayList<>();
    for (int[] entry : map.values()) { // COLLECT: columns in order, value only
      list.add(entry[0]);
    }
    return list;
  }

  private static void bottomView(TreeNode root, int dist, int level, Map<Integer, int[]> map) {
    if (root == null) {
      return; // BASE: past a leaf
    }

    if (!map.containsKey(dist) || level >= map.get(dist)[1]) {
      map.put(dist, new int[] { root.val, level }); // RECORD: lower or equal row replaces
    }

    bottomView(root.left, dist - 1, level + 1, map); // WALK: left child, one column left
    bottomView(root.right, dist + 1, level + 1, map); // WALK: right child, one column right
  }

  public static void main(String[] args) {
    TreeNode root = newNode(20);
    root.left = newNode(8);
    root.right = newNode(22);
    root.left.left = newNode(5);
    root.left.right = newNode(3);
    root.left.right.left = newNode(10);
    root.left.right.right = newNode(14);
    root.right.right = newNode(25);
    System.out.println(bottomView(root)); // [5, 10, 3, 14, 25]

    TreeNode root2 = newNode(20);
    root2.left = newNode(8);
    root2.right = newNode(22);
    root2.left.left = newNode(5);
    root2.left.right = newNode(3);
    root2.left.right.left = newNode(10);
    root2.left.right.right = newNode(14);
    root2.right.left = newNode(4);
    root2.right.right = newNode(25);
    System.out.println(bottomView(root2)); // [5, 10, 4, 14, 25]
  }
}