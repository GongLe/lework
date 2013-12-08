package org.lework.core.web.role;

import com.google.common.collect.Lists;
import org.lework.core.common.enumeration.Status;
import org.lework.core.common.enumeration.RoleTypes;
import org.lework.core.persistence.entity.organization.Organization;
import org.lework.core.persistence.entity.role.Role;
import org.lework.core.service.auditor.OperatingAudit;
import org.lework.core.service.organization.OrganizationService;
import org.lework.core.service.role.RoleService;
import org.lework.runner.orm.support.SearchFilter;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * 角色Controller
 *
 * @author Gongle
 */
@Controller
@RequestMapping(value = "role")
public class RoleController extends AbstractController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private OrganizationService organizationService;

    /**
     * list页面*
     */
    @OperatingAudit(value = "角色管理",function = "查看操作")
    @RequestMapping(method = RequestMethod.GET)
    public String list() {

        return "role/role";
    }

    /**
     * 修改页面
     *
     * @param roleGroupId 角色组ID
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(@ModelAttribute("entity") Role role, Model model,
                         @RequestParam(value = "roleGroupId", required = false) String roleGroupId) {
        model.addAttribute("statusList", Status.values());
        model.addAttribute("typeList", RoleTypes.values());
        model.addAttribute("checkedPermissionIds", null);
        model.addAttribute("roleGroupId", roleGroupId);
        return "role/role-update";
    }

    /**
     * 保存
     */
    @OperatingAudit(value = "角色管理",function = "update操作")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(@Valid @ModelAttribute("entity") Role entity, BindingResult result,
                       @RequestParam(value = "groupId", required = false) String groupId,
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
            callback(response, CallbackData.build("actionCallback", "角色&quot;" + entity.getName() + "&quot;保存失败", NotificationType.ERROR));
        }
        try {
            //保存
            roleService.saveRole(entity);
            callback(response, CallbackData.build("actionCallback", "角色&quot;" + entity.getName() + "&quot;保存成功", NotificationType.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
            callback(response, CallbackData.build("actionCallback", "角色&quot;" + entity.getName() + "&quot;保存失败", NotificationType.ERROR));
        }

    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(@RequestParam(value = "deleteId", required = false) String deleteId,
                       @RequestParam(value = "deleteIds", required = false) String deleteIds,
                       HttpServletResponse response) {

        try {
            //单个删除
            if (Strings.isNotBlank(deleteId)) {
                Role entity = roleService.getRole(deleteId);
                roleService.deleteRole(entity);
                callback(response, CallbackData.build("deleteCallback", "角色&quot;" + entity.getName() + "&quot;删除成功", NotificationType.DEFAULT));
            } else if (Strings.isNotBlank(deleteIds)) {   //多个删除
                String[] ids = Strings.split(deleteIds, ",");
                List<Role> entities = roleService.getRoleByIds(Arrays.asList(ids));
                List<String> names = Collections3.extractToList(entities, "name");
                roleService.deleteRoles(entities);
                callback(response, CallbackData.build("deleteCallback", "角色&quot;" + Strings.join(names, ",") + "&quot;删除失败", NotificationType.DEFAULT));
            }

        } catch (Exception e) {
            e.printStackTrace();
            callback(response, CallbackData.build("deleteCallback", "角色删除失败!" + e.toString(), NotificationType.ERROR));
        }

    }

    /**
     * 查看
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view(@ModelAttribute("entity") Role role, Model model) {
        model.addAttribute("statusList", Status.values());
        return "role/role-view";
    }

    /**======================
     *       ajax json data
     * ======================
     **/
    /**
     * 验证角色代码是否可用
     *
     * @return JSON true || false
     */
    @RequestMapping(value = "/validateRoleCode", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    Boolean validateRoleCode(@RequestParam(value = "roleId", required = false) String id,
                             @RequestParam(value = "code", required = true) String code) {
        return roleService.validateRoleCode(id, code);
    }


    /**
     * datatables  json result*
     */
    @RequestMapping(value = "/getDatatablesJson", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    DataTableResult<Role> getDatatablesJson(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @RequestParam(value = "search", required = false) String search,
            HttpServletRequest request) {

        List<SearchFilter> filters = SearchFilter.buildFromHttpRequest(request);
        if (Strings.isNotBlank(search)) {
            filters.add(new SearchFilter("LIKES_name_OR_code", search));
        }
        Page<Role> page = roleService.searchPageRole(pageable, filters);

        return DataTableResult.build(page);
    }

    /**
     * get Role's easyui tree json result
     *
     * @param status 过滤节点状态
     */
    @RequestMapping(value = "/getTree", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    List<TreeResult> getTree(@RequestParam(value = "checkbox", required = false) String checkbox,
                             @RequestParam(value = "status", required = false) String status) {
        boolean isCheckbox = Strings.equals(checkbox, "true");
        boolean filterStatus = Strings.isNotBlank(status);
        boolean disable;
        List<Role> entities;
        List<TreeResult> nodeList = Lists.newArrayList();
        //TreeResult root = new TreeResult("root","角色",Strings.EMPTY,Strings.EMPTY) ;
        if (filterStatus) {
            entities = roleService.getAllRoleByStatus(Status.valueOf(status));
        } else {
            entities = roleService.getAllRole();
        }
        if (!Collections3.isEmpty(entities)) {
            for (Role r : entities) {
                disable = Strings.equals(r.getStatus(), Status.disable.getCode());
                nodeList.add(new TreeResult(r.getId(), r.getName(), disable ? "red" : Strings.EMPTY, Strings.EMPTY));
            }
        }

        return nodeList;
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
