package com.yutong.util.db.enums;

/**
 * 数据库类型枚举类
 * 
 * @author yuxiangtong
 *
 */
public enum EnumDBType {

    /** oracle */
    ORACLE("oracle", "oracle.jdbc.driver.OracleDriver"),

    /** mysql */
    MYSQL("mysql", "com.mysql.cj.jdbc.Driver");

    /** 名称 */
    private String name;

    /** 驱动名称 */
    private String driveClass;


    /**
     * 私有构造方法
     * 
     * @param name
     *        名称
     * @param driveClassName
     *        驱动类名称
     * 
     */
    private EnumDBType(String name, String driveClass) {
        this.name = name;
        this.driveClass = driveClass;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getDriveClass() {
        return driveClass;
    }


    public void setDriveClass(String driveClass) {
        this.driveClass = driveClass;
    }

}
