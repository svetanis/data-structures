package com.svetanis.datastructures.tree.binary.bt.cousins;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.bt.Level.level;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class PrintCousinsRecursive {

  public static ImmutableList<Integer> cousins(Node root, Node node) {
    // Time complexity: O(n)

    int level = level(root, node);
    List<Integer> list = newArrayList();
    givenLevel(root, node, level, list);
    return newList(list);
  }

  private static void givenLevel(Node root, Node node, 
                        int level, List<Integer> list) {

    if (isNull(root) || level < 2) {
      return;
    }

    if (level == 2) {
      if (root.left == node || root.right == node) {
        return;
      }
      if (isNotNull(root.left)) {
        list.add(root.left.data);
      }
      if (isNotNull(root.right)) {
        list.add(root.right.data);
      }
    } else if (level > 2) {
      givenLevel(root.left, node, level - 1, list);
      givenLevel(root.right, node, level - 1, list);
    }
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.left.right.right = newNode(15);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    root.right.left.right = newNode(8);
    print(cousins(root, root.left.right));
  }
}
