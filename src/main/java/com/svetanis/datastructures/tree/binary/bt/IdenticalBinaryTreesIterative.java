package com.svetanis.datastructures.tree.binary.bt;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.ArrayDeque;
import java.util.Deque;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.java.base.Pair;

// Two trees are identical when they have same data and arrangement of data is also same.

public final class IdenticalBinaryTreesIterative {

  public static boolean identical(Node root1, Node root2) {
    // 1. both empty
    if (isNull(root1) && isNull(root2)) {
      return true;
    }
    // 2. one empty, one not
    if (isNull(root1) || isNull(root2)) {
      return false;
    }
    // 3. both non-emty, compare them:
    Deque<Pair<Node, Node>> stack = new ArrayDeque<>();
    stack.add(Pair.build(root1, root2));
    while (!stack.isEmpty()) {
      Pair<Node, Node> pair = stack.poll();
      Node node1 = pair.getLeft();
      Node node2 = pair.getRight();

      if (node1.data != node2.data) {
        return false;
      }

      if (isNotNull(node1.left) && isNotNull(node2.left)) {
        stack.add(Pair.build(node1.left, node2.left));
      } else if (isNotNull(node1.left) || isNotNull(node2.left)) {
        return false;
      }

      if (isNotNull(node1.right) && isNotNull(node2.right)) {
        stack.add(Pair.build(node1.right, node2.right));
      } else if (isNotNull(node1.right) || isNotNull(node2.right)) {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    Node tree1 = newNode(26);
    tree1.right = newNode(3);
    tree1.right.right = newNode(3);
    tree1.left = newNode(10);
    tree1.left.left = newNode(4);
    tree1.left.left.right = newNode(30);
    tree1.left.right = newNode(6);

    Node tree2 = newNode(10);
    tree2.left = newNode(4);
    tree2.right = newNode(6);
    tree2.left.right = newNode(30);

    System.out.println(identical(tree1, tree2));

    Node root1 = newNode(1);
    root1.left = newNode(2);
    root1.right = newNode(3);
    root1.left.left = newNode(4);
    root1.left.right = newNode(5);

    Node root2 = newNode(1);
    root2.left = newNode(2);
    root2.right = newNode(3);
    root2.left.left = newNode(4);
    root2.left.right = newNode(5);

    System.out.println(identical(root1, root2));
  }
}
