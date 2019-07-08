package com.svetanis.datastructures.linkedlist.single.sum;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import com.svetanis.datastructures.linkedlist.single.Node;

public final class AddOneRecursive {

  public static Node addOne(Node head) {
    int carry = addWithCarry(head);
    if (carry > 0) {
      Node node = new Node(carry);
      node.next = head;
      return node;
    }
    return head;
  }

  private static int addWithCarry(Node head) {
    if (head == null) {
      return 1;
    }
    int res = head.data + addWithCarry(head.next);
    head.data = res % 10;
    return res / 10;
  }

  public static void main(String[] args) {
    Node head = fromList(newArrayList(1, 9, 9, 9));
    print(head);
    head = addOne(head);
    print(head);
  }
}
