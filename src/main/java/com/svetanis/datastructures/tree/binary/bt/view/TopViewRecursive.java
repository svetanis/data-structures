package com.svetanis.datastructures.tree.binary.bt.view;

import static com.google.common.collect.Maps.newTreeMap;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.java.base.Pairs.left;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Lists.transform;
import static com.svetanis.java.base.utils.Print.print;

import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.java.base.Pair;

public final class TopViewRecursive {

  public static ImmutableList<Integer> topView(Node root) {
    // Time Complexity: O(n log n)

    // base case
    if (root == null) {
      return newList();
    }

    Map<Integer, Pair<Integer, Integer>> map = newTreeMap();
    bottomView(root, 0, 0, map);
    return transform(map.values(), left());
  }

  private static void bottomView(Node root, int dist, int level, 
                      Map<Integer, Pair<Integer, Integer>> map) {
    if (root == null) {
      return;
    }

    if (!map.containsKey(dist) || level < map.get(dist).getRight()) {
      map.put(dist, Pair.build(root.data, level));
    }
    bottomView(root.left, dist - 1, level + 1, map);
    bottomView(root.right, dist + 1, level + 1, map);
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    print(topView(root)); // 4 2 1 3 7

    Node root2 = newNode(1);
    root2.left = newNode(2);
    root2.right = newNode(3);
    root2.left.right = newNode(4);
    root2.left.right.right = newNode(5);
    root2.left.right.right.right = newNode(6);
    print(topView(root2)); // 2 1 3 6
  }
}