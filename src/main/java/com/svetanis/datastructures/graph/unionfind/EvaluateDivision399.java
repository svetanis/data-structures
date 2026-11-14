package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayList;
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
// The one idea: pick a name in each connected group as its ROOT, and let every name x remember one
// number weight[x] = x / (its parent). Following parents to the root and multiplying the weights
// on the way gives x / root. Then any two names C and D in one group give C / D = (C / root) /
// (D / root), a single division. To add "A / B = v": if A and B already sit in one group the
// equation adds nothing; otherwise hang A's root under B's root, with the weight that makes the
// ratios agree: root_A / root_B = v * (B / root_B) / (A / root_A).
//
// Time: O((e + q) * n) worst case, near O((e + q) log n) amortised -- e equations and q queries;
// each find compresses its path but the trees are not balanced, and n is at most 2e names.
// Space: O(n) -- the two maps.
//
// Siblings: EvaluateDivisionIndexed maps the names to int ids and keeps the union-find in a
// separate WeightedDisjointSet; this file keeps the maps inside the problem class.

public final class EvaluateDivision399 {

  private Map<String, String> parent; // CARDS: parent.get(x) -- the name above x in its group
  private Map<String, Double> weight; // weight.get(x) -- x divided by its parent

  public double[] equation(List<List<String>> equations, double[] values, List<List<String>> queries) {
    init(equations);
    union(equations, values);
    return evaluate(queries);
  }

  private double[] evaluate(List<List<String>> queries) {
    int n = queries.size();
    double[] a = new double[n];
    for (int i = 0; i < n; i++) {
      String q1 = queries.get(i).get(0);
      String q2 = queries.get(i).get(1);
      if (!parent.containsKey(q1) || !parent.containsKey(q2) || !find(q1).equals(find(q2))) {
        a[i] = -1.0; // UNKNOWN: a name in no equation, or no chain between the two
      } else {
        a[i] = weight.get(q1) / weight.get(q2); // RATIO: (q1 / root) / (q2 / root)
      }
    }
    return a;
  }

  private void union(List<List<String>> equations, double[] values) {
    for (int i = 0; i < equations.size(); i++) {
      List<String> equation = equations.get(i);
      String a = equation.get(0);
      String b = equation.get(1);
      String pa = find(a);
      String pb = find(b);
      if (!pa.equals(pb)) { // JOIN: a's root goes under b's root
        parent.put(pa, pb);
        double val = weight.get(b) * values[i] / weight.get(a);
        weight.put(pa, val);
      }
    }
  }

  private void init(List<List<String>> equations) {
    parent = new HashMap<>();
    weight = new HashMap<>();
    for (List<String> equation : equations) {
      parent.put(equation.get(0), equation.get(0));
      parent.put(equation.get(1), equation.get(1));
      weight.put(equation.get(0), 1.0);
      weight.put(equation.get(1), 1.0);
    }
  }

  private String find(String x) {
    if (!x.equals(parent.get(x))) {
      String xp = parent.get(x);
      parent.put(x, find(parent.get(x))); // COMPRESS: x now points at the root
      weight.put(x, weight.get(x) * weight.get(xp)); // x / xp times xp / root = x / root
    }
    return parent.get(x);
  }

  public static void main(String[] args) {
    List<List<String>> equations = new ArrayList<>();
    equations.add(Arrays.asList("a", "b"));
    equations.add(Arrays.asList("b", "c"));
    double[] values = { 2.0, 3.0 };
    List<List<String>> queries = new ArrayList<>();
    queries.add(Arrays.asList("a", "c"));
    queries.add(Arrays.asList("b", "a"));
    queries.add(Arrays.asList("a", "e"));
    queries.add(Arrays.asList("a", "a"));
    queries.add(Arrays.asList("x", "x"));
    
    EvaluateDivision399 ed = new EvaluateDivision399();
    System.out.println(Arrays.toString(ed.equation(equations, values, queries)));
    // [6.0, 0.5, -1.0, 1.0, -1.0]
    //  a/c = 2*3        b/a = 1/2
    //  a/e = -1.0  e is not in any equation
    //  a/a =  1.0  a divided by itself, even though a IS known
    //  x/x = -1.0  x is unknown, so NOT 1.0 -- the case people miss
  }
}
