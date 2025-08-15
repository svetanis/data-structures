package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// 1348. Tweet Counts Per Frequency

public final class TweetCounts {

	private Map<String, TreeMap<Integer, Integer>> map;

	public TweetCounts() {
		this.map = new HashMap<>();
	}

	public void recordTweet(String tweet, int time) {
		map.putIfAbsent(tweet, new TreeMap<>());
		TreeMap<Integer, Integer> frequencies = map.get(tweet);
		frequencies.put(time, frequencies.getOrDefault(time, 0) + 1);
	}

	public List<Integer> getTweetCountsPerFrequency(String freq, String tweet, int start, int end) {
		int interval = intervalInSeconds(freq);
		List<Integer> list = new ArrayList<>();
		TreeMap<Integer, Integer> frequencies = map.get(tweet);
		for (int time = start; time <= end; time += interval) {
			int sum = 0;
			int intervalEnd = Math.min(time + interval, end + 1);
			for (int frequency : frequencies.subMap(time, intervalEnd).values()) {
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