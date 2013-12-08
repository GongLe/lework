package org.lework.core.service.permission;

import org.lework.core.common.enumeration.Status;
import org.lework.core.persistence.entity.permission.Permission;
import org.lework.runner.orm.support.SearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 权限Service层
 * User: Gongle
 * Date: 13-9-9
 */
public interface PermissionService {

    public Permission getPermission(String id);

    public List<Permission> getAllPermission();

    public List<Permission> getPermsiiomsByIds(List<String> ids);

    /**
     * 获取用户关联的权限集合
     *
     * @param userId
     * @param status
     * @return
     */
    public List<Permission> getUserPermissions(String userId, Status status);

    public Permission getPermissionByCode(String code);

    public void savePermission(Permission entity);

    public void deletePermission(String id);

    public void deletePermission(Permission entity);

    public void deletePermission(List<Permission> entities);

    public Page<Permission> getPagePermission(Pageable pageable);

    /**
     * 获取一个分页
     *
     * @param pageable 分页信息
     * @param filters  属性过滤器
     * @return Page<Permission>
     * @see org.springframework.data.domain.Page
     * @see org.springframework.data.domain.Pageable
     * @see org.lework.runner.orm.support.SearchFilter
     */
    public Page<Permission> searchPagePermission(Pageable pageable, List<SearchFilter> filters);
}
