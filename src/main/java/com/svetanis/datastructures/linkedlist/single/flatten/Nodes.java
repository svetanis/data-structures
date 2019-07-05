package com.svetanis.datastructures.linkedlist.single.flatten;

public final class Nodes {

  public static Node push(Node head, int data) {
    Node node = new Node(data);
    node.down = head;
    head = node;
    return head;
  }


  public static Node fromArray(int[] a) {
    int n = a.length;
    Node head = null;
    Node curr = null;
    for (int i = 0; i < n; ++i) {
      if (head == null) {
        head = new Node();
        curr = head;
      } else {
        curr.next = new Node();
        curr = curr.next;
      }
      curr.data = a[i];
      curr.next = null;
      curr.down = null;
    }
    return head;
  }

  public static void printDown(Node node) {
    while (node != null) {
      System.out.print(node + " ");
      node = node.down;
    }
    System.out.println();
  }

  public static void printNext(Node node) {
    while (node != null) {
      System.out.print(node + " ");
      node = node.next;
    }
    System.out.println();
  }

}
