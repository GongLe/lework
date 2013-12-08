package org.lework.core.service.role;

import org.lework.core.common.enumeration.Status;
import org.lework.core.persistence.entity.role.Role;
import org.lework.core.persistence.entity.user.User;
import org.lework.runner.orm.support.SearchFilter;
import org.lework.runner.web.vo.ChosenDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 角色Service层
 * User: Gongle
 * Date: 13-9-9
 */
public interface RoleService {

    public boolean validateRoleCode(String id, String code);

    public Role getRole(String id);

    public List<Role> getAllRole();

    public List<Role> getAllRoleByStatus(Status status);

    public List<Role> getAllRoleByGroupId(String groupId);

    public List<Role> getRolesByIds(List<String> ids);

    /**
     * 获取用户拥有的角色
     *
     * @param user
     * @param status
     * @return
     */
    public List<Role> getUserRolesByStatus(User user, Status status);

    public List<Role> getUserRolesByStatus(String userId, Status status);

    public List<Role> getRoleByIds(List<String> ids);

    public Role getRoleByCode(String code);


    public void saveRole(Role entity);

    public void deleteRole(String id);


    public void deleteRoles(List<Role> entities);

    public void deleteRole(Role entity);

    public Page<Role> getPageRole(Pageable pageable);

    /**
     * 获取一个分页角色信息
     *
     * @param pageable 分页信息
     * @param filters  属性过滤器
     * @return Page<Role>
     * @see org.springframework.data.domain.Page
     * @see org.springframework.data.domain.Pageable
     * @see org.lework.runner.orm.support.SearchFilter
     */
    public Page<Role> searchPageRole(Pageable pageable, List<SearchFilter> filters);
    /**
     * 获取菜单关联的角色.
     *
     * @param pageable
     * @param menuId   菜单ID
     * @param search   过滤条件:name or code
     * @return
     */
    public Page<Role> searchRolePageByMenu(Pageable pageable, String menuId, String search) ;
    /**
     * get jquery chosen group option DTO
     *
     * @param selectedList 已选择的节点集合
     * @param ignoreEmpty  是否隐藏空角色组
     * @return
     */
    public Map<String, List<ChosenDTO>> getRoleGroupOptions(List<Role> selectedList, boolean ignoreEmpty);

    /**
     * get jquery chosen group option DTO
     *
     * @return
     */
    public Map<String, List<ChosenDTO>> getRoleGroupOptions();


    /**
     * 获取角色成员
     * @param orgId 组织ID
     * @param roleId 角色ID
     * @return
     */
    public List<User2RoleVO> getRoleRelatedUser(String orgId, String roleId);

    /**
     * 解除角色成员
     *
     * @param role  角色Entity
     * @param userId 用户ID
     */
    public void removeRelatedUser(Role role, String userId);
    /**
     * 添加角色成员
     *
     * @param role  角色Entity
     * @param userId 用户ID
     */
    public void createRelateUser(Role role, String userId);

}
