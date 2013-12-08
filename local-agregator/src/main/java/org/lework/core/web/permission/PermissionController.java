package org.lework.core.web.permission;

import org.lework.core.common.enumeration.Status;
import org.lework.core.persistence.entity.permission.Permission;
import org.lework.core.persistence.entity.role.Role;
import org.lework.core.service.permission.PermissionService;
import org.lework.core.service.role.RoleService;
import org.lework.runner.utils.Collections3;
import org.lework.runner.utils.Strings;
import org.lework.runner.web.AbstractController;
import org.lework.runner.web.CallbackData;
import org.lework.runner.web.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * 角色权限Controller
 * User: Gongle
 * Date: 13-12-3
 */
@Controller
@RequestMapping(value = "permission")
public class PermissionController extends AbstractController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    /**
     * list页面*
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list() {

        return "permission/permission";
    }

    /**
     * 修改页面
     *
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(@ModelAttribute("entity") Permission permission , Model model ) {
        model.addAttribute("statusList", Status.values());

        return "permission/permission-update";
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(@Valid @ModelAttribute("entity") Role entity, BindingResult result,
                       HttpServletResponse response) {
        //关联角色

        if (result.hasErrors()) {
            callback(response, CallbackData.build("actionCallback", "权限&quot;" + entity.getName() + "&quot;保存失败", NotificationType.ERROR));
        }
        try {
            //保存

            callback(response, CallbackData.build("actionCallback", "权限&quot;" + entity.getName() + "&quot;保存成功", NotificationType.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
            callback(response, CallbackData.build("actionCallback", "权限&quot;" + entity.getName() + "&quot;保存失败", NotificationType.ERROR));
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
                Permission entity = permissionService.getPermission(deleteId);
                 permissionService.deletePermission(entity);
                callback(response, CallbackData.build("deleteCallback", "权限&quot;" + entity.getName() + "&quot;删除成功", NotificationType.DEFAULT));
            } else if (Strings.isNotBlank(deleteIds)) {   //多个删除
                String[] ids = Strings.split(deleteIds, ",");

                List<Permission> entities = permissionService.getPermsiiomsByIds(  Arrays.asList(ids));
                List<String> names = Collections3.extractToList(entities, "name");
                permissionService.deletePermission(entities);
                callback(response, CallbackData.build("deleteCallback", "权限&quot;" + Strings.join(names, ",") + "&quot;删除失败", NotificationType.DEFAULT));
            }

        } catch (Exception e) {
            e.printStackTrace();
            callback(response, CallbackData.build("deleteCallback", "权限删除失败!" + e.toString(), NotificationType.ERROR));
        }

    }

    /**
     * 查看
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view(@ModelAttribute("entity") Permission entity, Model model) {
        model.addAttribute("statusList", Status.values());
        return "permission/permission-view";
    }


    /**
     * Preparable二次部分绑定的效果
     */
    @ModelAttribute("entity")
    public void prepareModel(Model model, @RequestParam(value = "id", required = false) String id) {
        if (Strings.isNotBlank(id)) {
            model.addAttribute("entity", permissionService.getPermission(id));
        } else {
            model.addAttribute("entity", new Permission());
        }
    }

    /**
     * 不自动绑定对象中的属性
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
      //  binder.setDisallowedFields("users");
    }
}
