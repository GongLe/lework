package org.lework.core.service.organization;

import org.lework.core.common.enumeration.OrgTypes;
import org.lework.core.common.enumeration.Status;
import org.lework.core.persistence.entity.organization.Organization;
import org.lework.runner.orm.support.SearchFilter;
import org.lework.runner.web.easyui.TreeResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Organization Service 层
 * User: Gongle
 * Date: 13-10-22
 */
public interface OrganizationService {

    public List<Organization> getAllOrgByStatus(Status status);

    public List<Organization> getAllOrgByType(OrgTypes type);

    public List<Organization> getRootOrgs();

    public List<Organization> getChildOrgsByParentId(String parentId);

    /**
     * 获取当前节点所有子节点,包含自身
     *
     * @param id 菜单ID
     * @return
     */
    public List<Organization> getSelfAndChildOrgs(String id);

    /**
     * 验证code值是否有效.
     *
     * @param id
     * @param code 组织code
     */
    public boolean validateOrgCode(String id, String code);

    public Organization getOrganization(String id);

    public List<Organization> getAllOrganizations();

    public List<Organization> getOrganizationsByIds(List<String> ids);

    public Organization getOrganizationByCode(String code);

    /**
     * 新增操作时,默认序号为{同级节点最大值}+1
     * @param entity
     */
    public void saveOrganization(Organization entity);

    public void deleteOrganization(String id);

    public void deleteOrganization(Organization entity);

    public void deleteOrganization(List<Organization> entities);


    public Page<Organization> getPageOrganization(Pageable pageable);


    /**
     * 获取一个分页
     *
     * @param pageable 分页信息
     * @param filters  属性过滤器
     * @return Page<Organization>
     * @see org.springframework.data.domain.Page
     * @see org.springframework.data.domain.Pageable
     * @see org.lework.runner.orm.support.SearchFilter
     */
    public Page<Organization> searchPageOrganization(Pageable pageable, List<SearchFilter> filters);

    /**
     * get easyui tree grid json data
     *
     * @param ignore 忽略的节点集合
     * @return
     */
    public List<OrgTreeGridDTO> getOrgTreeGrid(List<Organization> ignore);

    /**
     * get easyui tree json data
     *
     * @param ignore 忽略的节点
     * @return
     */
    public List<TreeResult> getOrgTree(List<Organization> ignore);

    /**
     * 同级兄弟节点 上移排序
     *
     * @param entity
     */
    public void upSortNum(Organization entity);

    /**
     * 同级兄弟节点,下移序号
     *
     * @param entity
     */
    public void downSortNum(Organization entity);

}
