package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

// 2353. Design a Food Rating System

public final class FoodRatings {

	private Map<String, String> ftc;
	private Map<String, Integer> ftr;
	private Map<String, TreeSet<Food>> ctr;

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
			this.ctr.get(cuisine).add(new Food(rating, food));
		}
	}

	public void changeRating(String food, int newRating) {
		String cuisine = ftc.get(food);
		int rating = ftr.get(food);

		TreeSet<Food> ratings = ctr.get(cuisine);
		ratings.remove(new Food(rating, food));

		ftr.put(food, newRating);
		ratings.add(new Food(newRating, food));
	}

	public String highestRated(String cuisine) {
		TreeSet<Food> ratings = ctr.get(cuisine);
		return ratings.first().food;
	}

	public static void main(String[] args) {
		String[] foods = { "kimchi", "miso", "sushi", "moussaka", "ramen", "bulgogi" };
		String[] cuisines = { "korean", "japanese", "japanese", "greek", "japanese", "korean" };
		int[] ratings = { 9, 12, 8, 15, 14, 7 };
		FoodRatings fr = new FoodRatings(foods, cuisines, ratings);
		System.out.println(fr.highestRated("korean")); // kimchi
		System.out.println(fr.highestRated("japanese")); // ramen fr.changeRating("sushi", 16);
		System.out.println(fr.highestRated("japanese")); // sushi fr.changeRating("ramen", 16);
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
				return this.food.compareTo(other.food);
			}
			return other.rating - this.rating;
		}
	}
}