package com.svetanis.datastructures.tree.binary.bt.path;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Maps.checkedPut;
import static com.svetanis.java.base.collect.Maps.newMap;
import static com.svetanis.java.base.utils.Print.print;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class AncestorsIterative {

  public static ImmutableList<Integer> ancestors(Node root, int k) {

    if (isNull(root)) {
      return newList();
    }

    List<Integer> list = newArrayList();
    Map<Integer, Integer> map = ancestors(root);
    int key = map.get(k);
    while (key != 0) {
      list.add(key);
      key = map.get(key);
    }
    return newList(list);
  }

  private static ImmutableMap<Integer, Integer> ancestors(Node root) {

    // create a stack to hold ancestors
    Deque<Node> stack = new ArrayDeque<>();
    stack.push(root);

    Map<Integer, Integer> map = newHashMap();
    checkedPut(map, root.data, 0);

    while (!stack.isEmpty()) {
      Node node = stack.poll();
      if (isNotNull(node.right)) {
        checkedPut(map, node.right.data, node.data);
        stack.push(node.right);
      }
      if (isNotNull(node.left)) {
        checkedPut(map, node.left.data, node.data);
        stack.push(node.left);
      }
    }
    return newMap(map);
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    root.left.left.left = newNode(8);
    root.left.right.right = newNode(9);
    root.right.right.left = newNode(10);

    for (int key = 1; key <= 10; key++) {
      print(ancestors(root, key));
    }
  }
}
