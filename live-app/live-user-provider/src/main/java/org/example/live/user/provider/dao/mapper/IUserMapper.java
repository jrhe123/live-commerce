package org.example.live.user.provider.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.live.user.provider.dao.po.UserPO;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface IUserMapper extends BaseMapper<UserPO> {
}