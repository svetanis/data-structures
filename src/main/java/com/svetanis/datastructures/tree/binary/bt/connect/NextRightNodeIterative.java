package com.svetanis.datastructures.tree.binary.bt.connect;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.TreeNode;

// Next Right Node of a Given Key (not a LeetCode problem)
//
// Input: root -- the root of a binary tree, possibly null; k -- the key to look for. Keys are
// assumed distinct: with repeated keys this file answers for the first k in level order, while
// NextRightNodeRecursive answers for the first k in preorder. Returns the node immediately to the
// right of the node holding k on the same level, or empty when k is the last node of its level or
// is not in the tree.
//
// The one idea: take the tree one level at a time. Before a level is processed the queue holds that
// level's nodes left to right, and size counts how many of them are still to be taken out. When the
// node holding k comes out, the node now at the front of the queue is its right neighbour -- unless
// size says it was the last one of its level, when the front belongs to the next level and the
// answer is empty.
//
// Time: O(n) -- at worst every node is queued and taken out once.
// Space: O(n) -- the queue holds a whole level, up to about n / 2 nodes.
//
// Siblings: NextRightNodeRecursive finds it in a preorder walk, remembering only the level of k, so
// its extra space is O(h).

public final class NextRightNodeIterative {

  public static Optional<TreeNode> nextRight(TreeNode root, int k) {
    if (isNull(root)) { // BASE: empty tree
      return Optional.empty();
    }

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
      int size = queue.size(); // LEVEL: nodes of this level still to be taken out

      while (size > 0) {
        TreeNode node = queue.poll(); // TAKE: next node of this level, left to right
        size--;

        if (node.val == k) {
          if (size == 0) { // LAST: nothing to its right on this level
            return Optional.empty();
          }
          return Optional.of(queue.peek()); // NEIGHBOUR: the next node of the same level
        }

        if (isNotNull(node.left)) { // QUEUE: children go behind the level, left first
          queue.offer(node.left);
        }

        if (isNotNull(node.right)) {
          queue.offer(node.right);
        }
      }
    }
    return Optional.empty(); // MISSING: k is not in the tree
  }

  public static void main(String[] args) {
    TreeNode root = newNode(10);
    root.left = newNode(2);
    root.right = newNode(6);
    root.left.left = newNode(8);
    root.left.right = newNode(4);
    root.right.right = newNode(5);

    System.out.println(nextRight(root, 10)); // Optional.empty
    System.out.println(nextRight(root, 2)); // Optional[6]
    System.out.println(nextRight(root, 6)); // Optional.empty
    System.out.println(nextRight(root, 5)); // Optional.empty
    System.out.println(nextRight(root, 8)); // Optional[4]
    System.out.println(nextRight(root, 4)); // Optional[5]
  }
}
