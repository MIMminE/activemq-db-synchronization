package org.mq.mapper;

import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

@Mapper
public interface TableMapper {
    @Select("SELECT * FROM ${tableName} where mod(second(time_stamp),10)  = ${modIndex} and sync_flag = false")
    List<Map<String, Object>> selectTable(@Param("tableName") String tableName, @Param("modIndex") int modIndex);

    @UpdateProvider(type = UpdateQueryProvider.class, method = "updateTable")
    void updateTable(Map<String, Object> dataList);

}
