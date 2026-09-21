package com.svetanis.datastructures.graph.unionfind;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 399. Evaluate Division
// The variables are mapped to int ids and the union-find is a separate class.
// EvaluateDivision399 takes the other arrangement: Map<String, String> parent and
// Map<String, Double> weight, with the structure inlined in the problem class.
// Both work; this one keeps WeightedDisjointSet reusable and leaves this class
// with only the problem in it -- ids in, queries out.

public final class EvaluateDivisionIndexed {
	// Time Complexity: O((e + q) * a(n)) after the ids are built
	// Space Complexity: O(n)

	private WeightedDisjointSet wds;
	// only variables named in the EQUATIONS get an id. one named solely in a query is
	// undefined by the statement, which is why x / x is -1.0 and a / a is 1.0.
	private Map<String, Integer> ids;

	public double[] calcEquation(
			List<List<String>> equations, double[] values, List<List<String>> queries) {
		this.ids = new HashMap<>();
		buildIds(equations);
		// the size is known only once every equation has been walked
		this.wds = new WeightedDisjointSet(ids.size());
		merge(equations, values);
		double[] answers = new double[queries.size()];
		Arrays.fill(answers, -1.0);
		for (int i = 0; i < queries.size(); i++) {
			List<String> query = queries.get(i);
			String from = query.get(0);
			String to = query.get(1);
			// guard one: a variable that appears in no equation
			if (!ids.containsKey(from) || !ids.containsKey(to)) {
				continue;
			}
			// guard two lives in ratio(): both known, but no chain joins them
			answers[i] = wds.ratio(ids.get(from), ids.get(to));
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
		// [6.0, 0.5, -1.0, 1.0, -1.0] -- a / a is 1.0 because a appears in an equation,
		// x / x is -1.0 because x does not
		System.out.println(Arrays.toString(ed.calcEquation(equations, values, queries)));
	}
}
