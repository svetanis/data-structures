package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Nodes.isNull;

import java.util.List;

import com.google.common.base.Joiner;
import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

public final class SubTreeInOrderPreOrder {

  public static boolean isSubTree(Node rootT, Node rootS) {
    // Time Complexity: O(n)

    // base case
    if (isNull(rootS)) {
      return true;
    }

    if (isNull(rootT)) {
      return false;
    }

    // store inorder traversal of T and S
    List<Character> inT = newArrayList();
    List<Character> inS = newArrayList();

    inorder(rootT, inT);
    inorder(rootS, inS);

    
    String str1 = Joiner.on("").join(inT);
    String str2 = Joiner.on("").join(inS);
    
    if (!str1.contains(str2)) {
      return false;
    }

    // store preorder traversal of T and S
    List<Character> preT = newArrayList();
    List<Character> preS = newArrayList();

    preorder(rootT, preT);
    preorder(rootS, preS);

    String str3 = Joiner.on("").join(preT);
    String str4 = Joiner.on("").join(preS);

    return str3.contains(str4);
  }

  private static void inorder(Node node, List<Character> list) {
    if (isNull(node)) {
      return;
    }
    inorder(node.left, list);
    char c = (char) node.data;
    list.add(c);
    inorder(node.right, list);
  }

  private static void preorder(Node node, List<Character> list) {
    if (isNull(node)) {
      return;
    }
    char c = (char) node.data;
    list.add(c);
    preorder(node.left, list);
    preorder(node.right, list);
  }

  public static void main(String[] args) {
    Node tree1 = newNode('z');
    tree1.left = newNode('x');
    tree1.right = newNode('e');
    tree1.left.left = newNode('a');
    tree1.left.right = newNode('b');
    tree1.right.right = newNode('k');
    tree1.left.left.right = newNode('c');

    Node tree2 = newNode('x');
    tree2.left = newNode('a');
    tree2.right = newNode('b');
    tree2.left.right = newNode('c');

    System.out.println(isSubTree(tree1, tree2));

    Node root1 = newNode('a');
    root1.left = newNode('b');
    root1.right = newNode('d');
    root1.left.left = newNode('c');
    root1.right.right = newNode('e');

    Node root2 = newNode('a');
    root2.left = newNode('b');
    root2.right = newNode('d');
    root2.left.left = newNode('c');

    System.out.println(isSubTree(root2, root1));
  }
}
