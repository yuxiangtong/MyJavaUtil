package com.yutong.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.yutong.util.db.bean.DBBean;
import com.yutong.util.db.enums.EnumDBType;


public class ConnectionUtils {

    public static Connection getOracleConnection(String serverHostName,
        String serverPort, String dataBaseName, String username,
        String password) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        String driverClass = EnumDBType.ORACLE.getDriveClass();

        String url = "jdbc:oracle:thin:@"
                + serverHostName + ":" + serverPort + ":" + dataBaseName;

        DBBean dbBean = new DBBean(driverClass, url, username, password);
        connection = getConnection(dbBean);
        return connection;
    }


    /**
     * 获取DB链接
     * 
     * @param dbBean
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection(DBBean dbBean)
        throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Class.forName(dbBean.getDriverClass());
        connection = DriverManager.getConnection(dbBean.getUrl(),
                dbBean.getUsername(), dbBean.getPassword());
        return connection;
    }

}
