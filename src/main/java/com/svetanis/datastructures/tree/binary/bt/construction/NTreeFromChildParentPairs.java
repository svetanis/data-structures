package com.svetanis.datastructures.tree.binary.bt.construction;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Multimaps.invertFrom;
import static com.svetanis.java.base.collect.Lists.getUnique;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Lists.transform;
import static com.svetanis.java.base.collect.Multimaps.newMultimap;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.svetanis.java.base.Pair;

public final class NTreeFromChildParentPairs {

  public static Node construct(List<Pair<Integer, Integer>> pairs) {
    Map<Integer, Node> map = newHashMap();
    Node root = getRoot(pairs);
    map.put(root.data, root);
    for (Pair<Integer, Integer> pair : pairs) {
      int left = pair.getLeft(); // node id
      Node child = map.getOrDefault(left, new Node(left));
      map.put(left, child);

      int right = pair.getRight(); // parent id
      Node parent = map.getOrDefault(right, new Node(right));
      map.put(right, parent);

      parent.children.add(child);
      child.parent = parent;
    }
    return root;
  }

  public static void main(String[] args) {
    List<Pair<Integer, Integer>> list = pairs();
    Node root = construct(list);
    print(preorder(root));
  }

  public static ImmutableList<Integer> preorder(Node root) {
    List<Node> preOrder = newArrayList();
    buildPreOrder(root, preOrder);
    return newList(transform(preOrder, i -> i.data));
  }

  private static void buildPreOrder(Node node, List<Node> preOrder) {
    preOrder.add(node);
    for (Node child : node.children) {
      buildPreOrder(child, preOrder);
    }
  }

  private static ImmutableMultimap<Integer, Integer> asMulti(List<Pair<Integer, Integer>> pairs) {
    Multimap<Integer, Integer> mm = ArrayListMultimap.create();
    for (Pair<Integer, Integer> pair : pairs) {
      mm.put(pair.getLeft(), pair.getRight());
    }
    return newMultimap(mm);
  }

  public static Node getRoot(List<Pair<Integer, Integer>> pairs) {
    Multimap<Integer, Integer> mm = asMulti(pairs);
    Multimap<Integer, Integer> inverted = invertFrom(mm, ArrayListMultimap.create());
    int data = getUnique(inverted.get(-1), "root");
    Node root = new Node(data);
    return root;
  }

  public static ImmutableList<Pair<Integer, Integer>> pairs() {
    List<Pair<Integer, Integer>> list = newArrayList();
    list.add(Pair.build(10, -1));
    list.add(Pair.build(9, 10));
    list.add(Pair.build(8, 10));
    list.add(Pair.build(7, 10));
    list.add(Pair.build(6, 9));
    list.add(Pair.build(5, 9));
    list.add(Pair.build(4, 8));
    list.add(Pair.build(3, 8));
    list.add(Pair.build(2, 7));
    list.add(Pair.build(1, 7));
    return newList(list);
  }

  private static class Node {
    private int data;
    private Node parent;
    private List<Node> children;

    public Node(int data) {
      this.data = data;
      this.parent = null;
      this.children = newArrayList();
    }
  }
}
