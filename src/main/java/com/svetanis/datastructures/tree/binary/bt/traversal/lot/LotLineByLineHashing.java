package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 102. Binary Tree Level Order Traversal
//
// Input: root of a binary tree. Returns the values level by level, top level first, each level
// left to right, as a list of lists. On LeetCode: 0 <= number of nodes <= 2000,
// -1000 <= node value <= 1000.
//
// The one idea: one map entry is one level: its key is the level number (the root is level 1) and
// its value is the list of that level's values. A preorder walk (node, then left, then right)
// carries the level down and appends each value to its level's list; preorder reaches the nodes
// of one level in left-to-right order, so each list comes out left to right. The map is a
// LinkedHashMap, which hands the entries back in the order they were created -- and preorder
// meets level k + 1 for the first time after level k, so that is level 1, 2, 3, ... in order.
//
// Time: O(n) -- each node is visited once, one map operation per node.
// Space: O(n) -- the map holds every value; the recursion is as deep as the tree.
//
// Siblings: LotLineByLineDfs does the same walk but keeps the lists in a list indexed by depth,
// which needs no map; LotLineByLineQueue reads a level at a time with a queue.

public final class LotLineByLineHashing {

  public static List<List<Integer>> traverse(TreeNode root) {
    Map<Integer, List<Integer>> map = new LinkedHashMap<>(); // MAP: level -> its values
    preorder(root, 1, map);
    return new ArrayList<>(map.values()); // RETURN: a copy of the lists, in level order
  }

  private static void preorder(TreeNode root, int level, Map<Integer, List<Integer>> map) {
    if (isNull(root)) { // BASE: nothing below a missing child
      return;
    }
    map.computeIfAbsent(level, k -> new ArrayList<>()).add(root.val); // RECORD: this level's list
    preorder(root.left, level + 1, map); // WALK: left before right
    preorder(root.right, level + 1, map);
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);
    System.out.println(traverse(root)); // [[1], [2, 3], [4, 5, 6]]
  }
}
