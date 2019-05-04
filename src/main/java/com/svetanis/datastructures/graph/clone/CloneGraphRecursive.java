package com.svetanis.datastructures.graph.clone;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.graph.clone.CloneGraphDfs.bfs;

public final class CloneGraphRecursive {

  public static void clone(Node src) {
    clone(src, new Node());
  }

  public static void clone(Node src, Node dst) {
    if (src != null) {
      dst.data = src.data;
    }

    if (src.children != null) {
      if (dst.children == null) {
        dst.children = newArrayList();
      }

      for (Node child : src.children) {
        Node clone = new Node();
        dst.children.add(clone);
        clone(child, clone);
      }
    }
  }

  public static void main(String[] args) {
    Node node1 = new Node(1);
    Node node2 = new Node(2);
    Node node3 = new Node(3);
    node1.children.add(node2);
    node1.children.add(node3);
    bfs(node1);
    clone(node1);
    bfs(node1);
  }

}
