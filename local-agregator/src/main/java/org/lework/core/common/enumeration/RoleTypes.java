package org.lework.core.common.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * Role Type org.lework.common.enumeration Class
 * User: Gongle
 * Date: 13-11-17
 * <pre>
 * <option value="corporation">集团</option>
 * <option value="region">区域</option>
 * <option value="company">公司</option>
 * <option value="subCompany">子公司</option>
 * <option value="part">部门</option>
 * <option value="subPart">子部门</option>
 * <option value="team">工作组</option>
 * </pre>
 */
public enum RoleTypes {
    system("system", "系统角色"),
    business("business", "业务角色"),
    application("application ", "应用角色") ;
    // 值
    private String code;

    // 名称
    private String name;

    public static final Map<String, RoleTypes> data = new HashMap<String, RoleTypes>();

    static {
        for (RoleTypes c : RoleTypes.values()) {
            data.put(c.getCode(), c);
        }
    }

    public static RoleTypes parse(String code) {
        return data.get(code);
    }

    private RoleTypes(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 获取值
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取名称
     */
    public String getName() {
        return name;
    }
}
