package com.svetanis.datastructures.hashmap;

import java.util.TreeMap;

// 2034. Stock Price Fluctuation 

public final class StockPrice {

	private int maxTimestamp;
	private TreeMap<Integer, Integer> prices;
	private TreeMap<Integer, Integer> counts;

	public StockPrice() {
		this.maxTimestamp = 0;
		this.counts = new TreeMap<>();
		this.prices = new TreeMap<>();
	}

	public void update(int timestamp, int price) {
		if (prices.containsKey(timestamp)) {
			int prev = prices.get(timestamp);
			counts.put(prev, counts.get(prev) - 1);
			if (counts.get(prev) == 0) {
				counts.remove(prev);
			}
		}
		prices.put(timestamp, price);
		counts.put(price, counts.getOrDefault(price, 0) + 1);
		maxTimestamp = Math.max(maxTimestamp, timestamp);
	}

	public int current() {
		return prices.get(maxTimestamp);
	}

	public int maximum() {
		return counts.lastKey();
	}

	public int minimum() {
		return counts.firstKey();
	}

	public static void main(String[] args) {
		StockPrice sp = new StockPrice();
		sp.update(1, 10);
		sp.update(2, 5);
		System.out.println(sp.current()); // 5
		System.out.println(sp.maximum()); // 10
		sp.update(1, 3);
		System.out.println(sp.maximum()); // 5
		sp.update(4, 2);
		System.out.println(sp.minimum()); // 2
	}
}