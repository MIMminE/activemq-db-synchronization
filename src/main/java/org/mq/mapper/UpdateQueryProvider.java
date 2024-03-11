package org.mq.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

//TODO XML 파일로 이관
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
