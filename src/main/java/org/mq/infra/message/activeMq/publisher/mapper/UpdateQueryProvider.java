package org.mq.infra.message.activeMq.publisher.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

//TODO QueryDSL 사용
public class UpdateQueryProvider {
    public String updateTable(Map<String, Object> dataList) {
        return new SQL() {{
            UPDATE((String) dataList.get("table_name"));
            dataList.remove("table_name");
            for (Map.Entry<String, Object> entry : dataList.entrySet()) {
                if ("sync_flag".equals(entry.getKey())) {
                    SET(entry.getKey() + " = " + entry.getValue());
                } else {
                    SET(entry.getKey() + " = '" + entry.getValue() + "'");
                }
            }
            WHERE("id = #{id}");
        }}.toString();
    }
}
