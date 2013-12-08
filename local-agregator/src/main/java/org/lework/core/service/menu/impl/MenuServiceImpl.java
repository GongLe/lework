package org.lework.core.service.menu.impl;

import org.apache.commons.lang3.Validate;
import org.lework.core.common.enumeration.Status;
import org.lework.core.persistence.dao.menu.MenuDao;
import org.lework.core.persistence.dao.menu.MenuNativeDao;
import org.lework.core.persistence.dao.role.RoleDao;
import org.lework.core.persistence.entity.menu.Menu;
import org.lework.core.persistence.entity.role.Role;
import org.lework.core.service.menu.Menu2RoleVO;
import org.lework.core.service.menu.MenuService;
import org.lework.core.service.menu.MenuTreeGridDTO;
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
 * Menu Service implement
 * User: Gongle
 * Date: 13-10-22
 */

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    private static Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    private MenuDao menuDao;
    private MenuNativeDao menuNativeDao;
    private RoleDao roleDao;
    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
    @Autowired
    public void setMenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
    }
    @Autowired
    public void setMenuNativeDao(MenuNativeDao menuNativeDao) {
        this.menuNativeDao = menuNativeDao;
    }

    @Override
    public void downSortNum(Menu entity) {
        List<Menu> siblings;
        int curIndex;
        Integer temp;
        Menu next;
        if (!entity.hasParent()) {    //如果是根节点
            siblings = menuDao.findRoots();
        } else {  //非根节点时,根据parentId获取同级所有节点
            siblings = menuDao.findChildMenusByParentId(entity.getParentId());
        }
        if (Collections3.isNotEmpty(siblings) && siblings.size() > 1) {
            curIndex = siblings.indexOf(entity);
            if (curIndex < siblings.size() - 1) {
                next = siblings.get(curIndex + 1);
                //互换sortNum
                temp = next.getSortNum();
                next.setSortNum(entity.getSortNum());
                entity.setSortNum(temp);
                menuDao.save(next);
                menuDao.save(entity);
            }
        }

    }

    @Override
    public void upSortNum(Menu entity) {
        List<Menu> siblings;
        int curIndex;
        Integer temp;
        Menu pre;
        //如果是根节点
        if (!entity.hasParent()) {
            siblings = menuDao.findRoots();
        } else {  //非根节点时,根据parentId获取同级所有节点
            siblings = menuDao.findChildMenusByParentId(entity.getParentId());
        }
        if (Collections3.isNotEmpty(siblings) && siblings.size() > 1) {
            curIndex = siblings.indexOf(entity);
            if (curIndex > 0) {
                pre = siblings.get(curIndex - 1);
                //互换sortNum
                temp = pre.getSortNum();
                pre.setSortNum(entity.getSortNum());
                entity.setSortNum(temp);
                menuDao.save(pre);
                menuDao.save(entity);
            }
        }
    }

    @Override
    public boolean validateMenuCode(String id, String code) {
        Menu entity = menuDao.findByCode(code);
        if (entity == null) {  //code不存在
            return true;
        } else if (Strings.isBlank(id)) {   //新增操作
            return entity == null;
        } else {   //修改操作
            return entity.getId().equals(id);
        }
    }

    @Override
    public Menu getMenu(String id) {
        if (Strings.isBlank(id))
            return null;
        return menuDao.findOne(id);
    }

    @Override
    public List<Menu> getMenusByIds(List<String> ids) {
        if (Collections3.isEmpty(ids)) {
            return new ArrayList<Menu>();
        }
        return (List<Menu>) menuDao.findAll(ids);
    }

    @Override
    public List<Menu> getUserMenus(String userId, Status status) {
        List<Menu> ret = menuDao.findUserMenus(userId, status.getCode());
        if (Collections3.isEmpty(ret))
            ret = new ArrayList<Menu>();
        return ret;
    }

    @Override
    public List<Menu> getAllMenus() {
        return (List<Menu>) menuDao.findAll();
    }

    @Override
    public Menu getMenuByCode(String code) {
        return menuDao.findByCode(code);
    }

    @Override
    public List<Menu> getAllMenuByStatus(Status status) {
        Validate.notNull(status);
        return menuDao.findAllByStatus(status.getCode());
    }

    @Override
    public List<Menu> getRootMenus() {
        return menuDao.findRootMenus();
    }

    @Override
    public List<Menu> getChildMenusByParentId(String parentId) {
        return menuDao.findChildMenusByParentId(parentId);
    }

    @Override
    public List<Menu> getRoleMenusByStatus(Role role, Status status) {
        return menuDao.findRoleMenusByStatus(role.getId(), status.getCode());
    }

    @Override
    public List<Menu> getRoleMenus(Role role) {
        return menuDao.findRoleMenus(role.getId());
    }

    @Override
    public List<Menu> getRoleMenus(String roleId) {
        return menuDao.findRoleMenus(roleId);
    }

    @Override
    public void saveMenu(Menu entity) {
        Integer curSortNum;
        if (entity.isNew()) { //新增操作,默认序号为{同级节点最大值}+1
            if (!entity.hasParent()) {  //root node
                curSortNum = menuDao.findRootMaxSortNum();
            } else {
                curSortNum = menuDao.findChildMaxSortNum(entity.getParentId());
            }
            entity.setSortNum(curSortNum == null ? 0 : curSortNum + 1);
        }
        menuDao.save(entity);
    }

    @Override
    public void deleteMenu(String id) {
        menuDao.delete(id);
    }

    @Override
    public void deleteMenu(Menu entity) {
        menuDao.delete(entity);
    }

    @Override
    public void deleteMenus(List<Menu> entities) {
        if (Collections3.isEmpty(entities)) {
            return;
        }
        menuDao.delete(entities);
    }


    @Override
    public Page<Menu> getPageMenu(Pageable pageable) {
        return menuDao.findAll(pageable);
    }

    @Override
    public Page<Menu> searchPageMenu(Pageable pageable, List<SearchFilter> filters) {
        Specification<Menu> spec = Specifications.build(filters, Menu.class);
        return menuDao.findAll(spec, pageable);
    }

    /**
     * 获取角色关联的菜单.
     *
     * @param pageable
     * @param roleId   角色ID
     * @param search   过滤条件:name
     * @return
     */
    @Override
    public Page<Menu> searchMenuPageByRoleId(Pageable pageable, String roleId, String search) {
        return menuNativeDao.findMenuPageByRoleId(pageable, roleId, search);
    }

    @Override
    public List<Menu> getSelfAndChildMenus(String id) {
        List<Menu> ret = new ArrayList<Menu>();
        if (Strings.isBlank(id)) {
            return ret;
        }
        Menu entity = menuDao.findOne(id);
        ret.add(entity);
        fetchChild(entity, ret);
        return ret;
    }

    @Override
    public List<MenuTreeGridDTO> getMenuTreeGrid(List<Menu> ignore) {
        //roots node
        List<Menu> roots = menuDao.findRoots();
        List<MenuTreeGridDTO> rootNodes = new ArrayList<MenuTreeGridDTO>();
        MenuTreeGridDTO temp;
        Menu curNode;
        if (Collections3.isEmpty(roots))
            return rootNodes;
        int size = roots.size();
        for (int i = 0; i < size; i++) {
            curNode = roots.get(i);
            if (Collections3.contain(ignore, curNode)) {
                continue;
            }
            temp = new MenuTreeGridDTO(curNode);
            temp.setLevelSize(size);
            temp.setLevelIndex(i);
            rootNodes.add(temp);
            fetchChild4TreeGrid(curNode, temp, ignore);
        }

        return rootNodes;
    }

    @Override
    public List<TreeResult> getMenuTree(List<Menu> ignore) {
        //roots node
        List<Menu> roots = menuDao.findRoots();
        List<TreeResult> rootNodes = new ArrayList<TreeResult>();
        if (Collections3.isEmpty(roots))
            return rootNodes;
        TreeResult temp;
        for (Menu root : roots) {
            if (Collections3.contain(ignore, root)) {
                continue;
            }
            temp = convert2TreeNode(root);
            rootNodes.add(temp);
            //递归
            fetchChild4Tree(root, temp, ignore);
        }
        return rootNodes;
    }

    /**
     * 递归查找子节点到集合
     *
     * @param parent
     * @param collection
     */
    private void fetchChild(Menu parent, List<Menu> collection) {
        List<Menu> childMenu;
        if (parent.hasChild()) {
            childMenu = parent.getChildrenMenus();
            collection.addAll(childMenu);
            for (Menu m : childMenu) {
                if (m.hasChild()) {
                    //递归
                    fetchChild(m, collection);
                }
            }
        }
    }

    /**
     * 递归查找子节点 for easyui tree
     *
     * @param parent
     * @param parentNode
     */
    private void fetchChild4Tree(Menu parent, TreeResult parentNode, List<Menu> ignore) {
        List<TreeResult> child = new ArrayList<TreeResult>();
        List<Menu> childMenu;
        TreeResult node;
        if (parent.hasChild()) {
            childMenu = parent.getChildrenMenus();
            for (Menu m : childMenu) {
                if (Collections3.contain(ignore, m)) {  //忽略节点
                    continue;
                }
                node = convert2TreeNode(m);
                child.add(node);
                if (m.hasChild()) {
                    //递归
                    fetchChild4Tree(m, node, ignore);
                }
            }
        }
        parentNode.getChildren().addAll(child);
    }

    /**
     * 递归查找子节点 for easyui treeGrid
     *
     * @param parent
     * @param parentNode
     */
    private void fetchChild4TreeGrid(Menu parent, MenuTreeGridDTO parentNode, List<Menu> ignore) {
        List<MenuTreeGridDTO> child = new ArrayList<MenuTreeGridDTO>();
        List<Menu> childMenu;
        MenuTreeGridDTO node;
        Menu curNode;
        int size;
        if (parent.hasChild()) {

            childMenu = parent.getChildrenMenus();
            size = childMenu.size();
            for (int i = 0; i < size; i++) {
                curNode = childMenu.get(i);
                if (Collections3.contain(ignore, curNode)) {  //忽略节点
                    continue;
                }
                node = new MenuTreeGridDTO(curNode);
                node.setLevelIndex(i);
                node.setLevelSize(size);
                child.add(node);
                if (curNode.hasChild()) {
                    fetchChild4TreeGrid(curNode, node, ignore);
                }
            }

        }
        parentNode.getChildren().addAll(child);
    }

    private TreeResult convert2TreeNode(Menu entity) {
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

    /**
     * 菜单关联的角色VO
     * @param roleGroupId 角色组ID
     * @param menuId 菜单ID
     * @return
     */
    @Override
    public List<Menu2RoleVO> getMenuRelatedRole(String roleGroupId, String menuId) {
        List<Menu2RoleVO> ret = new ArrayList<Menu2RoleVO>() ;
        Menu2RoleVO temp ;
        //菜单关联的角色
        List<Role> selecedRoles = roleDao.findMenuRelatedRoles(menuId);
        //当前角色组的角色
        List<Role> groupRoles;
        if (Strings.isEmpty(roleGroupId)) {
            groupRoles = (List) roleDao.findAllRoles();
        } else {
            groupRoles = roleDao.findRolesByGroupId(roleGroupId);
        }

        //转换成VO
        if(Collections3.isNotEmpty(groupRoles)){
            for(Role r : groupRoles){
                temp =new Menu2RoleVO(r);
                //是否已关联
                temp.setSelected(Collections3.contain(selecedRoles,r));
                ret.add(temp);
            }
        }
        return ret;
    }
    /**
     * 解除关联的角色.
     *
     * @param menu  菜单Entity
     * @param roleId 角色ID
     */
    @Override
    public void removeRelatedRole(Menu menu, String roleId) {
        Role role = roleDao.findOne(roleId);
        role.getMenus().remove(menu);
        roleDao.save(role);
    }

    /**
     * 创建关联的角色.
     *
     * @param menu   菜单Entity
     * @param roleId 角色ID
     */
    @Override
    public void createRelateRole(Menu menu, String roleId) {
        Role role = roleDao.findOne(roleId);
        role.getMenus().add(menu);
        roleDao.save(role);
    }
}
