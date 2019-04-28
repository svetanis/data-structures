package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.java.base.collect.Maps.checkedGet;
import static com.svetanis.java.base.collect.Maps.checkedPut;
import static com.svetanis.java.base.collect.Maps.newMap;
import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class BinaryTreeFromInPostOrder {

  public static Node construct(List<Integer> in, List<Integer> post) {
    Map<Integer, Integer> map = indexMap(in);
    return construct(in, 0, in.size() - 1, post, 0, post.size() - 1, map);
  }

  private static Node construct(List<Integer> in, int inStart, int inEnd, List<Integer> post, int postStart,
      int postEnd, Map<Integer, Integer> map) {

    if (inStart > inEnd || postStart > postEnd) {
      return null;
    }

    Node node = new Node(post.get(postEnd));
    int root = checkedGet(map, node.data);
    int left = root - inStart; // size of left subtree

    node.left = construct(in, inStart, root - 1, post, postStart, postStart + left - 1, map);
    node.right = construct(in, root + 1, inEnd, post, postStart + left, postEnd - 1, map);
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
    List<Integer> in = asList(4, 8, 2, 5, 1, 6, 3, 7);
    List<Integer> post = asList(8, 4, 5, 2, 6, 7, 3, 1);
    Node root = construct(in, post);
    inOrder(root);
  }
}
