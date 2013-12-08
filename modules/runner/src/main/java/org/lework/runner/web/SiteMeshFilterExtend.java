package org.lework.runner.web;

import com.opensymphony.sitemesh.webapp.SiteMeshFilter;
import org.lework.runner.utils.Strings;

import javax.servlet.*;
import java.io.IOException;

/**
 * 扩充SiteMeshFilter Filter,增加跳过filter参数标识
 *
 * @see #IGNORE_DECORATE_FLAG
 */
public class SiteMeshFilterExtend extends SiteMeshFilter {
    /**
     * 忽略  SiteMeshFilter 参数标识*
     */
    public static String IGNORE_DECORATE_FLAG = "$SiteMesh";

    @Override
    public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain) throws IOException, ServletException {
        if (Strings.isNotBlank(rq.getParameter(IGNORE_DECORATE_FLAG))) {
            chain.doFilter(rq, rs);
            return;
        }
        super.doFilter(rq, rs, chain);
    }


}
