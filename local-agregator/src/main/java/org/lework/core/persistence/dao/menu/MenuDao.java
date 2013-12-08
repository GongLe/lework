package org.lework.core.persistence.dao.menu;

import org.lework.core.persistence.entity.menu.Menu;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Menu Dao
 * User: Gongle
 * Date: 13-10-22
 */
public interface MenuDao extends PagingAndSortingRepository<Menu, String>, JpaSpecificationExecutor<Menu> {
    public Menu findByCode(String code);

    public List<Menu> findAllByStatus(String status);

    @Query("from Menu m where m.parentMenu is null order by m.sortNum")
    public List<Menu> findRootMenus();

    @Query("from Menu m where  m.parentMenu.id=?1 order by m.sortNum")
    public List<Menu> findChildMenusByParentId(String parentId);

    @Query("from Menu m where  m.parentMenu is null  order by m.sortNum")
    public List<Menu> findRoots();

    @Query("select max(m.sortNum) from Menu m where  m.parentMenu is null ")
    public Integer findRootMaxSortNum();

    @Query("select max(m.sortNum) from Menu m where  m.parentMenu.id=?1 ")
    public Integer findChildMaxSortNum(String parentId) ;

    /**
     * 获取角色所属的菜单
     * @param roleId
     * @param status
     * @return
     */
    @Query("select m from Role r inner join r.menus m where r.id=?1 and m.status=?2  order by m.sortNum")
    public List<Menu> findRoleMenusByStatus(String roleId, String status);
    /**
     * 获取角色所属的菜单
     * @param roleId
     * @return
     */
    @Query("select m from Role r inner join r.menus m where r.id=?1 order by m.sortNum")
    public List<Menu> findRoleMenus(String roleId);

     @Query("select distinct m from User u inner join u.roles r inner join r.menus m where u.id=?1 and m.status=?2 order by m.sortNum")
    public List<Menu> findUserMenus(String userId, String status);

}
