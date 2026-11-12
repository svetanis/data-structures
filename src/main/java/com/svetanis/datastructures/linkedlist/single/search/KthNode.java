package com.svetanis.datastructures.linkedlist.single.search;

import static com.svetanis.datastructures.linkedlist.single.Nodes.fromList;
import static com.svetanis.datastructures.linkedlist.single.Nodes.print;

import java.util.Arrays;

import com.svetanis.datastructures.linkedlist.single.ListNode;

// Kth Node from the Head
//
// Input: head -- the first node of a singly linked list, or null; k -- how many steps to take
// from the head. Returns the node k steps from the head, counting from 0, so k = 0 is the head
// itself, or null when k is negative or the list has k nodes or fewer. Not a LeetCode problem,
// so there are no constraints: any list length, any int k.
//
// The one idea: a node knows only the node after it, so the only way to reach the k-th node is
// to stand on the head and step forward k times. curr is the node we stand on and count is how
// many steps curr has taken, so curr is the node at index count. Stepping stops when count
// reaches k, and curr is the answer, or when curr falls off the end, which means the list has
// fewer than k + 1 nodes and the answer is null.
//
// Time: O(min(k, n)) -- one step per count, and never past the end
// Space: O(1) -- two variables
//
// Siblings: KthToLastRecursive and KthToLastTwoPointers count from the end of the list instead.

public final class KthNode {

  public static ListNode kthNode(ListNode head, int k) {
    if (head == null || k < 0) { // BASE: an empty list or a negative index has no such node
      return null;
    }

    int count = 0; // COUNT: steps curr has taken, so curr is the node at index count
    ListNode curr = head; // START on the head, index 0
    while (curr != null && count < k) { // STOP after k steps, or when the list runs out
      curr = curr.next; // STEP forward one node
      count++;
    }
    return curr; // RETURN: the k-th node, or null when the list ran out first
  }

  public static void main(String[] args) {
    ListNode head = fromList(Arrays.asList(50, 20, 15, 4, 10, 60));
    print(head); // 50 20 15 4 10 60
    System.out.println(kthNode(head, 0)); // 50
    System.out.println(kthNode(head, 4)); // 10
    System.out.println(kthNode(head, 5)); // 60
    System.out.println(kthNode(head, 6)); // null
  }
}
