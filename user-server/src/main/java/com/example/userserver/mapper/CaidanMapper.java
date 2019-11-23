package com.example.userserver.mapper;

import com.example.userserver.model.Caidan;
import com.example.userserver.model.CaidanExample;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;

public interface CaidanMapper {
    long countByExample(CaidanExample example);

    int deleteByExample(CaidanExample example);

    int deleteByPrimaryKey(String id);

    int insert(Caidan record);

    int insertSelective(Caidan record);

    List<Caidan> selectByExample(CaidanExample example);

    Caidan selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Caidan record, @Param("example") CaidanExample example);

    int updateByExample(@Param("record") Caidan record, @Param("example") CaidanExample example);

    int updateByPrimaryKeySelective(Caidan record);

    int updateByPrimaryKey(Caidan record);
}