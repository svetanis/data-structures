package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Ancestors of a Node in a Binary Tree -- not a LeetCode problem
//
// Input: the root of a binary tree and a value k that is in the tree. Returns the values of the
// ancestors of the node holding k, from its parent up to the root; the root has none.
// Assumes every value is different and none is 0 (0 marks "no parent" below); a k that is not in
// the tree throws a NullPointerException, and a repeated value throws an
// IllegalArgumentException while the map is built.
//
// The one idea: one entry of the map says "this value's parent holds that value". Walk the whole
// tree once with a stack and fill the map: each child gets its parent's value. Then start at k
// and follow the entries up, writing down each value, until the entry for the root (0) is
// reached.
//
// Time: O(n + h) -- every node is pushed and popped once, then the chain of at most h parents is
//   followed.
// Space: O(n) -- the stack and the map.
//
// Siblings: AncestorsRecursive finds the same list with one recursive search and no map; it also
// answers [] for a k that is absent.
public final class AncestorsIterative {

  private static final String DUPLICATE = "duplicate value in the tree";

  public static List<Integer> ancestors(TreeNode root, int k) {
    if (isNull(root)) { // BASE: no tree, no ancestors
      return new ArrayList<>();
    }
    List<Integer> list = new ArrayList<>();
    Map<Integer, Integer> map = ancestors(root);
    int key = map.get(k); // START: the parent of k
    while (key != 0) { // WALK: up until the root's entry (0)
      list.add(key); // RECORD: one ancestor
      key = map.get(key);
    }
    return list;
  }

  private static Map<Integer, Integer> ancestors(TreeNode root) {
    Deque<TreeNode> stack = new ArrayDeque<>(); // STACK: nodes whose children are not yet read
    stack.push(root);
    Map<Integer, Integer> map = new HashMap<>(); // MAP: value -> its parent's value
    putOnce(map, root.val, 0); // BASE: the root has no parent
    while (!stack.isEmpty()) {
      TreeNode node = stack.poll();
      if (isNotNull(node.right)) {
        putOnce(map, node.right.val, node.val); // RECORD: the right child's parent
        stack.push(node.right);
      }
      if (isNotNull(node.left)) {
        putOnce(map, node.left.val, node.val); // RECORD: the left child's parent
        stack.push(node.left);
      }
    }
    return map;
  }

  private static void putOnce(Map<Integer, Integer> map, int child, int parent) {
    if (map.put(child, parent) != null) { // GUARD: a second entry would hide the first
      throw new IllegalArgumentException(DUPLICATE);
    }
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    root.left.left.left = newNode(8);
    root.left.right.right = newNode(9);
    root.right.right.left = newNode(10);
    // [], [1], [1], [2, 1], [2, 1], [3, 1], [3, 1], [4, 2, 1], [5, 2, 1], [7, 3, 1]
    for (int key = 1; key <= 10; key++) {
      System.out.println(ancestors(root, key));
    }
  }
}
