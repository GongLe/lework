package org.lework.runner.web;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.lework.runner.mapper.JsonMapper;
import org.lework.runner.utils.Strings;

import java.util.HashMap;
import java.util.Map;

/**
 * 页面回到函数参数POJO Class
 */
public class CallbackData {
    @JsonIgnore
    private static JsonMapper jsonMapper = new JsonMapper();
    private String functionName;
    private Map<String, Object> attributes = new HashMap<String, Object>();
    private NotificationType type;

    @JsonIgnore
    public static CallbackData build(String functionName, String title, String message, NotificationType type) {
        CallbackData ret = new CallbackData();
        ret.setFunctionName(functionName);
        ret.setType(type);
        ret.addAttribute("title", title);
        ret.addAttribute("message", message);
        ret.addAttribute("type", type.toString());
        return ret;
    }

    @JsonIgnore
    public static CallbackData build(String functionName, String message, NotificationType type) {
        CallbackData ret = new CallbackData();
        ret.setFunctionName(functionName);
        ret.addAttribute("message", message);
        ret.addAttribute("type", type.toString());
        return ret;
    }


    /**
     * 增加函调函数属性
     *
     * @param name
     * @param value
     */
    @JsonIgnore
    public void addAttribute(String name, Object value) {
       getAttributes().put(name, value);
    }

    @JsonIgnore
    public String toJson() {
        return jsonMapper.toJson(this);
    }


    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }
}
