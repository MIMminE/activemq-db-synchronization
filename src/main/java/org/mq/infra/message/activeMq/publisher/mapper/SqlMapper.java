package org.mq.infra.message.activeMq.publisher.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SqlMapper {
    @Select("SELECT * FROM ${tableName} where mod(second(time_stamp),10)  = ${modIndex} and sync_flag = false")
    List<Map<String, Object>> selectTable(@Param("tableName") String tableName, @Param("modIndex") int modIndex);

}
