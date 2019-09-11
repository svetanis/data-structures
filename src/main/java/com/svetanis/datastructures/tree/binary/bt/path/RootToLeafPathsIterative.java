package com.svetanis.datastructures.tree.binary.bt.path;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.printLists;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Given a binary tree, write an iterative algorithm
// to print leaf to root path for every leaf node of BT.

public final class RootToLeafPathsIterative {
  
  private static ImmutableList<ImmutableList<Integer>> paths(Node root){
    // Time Complexity: O(n log n)
    
    List<ImmutableList<Integer>> lists = newArrayList();
    Deque<Node> dq = new ArrayDeque<>();
    dq.add(root);
    Map<Node, Node> map = newHashMap();
    map.put(root, null);
    while(!dq.isEmpty()) {
      Node node = dq.poll();
      if(isLeaf(node)) {
        lists.add(path(node, map));
      }
      if(isNotNull(node.right)) {
        dq.add(node.right);
        map.put(node.right, node);
      }
      if(isNotNull(node.left)) {
        dq.add(node.left);
        map.put(node.left, node);
      }
    }
    return newList(lists);
  }
  
  private static ImmutableList<Integer> path(Node leaf, Map<Node, Node> map){
    Node node = leaf;
    List<Integer> list = newArrayList();
    while(isNotNull(map.get(node))) {
      list.add(node.data);
      node = map.get(node);
    }
    list.add(node.data);
    return newList(list);
  }

  public static void main(String[] args) {
    // 10->8->3
    // 10->8->5
    // 10->2->2
    Node root = newNode(10);
    root.left = newNode(8);
    root.right = newNode(2);
    root.left.left = newNode(3);
    root.left.right = newNode(5);
    root.right.left = newNode(2);

    inOrder(root);
    System.out.println();

    // print Root-to-leaf path
    System.out.println("Root-to-leaf paths: ");
    printLists(paths(root));
    System.out.println();
  }
}
