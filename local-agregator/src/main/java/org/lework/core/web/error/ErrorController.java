package org.lework.core.web.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Error page Controller
 * User: Gongle
 * Date: 13-12-5
 */
@Controller
@RequestMapping(value = "error")
public class ErrorController {

    @RequestMapping(value = "401", method = RequestMethod.GET)
    public String _401() {

        return "error/401";
    }
    @RequestMapping(value = "404", method = RequestMethod.GET)
    public String _404() {

        return "error/404";
    }

    @RequestMapping(value = "500", method = RequestMethod.GET)
    public String _500() {

        return "error/500";
    }
}
