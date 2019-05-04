package com.svetanis.datastructures.graph.clone;

import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.java.base.collect.Maps.checkedGet;
import static com.svetanis.java.base.collect.Maps.checkedPut;

import java.util.Map;
import java.util.Queue;

public final class CloneGraphWithConnectedComponents {

  public static Graph clone(Graph g) {
    Map<Node, Node> map = newHashMap();
    for (Node node : g.getNodes()) {
      if (map.get(node) == null) {
        clone(node, map);
      }
    }
    Graph cloned = new Graph();
    for (Node node : map.values()) {
      cloned.addNode(node);
    }
    return cloned;
  }

  public static Node clone(Node src, Map<Node, Node> map) {
    Queue<Node> queue = newLinkedList();
    queue.offer(src);
    checkedPut(map, src, new Node(src.data));
    while (!queue.isEmpty()) {
      Node node = queue.poll();
      Node copy = map.get(node);
      if (copy == null) {
        copy = new Node(node.data);
        checkedPut(map, node, copy);
      }
      for (Node child : node.children) {
        Node clone = map.get(child);
        if (clone == null) {
          queue.offer(child);
          clone = new Node(child.data);
          checkedPut(map, child, clone);
        }
        copy.children.add(clone);
      }
    }
    return checkedGet(map, src);
  }

  public static void main(String[] args) {
  }

}
