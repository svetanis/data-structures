package com.svetanis.datastructures.tree.binary.bt.subtree;

import java.util.ArrayList;
import java.util.Arrays;
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
// The one idea: one call answers two questions about one node's subtree: what do its values add up
// to, and how many of its nodes survive? A node's sum is its own value plus its children's sums,
// and its surviving count is 1 plus its children's counts. If the sum is 0 the whole subtree is
// deleted, so the count becomes 0. The sum is still returned as 0, which is right: a deleted child
// adds nothing to its parent's sum. A removed subtree never needs a second look, because removing
// a subtree whose sum is 0 leaves its parent's sum unchanged.
//
// Time: O(n) -- the children lists are built in one pass, then each node is visited once.
// Space: O(n) -- the children lists; the recursion is as deep as the tree.
//
// Known limit: the recursion goes one call deeper per level, so a chain of about 10^4 nodes (each
// node the only child of the one before) overflows the default Java stack. It happens only on a
// very deep input; DeleteTreeNodesIterative is the same method with an explicit loop.
//
// Siblings: DeleteTreeNodesIterative -- the same sums and counts, computed without recursion.

public final class DeleteTreeNodes {

  private int[] values;
  private List<Integer>[] tree; // TREE: tree[i] lists the children of node i

  public int deleteTreeNodes(int nodes, int[] parent, int[] value) {
    this.values = value;
    this.tree = new List[nodes];
    Arrays.setAll(tree, i -> new ArrayList<>());
    for (int i = 1; i < nodes; i++) {
      tree[parent[i]].add(i); // BUILD: node i is a child of parent[i]
    }
    return dfs(0)[1]; // RETURN: the surviving count of the whole tree
  }

  // returns { sum of the subtree's values, number of nodes that survive in it }
  private int[] dfs(int index) {
    int sum = values[index];
    int count = 1;
    for (int child : tree[index]) {
      int[] result = dfs(child); // WALK: the child's sum and surviving count
      sum += result[0];
      count += result[1];
    }
    if (sum == 0) {
      count = 0; // DELETE: the whole subtree sums to 0, nothing in it survives
    }
    return new int[] { sum, count };
  }

  public static void main(String[] args) {
    DeleteTreeNodes dtn = new DeleteTreeNodes();

    int[] parent1 = { -1, 0, 0, 1, 2, 2, 2 };
    int[] value1 = { 1, -2, 4, 0, -2, -1, -1 };
    System.out.println(dtn.deleteTreeNodes(7, parent1, value1)); // 2

    int[] parent2 = { -1, 0, 0, 1, 2, 2, 2 };
    int[] value2 = { 1, -2, 4, 0, -2, -1, -2 };
    System.out.println(dtn.deleteTreeNodes(7, parent2, value2)); // 6
  }
}
