package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Construct a binary tree from its in-order and level-order sequences. (No LeetCode problem asks
// for this pair; LeetCode 105 and 106 use pre-order or post-order with in-order instead.)
//
// Input: in -- the in-order values, level -- the level-order values of the same tree; all values
// are distinct. Returns: the root of the tree both sequences describe.
//
// The one idea: level order lists the top of the tree first, so level.get(0) is always the root
// of the tree being rebuilt. In in-order, everything to the left of the root is its left subtree
// and everything to the right is its right subtree. So the in-order index of the root splits the
// in-order slice in two, and each remaining level value goes to the side whose in-order slice holds
// it. A level list split this way keeps its level order inside each side, so the same step
// repeats on each side with its own shorter level list.
//
// Time: O(n^2) -- every node scans its whole level list to split it; on a left-leaning chain those
// lists have n-1, n-2, ... values.
// Space: O(n^2) worst case -- the left and right lists of every node on the current path are alive
// at once (n-1 + n-2 + ... on a left-leaning chain); O(n) on a balanced tree.
//
// Known limit: the recursion is as deep as the tree is tall. A chain of about 3,000 nodes
// overflows the default stack; this only happens on very large input.
//
// Siblings: BinaryTreeFromInLevelOrderIterative is the same algorithm with an explicit stack.

public final class BinaryTreeFromInLevelOrder {

  public static TreeNode construct(List<Integer> in, List<Integer> level) {
    Map<Integer, Integer> map = indexMap(in); // INDEX: value -> its in-order position
    return construct(in, level, 0, in.size() - 1, map);
  }

  public static TreeNode construct(List<Integer> in, List<Integer> level,
                       int start, int end, Map<Integer, Integer> map) {
    if (start > end) { // BASE: empty in-order slice, no node
      return null;
    }
    TreeNode node = newNode(level.get(0)); // ROOT: first level value of this subtree

    // if this node has no children then return
    if (start == end) {
      return node;
    }

    // else find the index of
    // this node in Inorder traversal
    int index = map.get(node.val); // SPLIT POINT: root's in-order position
    Set<Integer> set = new HashSet<>(in.subList(start, index)); // LEFT SET: values in the left slice
    List<Integer> left = new ArrayList<>();
    List<Integer> right = new ArrayList<>();
    for (int i = 1; i < level.size(); i++) { // SPLIT: level values, order kept, by side
      if (set.contains(level.get(i))) {
        left.add(level.get(i));
      } else {
        right.add(level.get(i));
      }
    }

    // using index in Inorder traversal,
    // construct left and right subtrees
    node.left = construct(in, left, start, index - 1, map); // RECURSE: left side
    node.right = construct(in, right, index + 1, end, map); // RECURSE: right side
    return node;
  }

  private static Map<Integer, Integer> indexMap(List<Integer> list) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < list.size(); i++) {
      map.put(list.get(i), i); // RECORD: value -> position
    }
    return map;
  }

  public static void main(String[] args) {
    List<Integer> in = Arrays.asList(4, 8, 10, 12, 14, 20, 22);
    List<Integer> level = Arrays.asList(20, 8, 22, 4, 12, 10, 14);
    TreeNode root = construct(in, level);
    inOrder(root); // 4 8 10 12 14 20 22
    System.out.println();
  }
}
