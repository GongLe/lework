package org.lework.core.common.servlet;

import org.lework.core.common.utils.SubjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 在统计在线人数 HttpSessionListener
 * <p/>
 * User: Gongle
 * Date: 13-11-27
 */
public class OnlineCounterListener extends HttpServlet implements HttpSessionListener {
    private static Logger logger = LoggerFactory.getLogger(OnlineCounterListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
       // logger.info(" {} 创建.Session", SubjectUtils.getUserLoginName());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        //TODO 用户在线统计:登出记录
        logger.info(" {} Session 销毁.", SubjectUtils.getUserLoginName());
    }
}
