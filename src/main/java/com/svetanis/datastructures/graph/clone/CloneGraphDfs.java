package com.svetanis.datastructures.graph.clone;

import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class CloneGraphDfs {

  public static Node clone(Node src) {
    return clone(src, newHashMap());
  }

  private static Node clone(Node src, Map<Integer, Node> map) {
    Node dst = new Node(src.data);
    map.put(dst.data, dst);
    for (Node child : src.children) {
      Node clone = map.get(child.data);
      if (clone == null) {
        clone = clone(child, map);
      }
      dst.children.add(clone);
    }
    return dst;
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

  public static void bfs(Node src) {
    Set<Node> visit = newHashSet();
    Queue<Node> queue = newLinkedList();
    queue.add(src);
    visit.add(src);
    while (!queue.isEmpty()) {
      Node node = queue.poll();
      System.out.println(node.data);
      for (Node neighbour : node.children) {
        if (!visit.contains(neighbour)) {
          queue.add(neighbour);
          visit.add(neighbour);
        }
      }
    }
    System.out.println();
  }
}
