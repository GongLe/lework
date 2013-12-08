package org.lework.core.service.role;

import org.lework.core.persistence.entity.user.User;
import org.lework.runner.mapper.BeanMapper;

import java.util.Date;

/**
 * 用户添加到角色VO
 * User: Gongle
 * Date: 13-12-2
 * Time: 上午10:17
 */
public class User2RoleVO {
    public User2RoleVO() {
    }

    public User2RoleVO(User user) {
        this.id = user.getId();
        this.loginName = user.getLoginName();
        this.niceName = user.getNiceName();
        this.name = user.getName();
        this.email = user.getEmail();
        this.userRegistered = user.getUserRegistered();
        this.description = user.getDescription();
        this.status = user.getStatus();
        this.type = user.getType();
        this.telphone = user.getTelphone();
        this.mobile = user.getMobile();
        this.orgName = user.getOrgName();
    }

    private String id;
    private String loginName;
    private String niceName;
    private String name;
    private String email;
    private Date userRegistered;
    private String description;
    private String status;
    private String type;
    private String telphone; //联系电话 eg:010-6552555
    private String mobile;  //手机号码
    private String orgName; //组织机构名称
    private boolean selected; //是否选中.
    public static User2RoleVO convert(User user) {
        return BeanMapper.map(user, User2RoleVO.class);

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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getUserRegistered() {
        return userRegistered;
    }

    public void setUserRegistered(Date userRegistered) {
        this.userRegistered = userRegistered;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
