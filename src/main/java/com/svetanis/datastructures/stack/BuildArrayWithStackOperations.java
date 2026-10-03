package com.svetanis.datastructures.stack;

import java.util.ArrayList;
import java.util.List;

// 1441. Build an Array With Stack Operations
//
// Input: the target array a, in increasing order, and n. The numbers 1, 2, 3, ... are offered
// one at a time. Returns the list of "Push" and "Pop" operations that leaves exactly a on the
// stack.
//
// Every number from 1 up to the last target is pushed once. A number that is not in a is
// popped straight back off, so nothing is ever left on top of it and the stack only ever
// keeps target values. Numbers after the last target are never needed, which is why n is not
// read.

public final class BuildArrayWithStackOperations {
	// Time Complexity: O(n), one step for each number from 1 to the last target, at most n
	// Space Complexity: O(n) for the list of operations

	public static List<String> buildArray(int n, int[] a) {
		int current = 0;
		List<String> operations = new ArrayList<>();
		for (int target : a) {
			while (++current < target) {
				operations.add("Push");
				operations.add("Pop"); // not in a: taken straight back off
			}
			operations.add("Push");
		}
		return operations;
	}

	public static void main(String[] args) {
		int[] a1 = { 1, 3 };
		System.out.println(buildArray(3, a1)); // push, push, pop, push

		int[] a2 = { 1, 2, 3 };
		System.out.println(buildArray(3, a2)); // push, push, push

		int[] a3 = { 1, 2 };
		System.out.println(buildArray(4, a3)); // push, push
	}
}
