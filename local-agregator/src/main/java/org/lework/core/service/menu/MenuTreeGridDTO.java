package org.lework.core.service.menu;

import org.lework.core.persistence.entity.menu.Menu;
import org.lework.runner.utils.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * Menu easyui tree grid DTO
 */
public class MenuTreeGridDTO {

    public MenuTreeGridDTO() {
    }

    public MenuTreeGridDTO(Menu entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.code = entity.getCode();
        this.type = entity.getType();
        this.status = entity.getStatus();
        this.url = entity.getUrl();
        this.icon = entity.getIcon();
        Menu parent = entity.getParentMenu();
        this.parentId = parent != null ? parent.getId() : Strings.EMPTY;
        this.parentCode = parent != null ? parent.getCode() : Strings.EMPTY;
    }
    private  String id ;
    private  String parentId  ;
    private  String parentCode  ;
    private String name;            //菜单名
    private String code;            //代码
    private Integer sort;            //排序
    private String type = "menu";        //类型  menu,url
    private String status;        //状态 默认为有效状态
    private String url;                //URL
    private String icon;                //图标
    private Integer levelIndex ; //同级节点index
    private Integer levelSize ; //同级节点个数
    private List<MenuTreeGridDTO> children = new ArrayList<MenuTreeGridDTO>();
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuTreeGridDTO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTreeGridDTO> children) {
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
}
