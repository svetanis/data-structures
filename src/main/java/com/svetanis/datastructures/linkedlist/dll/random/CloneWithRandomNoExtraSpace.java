package com.svetanis.datastructures.linkedlist.dll.random;

public final class CloneWithRandomNoExtraSpace {

  public static Node clone(Node head) {
    // Time Complexity: O(n)
    // Space Complexity: O(1)

    // 1. insert copy of the node
    // after every node in the list 
    Node curr = head;
    while(curr != null) {
      Node temp = curr.next;
      curr.next = new Node(curr.data);
      curr.next.next = temp;
      curr = temp;
    }
    
    curr = head;
    
    // 2. adjust random pointers
    // of the newly added nodes
    while(curr != null) {
      if(curr.next != null) {
        curr.next.rand = (curr.rand != null) ? curr.rand.next : curr.rand;
      }
      curr = (curr.next != null) ? curr.next.next : curr.next;
    }
    
    // 3. separate original and copied lists
    Node given = head;
    Node clone = head.next;
    Node cloneHead = clone;
    while(given != null && clone != null) {
      given.next = (given.next != null) ? given.next.next : given.next;
      clone.next = (clone.next != null) ? clone.next.next : clone.next;
      given = given.next;
      clone = clone.next;
    }
    return cloneHead;
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
