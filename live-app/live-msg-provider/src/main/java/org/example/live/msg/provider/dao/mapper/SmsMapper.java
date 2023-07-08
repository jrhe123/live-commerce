package org.example.live.msg.provider.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.live.msg.provider.dao.po.SmsPO;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface SmsMapper extends BaseMapper<SmsPO> {

}
