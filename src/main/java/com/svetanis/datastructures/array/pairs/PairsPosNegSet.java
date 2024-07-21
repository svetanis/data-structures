package com.svetanis.datastructures.array.pairs;

import static com.google.common.base.Predicates.not;
import static com.google.common.collect.Lists.newArrayList;
import static com.svetanis.java.base.collect.Lists.filter;
import static com.svetanis.java.base.collect.Lists.newList;
import static com.svetanis.java.base.collect.Sets.newSet;

import java.util.List;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.svetanis.java.base.Pair;

// given an array of n integers,
// find all pairs of positive and
// negative value of a number
// that exists in the array

public final class PairsPosNegSet {
	// Time Complexity: O(n)

	public static ImmutableList<Pair<Integer, Integer>> pairs(List<Integer> list) {
	  Predicate<Integer> pep = i -> i >= 0;
	  List<Integer> positive = filter(list, pep);
	  Set<Integer> negative = newSet(filter(list, not(pep)));
	  List<Pair<Integer, Integer>> pairs = newArrayList();
	  for(int pos : positive) {
		  int neg = -1 * pos;
		  if(negative.contains(neg)) {
			  pairs.add(Pair.build(pos, neg));
		  }
	  }
	  return newList(pairs);
	}

	public static void main(String[] args) {
		List<Integer> list = newArrayList(4, 8, 9, -4, 1, -1, -8, -9);
		System.out.println(pairs(list));
	}
}
