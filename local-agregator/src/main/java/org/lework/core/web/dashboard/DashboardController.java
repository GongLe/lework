package org.lework.core.web.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Dashboard Controller
 */
@Controller
@RequestMapping(value = "/dashboard")
public class DashboardController {

    @RequestMapping(method = RequestMethod.GET)
    public String list() {
        return "dashboard/dashboard";
    }

}
