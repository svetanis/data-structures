package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Find the lexicographically smallest str that starts at root and ends at leaf in BT

public final class LexicographicallySmallestRootToLeafPath {

  public static String minPath(Node node) {
    return minPath(node, "");
  }

  private static String minPath(Node root, String str) {
    // Time complexity: O(n)

    if (isNull(root)) {
      return str;
    }
    char c = (char) (root.data + 'a');
    str += c;
    if (root.left != null && root.right != null) {
      if (root.left.data < root.right.data) {
        return minPath(root.left, str);
      } else if (root.right.data < root.left.data) {
        return minPath(root.right, str);
      } else {
        String left = minPath(root.left, str);
        String right = minPath(root.right, str);
        return left.compareTo(right) < 0 ? left : right;
      }
    } else if (root.left != null) {
      return minPath(root.left, str);
    } else if (root.right != null) {
      return minPath(root.right, str);
    } else {
      return str;
    }
  }

  public static void main(String[] args) {
    Node root = newNode(0);
    root.left = newNode(1);
    root.right = newNode(2);
    root.left.left = newNode(3);
    root.left.right = newNode(4);
    root.right.left = newNode(3);
    root.right.right = newNode(4);

    inOrder(root);
    System.out.println();

    System.out.println(minPath(root));
  }
}
