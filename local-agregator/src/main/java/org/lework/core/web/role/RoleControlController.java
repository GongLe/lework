package org.lework.core.web.role;

import com.google.common.collect.Lists;
import org.lework.core.common.enumeration.RoleTypes;
import org.lework.core.common.enumeration.Status;
import org.lework.core.persistence.entity.menu.Menu;
import org.lework.core.persistence.entity.organization.Organization;
import org.lework.core.persistence.entity.role.Role;
import org.lework.core.persistence.entity.user.User;
import org.lework.core.service.account.AccountService;
import org.lework.core.service.menu.MenuService;
import org.lework.core.service.organization.OrganizationService;
import org.lework.core.service.role.RoleService;
import org.lework.runner.mapper.JsonMapper;
import org.lework.runner.utils.Collections3;
import org.lework.runner.utils.Strings;
import org.lework.runner.web.AbstractController;
import org.lework.runner.web.CallbackData;
import org.lework.runner.web.NotificationType;
import org.lework.runner.web.datatables.DataTableResult;
import org.lework.runner.web.easyui.TreeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 角色权限控制 Controller
 *
 * @author Gongle
 */
@Controller
@RequestMapping(value = "roleControl")
public class RoleControlController extends AbstractController {
    public static final String DEFAULT_ROLE_ICON ="tree-user-group";
    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private MenuService menuService;
    /**
     * list页面*
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list() {

        return "role/roleControl";
    }

    /**
     * 修改页面
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(@ModelAttribute("entity") Role role,
                         Model model) {
        model.addAttribute("statusList",  Status.values());
        model.addAttribute("typeList", RoleTypes.values());

        model.addAttribute("checkedPermissionIds", null);

        return "role/roleControl-update";
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(@Valid @ModelAttribute("entity") Role entity, BindingResult result,
                       @RequestParam(value = "groupId" ,required = false) String groupId ,
                       HttpServletResponse response) {
        //关联组
        Organization group = organizationService.getOrganization(groupId);
        if (group != null) {
            entity.setGroupId(group.getId());
            entity.setGroupName(group.getName());
        } else { //取消关联
            entity.setGroupId(null);
            entity.setGroupName(null);
        }
        if (result.hasErrors()) {
            callback(response, CallbackData.build("actionCallback", "角色&quot;" + entity.getName() + "&quot保存失败", NotificationType.ERROR));
        }
        try {
            //保存
            roleService.saveRole(entity);
            callback(response, CallbackData.build("actionCallback", "角色&quot;" + entity.getName() + "&quot保存成功", NotificationType.DEFAULT));
        }catch (Exception e){
            e.printStackTrace();
            callback(response, CallbackData.build("actionCallback", "角色&quot;" + entity.getName() + "&quot保存失败", NotificationType.ERROR));
        }

    }

    /**
     * 角色授权主页面 for ajax load
     */
    @RequestMapping(value = "/tabs", method = {RequestMethod.GET, RequestMethod.POST})
    public String tabs(@RequestParam(value = "roleId") String roleId, Model model) {

        model.addAttribute("role", roleService.getRole(roleId));
        return "role/roleControl-tabs";
    }

    /**
     * 角色成员ajax load页面
     */
    @RequestMapping(value = "/member", method = {RequestMethod.GET, RequestMethod.POST})
    public String member(@RequestParam(value = "roleId") String roleId, Model model) {
        model.addAttribute("role", roleService.getRole(roleId));
        return "role/roleControl-member";
    }

    /**添加成员到角色list页面**/
    @RequestMapping(value = "/addMember", method = {RequestMethod.GET })
    public String addMember(@RequestParam(value = "roleId") String roleId, Model model) {
        model.addAttribute("role", roleService.getRole(roleId));

        return "role/roleControl-addMember";
    }
    /**check user**/
    @RequestMapping(value = "/addMember-checkUser", method = {RequestMethod.GET})
    public String checkUser(@RequestParam(value = "roleId") String roleId,
                            @RequestParam(value = "orgId") String orgId,
                            Model model) {
        model.addAttribute("roleId", roleId);
        model.addAttribute("users", roleService.getRoleRelatedUser(orgId, roleId));
        return "role/roleControl-addMember-checkUser";
    }

    /**
     * 添加角色成员
     *
     * @param roleId   角色ID
     * @param userId   用户ID
     * @param response
     */
    @RequestMapping(value = "/createRelateUser", method = RequestMethod.POST)
    public void createRelateUser(@RequestParam(value = "roleId") String roleId,
                                 @RequestParam(value = "userId") String userId,
                                 @RequestParam(value = "userName", required = false) String userName,
                                 HttpServletResponse response) {
        Role role = roleService.getRole(roleId);
        roleService.createRelateUser(role, userId);
        callback(response, CallbackData.build("createRelateCallback", "添加成员&quot;" + userName + " &quot;成功",
                NotificationType.DEFAULT));
    }

