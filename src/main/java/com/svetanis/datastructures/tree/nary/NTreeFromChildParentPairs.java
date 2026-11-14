package com.svetanis.datastructures.tree.nary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Build an N-ary tree from a list of (child, parent) pairs. Not a LeetCode problem.
//
// Input: pairs, one entry per node: the key is the node's id, the value is its parent's id, and
// the root's parent is -1. The pairs come in any order and ids are distinct. Returns the root
// of the tree, with every node's children in the order its pairs were listed.
//
// The one idea: one pair says one thing -- "this node hangs under that node". Keep a map from
// id to node, so that the first time an id is seen (as a child or as a parent) its node is
// made, and every later mention gets the same node back. A parent may be mentioned before its
// own pair is read; the map makes that harmless, because the node made for the mention is the
// node its own pair later finds. The root is the one node whose parent is -1.
//
// Time: O(n) -- one pass over the pairs for the root, one for the links, each map lookup O(1).
// Space: O(n) -- the map and the nodes.
//
// NaryNode is private to this file: it is mutable and carries a parent pointer, unlike the
// immutable Node in this package. The root's own pair (id, -1) links it to a placeholder node
// with id -1, which nothing reads.

public final class NTreeFromChildParentPairs {

  private static final String ROOT_COUNT = "expected exactly 1 element for root, but found %d instead";

  public static NaryNode construct(List<Map.Entry<Integer, Integer>> pairs) {
    Map<Integer, NaryNode> map = new HashMap<>(); // MAP: id -> its one node
    NaryNode root = getRoot(pairs);
    map.put(root.data, root);
    for (Map.Entry<Integer, Integer> pair : pairs) { // WALK: one pair, one link
      int left = pair.getKey(); // node id
      NaryNode child = map.getOrDefault(left, new NaryNode(left)); // MAKE: on first sight
      map.put(left, child);

      int right = pair.getValue(); // parent id
      NaryNode parent = map.getOrDefault(right, new NaryNode(right)); // MAKE on first mention
      map.put(right, parent);

      parent.children.add(child); // LINK: child under parent, in pair order
      child.parent = parent;
    }
    return root;
  }

  public static void main(String[] args) {
    List<Map.Entry<Integer, Integer>> list = pairs();
    NaryNode root = construct(list);
    System.out.println(preorder(root)); // [10, 9, 6, 5, 8, 4, 3, 7, 2, 1]
  }

  public static List<Integer> preorder(NaryNode root) {
    List<Integer> preOrder = new ArrayList<>();
    buildPreOrder(root, preOrder);
    return preOrder;
  }

  private static void buildPreOrder(NaryNode node, List<Integer> preOrder) {
    preOrder.add(node.data); // TAKE: the node before its children
    for (NaryNode child : node.children) { // WALK: children in order
      buildPreOrder(child, preOrder);
    }
  }

  public static NaryNode getRoot(List<Map.Entry<Integer, Integer>> pairs) {
    int count = 0; // COUNT: nodes whose parent is -1; exactly one is a tree
    int data = -1;
    for (Map.Entry<Integer, Integer> pair : pairs) {
      if (pair.getValue() == -1) { // the root has no parent
        data = pair.getKey();
        count++;
      }
    }
    if (count != 1) {
      throw new IllegalArgumentException(ROOT_COUNT.formatted(count));
    }
    return new NaryNode(data);
  }

  public static List<Map.Entry<Integer, Integer>> pairs() {
    List<Map.Entry<Integer, Integer>> list = new ArrayList<>();
    list.add(Map.entry(10, -1));
    list.add(Map.entry(9, 10));
    list.add(Map.entry(8, 10));
    list.add(Map.entry(7, 10));
    list.add(Map.entry(6, 9));
    list.add(Map.entry(5, 9));
    list.add(Map.entry(4, 8));
    list.add(Map.entry(3, 8));
    list.add(Map.entry(2, 7));
    list.add(Map.entry(1, 7));
    return list;
  }

  private static class NaryNode {
    private int data;
    private NaryNode parent;
    private List<NaryNode> children;

    public NaryNode(int data) {
      this.data = data;
      this.parent = null;
      this.children = new ArrayList<>();
    }
  }
}
