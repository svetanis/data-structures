package com.svetanis.datastructures.tree.binary.bt.traversal.lot;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLists;

import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class LotLineByLineHashing {

  public static ImmutableList<ImmutableList<Integer>> traverse(Node root) {
    // time complexity: O(n)

    Multimap<Integer, Integer> mm = ArrayListMultimap.create();
    preorder(root, 1, mm);

    List<ImmutableList<Integer>> lists = newArrayList();
    for (int key : mm.keySet()) {
      lists.add(newList(mm.get(key)));
    }
    return newList(lists);
  }

  private static void preorder(Node root, int level, 
                      Multimap<Integer, Integer> mm) {
    if (isNull(root)) {
      return;
    }
    mm.put(level, root.data);
    preorder(root.left, level + 1, mm);
    preorder(root.right, level + 1, mm);
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);
    printLists(traverse(root));
    System.out.println();
  }
}
