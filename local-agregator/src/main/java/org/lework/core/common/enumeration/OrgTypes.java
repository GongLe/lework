package org.lework.core.common.enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Organization Type org.lework.common.enumeration Class
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
public enum OrgTypes {
    corporation("corporation", "集团"),
    region("region", "区域"),
    company("company", "公司"),
    subCompany("subCompany", "子公司"),
    part("part", "部门"),
    subPart("subPart", "子部门"),
    team("team", "工作组");
    // 值
    private String code;

    // 名称
    private String name;

    public static final Map<String, OrgTypes> data = new HashMap<String, OrgTypes>();

    static {
        for (OrgTypes c : OrgTypes.values()) {
            data.put(c.getCode(), c);
        }
    }

    public static OrgTypes parse(String code) {
        return data.get(code);
    }

    private OrgTypes(String code, String name) {
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
