package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Level order between two levels
//
// Input: root of a binary tree and two level numbers low and high, the root being level 1 (not a
// LeetCode problem). Returns the nodes on the levels low .. high in level order (top level first,
// left to right). A range that reaches past the last level just stops there; an empty tree gives
// an empty list.
//
// The one idea: one queue entry is a node waiting to be listed, and the nodes come off the queue
// in level order. To know which level the front node is on, count how many nodes of the current
// level are still in the queue: size starts at the number of nodes on the level, drops by one per
// node taken off, and when it reaches 0 that level is finished; the queue then holds exactly the
// next level, so size is reset to the queue's size and the level number goes up by one. A node is
// listed only while low <= level <= high.
//
// Time: O(n) -- every node is queued and taken off once (it does not stop after level high).
// Space: O(n) -- the output list; the queue holds at most two levels at a time.

public final class LotBetweenGivenTwoLevelsQueue {

  public static List<TreeNode> traverse(TreeNode root, int low, int high) {
    if (isNull(root)) {
      return new ArrayList<>();
    }

    List<TreeNode> list = new ArrayList<>();
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    int level = 1;
    int size = queue.size(); // SIZE: nodes of the current level still in the queue
    while (!queue.isEmpty()) {
      TreeNode node = queue.poll(); // TAKE: the next node in level order
      if (level >= low && level <= high) { // LIST: only inside the wanted range
        list.add(node);
      }
      if (isNotNull(node.left)) {
        queue.offer(node.left); // QUEUE: the next level
      }
      if (isNotNull(node.right)) {
        queue.offer(node.right);
      }
      if (--size == 0) { // LEVEL DONE: the queue now holds exactly the next level
        size = queue.size();
        level++;
      }
    }
    return list;
  }

  public static void main(String[] args) {
    TreeNode root = newNode(20);
    root.left = newNode(8);
    root.right = newNode(22);
    root.left.left = newNode(4);
    root.left.right = newNode(12);
    root.left.right.left = newNode(10);
    root.left.right.right = newNode(14);
    System.out.println(traverse(root, 2, 4)); // [8, 22, 4, 12, 10, 14]
  }
}
