package org.lework.core.service.account;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.lework.core.common.domain.ShiroUser;
import org.lework.core.common.enumeration.Status;
import org.lework.core.persistence.entity.menu.Menu;
import org.lework.core.persistence.entity.permission.Permission;
import org.lework.core.persistence.entity.role.Role;
import org.lework.core.service.menu.MenuService;
import org.lework.core.service.permission.PermissionService;
import org.lework.core.service.role.RoleService;
import org.lework.runner.utils.Collections3;
import org.lework.runner.utils.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * shiro授权类 抽象出授权类,使得验证{@link JdbcAuthenticationRealm}与授权解耦
 *
 * @author Gongle
 */
public abstract class AuthorizationRealm extends AuthorizingRealm {
    private static Logger logger = LoggerFactory.getLogger(AuthorizationRealm.class);

    protected AccountService accountService;
    private RoleService roleService;
    private MenuService menuService;
    private PermissionService permissionService;

    @PostConstruct
    public void initBeans() {
        System.out.printf("init shiro授权类 抽象出授权类");
    }

    /**
     * 实体中权限值属性名
     */
    public final static String SHIRO_PER_EXPRESSION = "perms";

    /**
     * shiro 角色表达式
     */
    public final static String SHIRO_ROLE_EXPRESSION = "code";

    /**
     * shiro权限值
     */
    private List<String> defaultPermissions = new ArrayList<String>();

    /**
     * shiro角色值
     */
    private List<String> defaultRoles = new ArrayList<String>();

    /**
     * 当用户进行访问链接时的授权方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        ShiroUser user = (ShiroUser) principals.getPrimaryPrincipal();
        //获取用户的菜单
        List<Menu> relatedMenus = menuService.getUserMenus(user.id, Status.enable);
        //获取用户的角色
        List<Role> relatedRoles = roleService.getUserRolesByStatus(user.id, Status.enable);
        //获取用户的权限
        List<Permission> relatedPermissions = permissionService.getUserPermissions(user.id, Status.enable);
        addRoles(info, relatedRoles);
        addPermissions(info, relatedPermissions);
        user.menus = makeMenusAsTree(relatedMenus) ;
        return info;
    }


    /**
     * 将集合中的role字段内容解析后添加到SimpleAuthorizationInfo授权信息中
     *
     * @param info     SimpleAuthorizationInfo
     * @param roleList 组集合
     */
    private void addRoles(final SimpleAuthorizationInfo info, List<Role> roleList) {

        //解析当前用户组中的role
        List<String> temp = Collections3.extractToList(roleList, SHIRO_ROLE_EXPRESSION, true);
        List<String> roles = temp;
        //  List<String> roles = getValue(temp, "roles\\[(.*?)\\]");

        //添加默认的roles到roels
        if (Collections3.isNotEmpty(defaultRoles)) {
            roles.addAll(defaultRoles);
        }

        //将当前用户拥有的roles设置到SimpleAuthorizationInfo中
        info.addRoles(roles);

    }

    /**
     * 将集合中的permission字段内容解析后添加到SimpleAuthorizationInfo授权信息中
     *
     * @param info              SimpleAuthorizationInfo
     * @param authorizationPers 权限集合
     */
    private void addPermissions(final SimpleAuthorizationInfo info, List<Permission> authorizationPers) {
        //解析当前用户资源中的permissions
        List<String> temp = Collections3.extractToList(authorizationPers, "code", true);
        List<String> permissions = getValue(temp, "perms\\[(.*?)\\]");

        //添加默认的permissions到permissions
        if (Collections3.isNotEmpty(defaultPermissions)) {
            permissions.addAll(defaultPermissions);
        }
        //将当前用户拥有的permissions设置到SimpleAuthorizationInfo中
        info.addStringPermissions(permissions);

    }

    /**
     * 并合子类资源到父类中
     */
    private List<Menu> makeMenusAsTree(List<Menu> menus) {
        List<Menu> ret = new ArrayList<Menu>();
        for (Menu m : menus) {
            if (!m.hasParent()) {
                logger.debug("根节点{}",m);
                makeMenuToParent(menus, m);
                ret.add(m);
            }
        }
        return ret;
    }

    /**
     * 遍历list中的数据,如果数据的父类与parent相等，将数据加入到parent的children中
     */
    private void makeMenuToParent(List<Menu> list, Menu parent) {
        if (!parent.hasChild()) {
            return;
        }
        parent.setChildrenMenus(new ArrayList<Menu>());
        for (Menu m : list) {
            if (Strings.equals(m.getParentId(), parent.getId())) {
                logger.debug("二级节点{}",m);
               // m.setChildrenMenus(null);
                parent.getChildrenMenus().add(m);
                makeMenuToParent(list, m);
            }
        }
    }
    /**
     * 通过正则表达式获取字符串集合的值
     *
     * @param obj   字符串集合
     * @param regex 表达式
     * @return List
     */
    private List<String> getValue(List<String> obj, String regex) {

        List<String> result = new ArrayList<String>();

        if (Collections3.isEmpty(obj)) {
            return result;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(StringUtils.join(obj, ","));

        while (matcher.find()) {
            result.add(matcher.group(1));
        }

        return result;
    }

    public List<String> getDefaultRoles() {
        return defaultRoles;
    }

    public void setDefaultRoles(List<String> defaultRoles) {
        this.defaultRoles = defaultRoles;
    }

    public List<String> getDefaultPermissions() {
        return defaultPermissions;
    }

    public void setDefaultPermissions(List<String> defaultPermissions) {
        this.defaultPermissions = defaultPermissions;
    }

    /**
     * =========================*
     */
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
}
