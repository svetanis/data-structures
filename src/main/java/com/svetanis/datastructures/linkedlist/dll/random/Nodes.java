package com.svetanis.datastructures.linkedlist.dll.random;

// Helpers for lists of this package's Node: insertAtHead, size, reverse (along next only;
// random pointers are not touched), print, and printRand, which prints each node with the
// value its random pointer aims at, -1 for none. insertAtHead and size are not static, so
// they need a Nodes object. LeetCode builds its input lists itself.
//
// reverse is recursive: the call reverses everything after head, and head is then hooked on
// behind it. It uses one stack frame per node, so it suits short lists.

public final class Nodes {

  private static final String RANDOM = " Data = %d, Random data = %d";

  // put data always at the head of linked list
  public Node insertAtHead(Node head, int val) {
    Node newNode = new Node(val);
    newNode.next = head; // LINK the new node in front of the old head
    head = newNode;
    return head; // RETURN the new head
  }

  public int size(Node head) {
    int count = 0;
    Node curr = head;
    while (curr != null) {
      count++; // COUNT one node
      curr = curr.next; // WALK
    }
    return count;
  }

  public static Node reverse(Node head) {
    if (head == null || head.next == null) {
      return head; // BASE: no node or one node is already reversed
    }
    Node node = reverse(head.next); // the last node of the list: the new head
    head.next.next = head; // FLIP: the node after head now points back at head
    head.next = null; // head becomes the last node
    return node;
  }

  public static void print(Node head) {
    Node curr = head;
    while (curr != null) {
      System.out.print(curr + " "); // PRINT the value
      curr = curr.next; // WALK
    }
    System.out.println();
  }

  public static void printRand(Node head) {
    Node curr = head;
    while (curr != null) {
      Node rand = curr.random;
      int randomData = (rand != null) ? rand.val : -1; // -1 stands for a null random
      System.out.print(RANDOM.formatted(curr.val, randomData)); // PRINT value and random value
      curr = curr.next; // WALK
    }
    System.out.println();
  }

}
