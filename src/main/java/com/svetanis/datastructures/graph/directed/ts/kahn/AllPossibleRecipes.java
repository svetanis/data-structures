package com.svetanis.datastructures.graph.directed.ts.kahn;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 2115. Find All Possible Recipes from Given Supplies

public final class AllPossibleRecipes {
	// Time Complexity: O(R + S + I)
	// Space Complexity: O(R + S + I)

	private Map<String, Integer> inDegree;
	private Map<String, List<String>> graph;

	public List<String> findAllRecipes(String[] recipes, 
			List<List<String>> ingredients, String[] supplies) {
		buildGraph(recipes, ingredients);
		Deque<String> queue = buildQueue(supplies);
		List<String> list = new ArrayList<>();
		while (!queue.isEmpty()) {
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				String ingredient = queue.pollFirst();
				List<String> nodes = graph.getOrDefault(ingredient, Collections.emptyList());
				for (String recipe : nodes) {
					int f = inDegree.get(recipe) - 1;
					inDegree.put(recipe, f);
					if (f == 0) {
						list.add(recipe);
						queue.offer(recipe);
					}
				}
			}
		}
		return list;
	}

	private Deque<String> buildQueue(String[] supplies) {
		Deque<String> dq = new ArrayDeque<>();
		for (String supply : supplies) {
			dq.offer(supply);
		}
		return dq;
	}

	private void buildGraph(String[] recipes, List<List<String>> ingredients) {
		this.graph = new HashMap<>();
		this.inDegree = new HashMap<>();
		for (int i = 0; i < recipes.length; i++) {
			String recipe = recipes[i];
			for (String ingredient : ingredients.get(i)) {
				graph.computeIfAbsent(ingredient, r -> new ArrayList<>()).add(recipe);
			}
			inDegree.put(recipe, ingredients.get(i).size());
		}
	}

	public static void main(String[] args) {
		AllPossibleRecipes apr = new AllPossibleRecipes();
		String[] r1 = { "bread" };
		List<List<String>> ing1 = new ArrayList<>();
		ing1.add(Arrays.asList("yeast", "flour"));
		String[] s1 = { "yeast", "flour", "corn" };
		System.out.println(apr.findAllRecipes(r1, ing1, s1)); // bread
	}
}
