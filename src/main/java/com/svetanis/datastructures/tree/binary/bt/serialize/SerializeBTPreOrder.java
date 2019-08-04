package com.svetanis.datastructures.tree.binary.bt.serialize;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.inOrder;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.StringTokenizer;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class SerializeBTPreOrder {

  private static final String MARKER = "-1";

  public static String serialize(Node root) {
    StringBuilder sb = new StringBuilder();
    serialize(root, sb);
    return sb.toString();
  }

  private static void serialize(Node root, StringBuilder sb) {
    // Time complexity: O(n)

    if (root == null) {
      sb.append(MARKER + " ");
      return;
    }
    sb.append(root.data + " ");
    serialize(root.left, sb);
    serialize(root.right, sb);
  }

  public static Node deserialize(String str) {
    if (isBlank(str)) {
      return null;
    }
    StringTokenizer st = new StringTokenizer(str, " ");
    return deserialize(st);
  }

  private static Node deserialize(StringTokenizer st) {
    if (!st.hasMoreTokens()) {
      return null;
    }
    String value = st.nextToken();
    if (value.equals(MARKER)) {
      return null;
    }
    Node root = newNode(parseInt(value));
    root.left = deserialize(st);
    root.right = deserialize(st);
    return root;
  }

  public static void main(String[] args) {
    Node root = newNode(20);
    root.left = newNode(8);
    root.left.left = newNode(4);
    root.left.right = newNode(12);
    root.left.right.left = newNode(10);
    root.left.right.right = newNode(14);
    inOrder(root);
    System.out.println();
    String serialized = serialize(root);
    System.out.println(serialized);
    Node deserialized = deserialize(serialized);
    inOrder(deserialized);
  }
}
