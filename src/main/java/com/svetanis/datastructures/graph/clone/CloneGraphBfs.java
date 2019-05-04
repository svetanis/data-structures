package com.svetanis.datastructures.graph.clone;

import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.datastructures.graph.clone.CloneGraphDfs.bfs;
import static com.svetanis.java.base.collect.Maps.checkedGet;
import static com.svetanis.java.base.collect.Maps.checkedPut;

import java.util.Map;
import java.util.Queue;

public final class CloneGraphBfs {

  public static Node clone(Node src) {
    Map<Node, Node> map = newHashMap();
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
    Node node1 = new Node(1);
    Node node2 = new Node(2);
    Node node3 = new Node(3);
    node1.children.add(node2);
    node1.children.add(node3);
    bfs(node1);
    Node clone = clone(node1);
    bfs(clone);
  }

}
