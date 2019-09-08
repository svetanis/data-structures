package com.svetanis.datastructures.tree.binary.bt.diagonal;

import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class DiagonalSumRecursive {

  public static ImmutableList<Integer> diagonalSum(Node root) {
    Map<Integer, Integer> map = newHashMap();
    diagonalSum(root, 0, map);
    return newList(map.values());
  }

  private static void diagonalSum(Node root, int diagonal, 
                                Map<Integer, Integer> map) {
    
    if (isNull(root)) {
      return;
    }
    map.putIfAbsent(diagonal, 0);
    map.put(diagonal, map.get(diagonal) + root.data);
    diagonalSum(root.left, diagonal + 1, map);
    diagonalSum(root.right, diagonal, map);
  }

  public static void main(String[] args) {
    // 9 is sum of 1, 3 and 5.
    // 19 is sum of 2, 6, 4 and 7.
    // 42 is sum of 9, 10, 11 and 12.

    Node root = newNode(1);
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
    print(diagonalSum(root));
  }
}
