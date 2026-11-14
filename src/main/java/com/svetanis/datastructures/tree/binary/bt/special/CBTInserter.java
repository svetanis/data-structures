package com.svetanis.datastructures.tree.binary.bt.special;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// 919. Complete Binary Tree Inserter
//
// Input: CBTInserter(root) -- the root of a complete binary tree; insert(val) -- adds a node holding
// val so that the tree stays complete, and returns the value of the new node's parent;
// get_root() -- returns the root. A tree is complete when every level except possibly the last is
// full, and the last level has its nodes as far left as possible. On LeetCode: the starting tree has
// 1 to 1000 nodes, 0 <= Node.val <= 5000, and there are at most 10^4 calls to insert and get_root.
//
// The one idea: number the nodes level by level, left to right, starting at 0. In a complete tree
// the node at position i has its children at positions 2i + 1 and 2i + 2, so the parent of position
// p is (p - 1) / 2. Keep the nodes in a list in that order. The next node to add goes at position
// size, so its parent is the list entry at (size - 1) / 2: it takes the parent's left child if that
// is free, otherwise the right one. The list is only added to, so the order stays correct.
//
// Time: O(n) for the constructor, which reads every node once; O(1) for insert and get_root.
// Space: O(n) -- the list of nodes, and the queue used while building it.

public final class CBTInserter {

  private List<TreeNode> nodes; // NODES: every node, level by level, left to right

  public CBTInserter(TreeNode root) {
    this.nodes = new ArrayList<>();
    Deque<TreeNode> dq = new ArrayDeque<>();
    dq.offer(root);
    while (!dq.isEmpty()) {
      int size = dq.size();
      for (int i = 0; i < size; i++) {
        TreeNode node = dq.poll();
        nodes.add(node); // LIST: this level's nodes, left to right
        if (node.left != null) {
          dq.offer(node.left);
        }
        if (node.right != null) {
          dq.offer(node.right);
        }
      }
    }
  }

  public int insert(int val) {
    int pid = (nodes.size() - 1) / 2; // PARENT: the position of the new node's parent
    TreeNode parent = nodes.get(pid);
    TreeNode node = new TreeNode(val);
    nodes.add(node); // LIST: the new node goes last
    if (parent.left == null) {
      parent.left = node; // ATTACH: the left slot first
    } else {
      parent.right = node;
    }
    return parent.val; // RETURN: the parent's value
  }

  public TreeNode get_root() {
    return nodes.get(0); // RETURN: the root is always position 0
  }

  public static void main(String[] args) {
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(2);
    CBTInserter inserter = new CBTInserter(root);
    System.out.println(inserter.insert(3)); // 1
    System.out.println(inserter.insert(4)); // 2
    TreeNode top = inserter.get_root();
    System.out.println(top.right.val); // 3 -- the root's right child
    System.out.println(top.left.left.val); // 4 -- the left child's left child
  }
}
