package org.mq.mapper;

import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

@Mapper
public interface TableMapper {

    @Select("SELECT * FROM ${tableName}")
    List<Map<String, Object>> selectTable(@Param("tableName") String tableName);

    @UpdateProvider(type = MyUpdateProvider.class, method = "updateTable")
    void updateTable(Map<String, Object> dataList);

}
