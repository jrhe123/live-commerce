package org.example.live.user.provider.service;

import org.example.live.user.contants.UserTagsEnum;

public interface IUserTagService {

	boolean setTag(Long userId, UserTagsEnum userTagsEnum);
	
	boolean cancelTag(Long userId,UserTagsEnum userTagsEnum);
	
	boolean containTag(Long userId,UserTagsEnum userTagsEnum);
}
