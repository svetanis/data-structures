package com.svetanis.datastructures.tree.binary.bt.dimension;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.HashMap;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Maximum Width of a Binary Tree, counting only real nodes (662 counts the empty positions
// between the end nodes too; this file does not).
//
// Input: the root of a binary tree. Returns the largest number of nodes on any one level
// (0 for an empty tree).
//
// The one idea: one entry per level. A preorder walk carries the level of the node it stands on
// (the root is level 1) and adds one to that level's counter in a map level -> nodes seen. When
// the walk ends, each counter is the number of nodes on that level, and the answer is the
// biggest counter.
//
// Time: O(n) -- each node is visited once, plus one pass over the h counters.
// Space: O(h) -- the map has one entry per level, and the recursion stack is h deep.
//
// Known limit: the recursion is as deep as the tree is tall, so a chain of tens of thousands of
// nodes overflows the default stack; MaxWidthQueue is the loop version.
//
// Siblings: MaxWidthQueue counts a level at a time with a queue; MaxWidthLevelOrder re-walks the
// tree once per level; MaxWidth662 counts empty positions too.

public final class MaxWidthPreOrder {

  // 2. Using PreOrder Traversal
  public static int maxWidth(TreeNode root) {
    Map<Integer, Integer> map = new HashMap<>();
    preorder(root, 1, map);
    int max = 0;
    for (int count : map.values()) {
      max = Math.max(max, count); // RECORD: the widest level so far
    }
    return max; // RETURN: 0 for an empty tree
  }

  private static void preorder(TreeNode root, int level, Map<Integer, Integer> map) {
    if (isNull(root)) {
      return;
    }
    map.putIfAbsent(level, 0);
    map.put(level, map.get(level) + 1); // COUNT: one more node on this level
    preorder(root.left, level + 1, map); // WALK: one level down
    preorder(root.right, level + 1, map);
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(8);
    root.right.right.left = newNode(6);
    root.right.right.right = newNode(7);
    System.out.println(maxWidth(root)); // 3
    System.out.println(maxWidth(null)); // 0
  }
}