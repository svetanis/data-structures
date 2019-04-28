package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.java.base.collect.Maps.checkedGet;
import static com.svetanis.java.base.collect.Maps.checkedPut;
import static com.svetanis.java.base.collect.Maps.newMap;
import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class BinaryTreeFromInPreOrder {

  public static Node construct(List<Integer> pre, List<Integer> in) {
    Map<Integer, Integer> map = indexMap(in);
    return construct(pre, 0, pre.size() - 1, in, 0, in.size() - 1, map);
  }

  private static Node construct(List<Integer> pre, int preStart, int preEnd, List<Integer> in, int inStart, int inEnd,
      Map<Integer, Integer> map) {

    if (preStart > preEnd || inStart > inEnd) {
      return null;
    }

    Node node = newNode(pre.get(preStart));
    int root = checkedGet(map, node.data);
    int left = root - inStart; // size of left subtree

    node.left = construct(pre, preStart + 1, preStart + left, in, inStart, root - 1, map);
    node.right = construct(pre, preStart + left + 1, preEnd, in, root + 1, inEnd, map);
    return node;
  }

  private static Map<Integer, Integer> indexMap(List<Integer> list) {
    Map<Integer, Integer> map = newHashMap();
    for (int i = 0; i < list.size(); i++) {
      checkedPut(map, list.get(i), i);
    }
    return newMap(map);
  }

  public static void main(String[] args) {
    List<Integer> in = asList(4, 2, 5, 1, 3, 6);
    List<Integer> pre = asList(1, 2, 4, 5, 3, 6);
    Node root = construct(pre, in);
    inOrder(root);
  }
}
