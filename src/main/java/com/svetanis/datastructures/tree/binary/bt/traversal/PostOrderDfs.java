package com.svetanis.datastructures.tree.binary.bt.traversal;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNotNull;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Sets.isAbsent;
import static com.svetanis.java.base.utils.Print.print;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class PostOrderDfs {

  public static ImmutableList<Node> postorder(Node root) {
    Node node = root;
    Set<Node> set = newHashSet();
    List<Node> list = newArrayList();
    while (isNotNull(node) && isAbsent(set, node)) {
      if (isNotNull(node.left) && isAbsent(set, node.left)) {
        node = node.left;
      } else if (isNotNull(node.right) && isAbsent(set, node.right)) {
        node = node.right;
      } else {
        list.add(node);
        set.add(node);
        node = root;
      }
    }
    return newList(list);
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.right = newNode(6);
    print(postorder(root));
  }
}
