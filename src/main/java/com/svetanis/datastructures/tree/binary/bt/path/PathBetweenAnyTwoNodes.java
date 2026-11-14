package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.bt.path.RootToGivenNodePath.path;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import java.util.ArrayList;
import java.util.List;

// Path Between Two Nodes of a Binary Tree -- not a LeetCode problem; the nearest is
// 2096. Step-By-Step Directions From a Binary Tree Node to Another
//
// Input: the root of a binary tree whose values are all different, and two values x and y.
// Returns the nodes on the path from the node holding x to the node holding y, in order, both
// ends included. If x or y is not in the tree the result is empty.
//
// The one idea: take the path from the root down to x and the path from the root down to y. They
// share a beginning, up to the lowest common ancestor, and then split. The way from x to y is:
// x's path read backwards, from x up to that shared last node, then y's path from just after it
// down to y. The shared last node is the last position at which the two lists hold the same
// node, found by walking both lists together.
//
// Time: O(n) -- two searches for the root paths, then one pass over the two lists (the front
//   insertions in RootToGivenNodePath cost O(h^2), h = height).
// Space: O(h) -- the two root paths; the result is extra.
//
// Siblings: none; RootToGivenNodePath supplies the two root paths.
public final class PathBetweenAnyTwoNodes {

  public static List<TreeNode> pathBetweenNodes(TreeNode root, int x, int y) {
    List<TreeNode> p1 = new ArrayList<>(); // P1: root down to x
    path(root, x, p1);
    List<TreeNode> p2 = new ArrayList<>(); // P2: root down to y
    path(root, y, p2);
    List<TreeNode> list = new ArrayList<>();
    if (p1.isEmpty() || p2.isEmpty()) { // ABSENT: no path to a value that is not there
      return list;
    }

    int shared = 0; // SHARED: how many nodes the two root paths have in common
    while (shared < p1.size() && shared < p2.size() && p1.get(shared) == p2.get(shared)) {
      shared++;
    }
    for (int i = p1.size() - 1; i >= shared - 1; i--) { // UP: x up to the last shared node
      list.add(p1.get(i));
    }
    for (int i = shared; i < p2.size(); i++) { // DOWN: from below the shared node to y
      list.add(p2.get(i));
    }
    return list;
  }

  public static void main(String[] args) {
    TreeNode root = newNode(0);
    root.left = newNode(1);
    root.right = newNode(2);
    root.left.left = newNode(3);
    root.left.right = newNode(4);
    root.right.left = newNode(5);
    root.right.right = newNode(6);
    root.left.left.left = newNode(7);
    root.left.right.left = newNode(8);
    root.left.right.right = newNode(9);
    System.out.println(pathBetweenNodes(root, 7, 4)); // [7, 3, 1, 4]
    System.out.println(pathBetweenNodes(root, 8, 6)); // [8, 4, 1, 0, 2, 6]
    System.out.println(pathBetweenNodes(root, 3, 7)); // [3, 7]
    System.out.println(pathBetweenNodes(root, 9, 9)); // [9]
  }
}
