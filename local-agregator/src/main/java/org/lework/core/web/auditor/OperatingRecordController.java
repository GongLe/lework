package org.lework.core.web.auditor;

import org.lework.core.persistence.entity.auditor.OperatingRecord;
import org.lework.core.persistence.entity.role.Role;
import org.lework.core.service.auditor.OperatingRecordManager;
import org.lework.runner.orm.support.SearchFilter;
import org.lework.runner.utils.Strings;
import org.lework.runner.web.datatables.DataTableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 系统操作记录 web Controller
 * User: Gongle
 * Date: 13-12-7
 */
@Controller
@RequestMapping(value = "operatingRecord")
public class OperatingRecordController {
    @Autowired
    private OperatingRecordManager operatingRecordManager;

    /**
     * list页面*
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list() {

        return "auditor/operatingRecord";
    }

    /**
     * 详情页面
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String update(@ModelAttribute("entity") OperatingRecord entity, Model model) {

        return "auditor/operatingRecord-view";
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
            filters.add(new SearchFilter("LIKES_username", search));
        }
        Page<OperatingRecord> page = operatingRecordManager.searchPageOperatingRecord(pageable, filters);

        return DataTableResult.build(page);
    }

    /**
     * Preparable二次部分绑定的效果
     */
    @ModelAttribute("entity")
    public void prepareModel(Model model, @RequestParam(value = "id", required = false) String id) {
        if (Strings.isNotBlank(id)) {
            model.addAttribute("entity", operatingRecordManager.getOperatingRecord(id));
        } else {
            model.addAttribute("entity", new OperatingRecord());
        }
    }

}
