package com.svetanis.datastructures.tree.binary.bt.cousins;

import static com.svetanis.datastructures.tree.binary.bt.Level.level;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Print All Cousins of a Given Node (no LeetCode number)
//
// Input: the root of a binary tree with unique values and a node of that tree. The cousins of the
// node are the nodes on its level that do not share its parent; its sibling and itself are not
// cousins. Returns the values of the cousins from left to right, as a list that cannot be
// changed. The root, and a node that is not in the tree, have no cousins. The values must be
// unique: the level of the node is looked up by its value.
//
// The one idea: first find the node's level, counting the root as level 1. Then walk down to the
// nodes one level above it -- the possible parents -- and from each parent collect its children,
// unless the node is one of them. The nodes collected are the whole level minus the node and its
// sibling, in left-to-right order.
//
// Time: O(n) -- one walk to find the level and one to collect.
// Space: O(h) -- the recursion stack, plus the output.
//
// Siblings: PrintCousinsIterative goes level by level with a queue, and compares nodes by
// reference, so it does not need unique values.

public final class PrintCousinsRecursive {

  public static List<Integer> cousins(TreeNode root, TreeNode node) {
    int level = level(root, node); // LEVEL: of the given node, root = 1, 0 if absent
    List<Integer> list = new ArrayList<>();
    givenLevel(root, node, level, list);
    return Collections.unmodifiableList(list); // RETURN: the cousins, left to right
  }

  private static void givenLevel(TreeNode root, TreeNode node,
                        int level, List<Integer> list) {

    if (isNull(root) || level < 2) {
      return; // BASE: no parents above the root
    }

    if (level == 2) {
      if (root.left == node || root.right == node) {
        return; // SKIP: this parent holds the node and its sibling
      }
      if (isNotNull(root.left)) {
        list.add(root.left.val); // TAKE: a child of another parent
      }
      if (isNotNull(root.right)) {
        list.add(root.right.val);
      }
    } else if (level > 2) {
      givenLevel(root.left, node, level - 1, list); // WALK: one level down
      givenLevel(root.right, node, level - 1, list);
    }
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.left.right.right = newNode(15);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    root.right.left.right = newNode(8);
    System.out.println(cousins(root, root.left.right)); // [6, 7]
  }
}
