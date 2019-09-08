package com.svetanis.datastructures.tree.binary.bt.lca;

import static com.svetanis.datastructures.tree.binary.bt.Level.level;
import static com.svetanis.datastructures.tree.binary.bt.lca.LcaBtNoParentBottomUp.lca;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// Find the dist between two keys in BT, 
// no parent pointers are given. 
// Distance between two nodes is the min num of edges 
// to be traversed to reach one node from other.

public final class DistBetweenTwoNodes {

  public static int dist(Node root, Node p, Node q) {
    
    // dist from root to node p
    int distP = level(root, p.data);
    
    // dist from root to node q
    int distQ = level(root, q.data);

    Node lca = lca(root, p, q);
    // dist from root to node lca of p and q
    int distLCA = level(root, lca.data);

    // dist between p and q
    return distP + distQ - 2 * distLCA;
  }

  public static void main(String[] args) {
    Node root = new Node(1);
    root.left = new Node(2);
    root.right = new Node(3);
    root.left.left = new Node(4);
    root.left.right = new Node(5);
    root.right.left = new Node(6);
    root.right.right = new Node(7);
    root.right.left.right = new Node(8);

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
