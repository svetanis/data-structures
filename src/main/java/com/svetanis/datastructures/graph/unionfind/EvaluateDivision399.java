package com.svetanis.datastructures.graph.unionfind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 399. Evaluate Division

public final class EvaluateDivision399 {

  private Map<String, String> parent;
  private Map<String, Double> weight;

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
        a[i] = -1.0;
      } else {
        a[i] = weight.get(q1) / weight.get(q2);
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
      if (!pa.equals(pb)) {
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
      parent.put(x, find(parent.get(x)));
      weight.put(x, weight.get(x) * weight.get(xp));
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
  }
}
