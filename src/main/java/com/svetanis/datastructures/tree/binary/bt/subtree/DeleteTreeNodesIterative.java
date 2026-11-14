package com.svetanis.datastructures.tree.binary.bt.subtree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

// 1273. Delete Tree Nodes
//
// Input: nodes -- how many nodes the tree has, labelled 0 to nodes - 1 with node 0 the root;
// parent[i] -- the parent of node i (parent[0] is -1); value[i] -- the value of node i. Every
// subtree whose values add up to 0 is removed, the node itself and everything below it. Returns the
// number of nodes left. On LeetCode: 1 <= nodes <= 10^4, parent.length == value.length == nodes,
// -10^5 <= value[i] <= 10^5, and the parents describe a valid tree (a parent may have a larger
// label than its child).
//
// The one idea: for one node keep two numbers: what its subtree's values add up to (sum), and how
// many of its nodes survive (count). A node's sum is its own value plus its children's sums; its
// count is 1 plus its children's counts, or 0 if its sum is 0. A node needs its children finished
// first. A walk from the root, taking each node before its children, lists every parent before its
// children; going through that list backwards finishes every child before its parent, and each node
// hands its sum and count to its parent as it is finished.
//
// Time: O(n) -- the children lists are built in one pass, the list is walked forwards once, then
// backwards once.
// Space: O(n) -- the children lists, the walk order and the two arrays.
//
// Siblings: DeleteTreeNodes -- the same sums and counts by recursion; it overflows the default
// stack on a chain of about 10^4 nodes, this one does not.

public final class DeleteTreeNodesIterative {

  public int deleteTreeNodes(int nodes, int[] parent, int[] value) {
    List<Integer>[] tree = new List[nodes];
    Arrays.setAll(tree, i -> new ArrayList<>());
    for (int i = 1; i < nodes; i++) {
      tree[parent[i]].add(i); // BUILD: node i is a child of parent[i]
    }
    int[] order = new int[nodes]; // ORDER: every parent comes before its children
    int size = 0;
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(0);
    while (!stack.isEmpty()) {
      int index = stack.pop();
      order[size++] = index; // WALK: take the node, then its children
      for (int child : tree[index]) {
        stack.push(child);
      }
    }
    long[] sum = new long[nodes]; // SUM: what the subtree's values add up to
    int[] count = new int[nodes]; // COUNT: nodes of the subtree that survive
    for (int i = 0; i < nodes; i++) {
      sum[i] = value[i];
      count[i] = 1;
    }
    for (int k = nodes - 1; k >= 0; k--) { // FINISH: children before their parent
      int index = order[k];
      if (sum[index] == 0) {
        count[index] = 0; // DELETE: the whole subtree sums to 0
      }
      if (index != 0) {
        sum[parent[index]] += sum[index]; // HAND UP: the parent adds this subtree
        count[parent[index]] += count[index];
      }
    }
    return count[0]; // RETURN: the surviving count of the whole tree
  }

  public static void main(String[] args) {
    DeleteTreeNodesIterative dtn = new DeleteTreeNodesIterative();

    int[] parent1 = { -1, 0, 0, 1, 2, 2, 2 };
    int[] value1 = { 1, -2, 4, 0, -2, -1, -1 };
    System.out.println(dtn.deleteTreeNodes(7, parent1, value1)); // 2

    int[] parent2 = { -1, 0, 0, 1, 2, 2, 2 };
    int[] value2 = { 1, -2, 4, 0, -2, -1, -2 };
    System.out.println(dtn.deleteTreeNodes(7, parent2, value2)); // 6
  }
}
