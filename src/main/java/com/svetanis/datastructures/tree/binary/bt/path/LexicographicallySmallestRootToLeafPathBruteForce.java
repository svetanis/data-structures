package com.svetanis.datastructures.tree.binary.bt.path;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isLeaf;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;
import static com.svetanis.java.base.utils.Wrapper.newGenWrapper;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;
import com.svetanis.java.base.utils.Wrapper;

// Find the lexicographically smallest str 
// that starts at root and ends at leaf in BT

public final class LexicographicallySmallestRootToLeafPathBruteForce {

  public static String minPath(Node node) {
    Wrapper<String> min = newGenWrapper("~");
    minPath(node, new StringBuilder(), min);
    return min.value;
  }

  private static void minPath(Node root, StringBuilder sb, Wrapper<String> min) {
    // Time complexity: O(n)

    if (isNull(root)) {
      return;
    }
    char c = (char) (root.data + 'a');
    sb.append(c);

    if (isLeaf(root)) {
      String str = sb.toString();
      if (str.compareTo(min.value) < 0) {
        min.value = str;
      }
    }

    minPath(root.left, sb, min);
    minPath(root.right, sb, min);
    sb.deleteCharAt(sb.length() - 1);
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
