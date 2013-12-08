package org.lework.runner.web.vo;

/**
 * jQuery Chosen plugins  DTO
 * User: Gongle
 * Date: 13-11-20
 * Time: 下午9:17
 */
public class ChosenDTO {
    public ChosenDTO(String name, String value, boolean selected) {
        this.name = name;
        this.value = value;
        this.selected = selected;
    }

    private String name;
    private String value;
    private boolean selected;

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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
