package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;

// 1797. Design Authentication Manager

public final class AuthenticationManager {

	private int timeToLive;
	private Map<String, Integer> map;

	public AuthenticationManager(int timeToLive) {
		this.timeToLive = timeToLive;
		this.map = new HashMap<>();
	}

	public void generate(String tokenId, int currentTime) {
		map.put(tokenId, currentTime + timeToLive);
	}

	public void renew(String tokenId, int currentTime) {
		int time = map.getOrDefault(tokenId, 0);
		if (time > currentTime) {
			generate(tokenId, currentTime);
		}
	}

	public int countUnexpiredTokens(int currentTime) {
		return (int) map.values().stream().filter(t -> t > currentTime).count();
	}

	public static void main(String[] args) {
		AuthenticationManager am = new AuthenticationManager(5);
		am.renew("aaa", 1);
		am.generate("aaa", 2);
		System.out.println(am.countUnexpiredTokens(6)); // 1
		am.generate("bbb", 7);
		am.renew("aaa", 8);
		am.renew("bbb", 10);
		System.out.println(am.countUnexpiredTokens(15)); // 0
	}
}