package org.lework.core.service.organization.impl;

import org.apache.commons.lang3.Validate;
import org.lework.core.common.enumeration.OrgTypes;
import org.lework.core.common.enumeration.Status;
import org.lework.core.persistence.dao.organization.OrganizationDao;
import org.lework.core.persistence.entity.organization.Organization;
import org.lework.core.service.organization.OrgTreeGridDTO;
import org.lework.core.service.organization.OrganizationService;
import org.lework.runner.orm.support.SearchFilter;
import org.lework.runner.orm.support.Specifications;
import org.lework.runner.utils.Collections3;
import org.lework.runner.utils.Strings;
import org.lework.runner.web.easyui.TreeResult;
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

/**
 * Organization Service Implement
 * User: Gongle
 * Date: 13-10-22
 */

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {
    private static Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    private OrganizationDao organizationDao;

    @Autowired
    public void setOrganizationDao(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
    }

    @Override
    public List<Organization> getAllOrgByStatus(Status status) {
        Validate.notNull(status);
        return organizationDao.findAllByStatus(status.getCode());
    }

    @Override
    public List<Organization> getAllOrgByType(OrgTypes type) {
        Validate.notNull(type);
        return organizationDao.findAllByType(type.getCode());
    }

    @Override
    public List<Organization> getRootOrgs() {
        return organizationDao.findRootOrgs();
    }

    @Override
    public List<Organization> getChildOrgsByParentId(String parentId) {
        return organizationDao.findChildOrgsByParentId(parentId);
    }

    /**
     * 获取当前节点所有子节点,包含自身
     *
     * @param id 菜单ID
     */
    @Override
    public List<Organization> getSelfAndChildOrgs(String id) {
        List<Organization> ret = new ArrayList<Organization>();
        if (Strings.isBlank(id)) {
            return ret;
        }
        Organization entity = organizationDao.findOne(id);
        ret.add(entity);
        fetchChild(entity, ret);
        return ret;
    }

    @Override
    public boolean validateOrgCode(String id, String code) {
        Organization entity = organizationDao.findByCode(code);
        if (entity == null) {  //code不存在
            return true;
        } else if (Strings.isBlank(id)) {   //新增操作
            return entity == null;
        } else {   //修改操作
            return entity.getId().equals(id);
        }
    }

    @Override
    public Organization getOrganization(String id) {
        if (Strings.isBlank(id)) {
            return null;
        }
        return organizationDao.findOne(id);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return (List<Organization>) organizationDao.findAll();
    }

    @Override
    public List<Organization> getOrganizationsByIds(List<String> ids) {
        if (Collections3.isEmpty(ids)) {
            return new ArrayList<Organization>();
        }
        return (List<Organization>) organizationDao.findAll(ids);
    }

    @Override
    public Organization getOrganizationByCode(String code) {
        return organizationDao.findByCode(code);
    }

    /**
     * 新增操作时,默认序号为{同级节点最大值}+1
     *
     * @param entity
     */
    @Override
    public void saveOrganization(Organization entity) {
        Integer curSortNum;
        if (entity.isNew()) { //新增操作,默认序号为{同级节点最大值}+1
            if (!entity.hasParent()) {  //root node
                curSortNum = organizationDao.findRootMaxSortNum();
            } else {
                curSortNum = organizationDao.findChildMaxSortNum(entity.getParentId());
            }
            entity.setSortNum(curSortNum == null ? 0 : curSortNum + 1);
        }
        organizationDao.save(entity);
    }

    @Override
    public void deleteOrganization(String id) {
        organizationDao.delete(id);
    }

    @Override
    public void deleteOrganization(Organization entity) {
        organizationDao.delete(entity);
    }

    @Override
    public void deleteOrganization(List<Organization> entities) {
        if (Collections3.isEmpty(entities)) {
            return;
        }
        organizationDao.delete(entities);
    }


    @Override
    public Page<Organization> getPageOrganization(Pageable pageable) {
        return organizationDao.findAll(pageable);
    }

    @Override
    public Page<Organization> searchPageOrganization(Pageable pageable, List<SearchFilter> filters) {
        Specification<Organization> spec = Specifications.build(filters, Organization.class);
        return organizationDao.findAll(spec, pageable);
    }

    @Override
    public List<OrgTreeGridDTO> getOrgTreeGrid(List<Organization> ignore) {
        //roots node
        List<Organization> roots = organizationDao.findRoots();
        List<OrgTreeGridDTO> rootsNodes = new ArrayList<OrgTreeGridDTO>();
        OrgTreeGridDTO temp;
        Organization curNode;
        if (Collections3.isEmpty(roots))
            return rootsNodes;
        int size = roots.size();
        for (int i = 0; i < size; i++) {
            curNode = roots.get(i);
            if (Collections3.contain(ignore, curNode))
                continue;
            temp = new OrgTreeGridDTO(curNode);
            temp.setLevelSize(size);
            temp.setLevelIndex(i);
            rootsNodes.add(temp);
            //递归
            fetchChild4TreeGrid(curNode, temp, ignore);
        }

        return rootsNodes;
    }

    @Override
    public List<TreeResult> getOrgTree(List<Organization> ignore) {
        //roots node
        List<Organization> roots = organizationDao.findRoots();
        List<TreeResult> rootNodes = new ArrayList<TreeResult>();
        TreeResult temp;
        if (Collections3.isEmpty(roots))
            return rootNodes;
        for (Organization root : roots) {
            if (Collections3.contain(ignore, root))
                continue;
            temp = convert2TreeNode(root);
            rootNodes.add(temp);
            //递归
            fetchChild4Tree(root, temp, ignore);
        }
        return rootNodes;
    }

    @Override
    public void upSortNum(Organization entity) {
        List<Organization> siblings;
        int curIndex;
        Integer temp;
        Organization pre;

        if (!entity.hasParent()) {  //如果是根节点
            siblings = organizationDao.findRoots();
        } else {  //非根节点时,根据parentId获取同级所有节点
            siblings = organizationDao.findChildOrgsByParentId(entity.getParentId());
        }
        if (Collections3.isNotEmpty(siblings) && siblings.size() > 1) {
            curIndex = siblings.indexOf(entity);
            if (curIndex > 0) {
                pre = siblings.get(curIndex - 1);
                //互换sortNum
                temp = pre.getSortNum();
                pre.setSortNum(entity.getSortNum());
                entity.setSortNum(temp);
                organizationDao.save(pre);
                organizationDao.save(entity);
            }
        }
    }

    @Override
    public void downSortNum(Organization entity) {
        List<Organization> siblings;
        int curIndex;
        Integer temp;
        Organization next;
        if (!entity.hasParent()) {  //如果是根节点
            siblings = organizationDao.findRoots();
        } else {  //非根节点时,根据parentId获取同级所有节点
            siblings = organizationDao.findChildOrgsByParentId(entity.getParentId());
        }
        if (Collections3.isNotEmpty(siblings) && siblings.size() > 1) {
            curIndex = siblings.indexOf(entity);
            if (curIndex < siblings.size() - 1) {
                next = siblings.get(curIndex + 1);
                //互换sortNum
                temp = next.getSortNum();
                next.setSortNum(entity.getSortNum());
                entity.setSortNum(temp);
                organizationDao.save(next);
                organizationDao.save(entity);
            }
        }

    }

    /**
     * 递归查找子节点 for easyui tree
     *
     * @param parent
     * @param parentNode
     */
    private void fetchChild4Tree(Organization parent, TreeResult parentNode, List<Organization> ignore) {
        List<TreeResult> childNodes = new ArrayList<TreeResult>();
        List<Organization> childOrgs;
        TreeResult node;
        if (parent.hasChild()) {
            childOrgs = parent.getChildrenOrganizations();
            for (Organization o : childOrgs) {
                if (Collections3.contain(ignore, o)) {  //忽略节点
                    continue;
                }
                node = convert2TreeNode(o);
                childNodes.add(node);
                if (o.hasChild()) {
                    fetchChild4Tree(o, node, ignore);
                }
            }
        }
        parentNode.getChildren().addAll(childNodes);
    }

    /**
     * 递归查找子节点 for easyui treeGrid
     *
     * @param parent
     * @param parentNode
     */
    private void fetchChild4TreeGrid(Organization parent, OrgTreeGridDTO parentNode, List<Organization> ignore) {
        List<OrgTreeGridDTO> childNodes = new ArrayList<OrgTreeGridDTO>();
        List<Organization> childMenu;
        OrgTreeGridDTO node;
        Organization curNode;
        int size;
        if (parent.hasChild()) {

            childMenu = parent.getChildrenOrganizations();
            size = childMenu.size();
            for (int i = 0; i < size; i++) {
                curNode = childMenu.get(i);
                if (Collections3.contain(ignore, curNode)) {  //忽略节点
                    continue;
                }
                node = new OrgTreeGridDTO(curNode);
                node.setLevelIndex(i);
                node.setLevelSize(size);
                childNodes.add(node);
                if (curNode.hasChild()) {
                    fetchChild4TreeGrid(curNode, node, ignore);
                }
            }

        }
        parentNode.getChildren().addAll(childNodes);
    }

    /**
     * 递归查找子节点到集合
     *
     * @param parent
     * @param collection
     */
    private void fetchChild(Organization parent, List<Organization> collection) {
        List<Organization> childOrg;
        if (parent.hasChild()) {
            childOrg = parent.getChildrenOrganizations();
            collection.addAll(childOrg);
            for (Organization o : childOrg) {
                if (o.hasChild()) {
                    fetchChild(o, collection);
                }
            }
        }
    }

    private TreeResult convert2TreeNode(Organization entity) {
        TreeResult ret = new TreeResult();
        ret.setId(entity.getId());
        ret.setText(entity.getName());
        if (Strings.equals(entity.getStatus(), Status.disable.getCode())) {
            ret.setIconCls("red");
        }
        ret.setState(TreeResult.STATE_OPEN);
        ret.addAttribute("code", entity.getCode());
        return ret;

    }


}
