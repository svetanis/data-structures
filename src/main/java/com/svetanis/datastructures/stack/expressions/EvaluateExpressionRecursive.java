package com.svetanis.datastructures.stack.expressions;

import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.newList;
import static java.lang.Character.isDigit;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.StringUtils.containsNone;

import java.util.List;

import com.google.common.collect.ImmutableList;

// given an expression containing digits and operations
// find all possible ways in which the expression can be
// evaluated by grouping the numbers and operators 
// using parentheses

public final class EvaluateExpressionRecursive {
	// Time Complexity: O(n * 2^n)
	// Space Complexity: O(n * 2^n)

	public static ImmutableList<Integer> generate(String s) {
		List<Integer> list = newArrayList();
		char[] operations = { '+', '-', '*' };
		if (containsNone(s, operations)) {
			list.add(parseInt(s));
		} else {
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (!isDigit(c)) {
					List<Integer> left = generate(s.substring(0, i));
					List<Integer> right = generate(s.substring(i + 1));
					for (int l : left) {
						for (int r : right) {
							if (c == '+') {
								list.add(l + r);
							} else if (c == '-') {
								list.add(l - r);
							} else if (c == '*') {
								list.add(l * r);
							}
						}
					}
				}
			}
		}
		return newList(list);
	}

	public static void main(String[] args) {
		System.out.println(generate("1+2*3"));
		System.out.println(generate("2*3-4-5"));
	}
}