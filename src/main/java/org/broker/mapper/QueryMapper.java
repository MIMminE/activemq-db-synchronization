package org.broker.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface QueryMapper {

    @Select("SELECT * FROM ${tableName} where id_flag= ${id}")
    Map<String, Object> selectById(@Param("tableName") String tableName, @Param("id") Long id);

    @Select("SELECT * FROM ${tableName} where mod(second(time_stamp),10)  = ${modIndex} and sync_flag = false")
    List<Map<String, Object>> selectTableBySyncCondition(@Param("tableName") String tableName, @Param("modIndex") int modIndex);

    @Update("UPDATE ${tableName} SET sync_flag = true WHERE id_flag= ${id}")
    void updateTableSyncCondition(@Param("tableName") String tableName, @Param("id") Long id);

    void insertTopicMessage(String tableName ,Map<String, Object> message);
}
