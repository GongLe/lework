package org.lework.core.web.developer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 框架功能演示
 */
@Controller
@RequestMapping(value = "developer")
public class DeveloperController {

    @RequestMapping(method = RequestMethod.GET )
    public String developer() {

        return "developer/developer";
    }
    @RequestMapping(method = RequestMethod.GET, value = "/easyui")
    public String easyui() {

        return "developer/easyui";
    }
    @RequestMapping(method = RequestMethod.GET, value = "/validate")
    public String validate() {

        return "developer/validate";
    }
    @RequestMapping(method = RequestMethod.GET, value = "/taglib-form")
    public String taglibForm(Model model) {
        return "developer/taglib-form";
    }
}
