package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

// 2353. Design a Food Rating System
//
// The constructor takes three arrays of length n: foods[i] has cuisine cuisines[i] and
// rating ratings[i]. changeRating(food, newRating) changes one food's rating, and
// highestRated(cuisine) returns the food of that cuisine with the highest rating, the
// lexicographically smaller name on a tie. On LeetCode: 1 <= n <= 2 * 10^4, food names are
// distinct, 1 <= ratings[i] <= 10^8, at most 2 * 10^4 calls in total.
//
// Each cuisine keeps its foods in a TreeSet ordered by rating, highest first, then by name,
// so the answer is always first(). A TreeSet finds an entry by comparing it, not by
// identity, so changing a rating means removing the entry with the OLD rating and adding
// one with the new: that is why ftr keeps every food's current rating, and ftc its
// cuisine. Changing the rating inside an entry that is already in the set would leave it
// in the wrong place, and the set would no longer find it.

public final class FoodRatings {
	// Time complexity: constructor O(n log n); changeRating and highestRated O(log n), a
	// remove and an add, or first(), on one cuisine's TreeSet
	// Auxiliary Space: O(n), one entry per food in each of the three maps

	private Map<String, String> ftc; // food -> its cuisine
	private Map<String, Integer> ftr; // food -> its current rating
	private Map<String, TreeSet<Food>> ctr; // cuisine -> its foods, best first

	public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
		this.ftc = new HashMap<>();
		this.ftr = new HashMap<>();
		this.ctr = new HashMap<>();
		init(foods, cuisines, ratings);
	}

	private void init(String[] foods, String[] cuisines, int[] ratings) {
		for (int i = 0; i < foods.length; i++) {
			String food = foods[i];
			String cuisine = cuisines[i];
			int rating = ratings[i];
			this.ftc.put(food, cuisine);
			this.ftr.put(food, rating);
			this.ctr.putIfAbsent(cuisine, new TreeSet<>());
			this.ctr.get(cuisine).add(new Food(rating, food)); // ADD to its cuisine's set
		}
	}

	public void changeRating(String food, int newRating) {
		String cuisine = ftc.get(food);
		int rating = ftr.get(food); // FIND the old rating: the set finds entries by it

		TreeSet<Food> ratings = ctr.get(cuisine);
		ratings.remove(new Food(rating, food)); // DROP the entry with the old rating

		ftr.put(food, newRating);
		ratings.add(new Food(newRating, food)); // ADD it back with the new one
	}

	public String highestRated(String cuisine) {
		TreeSet<Food> ratings = ctr.get(cuisine);
		return ratings.first().food; // FIRST: highest rating, smaller name on a tie
	}

	public static void main(String[] args) {
		String[] foods = { "kimchi", "miso", "sushi", "moussaka", "ramen", "bulgogi" };
		String[] cuisines = { "korean", "japanese", "japanese", "greek", "japanese", "korean" };
		int[] ratings = { 9, 12, 8, 15, 14, 7 };
		FoodRatings fr = new FoodRatings(foods, cuisines, ratings);
		System.out.println(fr.highestRated("korean")); // kimchi
		System.out.println(fr.highestRated("japanese")); // ramen
		fr.changeRating("sushi", 16);
		System.out.println(fr.highestRated("japanese")); // sushi
		fr.changeRating("ramen", 16);
		System.out.println(fr.highestRated("japanese")); // ramen

		String[] foods2 = { "cpctxzh", "bryvgjqmj", "wedqhqrmyc", "ee", "lafzximxh", "lojzxfel", "flhs" };
		String[] cuisines2 = { "wbhdgqphq", "wbhdgqphq", "mxxajogm", "wbhdgqphq", "wbhdgqphq", "mxxajogm", "mxxajogm" };
		int[] ratings2 = { 15, 5, 7, 16, 16, 10, 13 };
		FoodRatings fr2 = new FoodRatings(foods2, cuisines2, ratings2);
		fr2.changeRating("lojzxfel", 1);
		System.out.println(fr2.highestRated("mxxajogm")); // flhs
		System.out.println(fr2.highestRated("wbhdgqphq")); // ee
		System.out.println(fr2.highestRated("mxxajogm")); // flhs
	}

	public static class Food implements Comparable<Food> {
		private int rating;
		private String food;

		public Food(int rating, String food) {
			this.rating = rating;
			this.food = food;
		}

		@Override
		public int compareTo(Food other) {
			if (this.rating == other.rating) {
				return this.food.compareTo(other.food); // TIE: the smaller name first
			}
			return Integer.compare(other.rating, this.rating); // ORDER: higher rating first
		}
	}
}