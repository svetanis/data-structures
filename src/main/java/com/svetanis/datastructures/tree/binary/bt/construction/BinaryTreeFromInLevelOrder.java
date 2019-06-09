package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.java.base.collect.Maps.checkedGet;
import static com.svetanis.java.base.collect.Maps.checkedPut;
import static com.svetanis.java.base.collect.Maps.newMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class BinaryTreeFromInLevelOrder {

  public static Node construct(List<Integer> in, List<Integer> level) {
    Map<Integer, Integer> map = indexMap(in);
    return construct(in, level, 0, in.size() - 1, map);
  }

  public static Node construct(List<Integer> in, List<Integer> level, 
                       int start, int end, Map<Integer, Integer> map) {
    if (start > end) {
      return null;
    }
    Node node = newNode(level.get(0));

    // if this node has no children then return
    if (start == end) {
      return node;
    }

    // else find the index of
    // this node in Inorder traversal
    int index = checkedGet(map, node.data);
    Set<Integer> set = newHashSet(in.subList(start, index));
    List<Integer> left = newArrayList();
    List<Integer> right = newArrayList();
    for (int i = 1; i < level.size(); i++) {
      if (set.contains(level.get(i))) {
        left.add(level.get(i));
      } else {
        right.add(level.get(i));
      }
    }

    // using index in Inorder traversal,
    // construct left and right subtrees
    node.left = construct(in, left, start, index - 1, map);
    node.right = construct(in, right, index + 1, end, map);
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
    List<Integer> in = newArrayList(4, 8, 10, 12, 14, 20, 22);
    List<Integer> level = newArrayList(20, 8, 22, 4, 12, 10, 14);
    Node root = construct(in, level);
    inOrder(root);
  }
}
