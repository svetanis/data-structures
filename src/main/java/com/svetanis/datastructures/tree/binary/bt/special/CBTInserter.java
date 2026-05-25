package com.svetanis.datastructures.tree.binary.bt.special;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 919. Complete Binary Tree Inserter

public final class CBTInserter {
  // Time Complexity: O(n)
  // Space Complexity: O(n)

  private List<Node> nodes;

  public CBTInserter(Node root) {
    this.nodes = new ArrayList<>();
    Deque<Node> dq = new ArrayDeque<>();
    dq.offer(root);
    while (!dq.isEmpty()) {
      int size = dq.size();
      for (int i = 0; i < size; i++) {
        Node node = dq.poll();
        nodes.add(node);
        if (node.left != null) {
          dq.offer(node.left);
        }
        if (node.right != null) {
          dq.offer(node.right);
        }
      }
    }
  }

  public int insert(int val) {
    int pid = (nodes.size() - 1) / 2;
    Node parent = nodes.get(pid);
    Node node = new Node(val);
    nodes.add(node);
    if (parent.left == null) {
      parent.left = node;
    } else {
      parent.right = node;
    }
    return parent.data;
  }

  public Node get_root() {
    return nodes.get(0);
  }

  public static void main(String[] args) {}
}