    /**
     * 解除角色成员
     *
     * @param roleId   角色ID
     * @param userId   用户ID
     * @param response
     */
    @RequestMapping(value = "/removeRelatedUser", method = RequestMethod.POST)
    public void removeRelatedUser(@RequestParam(value = "roleId") String roleId,
                                  @RequestParam(value = "userId") String userId,
                                  @RequestParam(value = "userName", required = false) String userName,
                                  HttpServletResponse response) {
        Role role = roleService.getRole(roleId);
        roleService.removeRelatedUser(role, userId);
        callback(response, CallbackData.build("removeRelatedCallback", "解除成员&quot;" + userName + " &quot;成功",
                NotificationType.DEFAULT));
    }

    /**
     * 角色模块权限 ajax load页面
     */
    @RequestMapping(value = "/menu", method = {RequestMethod.GET, RequestMethod.POST})
    public String menu(@RequestParam(value = "roleId") String roleId, Model model) {
        List<String > checkedIds = Collections3.extractToList(menuService.getRoleMenus(roleId), "id");
        model.addAttribute("checkedIds", new JsonMapper().toJson(checkedIds));
        model.addAttribute("roleId",roleId );
        return "role/roleControl-menu";
    }
     //TODO save relate menu action

    /**
     * 授权角色菜单给角色.
     *
     * @param roleId         角色ID
     * @param checkedMenuIds 选中的菜单IDs
     * @param response
     */
    @RequestMapping(value = "/saveRelatedMenu", method = RequestMethod.POST)
    public void saveRelatedMenu(@RequestParam(value = "roleId") String roleId,
                                @RequestParam(value = "checkedMenuId", required = false) List<String> checkedMenuIds,
                                HttpServletResponse response) {
        Role role = roleService.getRole(roleId);
        //关联菜单
        if (Collections3.isNotEmpty(checkedMenuIds)) {
            List<Menu> menus = menuService.getMenusByIds(checkedMenuIds);
            role.getMenus().clear();
            role.getMenus().addAll(menus);
        } else {
            role.setMenus(null);
        }
        roleService.saveRole(role);
        callback(response, CallbackData.build("saveRelatedMenuCallback", "授权&quot;\"" + role.getName() + "\" &quot;菜单成功",
                NotificationType.DEFAULT));
    }

    /**
     * 根据角色组ID加载所属角色
     * return easyui tree json result
     */
    @RequestMapping(value = "/getRoleTreeByGroupId", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    List<TreeResult> getRoleTreeByGroupId(@RequestParam(value = "groupId", required = true) String groupId) {


        boolean disable; //高亮状态
        List<Role> entities;
        List<TreeResult> nodeList = Lists.newArrayList();
        entities = roleService.getAllRoleByGroupId(groupId);
        if (!Collections3.isEmpty(entities)) {
            for (Role r : entities) {
                disable = Strings.equals(r.getStatus(), Status.disable.getCode());
                nodeList.add(new TreeResult(r.getId(), r.getName(),  DEFAULT_ROLE_ICON  , Strings.EMPTY));
            }
        }

        return nodeList;
    }

    /**
     * ====================================
     *    获取角色成员 Datatables JSON
     * ====================================
     */
    /**
     * 获取角色关联的用户
     *
     * @param pageable
     * @param roleId   菜单ID
     * @param search   用户名||登录名
     * @return Datatables Json Data
     */
    @RequestMapping(value = "/geRoleRelatedUserJson", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    DataTableResult<User> geRoleRelatedUserJson(@PageableDefault(page = 0, size = 15) Pageable pageable,
                                                @RequestParam(value = "roleId") String roleId,
                                                @RequestParam(value = "search", required = false) String search) {

        Page<User> page = accountService.searchUserPageByRoleId(pageable, roleId, search);
        return DataTableResult.build(page);
    }


    /**
     * Preparable二次部分绑定的效果
     */
    @ModelAttribute("entity")
    public void prepareModel(Model model, @RequestParam(value = "id", required = false) String id) {
        if (Strings.isNotBlank(id)) {
            model.addAttribute("entity", roleService.getRole(id));
        } else {
            model.addAttribute("entity", new Role());
        }
    }

    /**
     * 不自动绑定对象中的roles属性
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("users");
        binder.setDisallowedFields("permissions");
    }


}
