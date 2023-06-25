package org.example.live.user.interfaces;

import org.example.live.user.contants.UserTagsEnum;

public interface IUserTagRpc {

	boolean setTag(Long userId, UserTagsEnum userTagsEnum);
	
	boolean cancelTag(Long userId,UserTagsEnum userTagsEnum);
	
	boolean containTag(Long userId,UserTagsEnum userTagsEnum);
}
