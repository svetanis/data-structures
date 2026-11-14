package com.svetanis.datastructures.tree.binary.bt.diagonal;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.bt.view.Item;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Diagonal Traversal of a Binary Tree
//
// Input: the root of a binary tree. Draw it so that a left child hangs one step down-left of its
// parent and a right child stays on the parent's diagonal line. Returns the node values grouped
// by diagonal, the diagonal through the root first; inside a diagonal, nodes come in the order
// they are met walking down from the root and then right.
//
// The one idea: one diagonal is a node followed by its chain of right children. So a queue holds
// the first node of each diagonal that is still to be done, paired with its diagonal number. Take
// one, walk down its right chain adding every value to that diagonal's list, and for every node
// on the chain queue its left child as the first node of the next diagonal (number + 1). The
// queue hands out diagonals in order, so a new diagonal number is always the next free slot in
// the result list.
//
// Time: O(n) -- each node is walked once and queued at most once.
// Space: O(n) -- the queue and the result; the queue holds at most one waiting node per diagonal.
//
// Siblings: DiagonalTraversalRecursive gets the same lists with a preorder recursion that adds
// one to the diagonal number going left.

public final class DiagonalTraversalIterative {

  public static List<List<Integer>> traverse(TreeNode root) {
    List<List<Integer>> lists = new ArrayList<>();
    if (isNull(root)) {
      return lists; // BASE: no nodes, no diagonals
    }
    Queue<Item> queue = new ArrayDeque<>();
    queue.offer(new Item(root, 0)); // START: the root begins diagonal 0
    while (!queue.isEmpty()) {
      Item item = queue.poll();
      TreeNode node = item.node;
      int dist = item.hd;
      if (dist == lists.size()) {
        lists.add(new ArrayList<>()); // OPEN: the first node of a new diagonal
      }
      while (isNotNull(node)) { // WALK: the right chain stays on this diagonal
        lists.get(dist).add(node.val); // TAKE this node into its diagonal
        if (isNotNull(node.left)) {
          queue.offer(new Item(node.left, dist + 1)); // QUEUE: first node of the next diagonal
        }
        node = node.right;
      }
    }
    return lists;
  }

  public static void main(String[] args) {
    TreeNode root = newNode(8);
    root.left = newNode(3);
    root.right = newNode(10);
    root.left.left = newNode(1);
    root.left.right = newNode(6);
    root.right.right = newNode(14);
    root.left.right.left = newNode(4);
    root.left.right.right = newNode(7);
    root.right.right.left = newNode(13);
    System.out.println(traverse(root)); // [[8, 10, 14], [3, 6, 7, 13], [1, 4]]
    System.out.println(traverse(null)); // []
  }
}
