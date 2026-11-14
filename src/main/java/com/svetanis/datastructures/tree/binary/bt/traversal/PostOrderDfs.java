package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 145. Binary Tree Postorder Traversal
//
// Input: root -- the root of a binary tree, possibly null. Returns the nodes in postorder: the
// whole left subtree, the whole right subtree, then the node. On LeetCode: 0 <= nodes <= 100,
// -100 <= Node.val <= 100; values can repeat, which is why the reported nodes are remembered by
// identity and not by equality.
//
// The one idea: no stack and no recursion -- the set of already reported nodes does the remembering.
// Start at the root and keep stepping to the left child if it is not reported yet, else to the
// right child if it is not reported yet. When both children are reported (or missing), this node is
// the next one in postorder: report it, add it to the set, and start again from the root. A node
// is reported only after both of its subtrees are, which is the postorder rule.
//
// Time: O(n * h) -- each node reported costs one walk from the root of at most h steps; O(n^2) on a
// skewed tree.
// Space: O(n) -- the set of reported nodes and the result list.
//
// Siblings: PostOrderRecursive prints the same order with the call stack; PostOrderTwoStacks gets
// it in O(n) time by reversing a root, right, left walk.

public final class PostOrderDfs {

  public static List<TreeNode> postorder(TreeNode root) {
    TreeNode node = root;
    Set<TreeNode> set = Collections.newSetFromMap(new IdentityHashMap<>()); // DONE: reported nodes, by identity
    List<TreeNode> list = new ArrayList<>();
    while (isNotNull(node) && isAbsent(set, node)) {
      if (isNotNull(node.left) && isAbsent(set, node.left)) { // LEFT: not reported yet, go down
        node = node.left;
      } else if (isNotNull(node.right) && isAbsent(set, node.right)) { // RIGHT: left is done, go down
        node = node.right;
      } else {
        list.add(node); // REPORT: both sides done
        set.add(node); // DONE: never walk into it again
        node = root; // RESTART: from the top
      }
    }
    return list;
  }

  private static boolean isAbsent(Set<TreeNode> set, TreeNode node) {
    return !set.contains(node);
  }

  public static void main(String[] args) {
    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);
    System.out.println(postorder(root)); // [4, 5, 2, 6, 3, 1]
  }
}
