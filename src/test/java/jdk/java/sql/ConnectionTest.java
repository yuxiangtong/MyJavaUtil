package jdk.java.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import org.apache.commons.dbutils.DbUtils;
import com.yutong.util.db.ConnectionUtils;


public class ConnectionTest {

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = ConnectionUtils.getOracleConnection("192.168.1.10",
                    "1521", "orcl", "wxdbs", "wxdbs");
            System.out.println("此 Connection 对象的自动提交模式的当前状态:"
                    + connection.getAutoCommit());

            /* 关于数据库的整体综合信息 */
            DatabaseMetaData metadata = connection.getMetaData();
            
            StringBuffer buffer = new StringBuffer();
            buffer.append("16.检索底层数据库的主版本号:【"+metadata.getDatabaseMajorVersion()+"】 [getDatabaseMajorVersion()]\n");
            buffer.append("17.底层数据库的次版本号:【"+metadata.getDatabaseMinorVersion()+"】 [getDatabaseMinorVersion()]\n");
            buffer.append("18.检索此数据库产品的名称:【"+metadata.getDatabaseProductName()+"】 [getDatabaseProductName()]\n");
            buffer.append("19.检索此数据库产品的版本号:【"+metadata.getDatabaseProductVersion()+"】 [getDatabaseProductVersion()]\n");
            buffer.append("20.检索此数据库的默认事务隔离级别:【"+metadata.getDefaultTransactionIsolation()+"】 [getDefaultTransactionIsolation()]\n");
            buffer.append("21.检索此 JDBC 驱动程序的主版本号:【"+metadata.getDriverMajorVersion()+"】 [getDriverMajorVersion()]\n");
            buffer.append("22.检索此 JDBC 驱动程序的次版本号:【"+metadata.getDriverMinorVersion()+"】 [getDriverMinorVersion()]\n");
            buffer.append("23.检索此 JDBC 驱动程序的名称:【"+metadata.getDriverName()+"】 [getDriverName()]\n");
            buffer.append("24.检索此 JDBC 驱动程序的 String 形式的版本号:【"+metadata.getDriverVersion()+"】 [getDriverVersion()]\n");
            buffer.append("26.检索可以在不带引号的标识符名称中使用的所有“额外”字符（除了 a-z、A-Z、0-9 和 _ 以外的字符）:【"+metadata.getExtraNameCharacters()+"】 [getExtraNameCharacters()]\n");
            buffer.append("27.检索用于引用 SQL 标识符的字符串:【"+metadata.getIdentifierQuoteString()+"】 [getIdentifierQuoteString()]\n");
            buffer.append("30.检索此驱动程序的主 JDBC 版本号:【"+metadata.getJDBCMajorVersion()+"】 [getJDBCMajorVersion()]\n");
            buffer.append("31.检索此驱动程序的次 JDBC 版本号:【"+metadata.getJDBCMinorVersion()+ "】 [getJDBCMinorVersion()]\n");

            System.out.println(buffer);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            DbUtils.closeQuietly(connection);
        }
    }

}
