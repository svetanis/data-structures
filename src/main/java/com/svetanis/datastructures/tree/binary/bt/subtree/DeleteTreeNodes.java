package com.svetanis.datastructures.tree.binary.bt.subtree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 1273. Delete Tree Nodes

public final class DeleteTreeNodes {
  // Time Complexity: O(n)
  // Space Complexity: O(n)

  private int[] values;
  private List<Integer>[] tree;

  public int deleteTreeNodes(int nodes, int[] parent, int[] value) {
    this.values = value;
    this.tree = new List[nodes];
    Arrays.setAll(tree, i -> new ArrayList<>());
    for (int i = 1; i < nodes; i++) {
      tree[parent[i]].add(i);
    }
    return dfs(0)[1];
  }

  private int[] dfs(int index) {
    int sum = values[index];
    int count = 1;
    for (int child : tree[index]) {
      int[] result = dfs(child);
      sum += result[0];
      count += result[1];
    }
    if (sum == 0) {
      count = 0;
    }
    return new int[] { sum, count };
  }

  public static void main(String[] args) {
    DeleteTreeNodes dtn = new DeleteTreeNodes();

    int[] parent1 = { -1, 0, 0, 1, 2, 2, 2 };
    int[] value1 = { 1, -2, 4, 0, -2, -1, -1 };
    System.out.println(dtn.deleteTreeNodes(7, parent1, value1));

    int[] parent2 = { -1, 0, 0, 1, 2, 2, 2 };
    int[] value2 = { 1, -2, 4, 0, -2, -1, -2 };
    System.out.println(dtn.deleteTreeNodes(7, parent2, value2));
  }
}
