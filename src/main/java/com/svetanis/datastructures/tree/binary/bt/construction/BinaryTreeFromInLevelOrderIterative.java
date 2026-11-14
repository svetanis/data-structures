package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Construct a binary tree from its in-order and level-order sequences, without recursion. (No
// LeetCode problem asks for this pair; LeetCode 105 and 106 use pre-order or post-order with
// in-order instead.)
//
// Input: in -- the in-order values, level -- the level-order values of the same tree; all values
// are distinct. Returns: the root of the tree both sequences describe.
//
// The one idea: level order lists the top of the tree first, so level.get(0) is always the root
// of the tree being rebuilt. In in-order, everything to the left of the root is its left subtree
// and everything to the right is its right subtree. So the in-order index of the root splits the
// in-order slice in two, and each remaining level value goes to the side whose in-order slice holds
// it. This file keeps the pieces still to be built on a stack: one entry holds a node already
// created, its own level list, and the in-order slice that node roots. Popping an entry splits it
// and pushes one entry per non-empty side, so the depth of the tree never uses the call stack.
//
// Time: O(n^2) -- every node scans its whole level list to split it; on a left-leaning chain those
// lists have n-1, n-2, ... values.
// Space: O(n^2) worst case -- on a left-leaning chain every level list stays referenced until its
// entry is popped; O(n) on a balanced tree.
//
// Siblings: BinaryTreeFromInLevelOrder is the same algorithm with recursion; it overflows the
// default stack on a chain of about 3,000 nodes, this file does not.

public final class BinaryTreeFromInLevelOrderIterative {

  private record Frame(TreeNode node, List<Integer> level, int start, int end) {
  }

  public static TreeNode construct(List<Integer> in, List<Integer> level) {
    Map<Integer, Integer> map = indexMap(in); // INDEX: value -> its in-order position
    return construct(in, level, 0, in.size() - 1, map);
  }

  public static TreeNode construct(List<Integer> in, List<Integer> level, int start, int end,
      Map<Integer, Integer> map) {
    if (start > end) { // BASE: empty in-order slice, no node
      return null;
    }
    TreeNode root = newNode(level.get(0)); // ROOT: first level value
    Deque<Frame> stack = new ArrayDeque<>();
    stack.push(new Frame(root, level, start, end));
    while (!stack.isEmpty()) { // WALK: one pending subtree per pop
      Frame frame = stack.pop();
      if (frame.start() == frame.end()) { // LEAF: nothing to split
        continue;
      }
      TreeNode node = frame.node();
      int index = map.get(node.val); // SPLIT POINT: root's in-order position
      Set<Integer> set = new HashSet<>(in.subList(frame.start(), index)); // LEFT SET
      List<Integer> left = new ArrayList<>();
      List<Integer> right = new ArrayList<>();
      for (int i = 1; i < frame.level().size(); i++) { // SPLIT: level values by side, order kept
        int value = frame.level().get(i);
        if (set.contains(value)) {
          left.add(value);
        } else {
          right.add(value);
        }
      }
      if (frame.start() <= index - 1) { // LEFT: non-empty left slice
        node.left = newNode(left.get(0));
        stack.push(new Frame(node.left, left, frame.start(), index - 1));
      }
      if (index + 1 <= frame.end()) { // RIGHT: non-empty right slice
        node.right = newNode(right.get(0));
        stack.push(new Frame(node.right, right, index + 1, frame.end()));
      }
    }
    return root;
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
