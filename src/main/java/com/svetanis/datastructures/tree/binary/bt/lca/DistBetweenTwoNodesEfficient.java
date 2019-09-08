package com.svetanis.datastructures.tree.binary.bt.lca;

import static com.svetanis.datastructures.tree.binary.bt.Level.level;
import static com.svetanis.datastructures.tree.binary.bt.lca.LcaBtNoParentBottomUp.lca;
import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Find the dist between two keys in BT, 
// no parent pointers are given. 
// Distance between two nodes is the min num of edges 
// to be traversed to reach one node from other.

public final class DistBetweenTwoNodesEfficient {

  public static int dist(Node root, Node p, Node q) {
    Node lca = lca(root, p, q);
    // dist from lca to node p
    int distP = level(lca, p.data, 0);
    // dist from lca to node q
    int distQ = level(lca, q.data, 0);
    // dist between p and q
    return distP + distQ;
  }

  public static void main(String[] args) {
    Node root = newNode(1);
    root.left = newNode(2);
    root.right = newNode(3);
    root.left.left = newNode(4);
    root.left.right = newNode(5);
    root.right.left = newNode(6);
    root.right.right = newNode(7);
    root.right.left.right = newNode(8);

    Node node4 = root.left.left;
    Node node5 = root.left.right;
    Node node6 = root.right.left;
    Node node3 = root.right;
    Node node2 = root.left;
    Node node8 = root.right.left.right;

    System.out.println("Dist(4,5): " + dist(root, node4, node5));
    System.out.println("Dist(4,6): " + dist(root, node4, node6));
    System.out.println("Dist(3,4): " + dist(root, node3, node4));
    System.out.println("Dist(2,4): " + dist(root, node2, node4));
    System.out.println("Dist(8,5): " + dist(root, node8, node5));
  }
}
