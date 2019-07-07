package com.svetanis.datastructures.linkedlist.dll.random;

import static com.google.common.collect.Maps.newHashMap;
import static com.svetanis.java.base.collect.Maps.newMap;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public final class CloneWithRandomHashing {

  public static Node clone(Node head) {
    // Time Complexity: O(n)
    // Space Complexity: O(n)

    Map<Node, Node> map = asMap(head);
    Node curr = head;
    while (curr != null) {
      Node copy = map.get(curr);
      copy.next = map.get(curr.next);
      copy.rand = map.get(curr.rand);
      curr = curr.next;
    }
    return map.get(head);
  }

  private static ImmutableMap<Node, Node> asMap(Node head) {
    Map<Node, Node> map = newHashMap();
    Node curr = head;
    while (curr != null) {
      Node copy = new Node(curr.data);
      map.put(curr, copy);
      curr = curr.next;
    }
    return newMap(map);
  }

  public static void main(String[] args) {
    Node head = new Node(1);
    head.next = new Node(2);
    head.next.next = new Node(3);
    head.next.next.next = new Node(4);
    head.next.next.next.next = new Node(5);

    // Setting up random references
    head.rand = head.next.next;
    head.next.rand = head.next.next.next;
    head.next.next.rand = head.next.next.next.next;
    head.next.next.next.next.rand = head.next;

    // making a clone of the original linked list
    Node clone = clone(head);

    System.out.println("original linked list: ");
    print(head);

    System.out.println("cloned linked list: ");
    print(clone);
  }

  private static void print(Node head) {
    Node curr = head;
    while (curr != null) {
      Node rand = curr.rand;
      int randomData = (rand != null) ? rand.data : -1;
      System.out.print("[" + curr.data + ", " + randomData + "] ");
      curr = curr.next;
    }
    System.out.println();
  }

  // original linked list:
  // [1, 3] [2, 4] [3, 5] [4, -1] [5, 2]
  // cloned linked list:
  // [1, 3] [2, 4] [3, 5] [4, -1] [5, 2]

}
