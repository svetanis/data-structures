package com.svetanis.datastructures.linkedlist.single.rearrange;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;
import com.svetanis.java.base.Pair;

public final class SwapTwoNodes {

  public static Node swap(Node head, int x, int y) {

    if (x == y) {
      return head;
    }

    Pair<Node, Node> pairX = search(head, x);
    Node prevX = pairX.getLeft();
    Node currX = pairX.getRight();

    Pair<Node, Node> pairY = search(head, y);
    Node prevY = pairY.getLeft();
    Node currY = pairY.getRight();

    if (currX == null || currY == null) {
      return head;
    }
    
    // if x is not head
    if (prevX != null) {
      prevX.next = currY;
    } else { // if x is head
      head = currY;
    }
    
    // if y is not head
    if (prevY != null) {
      prevY.next = currX;
    } else {// if y is head
      head = currX;
    }

    // swap
    Node temp = currY.next;
    currY.next = currX.next;
    currX.next = temp;
    return head;
  }

  private static Pair<Node, Node> search(Node head, int x) {
    Node curr = head;
    Node prev = null;
    while (curr != null && curr.data != x) {
      prev = curr;
      curr = curr.next;
    }
    return Pair.build(prev, curr);
  }

  public static void main(String[] args) {
    // two non-adjacent nodes
    Node head1 = fromList(newArrayList(10, 15, 12, 13, 20, 14));
    print(head1);
    swap(head1, 12, 20);
    print(head1);
    System.out.println();

    // y is tail
    Node head2 = fromList(newArrayList(10, 15, 12, 13, 20, 14));
    print(head2);
    swap(head2, 12, 14);
    print(head2);
    System.out.println();

    // two adjacent nodes
    Node head3 = fromList(newArrayList(10, 15, 12, 13, 20, 14));
    print(head3);
    swap(head3, 12, 13);
    print(head3);
    System.out.println();

    // x is head
    Node head4 = fromList(newArrayList(10, 15, 12, 13, 20, 14));
    print(head4);
    Node swapped = swap(head4, 15, 20);
    print(swapped);
    System.out.println();
  }
}
