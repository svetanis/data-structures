package com.svetanis.datastructures.tree.binary.bt.view;

import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Maps.newTreeMap;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.Map;
import java.util.Queue;

import com.google.common.collect.ImmutableList;

public final class BottomViewBT {

  public static ImmutableList<Integer> bottomView(Node root) {
    // Time Complexity: O(n)

    // base case
    if (root == null) {
      return newList();
    }

    // initialize hDist = 0 for root element
    int hDist = 0;

    Map<Integer, Integer> map = newTreeMap();
    Queue<Node> queue = newLinkedList();

    // horizontal distance of root is 0
    root.hDist = hDist;
    queue.offer(root);

    // standard BFS or level order traversal loop
    while (!queue.isEmpty()) {
      // remove the front item and get its details
      Node node = queue.poll();
      hDist = node.hDist;

      // put the dequeued node to TreeMap
      // having key at horizontal distance
      // every time we find a node having
      // same horizontal dist we need to
      // replace the data in the map
      map.put(hDist, node.data);

      // enqueue left and right children of current node
      if (node.left != null) {
        node.left.hDist = hDist - 1;
        queue.offer(node.left);
      }

      if (node.right != null) {
        node.right.hDist = hDist + 1;
        queue.offer(node.right);
      }
    }
    return newList(map.values());
  }

  public static void main(String[] args) {
    Node root = new Node(20);
    root.left = new Node(8);
    root.right = new Node(22);
    root.left.left = new Node(5);
    root.left.right = new Node(3);
    root.left.right.left = new Node(10);
    root.left.right.right = new Node(14);
    root.right.right = new Node(25);

    System.out.println("Input BT: ");
    inOrder(root);
    System.out.println();

    System.out.println("Bottom View of BT: ");
    print(bottomView(root));
    System.out.println();

    Node root2 = new Node(20);
    root2.left = new Node(8);
    root2.right = new Node(22);
    root2.left.left = new Node(5);
    root2.left.right = new Node(3);
    root2.left.right.left = new Node(10);
    root2.left.right.right = new Node(14);
    root2.right.left = new Node(4);
    root2.right.right = new Node(25);

    System.out.println("Input BT: ");
    inOrder(root2);
    System.out.println();

    System.out.println("Bottom View of BT: ");
    print(bottomView(root2));
    System.out.println();
  }

  private static void inOrder(Node node) {
    if (node == null) {
      return;
    }
    inOrder(node.left);
    System.out.print(node.data + " ");
    inOrder(node.right);
  }

  private static class Node {
    private int data;
    private int hDist;
    private Node left;
    private Node right;

    public Node(int data) {
      this.data = data;
      this.hDist = 0;
      this.left = null;
      this.right = null;
    }

    @Override
    public String toString() {
      return Integer.toString(data);
    }
  }
}