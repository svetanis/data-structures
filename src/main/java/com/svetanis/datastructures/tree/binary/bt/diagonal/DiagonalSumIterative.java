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

// Diagonal Sum of a Binary Tree
//
// Input: the root of a binary tree. Draw it so that a left child hangs one step down-left of its
// parent and a right child stays on the parent's diagonal line. Returns the sum of the node
// values on each diagonal, the first diagonal (the one through the root) first.
//
// The one idea: one diagonal is a node followed by its chain of right children. So a queue holds
// the first node of each diagonal that is still to be done, paired with its diagonal number. Take
// one, walk down its right chain adding every value to that diagonal's sum, and for every node on
// the chain queue its left child as the first node of the next diagonal (number + 1). The queue
// hands out diagonals in order, so a new diagonal number is always the next free slot in the
// result list.
//
// Time: O(n) -- each node is walked once and queued at most once.
// Space: O(n) -- the queue and the result; the queue holds at most one waiting node per diagonal.
//
// Siblings: DiagonalSumRecursive reaches the same sums with a preorder recursion that adds one
// to the diagonal number going left; the iterative form here needs no recursion.

public final class DiagonalSumIterative {

  public static List<Integer> diagonalSum(TreeNode root) {
    List<Integer> sums = new ArrayList<>();
    if (isNull(root)) {
      return sums; // BASE: no nodes, no diagonals
    }

    Queue<Item> queue = new ArrayDeque<>();
    queue.offer(new Item(root, 0)); // START: the root begins diagonal 0
    while (!queue.isEmpty()) {
      Item item = queue.poll();
      TreeNode node = item.node;
      int dist = item.hd;
      if (dist == sums.size()) {
        sums.add(0); // OPEN: the first node of a new diagonal
      }
      while (isNotNull(node)) { // WALK: the right chain stays on this diagonal
        sums.set(dist, sums.get(dist) + node.val); // ADD to this diagonal's sum

        if (isNotNull(node.left)) {
          queue.offer(new Item(node.left, dist + 1)); // QUEUE: first node of the next diagonal
        }
        node = node.right;
      }
    }
    return sums;
  }

  public static void main(String[] args) {
    // 9 is sum of 1, 3 and 5.
    // 19 is sum of 2, 6, 4 and 7.
    // 42 is sum of 9, 10, 11 and 12.

    TreeNode root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(9);
    root.left.right = newNode(6);
    root.right.left = newNode(4);
    root.right.right = newNode(5);
    root.left.left.right = newNode(10);
    root.left.right.left = newNode(11);
    root.right.left.left = newNode(12);
    root.right.left.right = newNode(7);
    System.out.println(diagonalSum(root)); // [9, 19, 42]
    System.out.println(diagonalSum(null)); // []
  }
}
