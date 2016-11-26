package com.yutong.util.db;

import java.sql.Connection;
import java.util.Map;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;


public class ConnectionUtilsTest {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = ConnectionUtils.getOracleConnection("192.168.1.10", "1521",
                    "orcl", "wxdbs", "wxdbs");
            String sql = "SELECT * FROM CK10_GHDJ_TOTAL WHERE ID='12794917032'";
            QueryRunner queryRunner = new QueryRunner();

            Map<String, Object> resultMap =
                    queryRunner.query(conn, sql, new MapHandler());
            System.out.println(resultMap);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            DbUtils.closeQuietly(conn);
        }
    }
}
