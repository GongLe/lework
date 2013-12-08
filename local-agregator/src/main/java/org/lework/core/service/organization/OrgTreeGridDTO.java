package org.lework.core.service.organization;

import org.lework.core.persistence.entity.organization.Organization;
import org.lework.runner.utils.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * Menu easyui tree grid DTO
 */
public class OrgTreeGridDTO {

    public OrgTreeGridDTO() {
    }

    public OrgTreeGridDTO(Organization entity) {
        this.id = entity.getId();
        this.id2 = entity.getId();
        this.name = entity.getName();
        this.code = entity.getCode();
        this.type = entity.getType();
        this.typeName = entity.getTypeName();
        this.status = entity.getStatus();
        Organization parent = entity.getParentOrganization();
        this.parentId = parent != null ? parent.getId() : Strings.EMPTY;
        this.parentCode = parent != null ? parent.getCode() : Strings.EMPTY;
    }

    private String id;
    private String id2;
    private String parentId;
    private String parentCode;
    private String name;            //菜单名
    private String code;            //代码
    private Integer sort;            //排序
    private String type;        //类型
    private String typeName ;        //类型
    private String status;        //状态 默认为有效状态
    private Integer levelIndex; //同级节点index
    private Integer levelSize; //同级节点个数
    private List<OrgTreeGridDTO> children = new ArrayList<OrgTreeGridDTO>();
    //easyui  prop
    private String iconCls;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public List<OrgTreeGridDTO> getChildren() {
        return children;
    }

    public void setChildren(List<OrgTreeGridDTO> children) {
        this.children = children;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public Integer getLevelIndex() {
        return levelIndex;
    }

    public void setLevelIndex(Integer levelIndex) {
        this.levelIndex = levelIndex;
    }

    public Integer getLevelSize() {
        return levelSize;
    }

    public void setLevelSize(Integer levelSize) {
        this.levelSize = levelSize;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }
}
