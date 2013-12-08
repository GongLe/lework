package org.lework.core.service.role.impl;


import com.google.common.collect.Maps;
import org.apache.commons.lang3.Validate;
import org.lework.core.common.enumeration.Status;
import org.lework.core.persistence.dao.organization.OrganizationDao;
import org.lework.core.persistence.dao.role.RoleDao;
import org.lework.core.persistence.dao.role.RoleNativeDao;
import org.lework.core.persistence.dao.user.UserDao;
import org.lework.core.persistence.entity.organization.Organization;
import org.lework.core.persistence.entity.role.Role;
import org.lework.core.persistence.entity.user.User;
import org.lework.core.service.role.RoleService;
import org.lework.core.service.role.User2RoleVO;
import org.lework.runner.orm.support.SearchFilter;
import org.lework.runner.orm.support.Specifications;
import org.lework.runner.utils.Collections3;
import org.lework.runner.utils.Strings;
import org.lework.runner.web.vo.ChosenDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Role Service implement
 *
 * @author Gongle
 */

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    private RoleDao roleDao;
    private RoleNativeDao roleNativeDao;
    private UserDao userDao ;
    private OrganizationDao organizationDao;
    @Autowired
    public void setRoleNativeDao(RoleNativeDao roleNativeDao) {
        this.roleNativeDao = roleNativeDao;
    }

    @Autowired
    public void setOrganizationDao(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
    }
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> getUserRolesByStatus(User user, Status status) {
        if (user == null) {
            return new ArrayList<Role>();
        }
        return roleDao.findUserRolesByStatus(user.getId(), status.getCode());
    }

    @Override
    public List<Role> getUserRolesByStatus(String userId, Status status) {
        return roleDao.findUserRolesByStatus(userId,status.getCode()) ;
    }

    @Override
    public List<Role> getAllRoleByStatus(Status status) {
        Validate.notNull(status);
        return roleDao.findAllByStatus(status.getCode());
    }

    @Override
    public List<Role> getAllRoleByGroupId(String groupId) {
        return roleDao.findRolesByGroupId(groupId);
    }

    @Override
    public List<Role> getRolesByIds(List<String> ids) {
        if (Collections3.isEmpty(ids)) {
            return new ArrayList<Role>();
        }
        return (List<Role>) roleDao.findAll(ids);
    }

    /**
     * @param id   角色主键
     * @param code 角色代码
     * @return true:代码值可用,false:代码值不可用
     */
    @Override
    public boolean validateRoleCode(String id, String code) {
        Role entity = roleDao.findByCode(code);
        if (entity == null) {  //code不存在
            return true;
        } else if (Strings.isBlank(id)) {   //新增操作
            return entity == null;
        } else {   //修改操作
            return entity.getId().equals(id);
        }
    }

    @Override
    public Page<Role> searchPageRole(Pageable pageable, List<SearchFilter> filters) {

        Specification<Role> spec = Specifications.build(filters, Role.class);
        return roleDao.findAll(spec, pageable);
    }

    @Override
    public Page<Role> searchRolePageByMenu(Pageable pageable, String menuId, String search) {
         return  roleNativeDao.findRolePageByMenuId(pageable, menuId, search) ;
    }

    @Override
    public Page<Role> getPageRole(Pageable pageable) {
        return roleDao.findAll(pageable);
    }

    @Override
    public void deleteRole(Role entity) {
        roleDao.delete(entity);
    }

    @Override
    public void deleteRole(String id) {
        roleDao.delete(id);
    }

    @Override
    public void deleteRoles(  List<Role> entities) {
        if(Collections3.isEmpty(entities))
            return;
        roleDao.delete(entities);
    }

    @Override
    public void saveRole(Role entity) {
        roleDao.save(entity);
    }

    @Override
    public List<Role> getRoleByIds(List<String> ids) {
        if (Collections3.isEmpty(ids)) {
            return new ArrayList<Role>();
        }
        return (List<Role>) roleDao.findAll(ids);
    }

    @Override
    public Role getRoleByCode(String code) {
        return roleDao.findByCode(code);
    }

    @Override
    public List<Role> getAllRole() {
        return (List<Role>) roleDao.findAll();
    }

    @Override
    public Role getRole(String id) {
        return roleDao.findOne(id);

    }

    /**
     * query chosen group option DTO
     * @param selectedList 已选择的节点集合
     * @param ignoreEmpty  是否隐藏空角色组
     * @return
     */
    @Override
    public Map<String, List<ChosenDTO>> getRoleGroupOptions(List<Role> selectedList, boolean ignoreEmpty) {
        Map<String, List<ChosenDTO>> ret = Maps.newHashMap();
        List<ChosenDTO> options = new ArrayList<ChosenDTO>();
        //获取所有分组,
        List<Organization> roleGroups = organizationDao.findAllByStatus(Status.enable.getCode());
        if (Collections3.isNotEmpty(roleGroups)) {
            //循环查找所属角色
            for (Organization group : roleGroups) {
                List<Role> member = roleDao.findRolesByGroupId(group.getId());
                if (Collections3.isNotEmpty(member)) {
                    for (Role m : member) {
                        options.add(new ChosenDTO(m.getName(), m.getId(),Collections3.contain(selectedList, m)));
                    }
                } else if (ignoreEmpty) {
                    continue;
                }
                //key format : {name}-({code})
                ret.put(String.format("%s-(%s)", group.getName(), group.getCode()), options);
                options = new ArrayList<ChosenDTO>();
            }
        }
        return ret;
    }
    @Override
    public Map<String, List<ChosenDTO>> getRoleGroupOptions() {
        return getRoleGroupOptions(null, false);
    }
    @Override
    public void createRelateUser(Role role, String userId) {
        User user = userDao.findOne(userId);
        user.getRoles().add(role);
        userDao.save(user);
    }

    @Override
    public void removeRelatedUser(Role role, String userId) {
        User user = userDao.findOne(userId);
        user.getRoles().remove(role);
        userDao.save(user);
    }

    /**
     * 获取角色成员
     *
     * @param orgId  组织ID
     * @param roleId 角色ID
     * @return
     */
    @Override
    public List<User2RoleVO> getRoleRelatedUser(String orgId, String roleId) {
        List<User2RoleVO> ret = new ArrayList<User2RoleVO>();
        User2RoleVO temp;
        //当前组织下的人员
        List<User> orgUsers = userDao.findOrgRelatedUsers(orgId);
        //当前角色关联的用户
        List<User> roleUsers = userDao.findRoleRelatedUsers(roleId);
        //转换成VO
        if (Collections3.isNotEmpty(orgUsers)) {
            for (User u : orgUsers) {
                temp = User2RoleVO.convert(u);
                //是否已关联
                temp.setSelected(Collections3.contain(roleUsers, u));
                ret.add(temp);
            }
        }
        return ret;
    }
}
