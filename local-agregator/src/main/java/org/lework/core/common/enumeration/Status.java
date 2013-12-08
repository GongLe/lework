package org.lework.core.common.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Description: 状态枚举类
 * @author Gongle
 * @date Jan 31, 2013 3:19:50 PM
 */
public enum Status {

    /**
     * 启用
     */
    enable("enable", "启用"),
    /**
     * 禁用
     */
    disable("disable", "禁用");

    // 值
    private String  code;

    // 名称
    private String name;

    public static final  Map<String , Status> data = new HashMap<String , Status>();
    static {
        for (Status c : Status.values()) {
            data.put(c.getCode(), c);
        }
    }

    public static Status parse(String code) {
        return data.get(code);
    }

    private Status(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 获取值
     * 
     * @return Integer
     */
    public String  getCode() {
        return code;
    }

    /**
     * 获取名称
     * 
     * @return String
     */
    public String getName() {
        return name;
    }

}
