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
//
// Input: recipes -- the names of recipes; ingredients.get(i) -- the names a recipe needs, which may
// be supplies or other recipes; supplies -- the names of ingredients available in unlimited
// amount. Returns: the names of all recipes that can be made, in any order. On LeetCode: 1 <= n <=
// 100 recipes, 1 <= ingredients[i].length, supplies.length <= 100, and every name in recipes and
// supplies is different, as are the names inside one ingredients list.
//
// The one idea: a recipe can be made once every one of its ingredients is available. Count, for
// each recipe, how many of its ingredients are still missing (its in-degree), and for each
// ingredient keep the list of recipes that need it. Start with the supplies as the available
// things. Take one off the queue and, for every recipe that needs it, reduce that recipe's missing
// count; a recipe that reaches 0 can be made, so it is recorded, and it becomes available too
// (another recipe may need it). A recipe caught in a cycle of needs, or needing something that is
// neither supplied nor a recipe, never reaches 0.
//
// Time: O(R + S + I) -- R recipes, S supplies and I ingredient entries in all; each entry is
// read once to build the lists and used up once.
// Space: O(R + S + I) -- the lists, the counts and the queue.
//
// Siblings: none in this package.

public final class AllPossibleRecipes {

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
				for (String recipe : nodes) { // every recipe that needs this ingredient
					int f = inDegree.get(recipe) - 1; // USE UP one missing ingredient
					inDegree.put(recipe, f);
					if (f == 0) { // READY: nothing is missing any more
						list.add(recipe);
						queue.offer(recipe); // it is available to the recipes that need it
					}
				}
			}
		}
		return list;
	}

	private Deque<String> buildQueue(String[] supplies) {
		Deque<String> dq = new ArrayDeque<>();
		for (String supply : supplies) {
			dq.offer(supply); // the supplies are available from the start
		}
		return dq;
	}

	private void buildGraph(String[] recipes, List<List<String>> ingredients) {
		this.graph = new HashMap<>();
		this.inDegree = new HashMap<>();
		for (int i = 0; i < recipes.length; i++) {
			String recipe = recipes[i];
			for (String ingredient : ingredients.get(i)) {
				graph.computeIfAbsent(ingredient, r -> new ArrayList<>()).add(recipe); // ingredient -> recipe
			}
			inDegree.put(recipe, ingredients.get(i).size()); // missing at the start: all of them
		}
	}

	public static void main(String[] args) {
		AllPossibleRecipes apr = new AllPossibleRecipes();
		String[] r1 = { "bread" };
		List<List<String>> ing1 = new ArrayList<>();
		ing1.add(Arrays.asList("yeast", "flour"));
		String[] s1 = { "yeast", "flour", "corn" };
		System.out.println(apr.findAllRecipes(r1, ing1, s1)); // [bread]

		// LeetCode example 2: sandwich needs bread and meat, bread needs yeast and flour
		String[] r2 = { "bread", "sandwich" };
		List<List<String>> ing2 = new ArrayList<>();
		ing2.add(Arrays.asList("yeast", "flour"));
		ing2.add(Arrays.asList("bread", "meat"));
		String[] s2 = { "yeast", "flour", "meat" };
		System.out.println(apr.findAllRecipes(r2, ing2, s2)); // [bread, sandwich]

		// LeetCode example 3: burger needs sandwich, which needs bread and meat
		String[] r3 = { "bread", "sandwich", "burger" };
		List<List<String>> ing3 = new ArrayList<>();
		ing3.add(Arrays.asList("yeast", "flour"));
		ing3.add(Arrays.asList("bread", "meat"));
		ing3.add(Arrays.asList("sandwich", "meat", "bread"));
		String[] s3 = { "yeast", "flour", "meat" };
		System.out.println(apr.findAllRecipes(r3, ing3, s3)); // [bread, sandwich, burger]
	}
}
