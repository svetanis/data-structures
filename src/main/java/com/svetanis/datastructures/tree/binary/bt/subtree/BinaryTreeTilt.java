package com.svetanis.datastructures.tree.binary.bt.subtree;

import static com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node.newNode;

import java.util.concurrent.atomic.AtomicInteger;

import com.svetanis.datastructures.tree.binary.model.mutable.primitive.Node;

// 563. Binary Tree Tilt

public final class BinaryTreeTilt {

	public static int tilt(Node root) {
		AtomicInteger sum = new AtomicInteger(0);
		dfs(root, sum);
		return sum.get();
	}
	
	private static int dfs(Node root, AtomicInteger sum) {
		if(root == null) {
			return 0;
		}
	  int left = dfs(root.left, sum);
		int right = dfs(root.right, sum);
		int tilt = Math.abs(left - right);
		sum.addAndGet(tilt);
		return root.data + left + right;
	}

	public static void main(String[] args) {
		Node root = newNode(1);
		root.left = newNode(2);
		root.right = newNode(3);
		System.out.println(tilt(root)); // 1
		
		Node root1 = newNode(4);
		root1.left = newNode(2);
		root1.right = newNode(9);
		root1.left.left = newNode(3);
		root1.left.right = newNode(5);
		root1.right.right = newNode(7);
		System.out.println(tilt(root1)); // 15
	}
}
