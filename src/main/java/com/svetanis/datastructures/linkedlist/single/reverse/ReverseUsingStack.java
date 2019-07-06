package com.svetanis.datastructures.linkedlist.single.reverse;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import java.util.Stack;

import com.svetanis.datastructures.linkedlist.single.Node;

public final class ReverseUsingStack {

  public static Node reverse(Node head) {
    Stack<Node> stack = new Stack<>();
    Node node = head;
    while (node.next != null) {
      stack.push(node);
      node = node.next;
    }

    head = node;
    while (!stack.isEmpty()) {
      node.next = stack.pop();
      node = node.next;
    }
    node.next = null;
    return head;
  }

  public static void main(String[] agrs) {
    Node head = fromList(newArrayList(4, 3, 2, 1));
    print(head);
    Node reversed = reverse(head);
    print(reversed);
  }
}
