package org.lework.runner.web;

import org.apache.commons.lang3.StringUtils;
import org.lework.runner.mapper.JsonMapper;
import org.lework.runner.utils.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring mvc Abstract Controller
 */
public abstract class AbstractController {

    protected static final String DEFAULT_TITLE = "提示消息";

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**=============================
     *      向request作用域写入通知信息.
     * =============================
     */
    /**
     * 提示消息
     *
     * @param model   参数存放的model对象
     * @param message 消息内容
     */
    protected void prompt(Model model, String message) {
        prompt(model, null, message, NotificationType.INFO);
    }

    /**
     * 提示消息
     *
     * @param redirectAttributes 参数存放的RedirectAttributes对象
     * @param message            消息内容
     */
    protected void prompt(RedirectAttributes redirectAttributes, String message) {
        prompt(redirectAttributes, null, message, NotificationType.INFO);
    }

    /**
     * 提示消息
     *
     * @param model   参数存放的model对象
     * @param title   消息提示框标题
     * @param message 消息内容
     * @param type    通知类型
     */
    protected void prompt(Model model, String title, String message, NotificationType type) {
        model.addAttribute("$title", StringUtils.isEmpty(title) ? DEFAULT_TITLE : title);
        model.addAttribute("$message", message);
        model.addAttribute("$type", type == null ? NotificationType.INFO : type.toString());
    }

    /**
     * 提示消息
     *
     * @param redirectAttributes 参数存放的RedirectAttributes对象
     * @param title              消息提示框标题
     * @param message            消息内容
     * @param type               通知类型
     */
    protected void prompt(RedirectAttributes redirectAttributes, String title, String message, NotificationType type) {
        redirectAttributes.addFlashAttribute("$title", StringUtils.isEmpty(title) ? DEFAULT_TITLE : title);
        redirectAttributes.addFlashAttribute("$message", message);
        redirectAttributes.addFlashAttribute("$type", type == null ? NotificationType.INFO : type.toString());
    }

    /**=============================
     *          页面回调
     * =============================
     */

    /**
     * 页面提交到iframe,返回javascript函数,调用父页面回调函数"{functionName}"
     * 输出 : <script> self.parent.{functionName}.call({jsonObject})</script>
     *
     * @param response
     * @param callbackData 回调函数参数POJO
     */
    protected void callback(HttpServletResponse response, CallbackData callbackData) {
        try {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            Writer out = response.getWriter();
            out.flush();
            out.write("<script> \n ");
            out.write("var actionParam = " + callbackData.toJson() + " ; \n");
            out.write("self.parent." + callbackData.getFunctionName() + " && self.parent." + callbackData.getFunctionName() + "(actionParam) ; \n");
            out.write("</script>");
        } catch (IOException e) {
            logger.error("json转换失败");
            e.printStackTrace();
        }

    }
}
