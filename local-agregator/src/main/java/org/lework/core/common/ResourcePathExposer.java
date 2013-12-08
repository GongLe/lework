package org.lework.core.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * 容器初始化设置的ServletContext级别的变量ctx,src
 *
 * @author Gongle
 */
public class ResourcePathExposer implements ServletContextAware {
    private static Logger logger = LoggerFactory.getLogger(ResourcePathExposer.class);


    public ResourcePathExposer() {
    }

    private ServletContext servletContext;


    /*
     * 'ctx':'${ctx}',
                'src':'${src}',
                'appName': "/<%= request.getServerName() %>:<%= request.getServerPort() %><%= request.getContextPath() %>",
                'appImg': "http://<%= request.getServerName() %>:<%= request.getServerPort() %>${src}/images",
                'appSrc': "http://<%= request.getServerName() %>:<%= request.getServerPort() %>${src}"
     */
    public void init() {
        //配置为本地上下文根
        AppConfigConstant.CTX = getServletContext().getContextPath();
        AppConfigConstant.SRC = getServletContext().getContextPath() + "/static";
        AppConfigConstant.REAL_PATH = getServletContext().getRealPath("");
        getServletContext().setAttribute("src", AppConfigConstant.SRC);
        getServletContext().setAttribute("ctx", AppConfigConstant.CTX);
        logger.info("ctx:{}", AppConfigConstant.CTX);
        logger.info("src:{}", AppConfigConstant.SRC);
        logger.info("RealPath:{}", AppConfigConstant.REAL_PATH);
        logger.warn("AppConfigConstant.REAL_PATH变量在BAE,SAE,GAE环境不可用,不能通过 ServletContext#getRealPath获取");
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }


}
