package com.svetanis.datastructures.hashmap;

import java.util.TreeMap;

// 2034. Stock Price Fluctuation
//
// update(timestamp, price) records the price at a timestamp; records may arrive out of order,
// and a second record for the same timestamp CORRECTS the first. current() returns the price
// at the latest timestamp; maximum() and minimum() return the highest and lowest price over
// all timestamps, using only the corrected prices. On LeetCode:
// 1 <= timestamp, price <= 10^9, at most 10^5 calls, current, maximum and minimum only after
// at least one update.
//
// Two maps. prices takes each timestamp to its price. counts takes each price to how many
// timestamps currently have it, in a TreeMap, so the maximum and minimum are its last and
// first keys. A correction takes one count away from the old price first and removes that
// price when its count reaches 0, so a price that was corrected away can no longer be the
// maximum or minimum. The latest timestamp is a running max.
//
// Siblings, same problem: none in this package.

public final class StockPrice {
	// Time complexity: update, current, maximum, minimum O(log n), TreeMap steps
	// Auxiliary Space: O(n), one entry per timestamp in prices and at most one per price in counts

	private int maxTimestamp; // the latest timestamp seen
	private TreeMap<Integer, Integer> prices; // timestamp -> its current price
	private TreeMap<Integer, Integer> counts; // price -> how many timestamps have it now

	public StockPrice() {
		this.maxTimestamp = 0;
		this.counts = new TreeMap<>();
		this.prices = new TreeMap<>();
	}

	public void update(int timestamp, int price) {
		if (prices.containsKey(timestamp)) { // CORRECT: take the old price's count away
			int prev = prices.get(timestamp);
			counts.put(prev, counts.get(prev) - 1);
			if (counts.get(prev) == 0) {
				counts.remove(prev); // DROP: no timestamp has it any more
			}
		}
		prices.put(timestamp, price); // RECORD
		counts.put(price, counts.getOrDefault(price, 0) + 1); // COUNT
		maxTimestamp = Math.max(maxTimestamp, timestamp); // LATEST
	}

	public int current() {
		return prices.get(maxTimestamp);
	}

	public int maximum() {
		return counts.lastKey(); // MAX: the highest price still in use
	}

	public int minimum() {
		return counts.firstKey(); // MIN: the lowest price still in use
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