package org.lework.core.service.menu;

import org.lework.core.persistence.entity.role.Role;
import org.lework.runner.mapper.BeanMapper;

/**
 * 添加菜单到用户 VO
 * User: Gongle
 * Date: 13-11-24
 */
public class Menu2RoleVO {
    public Menu2RoleVO() {
    }

    public Menu2RoleVO(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.code = role.getCode();
        this.status = role.getStatus();
        this.type = role.getType();
        this.groupName = role.getGroupName();
        this.description = role.getDescription();
        this.sortNum = role.getSortNum();

    }

    public static Menu2RoleVO convert(Role role) {
        return BeanMapper.map(role, Menu2RoleVO.class);
    }

    private String id;
    /**
     * 角色名称*
     */
    private String name;
    /**
     * 角色代码*
     */
    private String code;
    private String status;
    private String type;
    /**
     * 角色所属组*
     */
    private String groupId;
    /**
     * 组名*
     */
    private String groupName;
    private String description;
    private Integer sortNum;
    private boolean selected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
