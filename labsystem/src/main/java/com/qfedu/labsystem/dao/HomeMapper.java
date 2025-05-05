package com.qfedu.labsystem.dao;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface HomeMapper {
    Integer getUserCount();
    Integer getLabCount();
    Integer getGadgetCount();
    List<Map<String, Object>> getLabBookData();
}
