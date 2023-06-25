package org.example.live.user.provider.dao.mapper;

import org.apache.ibatis.annotations.Update;
import org.example.live.user.provider.dao.po.UserTagPO;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface IUserTagMapper extends BaseMapper<UserTagPO> {

	/**
	 * add tag: 2
	 * update t_user_tag set tag_info_01=tag_info_01|2 where user_id=1001;
	 * 
	 * remove tag: 2
	 * update t_user_tag set tag_info_01=tag_info_01 &~2 where user_id=1001;
	 */
	@Update("update t_user_tag set ${fieldName}=${fieldName} | #{tag} where user_id=#{userId}")
	int setTag(Long userId, String fieldName, long tag);

	@Update("update t_user_tag set ${fieldName}=${fieldName} &~ #{tag} where user_id=#{userId}")
	int cancelTag(Long userId, String fieldName, long tag);

}
