package com.svetanis.datastructures.tree.binary.bt.convert;

import static com.google.common.collect.Sets.newTreeSet;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.Iterator;
import java.util.Set;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class ConvertBtToBst {

  public static void btToBst(Node root) {
    Set<Integer> set = newTreeSet();
    toTreeSet(root, set);
    toBst(root, set.iterator());
  }

  private static void toTreeSet(Node root, Set<Integer> set) {
    if (isNull(root)) {
      return;
    }
    toTreeSet(root.left, set);
    set.add(root.data);
    toTreeSet(root.right, set);
  }

  private static void toBst(Node root, Iterator<Integer> iter) {
    if (isNull(root)) {
      return;
    }
    toBst(root.left, iter);
    root.data = iter.next();
    toBst(root.right, iter);
  }

  public static void main(String[] args) {
    Node root = newNode(8);
    root.left = newNode(3);
    root.right = newNode(5);
    root.left.left = newNode(10);
    root.left.right = newNode(2);
    root.right.left = newNode(4);
    root.right.right = newNode(6);

    inOrder(root);
    System.out.println();
    btToBst(root);
    inOrder(root);
  }
}
