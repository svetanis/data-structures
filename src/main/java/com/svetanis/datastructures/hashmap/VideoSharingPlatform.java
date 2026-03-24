package com.svetanis.datastructures.hashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import com.svetanis.java.base.utils.Print;

// 2254. Design Video Sharing Platform

public final class VideoSharingPlatform {

	private int videoId;
	private PriorityQueue<Integer> usedIds;
	private Map<Integer, String> vidToVideo;
	private Map<Integer, Integer> vidToViews;
	private Map<Integer, Integer> vidToLikes;
	private Map<Integer, Integer> vidToDislikes;

	public VideoSharingPlatform() {
		this.videoId = 0;
		this.usedIds = new PriorityQueue<>();
		this.vidToVideo = new HashMap<>();
		this.vidToViews = new HashMap<>();
		this.vidToLikes = new HashMap<>();
		this.vidToDislikes = new HashMap<>();
	}

	public int upload(String video) {
		int videoId = getVideoId();
		vidToVideo.put(videoId, video);
		return videoId;
	}

	private int getVideoId() {
		int result;
		if (usedIds.isEmpty()) {
			result = this.videoId;
			this.videoId += 1;
		} else {
			result = usedIds.poll();
		}
		return result;
	}

	public void remove(int vid) {
		if (vidToVideo.containsKey(vid)) {
			usedIds.add(vid);
			vidToVideo.remove(vid);
			vidToViews.remove(vid);
			vidToLikes.remove(vid);
			vidToDislikes.remove(vid);
		}
	}

	public String watch(int vid, int startMinute, int endMinute) {
		if (!vidToVideo.containsKey(vid)) {
			return "-1";
		}
		vidToViews.merge(vid, 1, Integer::sum);
		String video = vidToVideo.get(vid);
		int duration = Math.min(endMinute, video.length() - 1) - startMinute + 1;
		return video.substring(startMinute, startMinute + duration);
	}

	public void like(int vid) {
		if (vidToVideo.containsKey(vid)) {
			vidToLikes.merge(vid, 1, Integer::sum);
		}
	}

	public void dislike(int vid) {
		if (vidToVideo.containsKey(vid)) {
			vidToDislikes.merge(vid, 1, Integer::sum);
		}
	}

	public int[] getLikesAndDislikes(int vid) {
		if (vidToVideo.containsKey(vid)) {
			int[] result = new int[2];
			result[0] = vidToLikes.getOrDefault(vid, 0);
			result[1] = vidToDislikes.getOrDefault(vid, 0);
			return result;
		} else {
			return new int[] { -1 };
		}
	}

	public int getViews(int vid) {
		if (vidToVideo.containsKey(vid)) {
			return vidToViews.getOrDefault(vid, 0);
		} else {
			return vidToViews.getOrDefault(vid, -1);
		}
	}

	public static void main(String[] args) {
		VideoSharingPlatform vsp = new VideoSharingPlatform();
		System.out.println(vsp.upload("123")); // The smallest available videoId is 0, so return 0.
		System.out.println(vsp.upload("456")); // The smallest available videoId is 1, so return 1.
		vsp.remove(4); // There is no video associated with videoId 4, so do nothing.
		vsp.remove(0); // Remove the video associated with videoId 0.
		System.out.println(vsp.upload("789")); // Since the video associated with videoId 0 was deleted,
		// 0 is the smallest available videoId, so return 0.
		System.out.println(vsp.watch(1, 0, 5)); // The video associated with videoId 1 is "456".
		// The video from minute 0 to min(5, 3 - 1) = 2 is "456", so return "456".
		System.out.println(vsp.watch(1, 0, 1)); // The video associated with videoId 1 is "456".
		// The video from minute 0 to min(1, 3 - 1) = 1 is "45", so return "45".
		vsp.like(1); // Increase the number of likes on the video associated with videoId 1.
		vsp.dislike(1); // Increase the number of dislikes on the video associated with videoId 1.
		vsp.dislike(1); // Increase the number of dislikes on the video associated with videoId 1.
		Print.print(vsp.getLikesAndDislikes(1)); // There is 1 like and 2 dislikes on the video associated with videoId
																							// 1, so return [1, 2].
		System.out.println(vsp.getViews(1)); // The video associated with videoId 1 has 2 views, so return 2.

		System.out.println();

		VideoSharingPlatform vsp1 = new VideoSharingPlatform();
		System.out.println(vsp1.upload("62370")); // 0
		System.out.println(vsp1.upload("75")); // 1
		System.out.println(vsp1.getViews(0)); // 0

		System.out.println();

		VideoSharingPlatform vsp2 = new VideoSharingPlatform();
		vsp2.remove(0); // There is no video associated with videoId 0, so do nothing.
		System.out.println(vsp2.watch(0, 0, 1)); // There is no video associated with videoId 0, so return "-1".
		vsp2.like(0); // There is no video associated with videoId 0, so do nothing.
		vsp2.dislike(0); // There is no video associated with videoId 0, so do nothing.
		Print.print(vsp2.getLikesAndDislikes(0)); // There is no video associated with videoId 0, so return [-1].
		System.out.println(vsp2.getViews(0)); // There is no video associated with videoId 0, so return -1.
	}
}
