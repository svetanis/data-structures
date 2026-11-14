package com.svetanis.datastructures.graph.unionfind;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 399. Evaluate Division
//
// Input: equations[i] = [A, B] with values[i] = A / B, and queries[j] = [C, D] asking C / D.
// On LeetCode: 1 <= equations.length <= 20, names are 1 to 5 lowercase letters or digits,
// 0.0 < values[i] <= 20.0, 1 <= queries.length <= 20, and the equations never contradict.
// Returns an answer per query, or -1.0 when it cannot be worked out: a name that appears in no
// equation (so x / x is -1.0 for such an x, while a / a is 1.0 for a known a), or two names with
// no chain of equations between them.
//
// The one idea: the same as EvaluateDivision399 -- one number per name, x divided by its parent,
// so that following parents to the root and multiplying gives x / root and any two names of one
// group divide as (C / root) / (D / root). What differs is the arrangement: the names are first
// mapped to int ids, and the weighted union-find is its own class, WeightedDisjointSet, so this
// class holds only the problem -- ids in, queries out. Both arrangements work; EvaluateDivision399
// keeps Map<String, String> parent and Map<String, Double> weight inside the problem class.
//
// Time: O((e + q) * n) worst case, near O((e + q) log n) amortised -- e equations and q queries,
// n at most 2e names; WeightedDisjointSet compresses paths but does not balance the trees.
// Space: O(n) -- the id map and the two arrays of WeightedDisjointSet.
//
// Siblings: EvaluateDivision399 -- the same method with the structure inlined.

public final class EvaluateDivisionIndexed {
	private WeightedDisjointSet wds;
	// only variables named in the EQUATIONS get an id. one named solely in a query is
	// undefined by the statement, which is why x / x is -1.0 and a / a is 1.0.
	private Map<String, Integer> ids;

	public double[] calcEquation(
			List<List<String>> equations, double[] values, List<List<String>> queries) {
		this.ids = new HashMap<>();
		buildIds(equations); // IDS: one int per name that appears in an equation
		// the size is known only once every equation has been walked
		this.wds = new WeightedDisjointSet(ids.size());
		merge(equations, values); // JOIN: record every equation
		double[] answers = new double[queries.size()];
		Arrays.fill(answers, -1.0);
		for (int i = 0; i < queries.size(); i++) {
			List<String> query = queries.get(i);
			String from = query.get(0);
			String to = query.get(1);
			// guard one: a variable that appears in no equation
			if (!ids.containsKey(from) || !ids.containsKey(to)) {
				continue; // SKIP: the answer stays -1.0
			}
			// guard two lives in ratio(): both known, but no chain joins them
			answers[i] = wds.ratio(ids.get(from), ids.get(to)); // RATIO: from / to, or -1.0
		}
		return answers;
	}

	private void merge(List<List<String>> equations, double[] values) {
		for (int i = 0; i < equations.size(); i++) {
			List<String> equation = equations.get(i);
			String a = equation.get(0);
			String b = equation.get(1);
			wds.union(ids.get(a), ids.get(b), values[i]);
		}
	}

	// one id per distinct variable, handed out in order of first appearance.
	// a hash code will not do: String.hashCode is negative for most inputs and far
	// larger than any array that could hold it.
	private void buildIds(List<List<String>> equations) {
		int id = 0;
		for (List<String> equation : equations) {
			String a = equation.get(0);
			String b = equation.get(1);
			if (!ids.containsKey(a)) {
				ids.put(a, id++);
			}
			if (!ids.containsKey(b)) {
				ids.put(b, id++);
			}
		}
	}

	public static void main(String[] args) {
		List<List<String>> equations = List.of(List.of("a", "b"), List.of("b", "c"));
		double[] values = { 2.0, 3.0 };
		List<List<String>> queries = List.of(List.of("a", "c"), List.of("b", "a"),
			List.of("a", "e"), List.of("a", "a"), List.of("x", "x"));
		EvaluateDivisionIndexed ed = new EvaluateDivisionIndexed();
		// a / a is 1.0 because a appears in an equation, x / x is -1.0 because x does not
		System.out.println(Arrays.toString(ed.calcEquation(equations, values, queries))); // [6.0, 0.5, -1.0, 1.0, -1.0]
	}
}
