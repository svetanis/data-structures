package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.utils.Arrays.toList;
import static com.svetanis.java.base.utils.Print.print;

import java.util.Stack;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Similar to preorder. Two differences :
//   1) Where pre-order prints, post-order puts the value into another stack to reverse the order
//   2) Order of left and right nodes is swapped

public final class PostOrderTwoStacks {

  public static ImmutableList<Node> postOrder(Node root) {
    if (isNull(root)) {
      return newList();
    }

    Stack<Node> stack = new Stack<>();
    Stack<Node> out = new Stack<>();
    stack.push(root);
    while (!stack.isEmpty()) {
      Node node = stack.pop();
      out.push(node);

      if (isNotNull(node.left)) {
        stack.push(node.left);
      }
      if (isNotNull(node.right)) {
        stack.push(node.right);
      }
    }
    return toList(out);
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);
    print(postOrder(root));
  }
}
