package org.lework.runner.web.easyui;

/**
 * Key Value POJO
 * User: Gongle
 * Date: 13-11-8
 */
public class KVResult {
    public KVResult(String name, String value) {
        this.name = name;
        this.value = value;
    }

    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
