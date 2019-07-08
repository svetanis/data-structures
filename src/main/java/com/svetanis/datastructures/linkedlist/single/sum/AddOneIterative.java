package com.svetanis.datastructures.linkedlist.single.sum;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;
import static com.svetanis.datastructures.linkedlist.single.reverse.ReverseIterative.reverse;

import com.svetanis.datastructures.linkedlist.single.Node;

public final class AddOneIterative {

  public static Node addOne(Node head) {
    head = reverse(head);
    head = addOneUtil(head);
    return reverse(head);
  }

  private static Node addOneUtil(Node head) {

    Node result = head;
    Node node = head;
    int sum = 0;
    int carry = 1;

    while (head != null) {
      sum = carry + head.data;
      carry = (sum >= 10) ? 1 : 0;
      sum = sum % 10;
      head.data = sum;
      node = head;
      head = head.next;
    }

    if (carry > 0) {
      node.next = new Node(carry);
    }
    return result;
  }

  public static void main(String[] args) {
    Node head = fromList(newArrayList(1, 9, 9, 9));
    print(head);
    head = addOne(head);
    print(head);
  }
}
