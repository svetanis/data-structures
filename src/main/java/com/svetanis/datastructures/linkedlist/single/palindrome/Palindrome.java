package com.svetanis.datastructures.linkedlist.single.palindrome;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.reverse.ReverseIterative.reverse;
import static com.svetanis.datastructures.linkedlist.single.search.MiddleNode.middle;

import com.svetanis.datastructures.linkedlist.single.Node;

// given the head of a Singly LinkedList
// check if the SLL is a palindrom or not

public final class Palindrome {
  // Time Complexity: O(n)

  public static boolean isPalindrome(Node head) {
    if (head == null || head.next == null) {
      return true;
    }

    Node mid = middle(head);
    Node reversed = reverse(mid);
    Node copy = reversed;

    while (head != null && reversed != null) {
      if (head.data != reversed.data) {
        break;
      }
      head = head.next;
      reversed = reversed.next;
    }

    reverse(copy);
    return head == null || reversed == null;
  }

  public static void main(String[] args) {
    // 0->1->2->1->0
    Node head = fromList(newArrayList(0, 1, 2, 1, 0));
    System.out.println(isPalindrome(head));
    
    Node head1 = fromList(newArrayList(0, 1, 3, 0));
    System.out.println(isPalindrome(head1));
    
  }
}
