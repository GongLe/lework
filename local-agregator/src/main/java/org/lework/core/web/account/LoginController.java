package org.lework.core.web.account;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.lework.core.service.account.CaptchaAuthenticationFilter;
import org.lework.runner.utils.CaptchaUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * <p/>
 * 真正登录的POST请求由Shrio Login Filter完成,
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @RequestMapping(method = RequestMethod.GET)
    public String login() {
        return "account/login";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
        return "account/login";
    }


    /**
     * 生成验证码
     *
     * @return byte[]
     * @throws java.io.IOException
     */
    @RequestMapping("/getCaptcha")
    public ResponseEntity<byte[]> getCaptcha(HttpSession session) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_GIF);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String captcha = CaptchaUtils.getGifCaptcha(70, 28, 4, outputStream, 200).toLowerCase();

        session.setAttribute(CaptchaAuthenticationFilter.DEFAULT_CAPTCHA_PARAM, captcha);
        byte[] bs = outputStream.toByteArray();
        outputStream.close();

        return new ResponseEntity<byte[]>(bs, headers, HttpStatus.OK);
    }
}
