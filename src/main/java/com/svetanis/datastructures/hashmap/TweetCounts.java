package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// 1348. Tweet Counts Per Frequency
//
// recordTweet(tweetName, time) records one tweet at a second. getTweetCountsPerFrequency(freq,
// tweetName, startTime, endTime) cuts the seconds startTime to endTime, both included, into
// chunks of 60, 3600 or 86400 seconds ("minute", "hour", "day"), the last chunk possibly
// shorter, and returns how many of that name's tweets fall in each chunk. On LeetCode:
// 0 <= time, startTime, endTime <= 10^9, 0 <= endTime - startTime <= 10^4, at most 10^4
// calls.
//
// Per tweet name, a TreeMap from second to how many tweets that second holds, so each chunk
// is one range query. A chunk starting at t covers t up to, not including,
// min(t + size, endTime + 1): the + 1 keeps endTime itself inside the last chunk. A name never
// recorded has no tweets, so every chunk counts 0.
//
// Siblings, same problem: none in this package.

public final class TweetCounts {
	// Time complexity: recordTweet O(log s); get O(c log s + k), c chunks, k tweets in range,
	//   s distinct seconds of that name
	// Auxiliary Space: O(s) per name, one entry per distinct second; the result is c ints

	private Map<String, TreeMap<Integer, Integer>> map; // name -> second -> tweets that second

	public TweetCounts() {
		this.map = new HashMap<>();
	}

	public void recordTweet(String tweet, int time) {
		map.putIfAbsent(tweet, new TreeMap<>());
		TreeMap<Integer, Integer> frequencies = map.get(tweet);
		frequencies.put(time, frequencies.getOrDefault(time, 0) + 1); // RECORD: one more that second
	}

	public List<Integer> getTweetCountsPerFrequency(String freq, String tweet, int start, int end) {
		int interval = intervalInSeconds(freq);
		List<Integer> list = new ArrayList<>();
		TreeMap<Integer, Integer> frequencies = map.getOrDefault(tweet, new TreeMap<>()); // NONE: never recorded
		for (int time = start; time <= end; time += interval) { // CHUNK: starts at time
			int sum = 0;
			int intervalEnd = Math.min(time + interval, end + 1); // END: excluded, so end + 1
			for (int frequency : frequencies.subMap(time, intervalEnd).values()) { // RANGE: [time, intervalEnd)
				sum += frequency;
			}
			list.add(sum);
		}
		return list;
	}

	private int intervalInSeconds(String frequncy) {
		int sec = 60;
		if ("hour".equals(frequncy)) {
			sec = 3600;
		} else if ("day".equals(frequncy)) {
			sec = 86400;
		}
		return sec;
	}

	public static void main(String[] args) {
		TweetCounts tc = new TweetCounts();
		tc.recordTweet("tweet3", 0);
		tc.recordTweet("tweet3", 60);
		tc.recordTweet("tweet3", 10);
		System.out.println(tc.getTweetCountsPerFrequency("minute", "tweet3", 0, 59)); // 2
		System.out.println(tc.getTweetCountsPerFrequency("minute", "tweet3", 0, 60)); // 2 1
		tc.recordTweet("tweet3", 120);
		System.out.println(tc.getTweetCountsPerFrequency("hour", "tweet3", 0, 210)); // 4
	}
}