package com.svetanis.datastructures.tree.binary.bt.vertical;

import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Maps.newMap;
import static com.svetanis.java.base.utils.Print.print;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given BT, find vertical sum of the nodes that are in same vertical line.

public final class VerticalSumHashing {

  public static ImmutableMap<Integer, Integer> verticalSum(Node root) {

    // Time Complexity: O(n)
    // base case

    Map<Integer, Integer> map = newHashMap();
    verticalSum(root, 0, map);
    return newMap(map);
  }

  private static void verticalSum(Node node, int hDist, 
                            Map<Integer, Integer> map) {
    if (isNull(node)) {
      return;
    }

    // store the values in hash map for left subtree
    verticalSum(node.left, hDist - 1, map);

    // update vertical sum for horizontal distance of this node
    int prev = (map.get(hDist) == null) ? 0 : map.get(hDist);
    map.put(hDist, prev + node.data);

    // store the values in hash map for right subtree
    verticalSum(node.right, hDist + 1, map);
  }

  public static void main(String[] args) {
    // 4, 2, 12, 3 and 7

    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    print(verticalSum(root));
  }
}
