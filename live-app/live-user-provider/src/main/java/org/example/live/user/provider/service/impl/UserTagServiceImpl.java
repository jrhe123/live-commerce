package org.example.live.user.provider.service.impl;

import org.example.live.user.contants.UserTagFieldNameConstants;
import org.example.live.user.contants.UserTagsEnum;
import org.example.live.user.provider.dao.mapper.IUserTagMapper;
import org.example.live.user.provider.dao.po.UserTagPO;
import org.example.live.user.provider.service.IUserTagService;
import org.example.live.user.provider.utils.TagInfoUtils;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class UserTagServiceImpl implements IUserTagService {
	
	@Resource
	private IUserTagMapper userTagMapper;

	@Override
	public boolean setTag(Long userId, UserTagsEnum userTagsEnum) {
		int setTag = userTagMapper.setTag(
				userId, userTagsEnum.getFieldName(), userTagsEnum.getTag()
				);
		return setTag > 0;
	}

	@Override
	public boolean cancelTag(Long userId, UserTagsEnum userTagsEnum) {
		int cancelTag = userTagMapper.cancelTag(
				userId, userTagsEnum.getFieldName(), userTagsEnum.getTag()
				);
		return cancelTag > 0;
	}

	@Override
	public boolean containTag(Long userId, UserTagsEnum userTagsEnum) {
//		UserTagPO userTagPO = userTagMapper.selectByUserId(userId);
		UserTagPO userTagPO = userTagMapper.selectById(userId);
		
		System.out.println("userTagPO: " + userTagPO);
		
		if (userTagPO == null) {
			return false;
		}
		
		String fieldName = userTagsEnum.getFieldName();
		Long tagInfo;
		
		if (UserTagFieldNameConstants.TAG_INFO_01.equals(fieldName)) {
			tagInfo = userTagPO.getTagInfo01();
			return TagInfoUtils.isContain(tagInfo, userTagsEnum.getTag());
		} else if (UserTagFieldNameConstants.TAG_INFO_02.equals(fieldName)) {
			tagInfo = userTagPO.getTagInfo02();
			return TagInfoUtils.isContain(tagInfo, userTagsEnum.getTag());
		} else if (UserTagFieldNameConstants.TAG_INFO_03.equals(fieldName)) {
			tagInfo = userTagPO.getTagInfo03();
			return TagInfoUtils.isContain(tagInfo, userTagsEnum.getTag());
		}
		
		return false;
	}

	

}
