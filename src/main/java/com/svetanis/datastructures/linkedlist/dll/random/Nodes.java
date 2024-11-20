package com.svetanis.datastructures.linkedlist.dll.random;

public final class Nodes {

  // put data always at the head of linked list
  public Node insertAtHead(Node head, int val) {
    Node newNode = new Node(val);
    newNode.next = head;
    head = newNode;
    return head;
  }

  public int size(Node head) {
    int count = 0;
    Node curr = head;
    while (curr != null) {
      count++;
      curr = curr.next;
    }
    return count;
  }

  public static Node reverse(Node head) {
    if (head == null || head.next == null) {
      return head;
    }
    Node node = reverse(head.next);
    head.next.next = head;
    head.next = null;
    return node;
  }


  public static void print(Node head) {
    Node curr = head;
    while (curr != null) {
      System.out.print(curr + " ");
      curr = curr.next;
    }
    System.out.println();
  }


  public static void printRand(Node head) {
    Node curr = head;
    while (curr != null) {
      Node rand = curr.rand;
      int randomData = (rand != null) ? rand.val : -1;
      System.out.print(" Data = " + curr.val + ", Random data = " + randomData);
      curr = curr.next;
    }
    System.out.println();
  }

}
