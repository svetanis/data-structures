package com.svetanis.datastructures.tree.binary.bt.path;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.intersection;
import static com.google.common.collect.Sets.newLinkedHashSet;
import static com.svetanis.datastructures.tree.binary.bt.path.RootToGivenNodePath.path;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.java.base.collect.Lists.newList;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a Binary Tree of distinct nodes and a pair of nodes. 
// The task is to find and print the path 
// between the two given nodes in the binary tree.

public final class PathBetweenAnyTwoNodes {

  public static ImmutableList<Node> pathBetweenNodes(Node root, int x, int y) {
    // Time Complexity: O(n)

    List<Node> p1 = newArrayList();
    path(root, x, p1);

    List<Node> p2 = newArrayList();
    path(root, y, p2);

    Set<Node> set1 = newLinkedHashSet(p1);
    Set<Node> set2 = newLinkedHashSet(p2);
    int i = intersection(set1, set2).size();
    List<Node> list1 = i > 0 ? p1.subList(0, p1.size() - (i - 1)) : p1;
    List<Node> list2 = i > 0 ? p2.subList(0, p2.size() - i) : p2;

    List<Node> list = newArrayList();
    list.addAll(list1);
    list.addAll(list2);
    return newList(list);
  }

  public static void main(String[] args) {
    Node root = newNode(0);
    root.left = newNode(1);
    root.right = newNode(2);
    root.left.left = newNode(3);
    root.left.right = newNode(4);
    root.right.left = newNode(5);
    root.right.right = newNode(6);
    root.left.left.left = newNode(7);
    root.left.right.left = newNode(8);
    root.left.right.right = newNode(9);

    System.out.println(pathBetweenNodes(root, 7, 4));
  }
}
