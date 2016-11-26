package com.yutong.util.db.bean;

import java.util.LinkedHashMap;
import java.util.Map;


public class DBBean {

    /** 驱动类 */
    private String driverClass;

    /** url */
    private String url;

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    private Map<String, Object> parameterMap =
            new LinkedHashMap<String, Object>();


    public DBBean(String driverClass, String url, String username,
        String password) {
        this.driverClass = driverClass;
        this.url = url;
        this.username = username;
        this.password = password;

    }


    public void put(String key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("key is null.");
        }
        parameterMap.put(key, value);
    }


    /**
     * 返回当前类中属性后的Map参数对象
     * <p>
     * waraning:当前类属性key,会覆盖自定义同名key
     * </p>
     * 
     * @return
     */
    public Map<String, Object> getParameterMap() {
        parameterMap.put("driverClass", driverClass);
        parameterMap.put("url", url);
        parameterMap.put("username", username);
        parameterMap.put("password", password);
        return parameterMap;
    }


    public String getDriverClass() {
        return driverClass;
    }


    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

}
