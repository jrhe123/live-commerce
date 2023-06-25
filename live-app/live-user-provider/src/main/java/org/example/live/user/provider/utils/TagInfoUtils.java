package org.example.live.user.provider.utils;

public class TagInfoUtils {

	/**
	 * 
	 * check match tag exists in current tag info
	 * 
	 * @param tagInfo: user current tags
	 * @param matchTag: search tag
	 * @return
	 */
	public static boolean isContain(Long tagInfo, Long matchTag) {
		return tagInfo != null && matchTag != null &&
				matchTag > 0 && (tagInfo & matchTag) == matchTag;
	}
	
	public static void main(String[] args) {
		/**
		 * 3L -> 11
		 * 2L -> 10
		 * 
		 * should be true
		 */
		
		Long testingLong = (3L & 2L);
		
		boolean contain = TagInfoUtils.isContain(3L, 2L);
		
		System.out.println("testingLong: " + testingLong);
		System.out.println("contain: " + contain);
	}
}
